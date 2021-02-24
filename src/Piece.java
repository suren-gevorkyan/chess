public abstract class Piece implements Cloneable {
    public static String whiteColorCode = "\u001b[30;1m";
    public static  String blackColorCode = "\u001b[38;5;16m";

    private boolean isWhite;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public Piece(Piece piece) {
        this.isWhite = piece.isWhite();
    }

    public int[][] reachableSquares(Piece[][] board, int fromX, int fromY) {
        return null;
    }

    public boolean isWhite() {
        return isWhite;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public String toString() {
        return null;
    }
}
