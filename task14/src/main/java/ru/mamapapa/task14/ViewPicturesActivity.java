package ru.mamapapa.task14;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.RequestBuilder;

import static ru.mamapapa.task14.GalleryAdapter.EXTRA_PARAM_ID;

public class ViewPicturesActivity extends AppCompatActivity implements PictureView {
    private ImageView imageView;
    private ProgressBar progressBar;
    private PicturePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pictures);

        initView();

        presenter = new PicturePresenter(this)
                .attachView(this);
    }

    private void initView() {
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
    }

    public void showPicture(RequestBuilder<Drawable> image) {
        image.into(imageView);
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int id = getIntent().getIntExtra(EXTRA_PARAM_ID, 1);
        presenter.showPicture(id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.deAttachView();
    }
}
