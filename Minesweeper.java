import java.util.Scanner;

public class Minesweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");

        // Initialize game settings
        int width = 10;
        int height = 10;
        int numberOfMines = 15;

        // Create the game board
        Board board = new Board(width, height, numberOfMines);
        board.placeMines();
        board.calculateAdjacentMines();

        // Game loop
        boolean gameOver = false;
        while (!gameOver) {
            System.out.println("Current Board:");
            board.printBoard(false); // false to hide mines for gameplay

            System.out.println("Choose action: \n1. Reveal (R row col)\n2. Toggle Flag (F row col)");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String action = parts[0];
            int row = Integer.parseInt(parts[1]);
            int col = Integer.parseInt(parts[2]);

            switch (action) {
                case "R":
                case "r":
                    gameOver = board.revealCell(row, col);
                    break;
                case "F":
                case "f":
                    board.toggleFlag(row, col);
                    break;
            }

            if (board.isWin()) {
                System.out.println("Congratulations! You've cleared all non-mine cells!");
                board.printBoard(true);
                return;
            }

            if (gameOver) {
                System.out.println("Game Over! You've hit a mine.");
                board.printBoard(true); // true to reveal mines
            }
        }

        scanner.close();
    }
}

