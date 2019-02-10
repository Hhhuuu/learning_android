package ru.mamapapa.task13.detail.info;

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
import ru.mamapapa.task13.yandex.dto.Hour;

public class DetailInfoAdapter extends RecyclerView.Adapter<DetailInfoAdapter.Holder> {

    private static List<WeatherItem> items = new ArrayList<>();
    private DetailInfoPresenter presenter;

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

    public void setPresenter(DetailInfoPresenter presenter){
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

    public void addItems(Forecast forecast) {
        items = new ArrayList<>();
        for (Hour hour: forecast.getHours()) {
            WeatherItem item = new WeatherItem();
            item.setMainText(hour.getHour());
            item.setTempSecond(hour.getTemp());
            items.add(item);
        }
        notifyDataSetChanged();
    }
}
