import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AppController 
{
    @FXML
    private TextField SearchBar;

    @FXML
    private Text WeatherInfo;

    String Input = "";
    StringBuilder GeoCodeData = new StringBuilder();
    WeatherData data = new WeatherData();
    StringBuilder WeatherData = new StringBuilder();

    @FXML
    void ButtonClick(ActionEvent event) 
    {
        Input=SearchBar.getText();

        try
        {        
            Input=Input.replaceAll(" ","_");//spaces need to be changed to "_" for the url
            GeoCodeData = data.getGeoCodeData(Input);
            data.FindLatAndLong(GeoCodeData);
            WeatherData = data.getWeatherData();
            data.FindWeatherInfo(WeatherData);    
        }

        catch(Exception e)
        {
            e.printStackTrace();    
        }

        WeatherInfo.setText("Temperature : " + data.getTemperature() + 
                            "\nWeather : " + data.getWeather() + 
                            "\nDescription : " + data.getDescription() + 
                            "\nHumidity : " + data.getHumidity() + 
                            "\nWind speed : " + data.getWindspeed());

    }

}
