//ORIGINATOR


import java.util.Random;

import java.util.Random;

public class Board {
    private Cell[][] cells;
    private int width;
    private int height;
    private int numberOfMines;

    public Board(int width, int height, int numberOfMines) {
        this.width = width;
        this.height = height;
        this.numberOfMines = numberOfMines;
        cells = new Cell[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    // Save the current state of the board.
    public BoardStateMemento saveToMemento() {
        return new BoardStateMemento(this.cells);
    }

    // Restore the board state from a memento.
    public void restoreFromMemento(BoardStateMemento memento) {
        Cell[][] state = memento.getState();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                this.cells[i][j] = new Cell(state[i][j]);
            }
        }
    }


    public void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < numberOfMines) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            if (!cells[row][col].isMine()) {
                cells[row][col].setMine(true);
                minesPlaced++;
            }
        }
    }

    public void calculateAdjacentMines() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (!cells[row][col].isMine()) {
                    int mines = countAdjacentMines(row, col);
                    cells[row][col].setAdjacentMines(mines);
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width && cells[newRow][newCol].isMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean revealCell(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            System.out.println("Invalid coordinates. Please try again.");
            return false;
        }

        Cell cell = cells[row][col];
        if (cell.isRevealed() || cell.isFlagged()) {
            return false;
        }

        cell.setRevealed(true);

        if (cell.isMine()) {
            return true;
        }

        if (cell.getAdjacentMines() == 0) {
            // Reveal surrounding cells if no adjacent mines
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newRow = row + i;
                    int newCol = col + j;
                    if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width) {
                        revealCell(newRow, newCol); // Recursion to reveal adjacent cells
                    }
                }
            }
        }

        return false;
    }

    public void toggleFlag(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            System.out.println("Invalid coordinates. Please try again.");
            return;
        }

        Cell cell = cells[row][col];
        if (!cell.isRevealed()) {
            cell.setFlagged(!cell.isFlagged());
        }
    }

    public boolean isWin() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = cells[row][col];
                if (!cell.isMine() && !cell.isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard(boolean revealMines) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j];
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        System.out.print("* ");
                    } else {
                        System.out.print(cell.getAdjacentMines() + " ");
                    }
                } else if (cell.isFlagged()) {
                    System.out.print("F ");
                } else if (revealMines && cell.isMine()) {
                    System.out.print("* ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }
}
