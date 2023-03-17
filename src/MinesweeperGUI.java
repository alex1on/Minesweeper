import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MinesweeperGUI extends Application {
//	private Minesweeper minesweeper;
//	
//	public MinesweeperGUI(Minesweeper minesweeper) {
//		this.minesweeper = minesweeper;
//	}
    
    @Override
    public void start(Stage primaryStage) {
        // create the root layout
        BorderPane root = new BorderPane();

        // create the menu bar
        TopMenuBar menuBar = new TopMenuBar(primaryStage, root);    	

        // set the menu bar at the top of the layout
        root.setTop(menuBar);
        
        // Create a button
        //Button btn = new Button("Click me!");
        
        // Create a layout and add the button to it
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
        //root.setLeft(btn);
        
        // Create a scene with the layout
        Scene scene = new Scene(root, 500, 400);
        
        // Set the scene on the stage and show it
        primaryStage.setScene(scene);
        primaryStage.setTitle("MediaLab Minesweeper");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}