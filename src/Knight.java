import java.awt.Color;

public class Knight extends Piece {
    private static final int numberOfStepsToSides = 2;

    public Knight(Color color) {
        super(color);
    }

    @Override
    public int[][] reachableSquares(Piece[][] board, int fromX, int fromY) {
        Piece initialPiece = board[fromX][fromY];

        int[][] result = new int[0][0];

        if (initialPiece == null) {
            return result;
        }

        int minX = fromX - Knight.numberOfStepsToSides;
        int maxX = fromX + Knight.numberOfStepsToSides;
        int minY = fromY - Knight.numberOfStepsToSides;
        int maxY = fromY + Knight.numberOfStepsToSides;

        if (minX >= 0) {
            int rightY = fromY + 1;
            int leftY = fromY - 1;

            if (rightY < Chess.boardSideLength) {
                Piece piece = board[minX][rightY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {minX, rightY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }

            if (leftY >= 0) {
                Piece piece = board[minX][leftY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {minX, leftY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }
        }

        if (maxX < Chess.boardSideLength) {
            int rightY = fromY + 1;
            int leftY = fromY - 1;

            if (rightY < Chess.boardSideLength) {
                Piece piece = board[maxX][rightY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {maxX, rightY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }

            if (leftY >= 0) {
                Piece piece = board[maxX][leftY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {maxX, leftY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }
        }

        if (minY >= 0) {
            int rightX = fromX + 1;
            int leftX = fromX - 1;

            if (rightX < Chess.boardSideLength) {
                Piece piece = board[rightX][minY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {rightX, minY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }

            if (leftX >= 0) {
                Piece piece = board[leftX][minY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {leftX, minY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }
        }

        if (maxY < Chess.boardSideLength) {
            int rightX = fromX + 1;
            int leftX = fromX - 1;

            if (rightX < Chess.boardSideLength) {
                Piece piece = board[rightX][maxY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {rightX, maxY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }

            if (leftX >= 0) {
                Piece piece = board[leftX][maxY];
                if (piece == null || piece.getColor() != initialPiece.getColor()) {
                    int[] point = {leftX, maxY};
                    result = Utilities.appendToMatrix(result, point);
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        if (getColor() == Color.black) {
            return Piece.blackColorCode + "♞";
        } else {
            return Piece.whiteColorCode + "♘";
        }
    }
}
