package ru.mamapapa.task14;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainGalleryActivity extends AppCompatActivity implements GalleryView {

    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private GalleryViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        model = ViewModelProviders.of(this).get(GalleryViewModel.class);
        model.setContext(this);
        model.attachView(this);
    }

    private void initView() {
        adapter = new GalleryAdapter();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
    }


    public void setViewModel(GalleryViewModel viewModel){
        adapter.setViewModel(viewModel);
    }

    public void showPictures(List<PictureInfo> pictures) {
        adapter.addItems(pictures);
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.getGallery();
    }

    @Override
    protected void onPause() {
        super.onPause();
        model.onCleared();
    }
}
