package com.weather.data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastInfo {
    @JsonProperty("cnt")
    private Integer timeStamps;
    @JsonProperty("list")
    private List<CurrentWeatherInfo> weather = null;

    public Integer getTimeStamps() {
        return timeStamps;
    }
    public void setTimeStamps(Integer timeStamps) {
        this.timeStamps = timeStamps;
    }
    public List<CurrentWeatherInfo> getWeather() {
        return weather;
    }
    public void setWeather(List<CurrentWeatherInfo> weather) {
        this.weather = weather;
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (CurrentWeatherInfo currentWeatherInfo : weather) {
            str.append(currentWeatherInfo.toString());
        }
        return str.toString();
    }

    public String dailyForecast() {
        StringBuilder str = new StringBuilder();
        for(int i = 7; i <= timeStamps; i+=8) {
            str.append(weather.get(i).toString());
        }
        return str.toString();
    }
}
