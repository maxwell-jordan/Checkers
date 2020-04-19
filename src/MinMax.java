public class MinMax {
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

    //The Original Coordinates of the Piece to be moved
    private int OldX;
    private int OldY;
    public int GetOldX(){return OldX;}
    public int GetOldY(){return OldY;}


    //Stores the type of move that will be done (Normal or Kill)
    private MoveType type;
    public MoveType getType(){return type;}


    //Starts the Move
    public void Move(Tile[][] board){
        //Creates the Pieces Array from the Board from the CheckerBoard class
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


    //Creates the Pieces Array from the Board from the CheckerBoard class
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


    //Gets the Maximum Value for each move
    public void MinMax(MinMaxMove Move){
        //Resets the User Moves Array
        UserMoves=new MinMaxMove[100];
        UserMoveCount=0;

        //Copy the Pieces board to simulate moves with out committing them to the main board
        int[][] MinMaxBoard=new int[8][8];

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                MinMaxBoard[i][j]=Pieces[i][j];
            }
        }

        //Minimum possible number of AI pieces on the board
        int Minimum=1000;


        //Gets the possible move coordinates and then commits the changes to the temporary board
        int Ox=Move.GetOldX();
        int Oy=Move.GetOldY();
        int Nx=Move.GetNewX();
        int Ny=Move.GetNewY();
        MoveType type=Move.getType();

        if(type.equals(MoveType.NORMAL)){
            MinMaxBoard[Ox][Oy]=0;
            MinMaxBoard[Nx][Ny]=1;
        }
        else if(type.equals(MoveType.KILL)){
            MinMaxBoard[Ox][Oy]=0;
            if (Ox<Nx){
                MinMaxBoard[Ox+1][Oy+1]=0;
            }
            else {
                MinMaxBoard[Ox-1][Oy+1]=0;
            }
            MinMaxBoard[Nx][Ny]=1;
        }

        //Populates the User Moves Array with all possible user moves
        PossibleUserMoves(MinMaxBoard);


        //Gets the Minimum value for each possible User Move
        for(int i=0;i<UserMoveCount;i++){
            MinUser(UserMoves[i],MinMaxBoard);
        }


        //Gets the Smallest Value from all the Moves and Set it as the Max for the AI move
        //But if the New Y for the AI move is at the bottom of the board, it is the Win Condition so it is given a really high number So it can be selected as the best move
        for(int i=0;i<UserMoveCount;i++){
            if(Minimum>UserMoves[i].getMax()){
                Minimum=UserMoves[i].getMax();
            }
        }

        if(Ny==7){
            Move.setMax(1000);
        }
        else {
            Move.setMax(Minimum);
        }

    }

    //Gets the Minimum value for a User Move
    public void MinUser(MinMaxMove UserMove, int [][] Board){

        //Creates a temporary copy of the board
        int[][] MinMaxBoard=new int[8][8];

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                MinMaxBoard[i][j]=Board[i][j];
            }
        }

        int Min=0;

        int Ox=UserMove.GetOldX();
        int Oy=UserMove.GetOldY();
        int Nx=UserMove.GetNewX();
        int Ny=UserMove.GetNewY();
        MoveType type=UserMove.getType();

        if(type.equals(MoveType.NORMAL)){
            MinMaxBoard[Ox][Oy]=0;
            MinMaxBoard[Nx][Ny]=2;
        }
        else if(type.equals(MoveType.KILL)){
            MinMaxBoard[Ox][Oy]=0;
            if (Ox<Nx){
                MinMaxBoard[Ox+1][Oy-1]=0;
            }
            else {
                MinMaxBoard[Ox-1][Oy-1]=0;
            }
            MinMaxBoard[Nx][Ny]=2;
        }

        //Counts the number of AI pieces on the Board, but if its next move is at the top row the Max is 0 Because it is the win condition
        if (Ny!=0){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(MinMaxBoard[i][j]==1){
                        Min++;
                    }
                }
            }
        }
        else {
            Min=0;
        }

        UserMove.setMax(Min);
    }


}
