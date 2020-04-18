public class MinMaxMove {
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

    public MinMaxMove(MoveType type, int OldX,int OldY,int NewX,int NewY){
        this.NewX=NewX;
        this.NewY=NewY;
        this.OldX=OldX;
        this.OldY=OldY;
        this.type=type;
    }


}
