import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;

public class WeatherAPICode 
{
    private String longitude;
    private String latitude;
    private String temperature;
    private String weather;
    private String API_key;
    private StringBuilder sb;//sb means stringbuilder

    //Constructor
    public WeatherAPICode()
    {
        longitude="";
        latitude="";
        temperature="";
        weather="";                                
        API_key= "johnDoe124141141";//the API key used to get access for the OpenWeatherMap API, but this isn't the real API key   
    }

    //gets the data from the GeoCoding API as a string
    public void GeoCodingAPI(String input) throws IOException
    {
        String urlGeocode= "https://api.openweathermap.org/geo/1.0/direct?q=";
        urlGeocode+=input+"&appid=";
        urlGeocode+=API_key;//adds API key to url

        URL urlObj=new URL(urlGeocode);
        HttpsURLConnection urlconnection = (HttpsURLConnection) urlObj.openConnection();
        urlconnection.setRequestMethod("GET");//gets the data from the API
        int responseCode = urlconnection.getResponseCode();//gets response code of URL

        sb = new StringBuilder();

        //checks for any connection problems when getting the data
        if( responseCode== HttpsURLConnection.HTTP_OK) //HTTP_Ok would be number 200, which means connnection is ok
        {
            Scanner scan = new Scanner(urlconnection.getInputStream());
        
            while (scan.hasNext())
            {
                sb.append(scan.nextLine());//adds API data to stringbuilder 
            }
            
            scan.close();
        }

        else
        {
            System.out.println("Error in sending a GET request");
        }
        
        urlconnection.disconnect();

        FindLonAndLat();
        WeatherAPI();
    }

    //reads through the data to find the longitude and latitude from the Geocode API
    public void FindLonAndLat()
    {
        String UrlString = sb.toString();//gets the data from the GeoCoding API as a string
        Scanner scan2 = new Scanner(UrlString);
        String token = "";
        
        //reads through every token to find longitude and latitude
        while (scan2.hasNext())
        {
            scan2.useDelimiter(",|:");//splits the string by the commas and ":"

            token=scan2.next();
            token=token.replace("\"","");//removes the double quotes
            
            if( token.equals("lat") )//in the data, "lat" is for latititude
            {
                latitude=scan2.next();
            }

            else if( token.equals("lon") )//in the data, "lon" is for longitude
            {
                longitude=scan2.next();
            }

        }

        scan2.close();
    }

    //gets the data from the Weather API as a string
    public void WeatherAPI() throws IOException
    {
        String urlWeather="https://api.openweathermap.org/data/2.5/weather?lat=";
        urlWeather+=latitude+"&lon=";//adds latitude to url
        urlWeather+=longitude+"&appid=";//adds longitude to url
        urlWeather+=API_key;//adds API key to url

        URL urlObj=new URL(urlWeather);

        HttpsURLConnection urlconnection2 = (HttpsURLConnection) urlObj.openConnection();
        urlconnection2.setRequestMethod("GET");//gets the data from the API
        int responseCode = urlconnection2.getResponseCode();

        sb = new StringBuilder();

        if( responseCode== HttpsURLConnection.HTTP_OK)
        {
            Scanner scan = new Scanner(urlconnection2.getInputStream());
            
            while (scan.hasNext())
            {
                sb.append(scan.nextLine());//adds API data to stringbuilder 
            }
            
            scan.close();
        }
        
        else
        {
            System.out.println("Error in sending a GET request");
        }

        urlconnection2.disconnect();

        FindWeatherInfo();
    }

    //reads through the data to find the current weather and temperature
    public void FindWeatherInfo()
    {
         String urlString2 = sb.toString();//gets the data from the WeatherMap API as a string
         urlString2=urlString2.replaceAll(","," ");//replaces commas with spaces
         urlString2=urlString2.replaceAll(":"," ");//replaces : with spaces
         Scanner scan2 = new Scanner(urlString2);
 
         String token2="";
 
         //reads through every token to find weather and temperature
        while (scan2.hasNext())
        {
            scan2.useDelimiter(" ");//splits string by using commas and ":"
          
            token2=scan2.next();
            token2=token2.replaceAll("\\p{P}","");//removes the double quotes, brackets and everything

            //in the API data, "weather" is where the weather is located
            if( token2.equals("weather") )
            {
                
                for(int n=0;n<5;n++)//goes through the next tokens to get the weather
                {
                    token2=scan2.next();
                    token2=token2.replaceAll("\\p{P}","");

                    //inside "main" is where the weather is
                    if( token2.equals("main") )
                    {
                        token2=scan2.next();
                        token2=token2.replaceAll("\\p{P}","");//removes any punctuations
                        weather=token2;

                        break;
                    }
                }

            }

            //in the API data, "main" would be where the temperature is located
            else if( token2.equals("main") )
            {
                //goes through the other tokens to get the temperature
                for(int n=0;n<3;n++)
                {
                    token2=scan2.next();
                    token2=token2.replaceAll("\\p{P}","");//removes the double quotes, brackets and everything

                    if( token2.equals("temp") )
                    {
                        token2=scan2.next();
                        temperature=token2;
                        break;
                    }
                }
                
            }
 
        }
        
        scan2.close();
    }

    //returns the latitude
    public String getLatitude()
    {
        return latitude;
    }

    //returns the longitude
    public String getLongitude()
    {
        return longitude;
    }

    //returns the weather
    public String getWeather()
    {
        return weather;
    }

    //returns the temperature, but first converts from kelvin to fahrenheit  (temperature from API is in Kelvin)
    public Double getTemperature()
    {
        double temp = Double.parseDouble(temperature);
        double fraction = (double) 9/5;//this is part of the conversion equation

        temp=(temp-273.15)*(fraction) + 32;//equation that converts kelvin to fahrenheit
        
        return temp;
    }

}
