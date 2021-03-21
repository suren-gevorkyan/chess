public class Pawn extends Piece {

    private int[] passingPawn;

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Pawn pawn = new Pawn(isWhite());
        pawn.passingPawn = Utilities.copyArray(passingPawn);
        return pawn;
    }

    @Override
    public int[][] reachableSquares(Piece[][] board, int x, int y) {
        int[][] result = new int[0][0];

        if (isWhite()) {
            if (board[x - 1][y] == null) {
                result = Utilities.appendToMatrix(result, new int[]{x - 1, y});
                if (x == 6 && board[x - 2][y] == null) {
                    result = Utilities.appendToMatrix(result, new int[]{x - 2, y});
                }
            }

            if (y > 0 && board[x - 1][y - 1] != null && !board[x - 1][y - 1].isWhite()) {
                result = Utilities.appendToMatrix(result, new int[]{x - 1, y - 1});
            }

            if (y < Chess.boardSideLength - 1 && board[x - 1][y + 1] != null && !board[x - 1][y + 1].isWhite()) {
                result = Utilities.appendToMatrix(result, new int[]{x - 1, y + 1});
            }
        } else {
            if (board[x + 1][y] == null) {
                result = Utilities.appendToMatrix(result, new int[]{x + 1, y});
                if (x == 1 && board[x + 2][y] == null) {
                    result = Utilities.appendToMatrix(result, new int[]{x + 2, y});
                }
            }

            if (y > 0 && board[x + 1][y - 1] != null && board[x + 1][y - 1].isWhite()) {
                result = Utilities.appendToMatrix(result, new int[]{x + 1, y - 1});
            }

            if (y < Chess.boardSideLength - 1 && board[x + 1][y + 1] != null && board[x + 1][y + 1].isWhite()) {
                result = Utilities.appendToMatrix(result, new int[]{x + 1, y + 1});
            }
        }

        return result;
    }

    public void setPassingPawn(int x, int y) {
        this.passingPawn = new int[]{x, y};
    }

    public void setPassingPawn() {
        this.passingPawn = null;
    }

    @Override
    public String toString() {
        if (isWhite()) {
            return Piece.whiteColorCode + "♙";
        } else {
            return Piece.blackColorCode + "♟";
        }
    }
}
