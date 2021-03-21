public class Queen extends Bishop {
    public Queen(boolean white) {
        super(white);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Queen(isWhite());
    }

    @Override
    public int[][] reachableSquares(Piece[][] board, int x, int y) {
        int[][] result = super.reachableSquares(board, x, y);

        for (int i = x + 1; i < Chess.boardSideLength; i++) {
            if (board[i][y] == null) {
                result = Utilities.appendToMatrix(result, new int[]{i, y});
            } else {
                if (!((board[i][y].isWhite() && this.isWhite()) ||
                        (!board[i][y].isWhite() && !this.isWhite()))) {
                    result = Utilities.appendToMatrix(result, new int[]{i, y});
                }
                break;
            }
        }

        for (int i = x - 1; i >= 0; i--) {
            if (board[i][y] == null) {
                result = Utilities.appendToMatrix(result, new int[]{i, y});
            } else {
                if (!((board[i][y].isWhite() && this.isWhite()) ||
                        (!board[i][y].isWhite() && !this.isWhite()))) {
                    result = Utilities.appendToMatrix(result, new int[]{i, y});
                }
                break;
            }
        }

        for (int i = y + 1; i < Chess.boardSideLength; i++) {
            if (board[x][i] == null) {
                result = Utilities.appendToMatrix(result, new int[]{x, i});
            } else {
                if (!((board[x][i].isWhite() && this.isWhite()) ||
                        (!board[x][i].isWhite() && !this.isWhite()))) {
                    result = Utilities.appendToMatrix(result, new int[]{x, i});
                }
                break;
            }
        }

        for (int i = y - 1; i >= 0; i--) {
            if (board[x][i] == null) {
                result = Utilities.appendToMatrix(result, new int[]{x, i});
            } else {
                if (!((board[x][i].isWhite() && this.isWhite()) ||
                        (!board[x][i].isWhite() && !this.isWhite()))) {
                    result = Utilities.appendToMatrix(result, new int[]{x, i});
                }
                break;
            }
        }


        return result;
    }

    @Override
    public String toString() {
        if (isWhite()) {
            return Piece.whiteColorCode + "♕";
        } else {
            return Piece.blackColorCode + "♛";
        }
    }
}
