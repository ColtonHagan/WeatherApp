package com.weather.data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TempInfo {
    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("feels_like")
    private Double feelsLike;
    @JsonProperty("temp_min")
    private Double tempMin;
    @JsonProperty("temp_max")
    private Double tempMax;
    @JsonProperty("humidity")
    private Integer humidity;

    public Double getTemp() {
        return temp;
    }
    public void setTemp(Double temp) {
        this.temp = temp;
    }
    public Double getFeelsLike() {
        return feelsLike;
    }
    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }
    public Double getTempMin() {
        return tempMin;
    }
    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }
    public Double getTempMax() {
        return tempMax;
    }
    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }
    public Integer getHumidity() {
        return humidity;
    }
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
    public boolean sameTemps() {
        double epsilon = 0.000001d;
        return (Math.abs(temp - tempMin) < epsilon) && (Math.abs(temp - tempMin) < epsilon);
    }
}
