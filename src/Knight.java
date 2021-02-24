import java.awt.Color;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Knight(isWhite());
    }

    @Override
    public int[][] reachableSquares(Piece[][] board, int fromX, int fromY) {
        Piece initialPiece = board[fromX][fromY];

        int[][] result = new int[0][0];

        if (initialPiece == null) {
            return result;
        }

        int[] xValues = {fromX - 1, fromX - 2, fromX - 2, fromX - 1, fromX + 1, fromX + 2, fromX + 2, fromX + 1};
        int[] yValues = {fromY - 2, fromY - 1, fromY + 1, fromY + 2, fromY + 2, fromY + 1, fromY - 1, fromY - 2};

        for (int i = 0; i < xValues.length && i < yValues.length; ++i) {
            if (Chess.areValidCellCoordinates(xValues[i], yValues[i])) {
                Piece piece = board[xValues[i]][yValues[i]];
                if (piece == null || piece.isWhite() != isWhite()) {
                    result = Utilities.appendToMatrix(result, new int[]{xValues[i], yValues[i]});
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        if (isWhite()) {
            return Piece.whiteColorCode + "♘";
        } else {
            return Piece.blackColorCode + "♞";
        }
    }
}
