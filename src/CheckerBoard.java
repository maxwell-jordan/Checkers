package src;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class CheckerBoard extends Application {

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private BorderPane layout;
    private Scene scene;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();


    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for(int y=0; y<HEIGHT ; y++){
            for(int x = 0; x < WIDTH; x++){
                Tile tile = new Tile((x+y)%2==0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if(y<=2 && (x+y)%2!=0){
                    piece = makePiece(PieceType.RED, x, y);
                }

                if(y>=5 && (x+y)%2!=0){
                    piece = makePiece(PieceType.BLACK, x, y);
                }

                if(piece!=null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY){
        if(board[newX][newY].hasPiece() || (newX + newY) % 2 ==0) {
            return new MoveResult(MoveType.NONE);
        }
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        if(Math.abs(newX-x0) == 1 && newY - y0 == piece.getType().moveDir){
            return new MoveResult(MoveType.NORMAL);
        } else if(Math.abs(newX-x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if(board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()){
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage window) throws Exception {

        layout = new BorderPane();
        scene = new Scene (layout, 100, 100);

        Button btn1 = new Button();
        btn1.setOnAction(e -> {
            Scene board = new Scene(createContent()); //This is where the Easy button is clicked and opens up the board for Easy mode.
            window.setScene(board);
            window.show();
        });
        btn1.setText("Easy");
        btn1.setMaxSize(100, 90);
        layout.setTop(btn1);


        Button btn2 = new Button();
        btn2.setOnAction (e -> {
            Scene board2 = new Scene (createContent()); //This is where the Hard button is clicked and opens up the board for Hard mode.
            window.setScene(board2);
            window.show();
        });
        btn2.setText("Hard");
        btn2.setMaxSize(100, 90);
        layout.setBottom(btn2);

        window.setTitle("Checkers");
        window.setScene(scene);
        window.show();
    }

    private Piece makePiece(PieceType type, int x, int y){
        Piece piece = new Piece(type, x, y);

        if (type.equals(PieceType.BLACK)) {
            piece.setOnMouseReleased(e -> {
                int newX = toBoard(piece.getLayoutX());
                int newY = toBoard(piece.getLayoutY());

                MoveResult result = tryMove(piece, newX, newY);

                int x0 = toBoard(piece.getOldX());
                int y0 = toBoard(piece.getOldY());

                switch (result.getType()) {
                    case NONE:
                        piece.abortMove();
                        break;
                    case NORMAL:
                        piece.move(newX, newY);
                        board[x0][y0].setPiece(null);
                        board[newX][newY].setPiece(piece);
                        break;
                    case KILL:
                        piece.move(newX, newY);
                        board[x0][y0].setPiece(null);
                        board[newX][newY].setPiece(piece);

                        Piece otherPiece = result.getPiece();
                        board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                        pieceGroup.getChildren().remove(otherPiece);
                        break;

                }
                MinMaxStart();

            });
        }
        if(type.equals(PieceType.RED)){
            piece.setOnMouseReleased(e->{
                piece.abortMove();
            });

        }
        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Starts the src.MinMax Algorithm
    public  void MinMaxStart(){
        //Creates the src.MinMax object and runs it to find the best move
        MinMax M=new MinMax();
        M.Move(board);
        int newX=M.GetNewX();
        int newY=M.GetNewY();
        int x0=M.GetOldX();
        int y0=M.GetOldY();

        //Selects the piece based on the coordinates
        Piece piece=board[x0][y0].getPiece();

        //Tries the move to check if everything was calculated properly
        MoveResult result = tryMove(piece, newX, newY);

        //Moves the pieces based on the move type
        if(M.getType().equals(MoveType.KILL)){
            piece.move(newX, newY);
            board[x0][y0].setPiece(null);
            board[newX][newY].setPiece(piece);

            Piece otherPiece = result.getPiece();
            board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
            pieceGroup.getChildren().remove(otherPiece);
        }
        else if(M.getType().equals(MoveType.NORMAL)){
            piece.move(newX, newY);
            board[x0][y0].setPiece(null);
            board[newX][newY].setPiece(piece);
        }

    }
    //Starts the src.MinMax Algorithm
    public void RandomStart(){
        //Creates the src.MinMax object and runs it to find the best move
        RandomMove R=new RandomMove();
        R.Move(board);
        int newX=R.GetNewX();
        int newY=R.GetNewY();
        int x0=R.GetOldX();
        int y0=R.GetOldY();

        //Selects the piece based on the coordinates
        Piece piece=board[x0][y0].getPiece();

        //Tries the move to check if everything was calculated properly
        MoveResult result = tryMove(piece, newX, newY);

        //Moves the pieces based on the move type
        if(R.getType().equals(MoveType.KILL)){
            piece.move(newX, newY);
            board[x0][y0].setPiece(null);
            board[newX][newY].setPiece(piece);

            Piece otherPiece = result.getPiece();
            board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
            pieceGroup.getChildren().remove(otherPiece);
        }
        else if(R.getType().equals(MoveType.NORMAL)){
            piece.move(newX, newY);
            board[x0][y0].setPiece(null);
            board[newX][newY].setPiece(piece);
        }

    }
}