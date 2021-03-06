package com.example.androidweatherapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherapp.Data;
import com.example.androidweatherapp.adapters.AddCityAdapter;
import com.example.androidweatherapp.api.WeatherAPI;
import com.example.androidweatherapp.api.WeatherApiInterface;

import com.example.androidweatherapp.api.models.findcity.FindCity;

import com.example.androidweatherapp.interfaces.ItemClicked;
import com.example.androidweatherapp.R;
import com.example.androidweatherapp.storage.Weather;
import com.example.androidweatherapp.storage.WeatherDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddCityActivity extends AppCompatActivity implements ItemClicked {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter addCityAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<com.example.androidweatherapp.api.models.findcity.List> data =  new ArrayList<>();

    private WeatherDatabase weatherDatabase;
    private Data dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        EditText searchCityText = findViewById(R.id.searchCityText);

        ImageView searchView = findViewById(R.id.citySearchView);
        searchView.setImageDrawable(getResources().getDrawable(R.drawable.ic_search_24dp));

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: API City Call
                if (!TextUtils.isEmpty(searchCityText.getText())) {
                    // searchForCityAsync("London");
                    //searchForCitySync("London");
                    searchForCitySync(searchCityText.getText().toString());
                }
            }
        });


        dataHandler = Data.getInstance(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewAddCity);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        addCityAdapter = new AddCityAdapter(this, data);
        recyclerView.setAdapter(addCityAdapter);

        weatherDatabase = WeatherDatabase.getAppDatabase(this);
        //List<Weather> weathers = getWeather();


    }

    @Override
    public void onItemClicked(String string) {
        String[] out = string.split(",");
        String city = out[0];
        String countryCode = out[1].trim();
        dataHandler.addWeather(city, countryCode);
        finish();
    }

    // -------------------------------- Database ---------------------------------------------------------------

    private void addWeather(String city, String countryCode){

        Weather weather = new Weather();
        weather.city = city;
        weather.countryCode = countryCode;

        weatherDatabase.weatherDao().insert(weather);
    }

    private List<Weather> getWeather(){
        List<Weather> weather = weatherDatabase.weatherDao().getAll();
        return weather;
    }

    // -------------------------------- API ---------------------------------------------------------------

    private void searchForCityAsync(String city) {
        Retrofit retrofit = WeatherAPI.retrofitAPI();
        WeatherApiInterface weatherAPIs = retrofit.create(WeatherApiInterface.class);
        Call<FindCity> call = weatherAPIs.searchForCity(city, WeatherAPI.getApiIdentifier());

        // Synchronously Call
        try {
            FindCity findCity = call.execute().body();
//            textView.setText(call.execute().body().getSys().getCountry());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void searchForCitySync(String city) {
        Retrofit retrofit = WeatherAPI.retrofitAPI();
        WeatherApiInterface weatherAPIs = retrofit.create(WeatherApiInterface.class);
        Call<FindCity> call = weatherAPIs.searchForCity(city, WeatherAPI.getApiIdentifier());

        // Asynchronously Call
        call.enqueue(new Callback<FindCity>() {
            @Override
            public void onResponse(Call<FindCity> call, Response<FindCity> response) {
                if (response.body() != null) {
                    FindCity findCity = response.body();
//                    textView.setText(currentWeatherData.getSys().getCountry());
                    data = (ArrayList<com.example.androidweatherapp.api.models.findcity.List>) findCity.getList();
                    addCityAdapter = new AddCityAdapter(AddCityActivity.this, data);
                    recyclerView.setAdapter(addCityAdapter);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
            }
        });
    }
}
