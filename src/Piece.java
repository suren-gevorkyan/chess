import java.awt.Color;

public abstract class Piece {
    public static String whiteColorCode = "\u001b[30;1m";
    public static  String blackColorCode = "\u001b[38;5;16m";

    private Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public int[][] reachableSquares(Piece[][] board, int fromX, int fromY) {
        return null;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return null;
    }
}
