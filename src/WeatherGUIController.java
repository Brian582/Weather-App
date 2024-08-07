import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.lang.Math;

public class WeatherGUIController 
{
    @FXML
    private TextField SearchBar;

    @FXML
    private Text WeatherInfo;

    String Input;
    WeatherAPICode weather= new WeatherAPICode();

    @FXML
    void ButtonClick(ActionEvent event) 
    {
        Input=SearchBar.getText();

        try
        {
            Input=Input.replaceAll(" ","_");//replaces the spaces of the Input between more than 2 words with " _ "
            weather.GeoCodingAPI(Input);//gets weather data from the API
        }

        //displays error message
        catch(Exception e)
        {
            System.out.println(e);    
        }

        //shows the text in the app
        WeatherInfo.setText("Weather:   " +  weather.getWeather() + "\nTemperature:   " + Math.round(weather.getTemperature()) + " \u00B0F");// the \u00B0 is for degree sign
    }

}
