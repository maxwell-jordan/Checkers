

public enum PieceType {
    RED(1), BLACK(-1);

    final int moveDir;

    PieceType(int moveDir){
        this.moveDir = moveDir;
    }
}
