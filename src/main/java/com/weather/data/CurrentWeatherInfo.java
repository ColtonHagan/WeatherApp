package com.weather.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherInfo {
	@JsonProperty("weather")
	private List<WeatherInfo> weather = null;
	@JsonProperty("main")
	private TempInfo temp;
	@JsonProperty("wind")
	private WindInfo wind;
	@JsonProperty("dt")
	private Integer dt;

	public List<WeatherInfo> getWeather() {
		return weather;
	}

	public void setWeather(List<WeatherInfo> weather) {
		this.weather = weather;
	}

	public TempInfo getTemp() {
		return temp;
	}

	public void setTemp(TempInfo temp) {
		this.temp = temp;
	}

	public WindInfo getWind() {
		return wind;
	}

	public void setWind(WindInfo wind) {
		this.wind = wind;
	}

	public Integer getDt() {
		return dt;
	}

	public void setDt(Integer dt) {
		this.dt = dt;
	}

	private String getFormatedTime() {
		Calendar time = Calendar.getInstance();
		time.setTimeInMillis(dt*1000L);

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy hh:mma z");
		return format.format(time.getTime()).replace("AM", "am").replace("PM","pm");
	}
	private String formatTemp() {
		if(temp.sameTemps()) {
			return " " + temp.getTemp() + "F";
		}
		StringBuilder str = new StringBuilder("\n");
		str.append("        Current: " + temp.getTemp() + "F\n");
		str.append("        High: " + temp.getTempMax() + "F\n");
		str.append("        Low: " + temp.getTempMin() + "F");
		return str.toString();
	}

	private String formatType() {
		StringBuilder str = new StringBuilder();
		for (WeatherInfo weatherInfo : weather) {
			String desc = capitalizeFirstLetter(weatherInfo.getDescription());

			str.append("   " + weatherInfo.getType());
			if(!desc.equals(weatherInfo.getType()))
				str.append(": " + capitalizeFirstLetter(weatherInfo.getDescription()));
			str.append("\n");
		}
		return str.toString();
	}

	private String weatherSummary() {
		StringBuilder str = new StringBuilder();
		str.append("At " + getFormatedTime() + "\n");
		str.append(formatType());
		str.append("   Temperature:" + formatTemp() + "\n");
		str.append("   Humidity: " + temp.getHumidity() +"%\n");
		str.append("   Wind: " + wind.getSpeed() + "mph\n");
		return str.toString();
	}

	private String capitalizeFirstLetter(String original) {
		if (original == null || original.trim().length() == 0)
			return original;
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

	@Override
	public String toString() {
		return weatherSummary();
	}
}
