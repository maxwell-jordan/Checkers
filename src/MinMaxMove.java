package src;

public class MinMaxMove {
    //Stores the New Coordinates
    private int NewX;
    private int NewY;
    public int GetNewX(){return NewX;}
    public int GetNewY(){return NewY;}

    //Stores the Original Coordinates
    private int OldX;
    private int OldY;
    public int GetOldX(){return OldX;}
    public int GetOldY(){return OldY;}

    //Stores the move type
    private MoveType type;
    public MoveType getType(){return type;}

    //Stores the Maximum value (Used for the Algorithm)
    private int Max;
    public int getMax(){return Max;}
    public void setMax(int Max){this.Max=Max;}


    //Constructor for each Move
    public MinMaxMove(MoveType type, int OldX,int OldY,int NewX,int NewY){
        this.NewX=NewX;
        this.NewY=NewY;
        this.OldX=OldX;
        this.OldY=OldY;
        this.type=type;
    }


}
