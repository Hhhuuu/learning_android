package ru.mamapapa.task14;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainGalleryActivity extends AppCompatActivity {

    private GalleryViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this).get(GalleryViewModel.class);
        model.attachView(new GalleryView(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.getGallery();
    }
}
