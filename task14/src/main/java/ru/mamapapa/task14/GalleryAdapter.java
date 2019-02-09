package ru.mamapapa.task14;

import android.content.Context;
import android.content.Intent;
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
    private static List<PictureInfo> items = new ArrayList<>();

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

    static class Holder extends RecyclerView.ViewHolder {
        private ItemViewBinding binding;

        public Holder(@NonNull ItemViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(v -> {
                PictureInfo info = (PictureInfo) binding.getRoot().getTag();
                startActivity(v.getContext(), info.getId());
            });
        }

        public void bind(PictureInfo value) {
            binding.setInfo(value);
            binding.getRoot().setTag(value);
            binding.executePendingBindings();
        }
    }

    private static void startActivity(Context context, int pictureId) {
        Intent intent = new Intent(context, ViewPicturesActivity.class);
        intent.putExtra(EXTRA_PARAM_ID, pictureId);
        context.startActivity(intent);
    }

    public void addItems(List<PictureInfo> pictureInfo) {
        items = new ArrayList<>();
        items.addAll(pictureInfo);
        notifyDataSetChanged();
    }
}
