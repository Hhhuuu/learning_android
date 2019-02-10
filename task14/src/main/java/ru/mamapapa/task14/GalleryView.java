package ru.mamapapa.task14;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class GalleryView {
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;

    public GalleryView(Activity activity) {
        this.adapter = new GalleryAdapter();
        this.recyclerView = activity.findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
    }

    public void setViewModel(GalleryViewModel viewModel){
        adapter.setViewModel(viewModel);
    }

    public void showPictures(List<PictureInfo> pictures) {
        adapter.addItems(pictures);
    }
}
