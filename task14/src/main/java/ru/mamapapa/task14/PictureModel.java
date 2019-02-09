package ru.mamapapa.task14;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class PictureModel {

    private final Context context;

    public PictureModel(Context context) {
        this.context = context;
    }

    public RequestBuilder<Drawable> getPicture(int id, PicturePresenter.ProgressBarCallback callback) {
        return loadPicture(id, callback);
    }

    private RequestBuilder<Drawable> loadPicture(int id, PicturePresenter.ProgressBarCallback callback) {
        return Glide
                .with(context)
                .load(String.format("file:///android_asset/%s.JPG", id))
                .addListener(getRequestListener(callback));
    }

    private RequestListener<Drawable> getRequestListener(PicturePresenter.ProgressBarCallback callback) {
        return new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                callback.hide();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                callback.hide();
                return false;
            }
        };
    }
}
