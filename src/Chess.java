import java.awt.*;

public class Chess {
    private static String reachableCellColorCode = "\u001b[48;5;196m";
    private static String blackCellColorCode = "\u001b[48;5;252m";
    private static String whiteCellColorCode = "\u001b[48;5;214m";
    private static String dropColorSettingsCode = "\u001b[0m";
    public static final int boardSideLength = 8;

    private Piece[][] board;

    public void play() {
        initializeBoard();
    }

    public void print() {
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[i].length; ++j) {
                boolean isBlack = (i + j) % 2 == 1;
                String backgroundColorText = isBlack ? Chess.blackCellColorCode : Chess.whiteCellColorCode;

                String symbolText;
                if (board[i][j] == null) {
                    symbolText = " ";
                } else {
                    symbolText = board[i][j] + "";
                }

                System.out.print(backgroundColorText + " " + symbolText + " " + Chess.dropColorSettingsCode);
            }
            System.out.println();
        }
    }

    public void print(int fromX, int fromY) {
        Piece piece = board[fromX][fromY];
        if (piece == null) {
            System.out.println("There is no piece at these coordinates, printing the board instead...");
            print();
        } else {
            int[][] reachablePoints = piece.reachableSquares(board, fromX, fromY);
            for (int i = 0; i < board.length; ++i) {
                for (int j = 0; j < board[i].length; ++j) {
                    boolean isBlack = (i + j) % 2 == 1;
                    String backgroundColorText;
                    if (reachableSquaresContainPoint(reachablePoints, i, j)) {
                        backgroundColorText = Chess.reachableCellColorCode;
                    } else {
                        backgroundColorText = isBlack ? Chess.blackCellColorCode : Chess.whiteCellColorCode;
                    }

                    String symbolText;
                    if (board[i][j] == null) {
                        symbolText = " ";
                    } else {
                        symbolText = board[i][j] + "";
                    }

                    System.out.print(backgroundColorText + " " + symbolText + " " + Chess.dropColorSettingsCode);
                }
                System.out.println();
            }
        }
    }

    public boolean isSquareReachable(int fromX, int fromY, int toX, int toY) {
        Piece piece = board[fromX][fromY];
        if (piece != null) {
            int[][] reachableSquares = piece.reachableSquares(board, fromX, fromY);
            return reachableSquaresContainPoint(reachableSquares, toX, toY);
        }
        return false;
    }

    public static boolean areValidCellCoordinates(int x, int y) {
        return x >= 0 && x < Chess.boardSideLength && y >= 0 && y < Chess.boardSideLength;
    }

    private boolean reachableSquaresContainPoint(int[][] reachableSquares, int x, int y) {
        for (int[] coordinates : reachableSquares) {
            if (coordinates.length == 2) {
                int xCoordinate = coordinates[0];
                int yCoordinate = coordinates[1];
                if (xCoordinate == x && yCoordinate == y) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initializeBoard() {
        if (board == null) {
            board = new Piece[Chess.boardSideLength][Chess.boardSideLength];

            board[5][2] = new Bishop(Color.BLACK);
            board[6][0] = new Knight(Color.BLACK);
            board[3][3] = new Knight(Color.BLACK);
            board[6][5] = new Bishop(Color.BLACK);
            board[3][5] = new Bishop(Color.WHITE);
            board[2][1] = new Bishop(Color.WHITE);
            board[0][2] = new Knight(Color.WHITE);
            board[0][6] = new Knight(Color.WHITE);
        }
    }
}
