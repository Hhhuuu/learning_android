package ru.mamapapa.task13.weather;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.mamapapa.task13.R;
import ru.mamapapa.task13.WeatherItem;
import ru.mamapapa.task13.databinding.ItemViewBinding;
import ru.mamapapa.task13.yandex.dto.Forecast;
import ru.mamapapa.task13.yandex.dto.Parts;
import ru.mamapapa.task13.yandex.dto.Weather;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder> {

    private static final String NOW = "Now";
    private List<WeatherItem> items = new ArrayList<>();
    private WeatherPresenter presenter;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ItemViewBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_view, viewGroup, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setPresenter(WeatherPresenter presenter){
        this.presenter = presenter;
    }

    class Holder extends RecyclerView.ViewHolder {
        private final ItemViewBinding binding;

        Holder(@NonNull ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(WeatherItem value) {
            binding.setItem(value);
            binding.setPresenter(presenter);
        }
    }

    public void addItems(Weather weathers) {
        items = new ArrayList<>();
        WeatherItem item = buildItem(NOW, null, weathers.getFact().getTemp());
        items.add(item);
        for (Forecast forecast : weathers.getForecasts()) {
            Parts parts = forecast.getParts();
            items.add(buildItem(forecast.getDate(),
                    parts.getDayShort().getTemp(),
                    parts.getNightShort().getTemp()));
        }
        notifyDataSetChanged();
    }

    private WeatherItem buildItem(String date, String tempFirst, String tempSecond) {
        WeatherItem item = new WeatherItem();
        item.setMainText(date);
        item.setTempFirst(tempFirst);
        item.setTempSecond(tempSecond);
        return item;
    }
}
