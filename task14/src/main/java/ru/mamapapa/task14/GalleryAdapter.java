package ru.mamapapa.task14;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.mamapapa.task14.databinding.ItemViewBinding;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.Holder>{
    public static final String EXTRA_PARAM_ID = "ID";
    private List<PictureInfo> items = new ArrayList<>();
    private GalleryViewModel viewModel;

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


    public void setViewModel(GalleryViewModel viewModel){
        this.viewModel = viewModel;
    }

    class Holder extends RecyclerView.ViewHolder {
        private ItemViewBinding binding;

        Holder(@NonNull ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PictureInfo value) {
            binding.setInfo(value);
            binding.setViewModel(viewModel);
            binding.executePendingBindings();
        }
    }

    public void addItems(List<PictureInfo> pictureInfo) {
        items = new ArrayList<>();
        items.addAll(pictureInfo);
        notifyDataSetChanged();
    }
}
