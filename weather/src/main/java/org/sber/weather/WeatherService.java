package org.sber.weather;

import org.sber.model.CurrentWeather;

public interface WeatherService {
    CurrentWeather getCurrentWeather(String city);
}
