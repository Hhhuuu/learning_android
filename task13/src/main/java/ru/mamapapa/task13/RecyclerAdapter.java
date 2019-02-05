package ru.mamapapa.task13;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Weather;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {
    private class Item {
        String date;
        String dayTemp;
        String nightTemp;
    }

    private List<Item> items = new ArrayList<>();

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new Holder(inflater.inflate(R.layout.item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView dayTextView;
        private TextView nightTextView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            dayTextView = itemView.findViewById(R.id.tempDayTextView);
            nightTextView = itemView.findViewById(R.id.tempNightTextView);

            itemView.setOnClickListener(v -> {
            });
        }

        public void bind(Item value) {
            dateTextView.setText(value.date);
            dateTextView.setTag(value);
            dayTextView.setText(value.dayTemp);
            nightTextView.setText(value.nightTemp);
        }
    }

    public void addItems(Weather weathers) {
        items = new ArrayList<>();
        for (Forecast forecast : weathers.getForecasts()){
            Item item = new Item();
            item.date = forecast.getDate();
            item.dayTemp = forecast.getParts().getDatShort().getTemp();
            item.nightTemp = forecast.getParts().getNightShort().getTemp();
            items.add(item);
        }
        notifyDataSetChanged();
    }
}
