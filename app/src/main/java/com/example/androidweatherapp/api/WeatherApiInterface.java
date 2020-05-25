package com.example.androidweatherapp.api;

import com.example.androidweatherapp.api.models.currentweatherdata.CurrentWeatherData;
import com.example.androidweatherapp.api.models.findcity.FindCity;
import com.example.androidweatherapp.api.models.forecastweatherdata.WeatherForecast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterface {

    @GET("data/2.5/weather")
    Call<CurrentWeatherData> getCurrentWeatherByLatLon(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String apiKey);

    @GET("/data/2.5/weather")
    Call<CurrentWeatherData> getCurrentWeatherByCity(@Query("q") String city, @Query("appid") String apiKey);

    @GET("/data/2.5/find")
    Call<FindCity> searchForCity(@Query("q") String city, @Query("appid") String apiKey);

    @GET("/data/2.5/forecast")
    Call<WeatherForecast> getWeatherForecastByCity(@Query("q") String city, @Query("appid") String apiKey);

    @GET("/data/2.5/forecast")
    Call<WeatherForecast> getWeatherForecastByLatLon(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String apiKey);

    @GET("/data/2.5/forecast")
    void getWeatherInfo (@Query("lat") String latitude,
                         @Query("lon") String longitude,
                         @Query("cnt") String cnt,
                         @Query("appid") String appid,
                         Callback<CurrentWeatherData> cb);

}
