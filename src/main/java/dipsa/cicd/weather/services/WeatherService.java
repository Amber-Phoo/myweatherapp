package dipsa.cicd.weather.services;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import dipsa.cicd.weather.models.Description;
import dipsa.cicd.weather.models.Weather;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {

    public static final String ENDPOINT = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${OPENWEATHERMAP_KEY}")
    private String apiKey;

    public Weather getWeather(String city) {
        Weather weather;
        String url = UriComponentsBuilder.fromUriString(ENDPOINT)
                .queryParam("q", city.replaceAll(" ", "+"))
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try {
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            weather = new Weather(500);
            weather.setMessage(ex.getMessage());
            return weather;
        }

        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        JsonObject json = reader.readObject();

        weather = Weather.create(json);
        List<Description> descriptions = json.getJsonArray("weather").stream()
                .map(v -> Description.create((JsonObject)v))
                .toList();
        weather.setDescriptions(descriptions);

        return weather;
    }
    
}
