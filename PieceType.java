package Checkers;

public enum PieceType {
    RED(1), BLACK(-1); //red tiles can move down the board which is represented as moving in the positive direction in the array
                                        //black tiles can move up the board which is represented as moving in the negative direction in the array
    final int moveDir;

    PieceType(int moveDir){
        this.moveDir = moveDir;
    }
}
