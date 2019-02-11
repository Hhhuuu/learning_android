package ru.mamapapa.task14;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;

public interface PictureView {

    void showPicture(RequestBuilder<Drawable> image);

    void showProgress();

    void hideProgress();
}
