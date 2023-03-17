import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TopMenuBar extends MenuBar {	
	private GameController gameController;
	private Minesweeper minesweeper;
	private boolean isLoaded;
	private Stage stage;
	private BorderPane borderpane;
	private GridPane gridpane;
		
	public Minesweeper getMinesweeper() {
		return minesweeper;
	}	
		
	public void setMinesweeper(Minesweeper minesweeper) {
		this.minesweeper = minesweeper;
	}

	public TopMenuBar(Stage stage, BorderPane borderpane) {
		this.stage = stage;
		this.borderpane = borderpane;
		isLoaded = false;
	    // create a menu
	    Menu appMenu = new Menu("Application");
	    Menu detailsMenu = new Menu("Details");
	
	    // create menu items
	    MenuItem createItem = new MenuItem("Create");
	    createItem.setOnAction(e -> {
	    	createPopup();
	    });
	    
	    MenuItem loadItem = new MenuItem("Load");
	    loadItem.setOnAction(e -> {
	    	loadPopup();
	    });
	    MenuItem startItem = new MenuItem("Start");
	    startItem.setOnAction(e-> {
	    	if(isLoaded)
	    		startGame(stage, borderpane);
	    	else {
	    		Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("An error occurred");
	    		alert.setContentText("Please try again later.");
	    		alert.showAndWait();
	    	} 
	    		
	    });
	    MenuItem exitItem = new MenuItem("Exit");
	    exitItem.setOnAction(e -> {
	    	stage.close();
	    });	    
	    MenuItem roundsItem = new MenuItem("Rounds");
	    roundsItem.setOnAction(e-> {
	    	roundsPopup();
	    });
	    MenuItem solutionItem = new MenuItem("Solutions");   
	    solutionItem.setOnAction(e-> {
	    	solution(gridpane);
	    });
	
	    // add menu items to the menu
	    appMenu.getItems().addAll(createItem, loadItem, startItem, exitItem);
	    detailsMenu.getItems().addAll(roundsItem, solutionItem);
	
	    // add the menu to the menu bar
	    this.getMenus().add(appMenu);
	    this.getMenus().add(detailsMenu);    	    
	}
	
    public GridPane getGridpane() {
		return gridpane;
	}

	public void setGridpane(GridPane gridpane) {
		this.gridpane = gridpane;
	}

	private void createPopup() {
        // Create the popup dialog
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Create Scenario");

        // Create the content for the popup dialog
        Label label = new Label("Enter the game scenario.");        
        
        //Defining the scenario text field
        TextField scenario = new TextField();
        scenario.setPromptText("Enter the game scenario.");
        
        TextField level = new TextField();
        level.setPromptText("Set game level.");
        
        TextField mines = new TextField();
        mines.setPromptText("Set mines count.");
        
        TextField time = new TextField();
        time.setPromptText("Set game timer.");
        
        TextField supermine = new TextField();
        supermine.setPromptText("Set supermine.");
        
        Button submit = new Button("Submit");
        submit.setOnAction(event -> {
            // Close the popup
        	String file = new String("./medialab/SCENARIO-" + scenario.getText() + ".txt");
        	File myObj = new File(file);
        	try {
				if (myObj.createNewFile()) {
					Path path = Paths.get(file);
			        System.out.println("File created: " + myObj.getName());
			        List<String> lines = Arrays.asList(level.getText(), mines.getText(), time.getText(), supermine.getText());
			        Files.write(path, lines, StandardCharsets.UTF_8);
				}
				else {
			        System.out.println("File already exists.");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            popup.close();
        });

        // Add the content to a layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(label, scenario, level, mines, time, supermine, submit);

        // Set the scene and show the popup
        Scene scene = new Scene(layout, 250, 250);
        popup.setScene(scene);
        popup.showAndWait();
    }
    
    public void loadPopup() {
        // Create the popup dialog
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Load Scenario");

        // Create the content for the popup dialog
        Label label = new Label("Enter the game scenario.");   
        

        //Defining the game scenario text field
        final TextField scenario = new TextField();
        scenario.setPromptText("Enter the game scenario.");
        Button submit = new Button("Submit");
        submit.setOnAction(event -> {
        	LoadGameFile loadGameFile = new LoadGameFile();
        	loadGameFile.LoadScenario(scenario.getText());
        	loadGameFile.getMinesweeper().getBoard();
        	this.setMinesweeper(loadGameFile.getMinesweeper());
        	this.isLoaded = true;
            popup.close();
        });

        // Add the content to a layout
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(label, scenario, submit);

        // Set the scene and show the popup
        Scene scene = new Scene(layout, 250, 150);
        popup.setScene(scene);
        popup.showAndWait();
    }
    
    private void startGame(Stage stage, BorderPane borderpane) {
    	this.gameController = new GameController(this.minesweeper, this);
    	Label totalMines = new Label();
    	borderpane.setLeft(totalMines);
    	totalMines.setText("Total mines: " + minesweeper.getMines());
    	borderpane.setPadding(new Insets(5));
    	
    	Label flaggedMines = new Label();
    	borderpane.setRight(flaggedMines);
    	flaggedMines.setText("Flags: " + Integer.toString(gameController.getFlaggedCells()));
    	
    	Label timeLabel = new Label();
        Instant startTime = Instant.now();
        Instant endTime = startTime.plus(Duration.ofSeconds(minesweeper.getTime()));
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(javafx.util.Duration.seconds(1), event -> {
        	gameController.incrementDuration();
            Duration timeLeft = Duration.between(Instant.now(), endTime);
            if (timeLeft.isZero() || timeLeft.isNegative() || gameController.isGameOver()) {
                timeline.stop();
                if (gameController.getFlaggedMines() == minesweeper.getMines())
                	gameController.Lose();

                return;
            }
            timeLabel.setText("Time: " + timeLeft.toSeconds());
        });
        timeline.getKeyFrames().addAll(kf, new KeyFrame(javafx.util.Duration.seconds(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        borderpane.setBottom(timeLabel);

    	Cell[][] Cell = minesweeper.getBoard();
        // Create a new GridPane
        GridPane gridPane = new GridPane();
        this.gridpane = gridPane;
        
        // Set the horizontal and vertical gap between the cells
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        
        // Set the padding for the grid
        gridPane.setPadding(new Insets(10));
        
        // Add some rectangles to the grid
        for (int i = 0; i < minesweeper.getRows(); i++) {
            for (int j = 0; j < minesweeper.getCols(); j++) {                
                Label label = new Label();
                label.setPrefSize(25, 25);
                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-border-color: black;");
                label.setText("");
                gridPane.add(label, j, i);
                

                final Integer inneri = new Integer(i);
                final Integer innerj = new Integer(j);
                
                label.setOnMouseClicked(e -> {
                	if(e.getButton() == MouseButton.PRIMARY) {
                		gameController.incrementTries();
                		if(!Cell[inneri][innerj].hasMine()) {
                			label.setDisable(true);
                			label.setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");
                			if(Cell[inneri][innerj].getNeighborMineCount() > 0) {
                				label.setText(String.valueOf(Cell[inneri][innerj].getNeighborMineCount()));
                				gameController.UpdateUncoveredCells();
                			}
                			else
                				openRecursively(gridPane, inneri, innerj);
                    		Cell[inneri][innerj].setRevealed(true);
                    		label.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
                		}
                		else {
                			label.setText("B");
                			gameController.Lose();
                		}
                	}
                	else if(e.getButton() == MouseButton.SECONDARY) {
	                		if(!Cell[inneri][innerj].isFlagged()) {
	                			if (gameController.getFlaggedCells() < minesweeper.getMines()) {
			                		Cell[inneri][innerj].setFlagged(true);
			                		if (Cell[inneri][innerj].isSupermine() && gameController.getTries() < 5)
			                			openSupermine(gridPane, inneri, innerj);
			                		label.setText("X");
			                		gameController.UpdateFlaggedMines(inneri, innerj);
			                		gameController.UpdateUncoveredCells();
	                			}
	                		}
	                		else {
	                			label.setText("");
		                		Cell[inneri][innerj].setFlagged(false);
		                		gameController.UpdateMinusFlaggedMines(inneri, innerj);
		                		gameController.UpdateMinusUncoveredCells();
	                		}
	
	                    	flaggedMines.setText("Flags: " +Integer.toString(gameController.getFlaggedCells())); 

                	}
                });
            }
        }
        
        borderpane.setCenter(gridPane);		
    }
    
    
    public BorderPane getBorderpane() {
		return borderpane;
	}

	public void setBorderpane(BorderPane borderpane) {
		this.borderpane = borderpane;
	}

	private void roundsPopup() {
    	String filename = "./rounds.txt";
        File file = new File(filename);
        Scanner scanner;
		try {
			scanner = new Scanner(file);
	        int[][] data = new int[5][4];
	        int lines = 0;
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine();
	            String[] values = line.split(" ");	            
	            for (int j = 0; j < 4; j++) {
	                data[lines][j] = Integer.parseInt(values[j]);
	            }
	            lines++;
	        }

	        scanner.close();
	        
	        Stage popup = new Stage();
	        popup.setTitle("Data Pop-Up");

	        GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        

            Label topLabel1 = new Label("Mines");
            Label topLabel2 = new Label("Tries");
            Label topLabel3 = new Label("Time");
            Label topLabel4 = new Label("Win");
            grid.add(topLabel1, 0, 0);
            grid.add(topLabel2, 1, 0);
            grid.add(topLabel3, 2, 0);
            grid.add(topLabel4, 3, 0);
	        // Add the data to the grid
	        for (int i = 0; i < lines; i++) {
	            for (int j = 0; j < 4; j++) {
	                Label label = new Label(Integer.toString(data[i][j]));
	                grid.add(label, j, i+1);
	            }
	        }

	        // Set the scene and show the popup
	        Scene scene = new Scene(grid, 250, 200);
	        popup.setScene(scene);
	        popup.showAndWait();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
        //return data;    	
    }
    
    private void solution(GridPane gridPane) {
    	Cell[][] Cell = minesweeper.getBoard();
    	List<Node> node = gridPane.getChildren();
    	
        for (int i = 0; i < minesweeper.getRows(); i++) {
            for (int j = 0; j < minesweeper.getCols(); j++) {                
            	if(Cell[i][j].hasMine()) {
            		((Label) node.get(i*minesweeper.getRows() + j)).setText("B");
            	}
            }
        }
        gameController.Lose();
    	
    }
    
    private void openRecursively(GridPane gridPane, int i, int j) {
    	Cell[][] Cell = minesweeper.getBoard();
    	List<Node> node = gridPane.getChildren();
    	
    	if(i < 0 || i > minesweeper.getRows()-1 || j < 0 || j > minesweeper.getCols()-1) return;
    	
    	if (Cell[i][j].isRevealed() || Cell[i][j].getNeighborMineCount() > 0 || Cell[i][j].isHasMine()) {
    		if ( Cell[i][j].getNeighborMineCount() > 0) {
    			((Label) node.get(i*minesweeper.getRows() + j)).setText(String.valueOf(Cell[i][j].getNeighborMineCount())); 
				((Label) node.get(i*minesweeper.getRows() + j)).setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)) );
				((Label) node.get(i*minesweeper.getRows() + j)).setDisable(true);
				((Label) node.get(i*minesweeper.getRows() + j)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");
				
    		}
    		return;
    	}
    	else {
    		Cell[i][j].setRevealed(true);
    		openRecursively(gridPane, i-1, j-1);
    		openRecursively(gridPane, i-1, j);
    		openRecursively(gridPane, i-1, j+1);
    		openRecursively(gridPane, i, j-1);
    		openRecursively(gridPane, i, j+1);
    		openRecursively(gridPane, i+1, j-1);
    		openRecursively(gridPane, i+1, j);
    		openRecursively(gridPane, i+1, j+1);    		
    	}
    	
		((Label) node.get(i*minesweeper.getRows() + j)).setText("");
		((Label) node.get(i*minesweeper.getRows() + j)).setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)) ); 
		((Label) node.get(i*minesweeper.getRows() + j)).setDisable(true);
		((Label) node.get(i*minesweeper.getRows() + j)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");
		gameController.UpdateUncoveredCells();
    }
    
    private void openSupermine(GridPane gridPane, int i, int j) {
    	Cell[][] Cell = minesweeper.getBoard();
    	List<Node> node = gridPane.getChildren();
    	

    	for (int index = 0; index < minesweeper.getRows(); index++) {
    		if (Cell[i][index].hasMine() && !Cell[i][index].isFlagged()) {
    			((Label) node.get(i*minesweeper.getRows() + index)).setText("X");
				((Label) node.get(i*minesweeper.getRows() + index)).setDisable(true);
				((Label) node.get(i*minesweeper.getRows() + index)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");
        		gameController.UpdateFlaggedMines(i, index); 
    		}
    		else if (Cell[i][index].getNeighborMineCount() > 0) {
    			((Label) node.get(i*minesweeper.getRows() + index)).setText(String.valueOf(Cell[i][index].getNeighborMineCount())); 
				((Label) node.get(i*minesweeper.getRows() + index)).setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)) );
				((Label) node.get(i*minesweeper.getRows() + index)).setDisable(true);
				((Label) node.get(i*minesweeper.getRows() + index)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");				
    		}
    		else {
    			((Label) node.get(i*minesweeper.getRows() + index)).setText(""); 
				((Label) node.get(i*minesweeper.getRows() + index)).setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)) );
				((Label) node.get(i*minesweeper.getRows() + index)).setDisable(true);
				((Label) node.get(i*minesweeper.getRows() + index)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");
    		}
    		gameController.UpdateUncoveredCells();
    	}
    	
    	for (int index = 0; index < minesweeper.getRows(); index++) {
    		if (Cell[index][j].hasMine() && !Cell[index][j].isFlagged()) {
    			((Label) node.get(index*minesweeper.getRows() + j)).setText("X");
				((Label) node.get(index*minesweeper.getRows() + j)).setDisable(true);
				((Label) node.get(index*minesweeper.getRows() + j)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");
        		gameController.UpdateFlaggedMines(index, j); 
    		}
    		else if (Cell[i][index].getNeighborMineCount() > 0) {
    			((Label) node.get(index*minesweeper.getRows() + j)).setText(String.valueOf(Cell[index][j].getNeighborMineCount())); 
				((Label) node.get(index*minesweeper.getRows() + j)).setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)) );
				((Label) node.get(index*minesweeper.getRows() + j)).setDisable(true);
				((Label) node.get(index*minesweeper.getRows() + j)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");				
    		}
    		else {
    			((Label) node.get(index*minesweeper.getRows() + j)).setText(""); 
				((Label) node.get(index*minesweeper.getRows() + j)).setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)) );
				((Label) node.get(index*minesweeper.getRows() + j)).setDisable(true);
				((Label) node.get(index*minesweeper.getRows() + j)).setStyle("-fx-opacity: 1.0; -fx-background-color: grey;");
    		}
    		gameController.UpdateUncoveredCells();
    	}
    }
}    	
    	

