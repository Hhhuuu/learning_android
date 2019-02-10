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
import ru.mamapapa.task13.yandex.dto.Parts;
import ru.mamapapa.task13.yandex.dto.Weather;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private static final String NOW = "Now";
    private OnClickCallback callback;

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

    public void setOnClickCallback(OnClickCallback callback){
        this.callback = callback;
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView dateTextView;
        private TextView dayTextView;
        private TextView nightTextView;

        Holder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            dayTextView = itemView.findViewById(R.id.tempDayTextView);
            nightTextView = itemView.findViewById(R.id.tempNightTextView);


            itemView.setOnClickListener(v -> {
                String date = (String) dateTextView.getTag();
                if (!isFirstElement(date)) {
                    callback.onClick(date);
                }
            });
        }

        void bind(Item value) {
            dateTextView.setText(value.date);
            dateTextView.setTag(value.date);
            if (!NOW.equals(value.date)) {
                dayTextView.setVisibility(View.VISIBLE);
                dayTextView.setText(value.dayTemp);
            } else {
                dayTextView.setVisibility(View.INVISIBLE);
            }
            nightTextView.setText(value.nightTemp);
        }
    }

    private boolean isFirstElement(String data) {
        return !items.isEmpty() && items.get(0).date.equals(data);
    }

    public void addItems(Weather weathers) {
        items = new ArrayList<>();
        Item item = buildItem(NOW, null, weathers.getFact().getTemp());
        items.add(item);
        for (Forecast forecast : weathers.getForecasts()) {
            Parts parts = forecast.getParts();
            items.add(buildItem(forecast.getDate(),
                    parts.getDayShort().getTemp(),
                    parts.getNightShort().getTemp()));
        }
        notifyDataSetChanged();
    }

    private Item buildItem(String date, String tempFirst, String tempSecond) {
        Item item = new Item();
        item.date = date;
        item.dayTemp = tempFirst;
        item.nightTemp = tempSecond;
        return item;
    }
}
