import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
 
public class App extends Application 
{ 
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) 
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("mainscene.fxml")); 
            Scene scene = new Scene(root);
            stage.setTitle("Weather App");
            stage.setScene(scene);
            stage.show();
        } 
        
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println();

    }

}
