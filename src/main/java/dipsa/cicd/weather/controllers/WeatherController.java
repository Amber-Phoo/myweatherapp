package dipsa.cicd.weather.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dipsa.cicd.weather.models.Weather;
import dipsa.cicd.weather.services.WeatherService;

@Controller
@RequestMapping(path="/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherSvc;

    @GetMapping
    public ModelAndView getWeather(@RequestParam(defaultValue = "singapore") String city) {
        final ModelAndView mvc = new ModelAndView();

        Weather result = weatherSvc.getWeather(city);

        if (result.getStatus() >= 400) {
            mvc.setViewName("error");
            mvc.setStatus(HttpStatus.resolve(result.getStatus()));
            mvc.addObject("message", result.getMessage());
            mvc.addObject("status", result.getStatus());
            return mvc;
        }

        mvc.setViewName("weather");
        mvc.setStatus(HttpStatus.OK);
        mvc.addObject("result", result);

        return mvc;
    }
    
}
