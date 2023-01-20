package com.weather;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.data.CurrentWeatherInfo;
import com.weather.data.ForecastInfo;
import com.weather.data.LocationInfo;

public class ApiManager {
    //This should be stored in a seperate file for security, but I am lazy
    private final String API_KEY = "27a0c274e429e961ddba9463276bacc6";
    private final String URL = "https://api.openweathermap.org/";

    private HttpClient client = HttpClient.newHttpClient();
    private ObjectMapper mapper = new ObjectMapper();


    public CurrentWeatherInfo getCurrentWeather (double lon, double lat) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header("accept", "application/json")
            .uri(URI.create(URL + "data/2.5/weather?lat=" + lat + "&lon=" + lon + "&units=imperial&appid=" + API_KEY))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        CurrentWeatherInfo currentWeather = mapper.readValue(response.body(), new TypeReference<CurrentWeatherInfo>() {});
        return currentWeather;
    }

    public ForecastInfo getForecast (double lon, double lat) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header("accept", "application/json")
            .uri(URI.create(URL + "data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&units=imperial&appid=" + API_KEY))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ForecastInfo forecast = mapper.readValue(response.body(), new TypeReference<ForecastInfo>() {});
        return forecast;
    }

    public double[] getLocation (String zip, String countryCode) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .GET()
            .header("accept", "application/json")
            .uri(URI.create(URL + "geo/1.0/zip?zip=" + zip + "," + countryCode + "&appid=" + API_KEY))
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        LocationInfo location = mapper.readValue(response.body(), new TypeReference<LocationInfo>() {});
        return location.getLongLat();
    }
}
