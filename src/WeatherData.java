import java.net.URL;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import java.lang.Math;
import java.net.MalformedURLException;

public class WeatherData 
{
    private String longitude;
    private String latitude;
    private String temperature;
    private String weather;
    private String description;
    private String humidity;
    private String windspeed;
    private String API_key;

    public WeatherData()
    {
        longitude="";
        latitude="";
        temperature="";
        humidity="";
        windspeed="";
        weather="";
        description="";
        API_key= (PLACE API KEY HERE)
    }

    //gets geocoding data from the GeoCoding API
    public StringBuilder getGeoCodeData(String input) throws MalformedURLException
    {
        StringBuilder GeoCodeData = new StringBuilder();

        try
        {
            String urlGeocode= "https://api.openweathermap.org/geo/1.0/direct?q=" + input;
            urlGeocode+="&appid="+ API_key;

            //sets request to get data from the API
            URL url =new URL(urlGeocode); 
            HttpsURLConnection urlconnection = (HttpsURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.connect();
            
            int responseCode = urlconnection.getResponseCode();

            //checks for any connection problems when getting the data  
            if( responseCode != 200)  
            {
                System.out.println("Error in sending a GET request");
            }
            
            else
            {
                //puts all the data into a string
                Scanner scan = new Scanner(urlconnection.getInputStream());
                while (scan.hasNext())
                {
                    GeoCodeData.append(scan.nextLine());
                }
                
                scan.close();
                urlconnection.disconnect();
            }

            return GeoCodeData;
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }

        return GeoCodeData;
    }

    //searches for latitude and longitude in the GeoCodeData string
    public void FindLatAndLong(StringBuilder GeoCodeData) throws ParseException
    {
        JSONParser parser= new JSONParser();
        JSONArray dataobject =(JSONArray) parser.parse(String.valueOf(GeoCodeData)); 
        JSONObject countryData = (JSONObject) dataobject.get(0);

        latitude = String.valueOf(countryData.get("lat"));
        longitude = String.valueOf(countryData.get("lon"));
    }

    //gets the Weather Data from the API
    public StringBuilder getWeatherData() 
    {
        StringBuilder weatherData = new StringBuilder();

        try
        {
            String urlWeather = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude;
            urlWeather+="&lon=" + longitude;
            urlWeather+="&appid=" + API_key;

            //sets request to get data from API
            URL url =new URL(urlWeather);
            HttpsURLConnection urlconnection = (HttpsURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.connect();
            
            int responseCode = urlconnection.getResponseCode();

        //checks for any connection problems when getting the data  
        if( responseCode != 200)  
        {
            System.out.println("Error in sending a GET request");
        }

        else
        {
            Scanner scan = new Scanner(urlconnection.getInputStream());
            while (scan.hasNext())
            {
                weatherData.append(scan.nextLine()); 
            }        
            scan.close();
        }

            urlconnection.disconnect();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return weatherData;
    }

    //searches for the weather information from the weather data string
    public void FindWeatherInfo(StringBuilder weatherData) throws ParseException
    {
        JSONParser parser= new JSONParser();
        JSONObject dObject = (JSONObject) parser.parse(String.valueOf(weatherData));
        JSONObject main = (JSONObject) dObject.get("main");
    
        temperature= convertTemperature(String.valueOf(main.get("temp")));

        JSONArray weath = (JSONArray) dObject.get("weather");
        weather = (String) ((JSONObject) weath.get(0)).get("main");
        description = (String) ((JSONObject) weath.get(0)).get("description");
        humidity = String.valueOf(((JSONObject) dObject.get("main")).get("humidity"));
        windspeed = String.valueOf(((JSONObject) dObject.get("wind")).get("speed"));

    }

    //Converts temperature from Kelvin to fahrenheit
    public String convertTemperature(String temperature)
    {
        String tempString=" ";
        double temp = Double.parseDouble(temperature);
        double fraction = (double) 9/5;

        temp=(temp-273.15)*(fraction) + 32;
        temp=Math.round(temp);

        tempString= String.valueOf(temp)+ " \u00B0F";

        return tempString;
    }

    public String getTemperature()
    {
        return temperature;
    }

    public String getLatitude() 
    {
        return latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public String getWeather()
    {
        return weather;
    }

    public String getDescription()
    {
        return description;
    }

    public String getHumidity()
    {
        return humidity;
    }
    public String getWindspeed()
    {
        return windspeed;
    }
    
}
