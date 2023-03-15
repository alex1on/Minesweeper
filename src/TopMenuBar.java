import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class TopMenuBar extends MenuBar {	
	public TopMenuBar() {	
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
	    MenuItem exitItem = new MenuItem("Exit");
	    
	    MenuItem roundsItem = new MenuItem("Rounds");
	    MenuItem solutionItem = new MenuItem("Solutions");    
	
	    // add menu items to the menu
	    appMenu.getItems().addAll(createItem, loadItem, startItem, exitItem);
	    detailsMenu.getItems().addAll(roundsItem, solutionItem);
	
	    // add the menu to the menu bar
	    this.getMenus().add(appMenu);
	    this.getMenus().add(detailsMenu);    	    
	}
	
    private void createPopup() {
        // Create the popup dialog
        // Create the popup dialog
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Create Scenario");

        // Create the content for the popup dialog
        Label label = new Label("Enter the game scenario.");        
        // create a text input dialog
//        TextInputDialog td = new TextInputDialog("enter any text");
//        td.showAndWait();
        
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
    
    private void loadPopup() {
        // Create the popup dialog
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Load Scenario");

        // Create the content for the popup dialog
        Label label = new Label("Enter the game scenario.");        
        // create a text input dialog
//        TextInputDialog td = new TextInputDialog("enter any text");
//        td.showAndWait();
        
        //Defining the Last Name text field
        final TextField scenario = new TextField();
        scenario.setPromptText("Enter the game scenario.");
        Button submit = new Button("Submit");
        submit.setOnAction(event -> {
            // Close the popup
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
    
    private void roundsPopup() {
    	
    }
}
