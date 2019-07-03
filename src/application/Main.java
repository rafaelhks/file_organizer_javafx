package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
	        
	        Scene scene = new Scene(root);

	        //stage.initStyle(StageStyle.UNDECORATED);
	        stage.resizableProperty().setValue(Boolean.FALSE);
	        stage.setScene(scene);
	        stage.setTitle("FileOrganizer v0.0.1");
	        stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
