import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameController {
	private TopMenuBar topMenuBar;
	private Minesweeper minesweeper;
	private int flaggedMines = 0;
	private int flaggedCells = 0;
	private int revealedCells = 0;
	private int duration = 0;
	private int tries = 0;
	private boolean gameOver = false; 
	
	public GameController(Minesweeper minesweeper, TopMenuBar topMenuBar) {
		this.minesweeper = minesweeper;
		this.topMenuBar = topMenuBar;
	}
	
	public void UpdateFlaggedMines(int i, int j) {
		flaggedCells++;
    	Cell[][] Cell = minesweeper.getBoard();
		if (Cell[i][j].isHasMine()) {
			flaggedMines++;
			System.out.println(flaggedMines);
		}
		if (flaggedMines == minesweeper.getMines()) {
			gameOver = true;
			System.out.println(flaggedMines);
			Win();
		}
		
	}

	public void UpdateMinusFlaggedMines(int i, int j) {
		flaggedCells--;
    	Cell[][] Cell = minesweeper.getBoard();
		if (Cell[i][j].isHasMine()) {
			flaggedMines--;
			System.out.println(flaggedMines);
		}		
	}
	
	public int getFlaggedCells() {
		return flaggedCells;
	}

	public void setFlaggedCells(int flaggedCells) {
		this.flaggedCells = flaggedCells;
	}
	
	public int getFlaggedMines() {
		return flaggedMines;
	}

	public void setFlaggedMines(int flaggedMines) {
		this.flaggedMines = flaggedMines;
	}

	public int getRevealedCells() {
		return revealedCells;
	}

	public void setRevealedCells(int revealedCells) {
		this.revealedCells = revealedCells;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void UpdateUncoveredCells() {
		revealedCells++;		
	}
	
	public void UpdateMinusUncoveredCells() {
		revealedCells--;		
	}
	
	public void Win() {
		topMenuBar.getBorderpane().setDisable(true);
		gameOver = true;
		UpdateGameHistory(1);
		
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Win");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations! You Won the Game!");
        
        ButtonType newGameButton = new ButtonType("New Game");
        alert.getButtonTypes().setAll(newGameButton);
        
        Platform.runLater(() -> {
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == newGameButton) {
                } 

            });
        });
		
		
	}
	
	public void Lose() {
		topMenuBar.getGridpane().setDisable(true);
		gameOver = true;
		UpdateGameHistory(0);
		
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Lose");
        alert.setHeaderText(null);
        alert.setContentText("You Lost the Game!");
        
        ButtonType newGameButton = new ButtonType("New Game");

        alert.getButtonTypes().setAll(newGameButton);
        
        Platform.runLater(() -> {
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == newGameButton) {
                } 
            });
        });		
	}
	
	public void UpdateGameHistory(int win) {
		String filePath = "./rounds.txt"; // Change this to the path of the file you want to edit
        try {
            // Create the input file if it doesn't exist
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            // Read the contents of the file into a StringBuilder and count the number of lines
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            int lineCount = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
                lineCount++;
            }
            reader.close();

            // Modify the contents of the StringBuilder as desired
            String newLine = minesweeper.getMines() + " " + tries + " " + duration + " " + win + "\n";
            content.insert(0, newLine); // Insert the new line at the beginning
            if (lineCount >= 5) {
            	int secondToLastNewlineIndex = content.lastIndexOf("\n", content.lastIndexOf("\n") - 1);
            	content.delete(secondToLastNewlineIndex + 1, content.length());
            	//content.delete(content.lastIndexOf("\n"), content.length()); // Delete the last line
            }
            // Write the modified contents back to the file
            FileWriter writer = new FileWriter(filePath);
            writer.write(content.toString());
            writer.close();

            // Print the number of lines in the file
            System.out.println("Number of lines in file: " + lineCount);
        } catch (IOException e) {
            e.printStackTrace();

        }
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}
	
	public void incrementTries() {
		this.tries++;
	}
	
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void incrementDuration() {
		this.duration++;
	}
	
}
