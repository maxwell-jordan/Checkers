package Checkers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckerBoard extends Application {

    public static final int TILE_SIZE = 100;    //universal size determinant for the game. Change this number everything scales up or down
    public static final int WIDTH = 8;          //board width; 8 for standard checkers
    public static final int HEIGHT = 8;         //board height; 8 for standard checkers

    private Tile[][] board = new Tile[WIDTH][HEIGHT];       //the game board as represented as a 2d array of tile objects

    private Group tileGroup = new Group();  //keeps track of rendered tiles i.e. the board
    private Group pieceGroup = new Group(); //keeps track of the rendered game pieces in play

    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);
        for(int y=0; y<HEIGHT ; y++){
            for(int x = 0; x < WIDTH; x++){
                Tile tile = new Tile((x+y)%2==0, x, y); //the first argument checks whether the tile is light or dark
                board[x][y] = tile;     //populates the board with tiles

                tileGroup.getChildren().add(tile);

                Piece piece = null; //piece is null by default

                if(y<=2 && (x+y)%2!=0){
                    piece = makePiece(PieceType.RED, x, y); //populates the top two rows with red pieces
                }

                if(y>=5 && (x+y)%2!=0){
                    piece = makePiece(PieceType.BLACK, x, y); //populates the bottom two rows with black pieces
                }

                if(piece!=null) {
                    tile.setPiece(piece);   //changes the tile object to include the piece
                    pieceGroup.getChildren().add(piece);
                }
            }
        }
        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY){
        if(board[newX][newY].hasPiece() || (newX + newY) % 2 ==0) { //checks if the target tile is either a light tile or already has a piece
            return new MoveResult(MoveType.NONE); //this results in no movement
        }
        int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());
                                            //get the original x and y coordinates so that the piece can return if the move is invalid

        if(Math.abs(newX-x0) == 1 && newY - y0 == piece.getType().moveDir){
            return new MoveResult(MoveType.NORMAL);
        } else if(Math.abs(newX-x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if(board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()){
                return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());         //these lines compare the original coordinates to the target
            }                                                                   //if the target is empty and there is an opposing piece in between the original and target it is a kill/jump move
        }
        return new MoveResult(MoveType.NONE);   //if the move doesn't satisfy any of the restraints, it returns to the original position
    }

    private int toBoard(double pixel){
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    } //converts pixel location to board coordinate location

    @Override
    public void start(Stage primaryStage) { //javaFx start sequence
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checkers");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(PieceType type, int x, int y){
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX()); //gets the pixel location that the piece was dragged to
            int newY = toBoard(piece.getLayoutY()); //converts to the board location that the piece was dragged to

            MoveResult result = tryMove(piece, newX, newY); //using the coordinates and the piece type, check what kind of move can be made

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()){
                case NONE:
                    piece.abortMove(); //returns piece to original location
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece); //moves piece one tile in the desired diagonal direction
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);//move the piece two spaces and remove the opposing jumped piece

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;

            }
        });
        return piece;
    }

    public static void main(String[] args) {
        launch(args);
    }
}