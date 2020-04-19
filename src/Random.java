package src;

public class Random {
    //Array that holds the board and represents User pieces with a 2 and AI with a 1
    private int[][] Pieces=new int[8][8];
    //Array that holds the Possible Movies for the AI along with and iterator to track how many moves there are
    private MinMaxMove[] AIMoves=new MinMaxMove[100];
    private int AIMoveCount;
    //Array that holds the Possible Movies for the user along with and iterator to track how many moves there are
    private MinMaxMove[] UserMoves=new MinMaxMove[100];
    private int UserMoveCount;
    //The New Coordinates that will be moved to
    private int NewX;
    private int NewY;
    public int GetNewX(){return NewX;}
    public int GetNewY(){return NewY;}
    //The Original Coordinates of the src.Piece to be moved
    private int OldX;
    private int OldY;
    public int GetOldX(){return OldX;}
    public int GetOldY(){return OldY;}
    //Stores the type of move that will be done (Normal or Kill)
    private MoveType type;
    public MoveType getType(){return type;}

    //Starts the Move
    public void Move(Tile[][] board){
        //Creates the Pieces Array from the Board from the src.CheckerBoard class
        GetBoardState(board);
        //Populates the AI Moves array with moves available for the AI
        PossibleAIMoves();
        //Gets the Max Values for all AIMoves
        for(int i=0;i<AIMoveCount;i++){
            MinMax(AIMoves[i]);
        }
        int Max=-1;
        int MaxMove=-1;
        //Chooses the Maximum Max value from all possible moves
        for(int i=0;i<AIMoveCount;i++){
            if(Max<AIMoves[i].getMax()){
                Max=AIMoves[i].getMax();
                MaxMove=i;
            }
        }
        //Sets the Final Coordinate Values to be used in the Main method
        NewX=AIMoves[MaxMove].GetNewX();
        NewY=AIMoves[MaxMove].GetNewY();
        OldX=AIMoves[MaxMove].GetOldX();
        OldY=AIMoves[MaxMove].GetOldY();
        if(AIMoves[MaxMove].getType().equals(MoveType.KILL)){
            type=MoveType.KILL;
        }
        else {
            type = MoveType.NORMAL;
        }
    }
    //Creates the Pieces Array from the Board from the src.CheckerBoard class
    public void GetBoardState(Tile[][]board){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j].hasPiece()) {
                    Piece P = board[i][j].getPiece();
                    if (P.getType().equals(PieceType.RED)) {
                        Pieces[i][j] = 1;
                    }
                    else if(P.getType().equals(PieceType.BLACK)){
                        Pieces[i][j]=2;
                    }
                }
            }
        }
    }
    //Populates the AI Moves array with moves available for the AI
    public void PossibleAIMoves(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(Pieces[i][j]==1) {
                    if(i>0 && j<7){
                        if (Pieces[i-1][j+1]==0){
                            MinMaxMove M=new MinMaxMove(MoveType.NORMAL,i,j,i-1,j+1);
                            AIMoves[AIMoveCount]=M;
                            AIMoveCount++;
                        }
                        if (i>1 && j<6) {
                            if (Pieces[i - 1][j + 1] == 2 && Pieces[i - 2][j + 2] == 0) {
                                MinMaxMove M=new MinMaxMove(MoveType.KILL, i, j, i - 2, j + 2);
                                AIMoves[AIMoveCount]=M;
                                AIMoveCount++;
                            }
                        }
                    }
                    if(i<7 && j<7){
                        if (Pieces[i+1][j+1]==0){
                            MinMaxMove M=new MinMaxMove(MoveType.NORMAL,i,j,i+1,j+1);
                            AIMoves[AIMoveCount]=M;
                            AIMoveCount++;
                        }
                        if (i<6 && j<6) {
                            if (Pieces[i + 1][j + 1] == 2 && Pieces[i + 2][j + 2] == 0) {
                                MinMaxMove M=new MinMaxMove(MoveType.KILL, i, j, i + 2, j + 2);
                                AIMoves[AIMoveCount]=M;
                                AIMoveCount++;
                            }
                        }
                    }

                }
            }
        }
    }
    //Populates the User Moves Array with all possible user moves
    public void PossibleUserMoves(int[][] Board){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(Board[i][j]==2) {
                    if(i>0 && j>0){
                        if (Board[i-1][j-1]==0){
                            MinMaxMove M=new MinMaxMove(MoveType.NORMAL,i,j,i-1,j-1);
                            UserMoves[UserMoveCount]=M;
                            UserMoveCount++;
                        }
                        if (i>1 && j>1) {
                            if (Board[i - 1][j - 1] == 1 && Board[i - 2][j - 2] == 0) {
                                MinMaxMove M=new MinMaxMove(MoveType.KILL, i, j, i - 2, j - 2);
                                UserMoves[UserMoveCount]=M;
                                UserMoveCount++;
                            }
                        }
                    }
                    if(i<7 && j>0){
                        if (Board[i+1][j-1]==0){
                            MinMaxMove M=new MinMaxMove(MoveType.NORMAL,i,j,i+1,j-1);
                            UserMoves[UserMoveCount]=M;
                            UserMoveCount++;
                        }
                        if (i<6 && j>1) {
                            if (Board[i + 1][j - 1] == 1 && Board[i + 2][j - 2] == 0) {
                                MinMaxMove M=new MinMaxMove(MoveType.KILL, i, j, i + 2, j - 2);
                                UserMoves[UserMoveCount]=M;
                                UserMoveCount++;
                            }
                        }
                    }
                }
            }
        }
    }
}
