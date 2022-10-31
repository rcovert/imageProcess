package imageProcess;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//System.out.println("new test is: " + getClass().getResource("FinalScene.fxml").getPath());
			//System.out.println("test is:  " + getClass().getResource("FinalScene.fxml"));
			// make sure to place this in resources so that maven can find it
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("FinalScene.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("ImageProcessor");
			// new test
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
