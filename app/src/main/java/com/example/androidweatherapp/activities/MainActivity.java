package com.example.androidweatherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.androidweatherapp.api.WeatherAPI;
import com.example.androidweatherapp.api.WeatherApiInterface;
import com.example.androidweatherapp.api.models.currentweatherdata.CurrentWeatherData;
import com.example.androidweatherapp.fragments.CityWeatherFragment;
import com.example.androidweatherapp.interfaces.ItemClicked;
import com.example.androidweatherapp.R;
import com.example.androidweatherapp.service.CountdownResultReceiver;
import com.example.androidweatherapp.service.TimerService;
import com.example.androidweatherapp.storage.Weather;
import com.example.androidweatherapp.storage.WeatherDatabase;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements ItemClicked, CountdownResultReceiver.GetResultInterface {
    private CountdownResultReceiver countdownResultReceiverReceiver;
    private WeatherDatabase weatherDatabase;
    ImageView addCity;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addCity = findViewById(R.id.addCity);
        addCity.setImageDrawable(getResources().getDrawable(R.drawable.ic_location_city_24dp));
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToNextActivity();
            }
        });

        countdownResultReceiverReceiver = new CountdownResultReceiver(new Handler());
        countdownResultReceiverReceiver.setReceiver(this);
        startCounter();

        weatherDatabase = WeatherDatabase.getAppDatabase(this);
        getWeather();
    }

    private void redirectToNextActivity(){
            Intent intent = new Intent(this, AddCityActivity.class);
            startActivity(intent);
    }

    @Override
    public void onItemClicked(String index) {
        CityWeatherFragment cityWeatherFragment = (CityWeatherFragment) getSupportFragmentManager().findFragmentById(R.id.cityFragment);
        if (cityWeatherFragment != null) {
            cityWeatherFragment.updateCityView(index);

        } else {
            CityWeatherFragment newFragment = new CityWeatherFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }

    // -------------------------------- API ---------------------------------------------------------------

    private void getCurrentWeatherDataAsync(String city) {
        Retrofit retrofit = WeatherAPI.retrofitAPI();
        WeatherApiInterface weatherAPIs = retrofit.create(WeatherApiInterface.class);
        Call<CurrentWeatherData> call = weatherAPIs.getCurrentWeatherByCity(city, WeatherAPI.getApiIdentifier());

        // Synchronously Call
        try {
            CurrentWeatherData currentWeatherData = call.execute().body();
//            textView.setText(call.execute().body().getSys().getCountry());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getCurrentWeatherDataSync(String city) {
        Retrofit retrofit = WeatherAPI.retrofitAPI();
        WeatherApiInterface weatherAPIs = retrofit.create(WeatherApiInterface.class);
        Call<CurrentWeatherData> call = weatherAPIs.getCurrentWeatherByCity(city, WeatherAPI.getApiIdentifier());

        // Asynchronously Call
        call.enqueue(new Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                if (response.body() != null) {
                    CurrentWeatherData currentWeatherData = response.body();
//                    textView.setText(currentWeatherData.getSys().getCountry());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }

    // -------------------------------- Database ---------------------------------------------------------------

    private List<Weather> getWeather(){
        List<Weather> weather = weatherDatabase.weatherDao().getAll();
        return weather;
    }

    // -------------------------------- Counter Service ---------------------------------------------------------------

    public void startCounter() {
        Intent intent = new Intent(MainActivity.this, TimerService.class);
        intent.putExtra("result", countdownResultReceiverReceiver);
        startService(intent);
    }

    @Override
    public void getResult(int resultCode, Bundle resultData) {
        if(resultData!=null){
            switch (resultCode){
                case 100:
//                    textView.setText("Countdown Done");
                    startCounter();
                    break;
            }
        }
    }
}