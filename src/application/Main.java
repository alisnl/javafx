package application;
	
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		 try {
			    AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("MainView.fxml"));
			    
	            Scene scene = new Scene(page);
	            primaryStage.setScene(scene);
	            primaryStage.setTitle("Client- Server- Oberäche");
	            primaryStage.show();
	        } catch (Exception ex) {
	            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
