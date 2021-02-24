import java.awt.*;
import java.util.Scanner;

public class Chess {
    private static final String reachableCellColorCode = "\u001b[48;5;196m";
    private static final String blackCellColorCode = "\u001b[48;5;252m";
    private static final String whiteCellColorCode = "\u001b[48;5;214m";
    private static final String dropColorSettingsCode = "\u001b[0m";
    public static final int boardSideLength = 8;

    private Piece[][] board;
    private King whiteKing;
    private King blackKing;

    public Chess() {
        this("24232225262223242121212121212121000000000000000000000000000000000000000000000000000000000000000011111111111111111413121516121314");
    }

    public Chess(String code) {
        initializeBoard();

        //The provided code can't be used to fill the board
        if (code.length() != 2 * Chess.boardSideLength * Chess.boardSideLength) {
            return;
        }

        for (int i = 0; i < Chess.boardSideLength; ++i) {
            for (int j = 0; j < Chess.boardSideLength; ++j) {
                int startIndex = 2 * ((Chess.boardSideLength * i) + j);
                String substring = code.substring(startIndex, startIndex + 2);
                Piece piece = Chess.pieceFromCode(substring);
                if (piece != null) {
                    board[i][j] = piece;
                    if (piece instanceof King) {
                        if (piece.isWhite()) {
                            whiteKing = (King) piece;
                        } else {
                            blackKing = (King) piece;
                        }
                    }
                }
            }
        }
    }

    public void play() {
        Scanner sc = new Scanner(System.in);
        int turn = 0;
        boolean gameNotFinished = true;
        String[] userInput;

        while (gameNotFinished) {
            boolean isWhitesTurn = turn % 2 == 0;
            if (isWhitesTurn) {
                System.out.print("White's move:     ");
            } else {
                System.out.print("Black's move:     ");
            }

            userInput = sc.nextLine().toLowerCase().split(" ");
            System.out.println();

            if (userInput.length == 1) {
                int[] sourceCoords = Chess.squareToCoordinates(userInput[0]);
                if (!Chess.areValidCellCoordinates(sourceCoords[0], sourceCoords[1])) {
                    continue;
                }

                Piece movingPiece = board[sourceCoords[0]][sourceCoords[1]];
                if (movingPiece == null || (isWhitesTurn && !movingPiece.isWhite()) ||
                        (!isWhitesTurn && movingPiece.isWhite())) {
                    System.out.println("Please select a " + (isWhitesTurn ? "white" : "black") + " piece to continue");
                    continue;
                }
                print(sourceCoords[0], sourceCoords[1]);
            } else {
                if (userInput.length == 2) {
                    int[] sourceCoords = Chess.squareToCoordinates(userInput[0]);
                    int[] destCoords = Chess.squareToCoordinates(userInput[1]);
                    if (!Chess.areValidCellCoordinates(sourceCoords[0], sourceCoords[1])) {
                        continue;
                    }
                    if (!Chess.areValidCellCoordinates(destCoords[0], destCoords[1])) {
                        continue;
                    }

                    Piece movingPiece = board[sourceCoords[0]][sourceCoords[1]];
                    if (movingPiece == null || (isWhitesTurn && !movingPiece.isWhite()) ||
                            (!isWhitesTurn && movingPiece.isWhite())) {
                        System.out.println("Please select a " + (isWhitesTurn ? "white" : "black") + " piece to continue");
                        continue;
                    }

                    if (isSquareReachable(sourceCoords[0], sourceCoords[1],
                            destCoords[0], destCoords[1])) {
                        PieceWithCoordinates currentKingCoordinates = getKingCoordinates(isWhitesTurn);
                        King currentKing = (King) currentKingCoordinates.piece;

                        Piece destinationPiece = board[destCoords[0]][destCoords[1]];
                        board[destCoords[0]][destCoords[1]] = board[sourceCoords[0]][sourceCoords[1]];
                        board[sourceCoords[0]][sourceCoords[1]] = null;

                        //Checking if a castle move is being made
                        if (movingPiece instanceof  King && !currentKing.hasMoved()) {
                            int moveLength = Math.abs(sourceCoords[1] - destCoords[1]);
                            //Castling
                            if (moveLength > 1) {
                                int rookYCoord;
                                if (destCoords[1] == Chess.boardSideLength - 2) {
                                    rookYCoord = Chess.boardSideLength - 1;
                                } else {
                                    rookYCoord = 0;
                                }

                                try {
                                    Rook r = (Rook) board[destCoords[0]][rookYCoord];
                                    if (rookYCoord > destCoords[1]) {
                                        board[destCoords[0]][destCoords[1] - 1] = r;
                                    } else {
                                        board[destCoords[0]][destCoords[1] + 1] = r;
                                    }
                                    board[destCoords[0]][rookYCoord] = null;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("Castling failed.");
                                    continue;
                                }
                            }
                        }



                        /**
                         * There are some situations, when after completing a move the current player's king remains
                         * or becomes vulnerable. To avoid such situations, before moving the pieces the old
                         * values are kept for both the source and the destination squares and after completing each
                         * move the current player's king's check state is checked. If the king is under the attack,
                         * the old pieces are put back to their original positions and the user is promptet to make another move.
                         * If not, the move is allowed, and the other player can make a move.
                         *
                         * NOTE: The castling case is not considered here, as check situations are taken
                         * into considerations when marking a castling move possible.
                         */
                        if (currentKing.isInCheck(board, currentKingCoordinates.x, currentKingCoordinates.y, currentKingCoordinates.x, currentKingCoordinates.y)) {
                            board[destCoords[0]][destCoords[1]] = destinationPiece;
                            board[sourceCoords[0]][sourceCoords[1]] = movingPiece;
                            System.out.println("You must make a move to move your king out of the danger zone!");
                            continue;
                        }

                        PieceWithCoordinates coordinates = getKingCoordinates(!isWhitesTurn);
                        King king = (King) coordinates.piece;

                        if (movingPiece != null && (movingPiece instanceof  King || movingPiece instanceof Rook)) {
                            try {
                                King k = (King) movingPiece;
                                k.setMoved();
                            } catch (Exception e) {

                            }

                            try {
                                Rook r = (Rook) movingPiece;
                                r.setMoved();
                            } catch (Exception e) {

                            }
                        }

                        if (king.isInCheckMate(board, coordinates.x, coordinates.y)) {
                            System.out.println("The game is finished. The " + (isWhitesTurn ? "whites" : "blacks") + " won");
                            print();
                            break;
                        } else if (king.isInCheck(board, coordinates.x, coordinates.y, coordinates.x, coordinates.y)) {
                            System.out.println("The " + (king.isWhite() ? "white" : "black") + " king is in check! " +
                                    "Hurry up to rescue him!");
                        }

                        print();
                        turn++;
                    }
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i < board.length; ++i) {
            System.out.print(" " + (Chess.boardSideLength - i) + " ");

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

        System.out.print("   ");
        for (int i = 0; i < Chess.boardSideLength; i++) {
            char letter = 'A';
            letter += i;
            System.out.print(" " + letter + " ");
        }
        System.out.println();
    }

    /**
     * This function will print the provided block's reachable squares, if the input is valid.
     *
     * @param blockName The chess styled block name (e. g. "A5", "H8", etc.)
     */
    public void print(String blockName) {
        int[] sourceCoords = Chess.squareToCoordinates(blockName.toLowerCase());
        if (sourceCoords.length == 2 && Chess.areValidCellCoordinates(sourceCoords[0], sourceCoords[1])) {
            print(sourceCoords[0], sourceCoords[1]);
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
                System.out.print(" " + (Chess.boardSideLength - i) + " ");

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

            System.out.print("   ");
            for (int i = 0; i < Chess.boardSideLength; i++) {
                char letter = 'A';
                letter += i;
                System.out.print(" " + letter + " ");
            }
            System.out.println();
        }
    }

    public PieceWithCoordinates getKingCoordinates(boolean lookForWhiteKing) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece instanceof King && piece.isWhite() == lookForWhiteKing) {
                    return new PieceWithCoordinates(piece, i, j);
                }
            }
        }
        return null;
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

    public static int[] squareToCoordinates(String s) {
        int c1 = s.charAt(0);
        int c2 = s.charAt(1);
        return new int[]{56 - c2, c1 - 97};
    }

    private boolean reachableSquaresContainPoint(int[][] reachableSquares, int x, int y) {
        if (reachableSquares != null) {
            for (int[] coordinates : reachableSquares) {
                if (coordinates.length == 2) {
                    int xCoordinate = coordinates[0];
                    int yCoordinate = coordinates[1];
                    if (xCoordinate == x && yCoordinate == y) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void initializeBoard() {
        if (board == null) {
            board = new Piece[Chess.boardSideLength][Chess.boardSideLength];
        }
    }

    private static Piece pieceFromCode(String code) {
        if (code.length() != 2) {
            return null;
        }

        char firstSymbol = code.charAt(0);
        char secondSymbol = code.charAt(1);
        boolean isWhite = firstSymbol == '1';

        switch (secondSymbol) {
            case '1':
                return new Pawn(isWhite);
            case '2':
                return new Bishop(isWhite);
            case '3':
                return new Knight(isWhite);
            case '4':
            case '7':
                Rook rook = new Rook(isWhite);
                if (secondSymbol == '7') {
                    rook.setMoved();
                }
                return rook;
            case '5':
                return new Queen(isWhite);
            case '6':
            case '8':
                King king = new King(isWhite);
                if (secondSymbol == '8') {
                    king.setMoved();
                }
                return king;
            default:
                return null;
        }
    }

    private class PieceWithCoordinates {
        Piece piece;
        int x;
        int y;

        public PieceWithCoordinates(Piece piece, int x, int y) {
            this.piece = piece;
            this.x = x;
            this.y = y;
        }
    }
}
