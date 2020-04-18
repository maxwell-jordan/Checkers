public class MinMax {
    int[][] RedPieces=new int[8][8];
    private Tile[][] MoveBoard;
    private MinMaxMove[] Moves=new MinMaxMove[100];
    private int MoveCount;

    private int NewX;
    private int NewY;
    public int GetNewX(){return NewX;}
    public int GetNewY(){return NewY;}

    private int OldX;
    private int OldY;
    public int GetOldX(){return OldX;}
    public int GetOldY(){return OldY;}

    private MoveType type;
    public MoveType getType(){return type;}


    public MinMax(Tile[][] board){
        MoveBoard = board;
    }

    public void Move(){
        GetBoardState();

        PossibleRedMoves();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                    System.out.print(RedPieces[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();

        int Kill=-1;

        for(int j=0;j<MoveCount;j++){
           // System.out.println(Moves[j].getType()+" "+Moves[j].GetOldX()+" "+Moves[j].GetOldY()+" "+Moves[j].GetNewX()+" "+Moves[j].GetNewY());
            if(Moves[j].getType().equals(MoveType.KILL)){
                Kill=j;
            }
        }

        if (Kill!=-1){
            NewX=Moves[Kill].GetNewX();
            NewY=Moves[Kill].GetNewY();
            OldX=Moves[Kill].GetOldX();
            OldY=Moves[Kill].GetOldY();
            type=MoveType.KILL;
        }
        else {
            NewX=Moves[0].GetNewX();
            NewY=Moves[0].GetNewY();
            OldX=Moves[0].GetOldX();
            OldY=Moves[0].GetOldY();
            type=MoveType.NORMAL;
        }





    }

    public void GetBoardState(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(MoveBoard[i][j].hasPiece()) {
                    Piece P = MoveBoard[i][j].getPiece();
                    if (P.getType().equals(PieceType.RED)) {
                        RedPieces[i][j] = 1;
                    }
                    else if(P.getType().equals(PieceType.BLACK)){
                        RedPieces[i][j]=2;
                    }
                }
            }
        }
    }

    public void PossibleRedMoves(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(RedPieces[i][j]==1) {
                    if(i>0 && j<7){
                        if (RedPieces[i-1][j+1]==0){
                            MinMaxMove M=new MinMaxMove(MoveType.NORMAL,i,j,i-1,j+1);
                            Moves[MoveCount]=M;
                            MoveCount++;
                        }
                        if (i>1 && j<6) {
                            if (RedPieces[i - 1][j + 1] == 2 && RedPieces[i - 2][j + 2] == 0) {
                                MinMaxMove M=new MinMaxMove(MoveType.KILL, i, j, i - 2, j + 2);
                                Moves[MoveCount]=M;
                                MoveCount++;
                            }
                        }
                    }

                    if(i<7 && j<7){
                        if (RedPieces[i+1][j+1]==0){
                            MinMaxMove M=new MinMaxMove(MoveType.NORMAL,i,j,i+1,j+1);
                            Moves[MoveCount]=M;
                            MoveCount++;
                        }
                        if (i<6 && j<6) {
                            if (RedPieces[i + 1][j + 1] == 2 && RedPieces[i + 2][j + 2] == 0) {
                                MinMaxMove M=new MinMaxMove(MoveType.KILL, i, j, i + 2, j + 2);
                                Moves[MoveCount]=M;
                                MoveCount++;
                            }
                        }
                    }

                }
            }
        }
    }


}
