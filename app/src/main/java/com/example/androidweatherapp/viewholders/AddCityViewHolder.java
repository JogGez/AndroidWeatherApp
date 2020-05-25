package com.example.androidweatherapp.viewholders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherapp.interfaces.ItemClicked;

public class AddCityViewHolder extends RecyclerView.ViewHolder {

    private TextView cityName;
    private ItemClicked activity;

    public AddCityViewHolder(@NonNull View itemView, TextView cityName, Context context) {
        super(itemView);
        this.cityName = cityName;
        this.activity = (ItemClicked) context;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activity.onItemClicked(itemView.getTag().toString());
            }
        });
    }

    public void setCityName(String cityName) {
        this.cityName.setText(cityName);
    }
}
