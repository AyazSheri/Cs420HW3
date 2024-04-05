
//CARETAKER

import java.util.Scanner;
import java.util.Stack;

public class Minesweeper {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper!");

        int width = 10;
        int height = 10;
        int numberOfMines = 15;

        Board board = new Board(width, height, numberOfMines);
        board.placeMines();
        board.calculateAdjacentMines();

        // Stack to hold the game states for undo functionality.
        Stack<BoardStateMemento> mementos = new Stack<>();

        boolean gameOver = false;
        while (!gameOver) {
            System.out.println("Current Board:");
            board.printBoard(false); // Print board without revealing mines.

            System.out.println("Choose action: \n1. Reveal (R row col)\n2. Toggle Flag (F row col)\n3. Undo (U)");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String action = parts[0];

            if (action.equalsIgnoreCase("U")) {
                if (!mementos.isEmpty()) {
                    board.restoreFromMemento(mementos.pop());
                    continue; // Skip the rest of the loop to redo the move.
                }
            } else {
                // Save current state before making a move.
                mementos.push(board.saveToMemento());

                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);

                if (action.equalsIgnoreCase("R")) {
                    gameOver = board.revealCell(row, col);
                } else if (action.equalsIgnoreCase("F")) {
                    board.toggleFlag(row, col);
                }

                if (board.isWin()) {
                    System.out.println("Congratulations! You've cleared all non-mine cells!");
                    board.printBoard(true); // Reveal the entire board.
                    return;
                }

                if (gameOver) {
                    System.out.println("Game Over! You've hit a mine.");
                    System.out.println("Undo this move? (Y/N)");
                    String undoChoice = scanner.nextLine();
                    if ("Y".equalsIgnoreCase(undoChoice) && !mementos.isEmpty()) {
                        board.restoreFromMemento(mementos.pop());
                        gameOver = false; // Reset game over state to allow undo.
                    } else {
                        board.printBoard(true); // Reveal the board with mines.
                    }
                }
            }
        }
        scanner.close();
    }
}
