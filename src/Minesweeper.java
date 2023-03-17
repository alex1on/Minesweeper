import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Minesweeper {
	//private int[][] board;
	//private boolean[][] revealed;
	//private boolean[][] flagged;
	private Cell[][] Cell;
	private int Rows;
	private int Cols;
	private int Level; 
	private int Mines;
	private int Time;
	private int Supermine;
	
	public Minesweeper(int level, int mines, int time, int supermine) {
		switch (level) {
		case 1:
			this.Rows = 9;
			this.Cols = 9;
			break;
		case 2: 
			this.Rows = 16;
			this.Cols = 16;
			break;
		}
		this.Level = level;
		this.Mines = mines;
		this.Time = time;
		this.Supermine = supermine;
		this.Cell = new Cell[this.Rows][this.Cols];
	    for (int row = 0; row < Rows; row++) {
	        for (int col = 0; col < Cols; col++) {
	            Cell[row][col] = new Cell(false, false, row, col);
	        }
	    }
//		this.board = new int[this.Rows][this.Cols];
//		this.revealed = new boolean[this.Rows][this.Cols];
//		this.flagged = new boolean[this.Rows][this.Cols];
		initializeBoard();
	}
	
	private void initializeBoard() {

		int supermineLoc = 0;
		int sprm = 0;
        try {
    		if (Level == 2 && Supermine == 1) {
    			Random rand = new Random();	
    			supermineLoc = rand.nextInt(Mines);
    		}
    		String filePath = "./mines.txt";
            FileWriter writer = new FileWriter(filePath, false); 
    	    // randomly place mines
		    int count = 0;
		    while (count < Mines) {
		        int row = (int)(Math.random() * Rows);
		        int col = (int)(Math.random() * Cols);
		        if (!Cell[row][col].hasMine()) {		        	
		            Cell[row][col].setHasMine();
		    		if (Level == 2 && Supermine == 1 && count == supermineLoc) {
		    			Cell[row][col].setSupermine(true);
		    		}
		            count++;
		            if(Cell[row][col].isSupermine()) sprm = 1;
	                writer.write(row + ", " + col + ", " + sprm + "\n"); // TODO : Add supermine 
	                sprm = 0;
		        }
		    }
	
			writer.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // initialize the board with the number of adjacent mines
	    for (int row = 0; row < Rows; row++) {
	        for (int col = 0; col < Cols; col++) {
	            if (!Cell[row][col].hasMine()) {
	                int adjacentMines = 0;
	                for (int i = Math.max(0, row-1); i <= Math.min(row+1, Rows-1); i++) {
	                    for (int j = Math.max(0, col-1); j <= Math.min(col+1, Cols-1); j++) {
	                        if (Cell[i][j].hasMine()) {
	                            adjacentMines++;
	                        }
	                    }
	                }
	                Cell[row][col].setNeighborMineCount(adjacentMines);
	                }
	        }
	    }
		
	}
	
	public Cell[][] getBoard() {
		for (int row = 0; row < Rows; row++) {
			for (int col = 0; col < Cols; col++) {
				if(!Cell[row][col].hasMine()) {
					//System.out.print(Cell[row][col].getNeighborMineCount());
				}
				//else 
					//System.out.print("X");
			}
			//System.out.println();
		}
		return Cell;
	}

	public int getRows() {
		return Rows;
	}

	public void setRows(int rows) {
		Rows = rows;
	}

	public int getCols() {
		return Cols;
	}

	public void setCols(int cols) {
		Cols = cols;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public int getMines() {
		return Mines;
	}

	public void setMines(int mines) {
		Mines = mines;
	}

	public int getTime() {
		return Time;
	}

	public void setTime(int time) {
		Time = time;
	}
	
	

}
