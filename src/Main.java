import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        char[][] board = new char[3][3];
        initializeBoard(board);

        char player = 'X';
        boolean gameOver = false;
        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            printBoard(board);

            if (player == 'X') {
                System.out.println("Player X enter: ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                if (makeMove(board, row, col, player)) {
                    gameOver = haveWon(board, player);
                    if (gameOver) {
                        System.out.println("Player X has won!");
                    } else {
                        player = 'O';
                    }
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                System.out.println("Player O (AI) is making a mo" +
                        "ve...");
                makeAIMove(board);
                gameOver = haveWon(board, 'O');
                player = 'X';
            }
        }

        printBoard(board);
    }

    private static void initializeBoard(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = ' ';
            }
        }
    }

    private static void printBoard(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
        }
    }

    private static boolean makeMove(char[][] board, int row, int col, char player) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
            board[row][col] = player;
            return true;
        }
        return false;
    }

    private static boolean haveWon(char[][] board, char player) {
        for (int row = 0; row < board.length; row++) {
            if (board[row][0] == player && board[row][1] == player && board[row][2] == player) {
                return true;
            }
        }
        //col
        for (int col = 0; col < board.length; col++) {
            if (board[0][col] == player && board[1][col] == player && board[2][col] == player) {
                return true;
            }
        }
        //diagonal
        if(board[0][0]==player && board[1][1]==player && board[2][2] ==player){
            return true;
        } if(board[2][0]==player && board[1][1]==player && board[0][2] ==player){
            return true;
        }
        return false;
    }

    private static void makeAIMove(char[][] board) {
        int[] bestMove = minimax(board, 'O');
        board[bestMove[0]][bestMove[1]] = 'O';

        if (haveWon(board, 'O')) {
            System.out.println("Player O (AI) has won!");
            System.exit(0); // Exit the game
        }
    }

    private static int[] minimax(char[][] board, char player) {
        if (haveWon(board, 'X')) {
            return new int[]{-1, -1, -1}; // Player X won
        } else if (haveWon(board, 'O')) {
            return new int[]{-1, -1, 1}; // Player O won
        } else if (isBoardFull(board)) {
            return new int[]{-1, -1, 0}; // It's a tie
        }


        int[] bestMove = new int[]{-1, -1, (player == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE};


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {

                    board[i][j] = player;
                    int[] currentMove = minimax(board, (player == 'X') ? 'O' : 'X');
                    board[i][j] = ' ';


                    if ((player == 'O' && currentMove[2] > bestMove[2]) || (player == 'X' && currentMove[2] < bestMove[2])) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestMove[2] = currentMove[2];
                    }
                }
            }
        }

        return bestMove;
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}
