package com.example.androidweatherapp;

import com.example.androidweatherapp.util.WeatherImageHelper;

public class Data {

    //TODO: API DATA

    String[] cities = {"Odense", "København", "Vejle", "Korsør", "Slagelse", "Vemmelev", "Århus", "Aalborg", "Tarm", "Vordingborg"};
    String[] description = {"Rain", "Drizzle", "Atmosphere", "Clear", "Rain", "Clouds", "Rain", "Drizzle", "Drizzle", "Extreme"};
    int[] temp = {12, 16, 13, 13, 14, 18, 10, 12, 12, 9};

    public Data(){
        WeatherImageHelper weatherImageHelper = new WeatherImageHelper();
    }

    public String[] getCities() {
        return cities;
    }

    public String[] getDescription() {
        return description;
    }

    public int[] getTemp() {
        return temp;
    }

}
