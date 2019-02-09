package ru.mamapapa.task14;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static ru.mamapapa.task14.GalleryAdapter.EXTRA_PARAM_ID;

public class ViewPicturesActivity extends AppCompatActivity {

    private PicturePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pictures);
        PictureModel model = new PictureModel(this);
        PictureView view = new PictureView(this);
        presenter = new PicturePresenter()
                .attachModel(model)
                .attachView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int id = getIntent().getIntExtra(EXTRA_PARAM_ID, 1);
        presenter.showPicture(id);
    }
}
