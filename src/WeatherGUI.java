import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
 
public class WeatherGUI extends Application 
{ 
    public static void main(String[] args)
    {
        launch(args);
    }

    //displays the app
    public void start(Stage stage) 
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));//gets the file of the GUI created in JavaFx Scenebuilder
            Scene scene = new Scene(root);
            stage.setTitle("OpenWeatherMapAPI");
            stage.setScene(scene);
            stage.show();
        } 
        
        //displays error message
        catch(Exception e)
        {
            System.out.println();
            System.out.println(e);
        }

        System.out.println();

    }

}
