package ru.mamapapa.task14;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.RequestBuilder;

public class PicturePresenter {
    public interface ProgressBarCallback {
        void hide();
    }

    private PictureModel model;
    private PictureView view;

    public PicturePresenter attachView(PictureView view) {
        this.view = view;
        return this;
    }

    public PicturePresenter attachModel(PictureModel model) {
        this.model = model;
        return this;
    }

    public void showPicture(int id) {
        view.showProgress();
        ProgressBarCallback callback = () -> view.hideProgress();
        RequestBuilder<Drawable> picture = model.getPicture(id, callback);
        view.showPicture(picture);
    }
}
