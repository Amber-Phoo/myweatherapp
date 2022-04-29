package dipsa.cicd.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import dipsa.cicd.weather.models.Weather;
import dipsa.cicd.weather.services.WeatherService;

@SpringBootTest
@AutoConfigureMockMvc
class YetAnotherWeatherAppApplicationTests {

	@Autowired
	private WeatherService weatherSvc;

	@Autowired
	private MockMvc mvc;

	@Test
	void shouldFindWeather() {
		Weather weather = weatherSvc.getWeather("singapore");
		assertEquals(200, weather.getStatus(), "Cannot find Singapore weather");
	}

	@Test
	void shouldNotFindWeather() {
		String hexString = UUID.randomUUID().toString().substring(0, 8);
		Weather weather = weatherSvc.getWeather(hexString);
		assertNotEquals(200, weather.getStatus(), "Cannot find %s weather".formatted(hexString));
	}

	@Test
	void shouldFindWeatherMvc() throws Exception {

		RequestBuilder req = MockMvcRequestBuilders.get("/weather")
				.queryParam("city", "tokyo")
				.accept(MediaType.TEXT_HTML_VALUE);

		MvcResult result = mvc.perform(req).andReturn();
		MockHttpServletResponse resp = result.getResponse();

		assertEquals(HttpStatus.OK.value(), resp.getStatus(), "Cannot find Tokyo weather");
	}

	@Test
	void shouldNotFindWeatherMvc() throws Exception {
		String hexString = UUID.randomUUID().toString().substring(0, 8);

		RequestBuilder req = MockMvcRequestBuilders.get("/weather")
				.queryParam("city", hexString)
				.accept(MediaType.TEXT_HTML_VALUE);

		MvcResult result = mvc.perform(req).andReturn();
		MockHttpServletResponse resp = result.getResponse();

		assertTrue(resp.getStatus() >= 400, "Cannot find %s weather".formatted(hexString));
	}

}
