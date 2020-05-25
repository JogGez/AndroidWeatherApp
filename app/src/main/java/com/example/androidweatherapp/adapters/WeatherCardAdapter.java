package com.example.androidweatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherapp.Data;
import com.example.androidweatherapp.interfaces.ItemClicked;
import com.example.androidweatherapp.R;
import com.example.androidweatherapp.viewholders.WeatherCardViewHolder;
import com.example.androidweatherapp.util.WeatherImageHelper;

public class WeatherCardAdapter extends RecyclerView.Adapter<WeatherCardViewHolder> {

    //temp
    Data data;
    String[] cities;
    String[] description;
    int[] temp; //using same array for current, max and min example
    Context context;
    WeatherImageHelper helper;

    ItemClicked activity;

    public WeatherCardAdapter(Context context, Data data) {
        this.helper = new WeatherImageHelper();
        this.context = context;
        this.data = data;
        this.cities = data.getCities();
        this.description = data.getDescription();
        this.temp = data.getTemp();
    }

    @NonNull
    @Override
    public WeatherCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout weatherCard = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.weathercard, parent, false);
        TextView city = weatherCard.findViewById(R.id.cityNameWeatherCard);
        TextView weatherDescription = weatherCard.findViewById(R.id.weatherDescriptionWeatherCard);
        TextView currentTemp = weatherCard.findViewById(R.id.currentTempWeatherCard);
        TextView maxTemp = weatherCard.findViewById(R.id.maxTempWeatherCard);
        TextView minTemp = weatherCard.findViewById(R.id.minTempWeatherCard);
        ImageView weatherImage = weatherCard.findViewById(R.id.weatherImageViewWeatherCard);
        return new WeatherCardViewHolder(weatherCard, city, weatherDescription, currentTemp, maxTemp, minTemp, weatherImage, context);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherCardViewHolder holder, int position) {
        holder.itemView.setTag(cities[position]);
        holder.setCity(cities[position]);
        holder.setWeatherDescription(description[position]);
        holder.setCurrentTemp(temp[position] + "°");
        holder.setMaxTemp(temp[position] + "°");
        holder.setMinTemp(temp[position] + "°");
        holder.setWeatherImage(context.getResources().getDrawable(helper.getWeatherImage(description[position])));

    }

    @Override
    public int getItemCount() {
        return cities.length;
    }
}
