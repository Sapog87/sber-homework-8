package org.sber.weather;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.sber.exception.WeatherException;
import org.sber.model.CurrentWeather;
import org.sber.model.ErrorResponse;

import java.io.IOException;
import java.util.Objects;

public class WeatherServiceImpl implements WeatherService {
    private static final String BASE_URL = "https://api.weatherapi.com/v1";
    private final String key;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final HttpUrl httpBaseUrl;

    public WeatherServiceImpl(OkHttpClient client, String key, ObjectMapper objectMapper) {
        this.client = client;
        this.key = key;
        this.objectMapper = objectMapper;
        this.httpBaseUrl = HttpUrl.parse(BASE_URL);
    }

    @Override
    public CurrentWeather getCurrentWeather(String city) {
        if (Objects.isNull(city) || city.isBlank()) {
            throw new IllegalArgumentException("название города не может быть пустым");
        }

        HttpUrl url = httpBaseUrl.newBuilder()
                .addPathSegment("current.json")
                .addQueryParameter("key", key)
                .addQueryParameter("q", city)
                .addQueryParameter("lang", "ru")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            try {
                String json = Objects.requireNonNull(response.body()).string();
                if (response.isSuccessful()) {
                    return objectMapper.readValue(json, CurrentWeather.class);
                } else {
                    Object message;
                    if (response.code() >= 400 && response.code() < 500) {
                        ErrorResponse errorResponse = objectMapper.readValue(json, ErrorResponse.class);
                        message = errorResponse.getError().getMessage();
                    } else {
                        message = response;
                    }
                    throw new WeatherException("не удалось получить данные {%s}".formatted(message));
                }
            } catch (JsonMappingException | JsonParseException e) {
                throw new WeatherException("не удалось спарсить json", e);
            }
        } catch (IOException e) {
            throw new WeatherException("ошибка подключения к серверу", e);
        }
    }
}
