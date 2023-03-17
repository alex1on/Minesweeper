
public class Cell {
    private boolean hasMine;
    private boolean isSupermine;
    private boolean isRevealed;
    private boolean isFlagged;
    private int row;
    private int column;
    private int neighborMineCount;

    public Cell(boolean hasMine, boolean isSupermine, int row, int column) {
        this.hasMine = hasMine;
        this.isSupermine = isSupermine;
        this.isRevealed = false;
        this.isFlagged = false;
        this.row = row;
        this.column = column;
        this.neighborMineCount = 0;
    }

    public boolean isHasMine() {
		return hasMine;
	}

	public void setHasMine() {
		this.hasMine = true;
	}

	public boolean hasMine() {
        return hasMine;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getNeighborMineCount() {
        return neighborMineCount;
    }

    public void setNeighborMineCount(int neighborMineCount) {
        this.neighborMineCount = neighborMineCount;
    }

	public boolean isSupermine() {
		return isSupermine;
	}

	public void setSupermine(boolean isSupermine) {
		this.isSupermine = isSupermine;
	}
}