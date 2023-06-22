package engine.windows.common;

import javafx.geometry.Pos;

public class Position {
    public float x;
    public float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public double distantSquare(Position b) {
        return Math.pow(b.x - this.x, 2) + Math.pow(b.y - this.y, 2);
    }

    @Override
    public Position clone() {
        return new Position(x, y);
    }
}
