package org.sber;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.sber.exception.WeatherException;
import org.sber.model.CurrentWeather;
import org.sber.weather.WeatherService;
import org.sber.weather.WeatherServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient();
        //истекает 25.12.2024
        String key = "0dc5fc36629e4be0811195309241112";
        ObjectMapper objectMapper = new ObjectMapper();

        WeatherService weatherService = new WeatherServiceImpl(client, key, objectMapper);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите город: ");
        String city = scanner.nextLine();
        try {
            CurrentWeather currentWeather = weatherService.getCurrentWeather(city);
            System.out.printf("""
                            температура: %sС
                            облачность: %s%%
                            """,
                    currentWeather.getCurrent().getTemp_c(),
                    currentWeather.getCurrent().getCloud()
            );
        } catch (WeatherException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}