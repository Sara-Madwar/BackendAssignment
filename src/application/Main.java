package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override 
    public void start(Stage primaryStage) { 
      try { 
Parent root = FXMLLoader.load(this.getClass().getResource("/Main.fxml")); 
Scene scene = new Scene(root); 
  

scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); 

primaryStage.setScene(scene); 
  
primaryStage.setTitle("GUI-Lab"); 
primaryStage.show(); 
       } catch(Exception e) { 
e.printStackTrace(); 
       } 
     }//End start 

     public static void main(String[] args) { 
launch(args); 
     } 
   }//end Class Main 
