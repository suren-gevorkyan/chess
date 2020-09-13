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
}
