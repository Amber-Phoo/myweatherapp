package dipsa.cicd.weather.models;

import java.util.LinkedList;
import java.util.List;

import jakarta.json.JsonObject;

public class Weather {

    private String cityName;
    private String country;
    private Float temperature;
    private Integer status;
    private String message = "";
    private List<Description> descriptions = new LinkedList<>();

    public Weather() { }
    public Weather(Integer status) { 
        this.status = status;
    }

    public String getCityName() {
        return cityName;
    }
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public Float getTemperature() {
        return temperature;
    }
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }
    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }
    public void addDescription(Description description) {
        this.descriptions.add(description);
    }

    public static Weather create(JsonObject json) {
        final Weather weather = new Weather();
        weather.setCityName(json.getString("name"));
        weather.setStatus(json.getInt("cod"));
        JsonObject tmp = json.getJsonObject("main");
        weather.setTemperature((float)tmp.getJsonNumber("temp").doubleValue());
        tmp = json.getJsonObject("sys");
        weather.setCountry(tmp.getString("country").toLowerCase());
        return weather;
    }
    
}
