package com.weather.data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {
    @JsonProperty("main")
    private String type;
    @JsonProperty("description")
    private String description;
    public String getType() {
        return type;
    }
    public void setType(String main) {
        this.type = main;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
