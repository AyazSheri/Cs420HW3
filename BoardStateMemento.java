public class BoardStateMemento {
    private Cell[][] state;

    public BoardStateMemento(Cell[][] state) {
        // Deep copy of cells to ensure the memento's state is independent
        this.state = new Cell[state.length][];
        for (int i = 0; i < state.length; i++) {
            this.state[i] = new Cell[state[i].length];
            for (int j = 0; j < state[i].length; j++) {
                this.state[i][j] = new Cell(state[i][j]);
            }
        }
    }

    public Cell[][] getState() {
        return state;
    }
}
