import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ChessDemo {
    /**
     * *********** REPORT ***********
     * *********** 5-th point from homework ***********
     *
     * Some concerns about passing the original board as an argument to all the pieces:
     * 1. All the pieces are allowed to freely modify the board while constructing their reachable squares,
     *      which can become a serious problem, if multiple developers work on the same project. Copying the
     *      board before passing it to a piece object can solve this problem, but copying a big matrix
     *      is an expensive task, and I don't think it's necessary for projects this small (it would
     *      keep us from some serious problems even in small projects like this though). As all the required
     *      fields are marked as private, it's hardly likely that the properties will be modified by other pieces.
     * 2. The structure of the board isn't right in my opinion, as it would be best if all the items in the board
     *      were cells, which had their coordinates and the piece on them. That would have made the code more readable,
     *      as all the null pointer checks may be skipped. Even storing piece coordinates in the Piece object itself
     *      would be better than this (which was the requirement of the task).
     * 3. Making the classes final wouldn't change anything, as final for classes means that these classes can not be
     *      further inherited, so it won't help us.
     */


    /**
     * The path for the puzzles files is added as a Program Argument from the project configurations,
     * it'll be passed to the main function as the first element of the args array.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(-1);
        }

        String filePath = args[0];
        String[][] boardTemplates = loadBoardsFromFile(filePath);
        int validBoardsCount = 0;
        for (int i = 0; i < boardTemplates.length; i++) {
            String[] boardData = boardTemplates[i];
            if (boardData.length == 2) {
                String message = boardData[0];
                String code = boardData[1];

                System.out.println((i + 1) + ")  " + message);
                Chess game = new Chess(code);
                game.print();
                System.out.println();

                validBoardsCount++;
            }
        }

        Chess game;
        if (validBoardsCount > 0 ) {

            Scanner scanner = new Scanner(System.in);
            int index = 0;
            while (index < 1 || index > validBoardsCount) {
                System.out.println("Enter the number of the board with which you'd like to play");
                index = scanner.nextInt();
            }

            String[] boardData = boardTemplates[index - 1];

            game = new Chess(boardData[1]);
        } else {
            game = new Chess();
        }

        System.out.println();
        System.out.println("The game is started, you can see the board below.");
        System.out.println();

        game.print();

        game.play();
    }

    private static String[][] loadBoardsFromFile(String path) {
        String[][] result = new String[0][0];

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(path));
            String message;
            while((message = fileReader.readLine()) != null) {
                String code = fileReader.readLine();

                if(message.length() != 0 && code != null) {
                    result = Utilities.appendToMatrix(result, new String[]{message, code});
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
