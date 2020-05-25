package com.example.androidweatherapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidweatherapp.R;
import com.example.androidweatherapp.viewholders.AddCityViewHolder;

public class AddCityAdapter extends RecyclerView.Adapter<AddCityViewHolder>{

    private Context context;
    private String[] city = {"Odense", "København"};

    public AddCityAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AddCityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout addCityCard = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.searchcitycard, parent, false);
        TextView cityNameTextView = addCityCard.findViewById(R.id.cityNameTextView);
        AddCityViewHolder addCityViewHolder = new AddCityViewHolder(addCityCard, cityNameTextView, context);
        return addCityViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddCityViewHolder holder, int position) {
        holder.itemView.setTag(city[position]);
        holder.setCityName(city[position]);

    }

    @Override
    public int getItemCount() {
        return city.length;
    }
}
