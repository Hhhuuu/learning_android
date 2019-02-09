package ru.mamapapa.task14;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.RequestBuilder;

public class PictureView {
    private ImageView imageView;
    private ProgressBar progressBar;

    public PictureView(Activity activity) {
        initView(activity);
    }

    private void initView(Activity activity) {
        this.imageView = activity.findViewById(R.id.imageView);
        this.progressBar = activity.findViewById(R.id.progressBar);
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
}
