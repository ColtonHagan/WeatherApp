package com.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.data.CountryCodeInfo;
import com.weather.data.ForecastInfo;

// I think I am done but if I was to finish this
// TODO: ADD error handling to API calls
//      GUI
public class App 
{
    private static ApiManager apiManager = new ApiManager();
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static List<CountryCodeInfo> countries;
    public static void main( String[] args) throws IOException, InterruptedException {
        readCountryInfo();
        String[] loc = getLocation();
        double[] longlat = apiManager.getLocation(loc[0], loc[1]);
        getWeather(longlat);
    }

    private static void readCountryInfo() throws StreamReadException, DatabindException, IOException {
        try {
            File json = new File("weather-app\\src\\main\\java\\com\\weather\\data\\CountryCodes.json");
            ObjectMapper mapper = new ObjectMapper();
            countries = mapper.readValue(json, new TypeReference<List<CountryCodeInfo>>() {});
        } catch (Exception e) {
            System.out.println("This file should be here, stop messing with things");
        }
    }

    private static String[] getLocation() throws IOException {
        String[] location = new String[2];
        System.out.print("Enter zip code: ");
        location[0] = parseZip();
        System.out.print("Enter country name: ");
        location[1] = parseCountryCode();
        if(!validString(location[0]) || !validString(location[1])) { //lazy emergency catch all
            errMsg("Something went wrong entering location info, please try again");
            getLocation();
        }
        System.out.println();
        return location;
    }

    private static void getWeather(double[] loc) throws IOException, InterruptedException {
        String curr = apiManager.getCurrentWeather(loc[0], loc[1]).toString();
        System.out.println(curr);
        
        System.out.print("Do you want a 5-day forecast? (Y/N): ");
        ForecastInfo forecast = getForecast(loc);
        if(forecast != null) {
            System.out.println(forecast.dailyForecast());
        }
    }

    private static ForecastInfo getForecast(double[] loc) throws IOException, InterruptedException {
        String response = reader.readLine();
        if(response.trim().toLowerCase().equals("y")) {
            return apiManager.getForecast(loc[0], loc[1]);
        } else if (!response.trim().toLowerCase().equals("n")) {
            errMsg("Response should be Y (yes) or N (no)");
            return getForecast(loc);
        }
        return null;
    }

    private static String parseZip() throws IOException {
        String zip = reader.readLine();
        boolean valid = true;
        if(zip.length() != 5) {
            errMsg("Zip code should be 5 digits long");
            valid = false;
        } else if (!isNumeric(zip)) {
            errMsg("Zip code should only contain numeric digits");
            valid = false;
        }
        if(!valid) parseZip();
        return zip;
    }

    private static String parseCountryCode() throws IOException {
        String country = reader.readLine();
        boolean valid = true;
        if(!isAlphetic(country)) {
            errMsg("Country name should not include numbers");
            valid = false;
        }
        String code = findCode(country);
        if(code == null) {
            errMsg("Country with this name is not found within our databases"); //databases = random file I found on github KEKW
            valid = false;
        }
        if(!valid) parseCountryCode();
        return code;
    }

    private static String findCode(String country) {
        for (CountryCodeInfo currCountry : countries) {
            if(currCountry.getName().equals(country)) {
                return currCountry.getCountryCode();
            }
        }
        return null;
    }

    private static boolean isNumeric(String s) {
        return s.matches("\\d+");
    }

    private static boolean isAlphetic(String s) { //is alphetic a word?
        return s.matches("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}");
    }

    private static void errMsg(String msg) {
        System.out.println("Error: " + msg + ", please re-enter");
    } 

    private static boolean validString(String s) {
        return s != null && !s.trim().isEmpty();
    }
}
