public class ChessDemo {
    public static void main(String[] args) {
        Chess game = new Chess();

        //Initialized the board and puts a few knights and bishops on it.
        game.play();

        /**
         * Please note that print function prints the board with a normal layout on mac.
         * I also tried it on windows, but the layout was not right(the lines, which had pieces on them, had smaller width).
         */

        //Prints the whole board
        game.print();
        System.out.println();

        //Prints all the whole board, on which all the reachable cells from the specified point are highlighted
        game.print(3, 3);
        System.out.println();

        //Prints all the whole board, on which all the reachable cells from the specified point are highlighted
        game.print(6, 5);
    }
}
