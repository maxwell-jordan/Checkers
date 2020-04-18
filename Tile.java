

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private Piece piece;

    public boolean hasPiece(){
        return piece!= null;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean light, int x, int y){
        setWidth(CheckerBoard.TILE_SIZE);
        setHeight(CheckerBoard.TILE_SIZE);

        relocate(x * CheckerBoard.TILE_SIZE, y * CheckerBoard.TILE_SIZE);

        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }
}
