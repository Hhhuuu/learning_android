package ru.mamapapa.task13;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Hour;

public class DetailInfoAdapter extends RecyclerView.Adapter<DetailInfoAdapter.Holder> {
    private class Item {
        String hour;
        String temp;
    }

    private static List<Item> items = new ArrayList<>();

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
            dateTextView.setVisibility(View.GONE);

            nightTextView = itemView.findViewById(R.id.tempNightTextView);
        }

        public void bind(Item value) {
            dateTextView.setText(value.hour);
            dateTextView.setTag(value);
            nightTextView.setText(value.temp);
        }
    }

    public void addItems(Forecast forecast) {
        items = new ArrayList<>();
        for (Hour hour: forecast.getHours()) {
            Item item = new Item();
            item.hour = hour.getHour();
            item.temp = hour.getTemp();
            items.add(item);
        }
        notifyDataSetChanged();
    }
}
