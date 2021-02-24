public class Utilities {
    public static int[][] appendToMatrix(int[][] matrix, int[] newItem) {
        int[][] result = new int[matrix.length + 1][0];
        for (int i = 0; i < matrix.length; ++i) {
            int[] elements = matrix[i];
            result[i] = new int[elements.length];
            for (int j = 0; j < elements.length; ++j) {
                result[i][j] = elements[j];
            }
        }

        result[matrix.length] = new int[newItem.length];
        for (int i = 0; i < newItem.length; ++i) {
            result[matrix.length][i] = newItem[i];
        }

        return result;
    }

    public static String[][] appendToMatrix(String[][] matrix, String[] newItem) {
        String[][] result = new String[matrix.length + 1][0];
        for (int i = 0; i < matrix.length; ++i) {
            String[] elements = matrix[i];
            result[i] = new String[elements.length];
            for (int j = 0; j < elements.length; ++j) {
                result[i][j] = elements[j];
            }
        }

        result[matrix.length] = new String[newItem.length];
        for (int i = 0; i < newItem.length; ++i) {
            result[matrix.length][i] = newItem[i];
        }

        return result;
    }

    public static int[] copyArray(int[] array) {
        if (array == null) { return null; }

        int[] result = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            result[i] = array[i];
        }

        return result;
    }

    public static Piece[][] copyBoard(Piece[][] board) {
        if (board == null) { return null; }

        Piece[][] result = new Piece[board.length][];

        for (int i = 0; i < board.length; i++) {
            Piece[] line = board[i];
            if (line != null) {
                result[i] = new Piece[line.length];
                for (int j = 0; j < line.length; j++) {
                    Piece piece = line[j];
                    if (piece != null) {
                        try {
                            result[i][j] = (Piece) piece.clone();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return result;
    }
}
