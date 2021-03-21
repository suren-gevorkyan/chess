public class King extends Piece {
    private boolean hasMoved;

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        King king = new King(isWhite());
        king.hasMoved = hasMoved;
        return king;
    }

    public void setMoved() {
        this.hasMoved = true;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    @Override
    public int[][] reachableSquares(Piece[][] board, int fromX, int fromY) {
        var result = new int[0][0];

        int[] xValues = {fromX, fromX, fromX - 1, fromX - 1, fromX - 1, fromX + 1, fromX + 1, fromX + 1};
        int[] yValues = {fromY - 1, fromY + 1, fromY, fromY - 1, fromY + 1, fromY, fromY - 1, fromY + 1};

        for (int i = 0; i < xValues.length && i < yValues.length; ++i) {
            if (Chess.areValidCellCoordinates(xValues[i], yValues[i])) {
                Piece piece = board[xValues[i]][yValues[i]];
                if (piece == null || piece.isWhite() != isWhite()) {
                    if (!isInCheck(board, xValues[i], yValues[i], fromX, fromY)) {
                        result = Utilities.appendToMatrix(result, new int[]{xValues[i], yValues[i]});
                    }
                }
            }
        }

        int[][] castleMoves = castleMoves(board, fromX, fromY);
        if (castleMoves != null) {
            for (int i = 0; i < castleMoves.length; i++) {
                int[] moves = castleMoves[i];
                if (moves != null && moves.length == 2 && !isInCheck(board, moves[0], moves[1], fromX, fromY)) {
                    result = Utilities.appendToMatrix(result, castleMoves[i]);
                }
            }
        }

        return result;
    }

    public boolean isInCheck(Piece[][] board, int x, int y, int originalX, int originalY) {
        //Vertical direction checks for the queen and rooks
        for (int i = x - 1; i >= 0; --i) {
            Piece piece = board[i][y];
            if (piece != null && i != originalX) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Rook)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = x + 1; i < Chess.boardSideLength; ++i) {
            Piece piece = board[i][y];
            if (piece != null && i != originalX) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Rook)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        //Horizontal direction checks for the queen and rooks
        for (int i = y - 1; i >= 0; --i) {
            Piece piece = board[x][i];
            if (piece != null && i != originalY) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Rook)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = y + 1; i < Chess.boardSideLength; ++i) {
            Piece piece = board[x][i];
            if (piece != null && i != originalY) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Rook)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        //Diagonal direction checks for the queen and bishops
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; --i, --j) {
            Piece piece = board[i][j];
            if (piece != null && i != originalX && j != originalY) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Bishop)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = x + 1, j = y + 1; i < Chess.boardSideLength && j < Chess.boardSideLength; ++i, ++j) {
            Piece piece = board[i][j];
            if (piece != null && i != originalX && j != originalY) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Bishop)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = x - 1, j = y + 1; i >= 0 && j < Chess.boardSideLength; --i, ++j) {
            Piece piece = board[i][j];
            if (piece != null && i != originalX && j != originalY) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Bishop)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        for (int i = x + 1, j = y - 1; i < Chess.boardSideLength && j >= 0; ++i, --j) {
            Piece piece = board[i][j];
            if (piece != null && i != originalX && j != originalY) {
                if (this.isWhite() != piece.isWhite() && (piece instanceof Queen || piece instanceof Bishop)) {
                    return true;
                } else {
                    break;
                }
            }
        }

        //Knight checks
        int[] xValues = {x - 1, x - 2, x - 2, x - 1, x + 1, x + 2, x + 2, x + 1};
        int[] yValues = {y - 2, y - 1, y + 1, y + 2, y + 2, y + 1, y - 1, y - 2};

        for (int i = 0; i < xValues.length && i < yValues.length; ++i) {
            if (Chess.areValidCellCoordinates(xValues[i], yValues[i])) {
                Piece piece = board[xValues[i]][yValues[i]];
                if (piece != null && xValues[i] != originalX && yValues[i] != originalY && piece instanceof Knight && piece.isWhite() != isWhite()) {
                    return true;
                }
            }
        }

        //Pawn checks
        int pawnX = isWhite() ? (x - 1) : (x + 1);
        int y1 = y - 1;
        int y2 = y + 1;

        if (Chess.areValidCellCoordinates(pawnX, y1)) {
            Piece piece = board[pawnX][y1];
            if (piece != null && pawnX != originalX && y1 != originalY && piece instanceof Pawn && piece.isWhite() != isWhite()) {
                return true;
            }
        }

        if (Chess.areValidCellCoordinates(pawnX, y2)) {
            Piece piece = board[pawnX][y2];
            if (piece != null && pawnX != originalX && y2 != originalY && piece instanceof Pawn && piece.isWhite() != isWhite()) {
                return true;
            }
        }

        //Other king checks, this case is only useful for the reachable squares getting method
        int[][] v = new int[][] {
                {y-1,y,y+1},
                {y-1,y+1},
                {y-1,y,y+1}
        };

        for (int i = x - 1, j = 0; i <= x + 1 && j < v.length; ++i, ++j) {
            int[] line = v[j];
            for (int k = 0; k < line.length; k++) {
                if (Chess.areValidCellCoordinates(i, line[k])) {
                    Piece piece = board[i][line[k]];
                    if (piece != null && piece instanceof King && piece.isWhite() != isWhite()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isInCheckMate(Piece[][] board, int x, int y) {
        int[][] reachableSquares = reachableSquares(board, x, y);
        return isInCheck(board, x, y, x, y) && (reachableSquares == null || reachableSquares.length == 0);
    }

    public int[][] castleMoves(Piece[][] board, int x, int y) {
        if (hasMoved) {
            return null;
        }

        int[][] result = new int[0][0];
        try {
            //Right(short) castling
            boolean areAllRightSquaresEmpty = true;
            for (int i = y + 1; i < Chess.boardSideLength - 1; ++i) {
                areAllRightSquaresEmpty &= board[x][i] == null;
            }
            Rook rook = (Rook) board[x][Chess.boardSideLength - 1];

            if (areAllRightSquaresEmpty && rook != null && !rook.hasMoved()) {
                result = Utilities.appendToMatrix(result, new int[] {x, Chess.boardSideLength - 2});
            }

            //Left(long) castling
            boolean areAllLeftSquaresEmpty = true;
            for (int i = y - 1; i > 0; --i) {
                areAllLeftSquaresEmpty &= board[x][i] == null;
            }
            Rook leftRook = (Rook) board[x][0];

            if (areAllLeftSquaresEmpty && leftRook != null && !leftRook.hasMoved()) {
                result = Utilities.appendToMatrix(result, new int[] {x, 2});
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean canCastle(Piece[][] board, int x, int y) {
        int[][] moves = castleMoves(board, x, y);
        return moves != null && moves.length > 0;
    }

    @Override
    public String toString() {
        if (isWhite()) {
            return Piece.whiteColorCode + "♔";
        } else {
            return Piece.blackColorCode + "♚";
        }
    }
}
