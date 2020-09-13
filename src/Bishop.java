import java.awt.Color;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public int[][] reachableSquares(Piece[][] board, int fromX, int fromY) {
        int[][] result = new int[0][0];

        Piece selectedPiece = board[fromX][fromY];
        if (selectedPiece == null) {
            return result;
        }

        boolean ignoreTopLeftDirection = false;
        boolean ignoreTopRightDirection = false;
        boolean ignoreBottomLeftDirection = false;
        boolean ignoreBottomRightDirection = false;

        int currentDelta = 1;

        while (!(ignoreTopLeftDirection && ignoreTopRightDirection &&
                ignoreBottomLeftDirection && ignoreBottomRightDirection)) {
            int topX = fromX - currentDelta;
            int bottomX = fromX + currentDelta;
            int rightY = fromY + currentDelta;
            int leftY = fromY - currentDelta;

            if (!ignoreTopLeftDirection && Chess.areValidCellCoordinates(topX, leftY)) {
                Piece piece = board[topX][leftY];
                if (piece == null || piece.getColor() != selectedPiece.getColor()) {
                    int[] newItem = {topX, leftY};
                    result = Utilities.appendToMatrix(result, newItem);
                    if (piece != null) {
                        ignoreTopLeftDirection = piece.getColor() != selectedPiece.getColor();
                    }
                } else {
                    ignoreTopLeftDirection = true;
                }
            } else {
                ignoreTopLeftDirection = true;
            }

            if (!ignoreTopRightDirection && Chess.areValidCellCoordinates(topX, rightY)) {
                Piece piece = board[topX][rightY];
                if (piece == null || piece.getColor() != selectedPiece.getColor()) {
                    int[] newItem = {topX, rightY};
                    result = Utilities.appendToMatrix(result, newItem);
                    if (piece != null) {
                        ignoreTopRightDirection = piece.getColor() != selectedPiece.getColor();
                    }
                } else {
                    ignoreTopRightDirection = true;
                }
            } else {
                ignoreTopRightDirection = true;
            }

            if (!ignoreBottomLeftDirection && Chess.areValidCellCoordinates(bottomX, leftY)) {
                Piece piece = board[bottomX][leftY];
                if (piece == null || piece.getColor() != selectedPiece.getColor()) {
                    int[] newItem = {bottomX, leftY};
                    result = Utilities.appendToMatrix(result, newItem);
                    if (piece != null) {
                        ignoreBottomLeftDirection = piece.getColor() != selectedPiece.getColor();
                    }
                } else {
                    ignoreBottomLeftDirection = true;
                }
            } else {
                ignoreBottomLeftDirection = true;
            }

            if (!ignoreBottomRightDirection && Chess.areValidCellCoordinates(bottomX, rightY)) {
                Piece piece = board[bottomX][rightY];
                if (piece == null || piece.getColor() != selectedPiece.getColor()) {
                    int[] newItem = {bottomX, rightY};
                    result = Utilities.appendToMatrix(result, newItem);
                    if (piece != null) {
                        ignoreBottomRightDirection = piece.getColor() != selectedPiece.getColor();
                    }
                } else {
                    ignoreBottomRightDirection = true;
                }
            } else {
                ignoreBottomRightDirection = true;
            }

            currentDelta += 1;
        }
        return result;
    }

    @Override
    public String toString() {
        if (getColor() == Color.black) {
            return Piece.blackColorCode + "♝";
        } else {
            return Piece.whiteColorCode + "♗";
        }
    }
}
