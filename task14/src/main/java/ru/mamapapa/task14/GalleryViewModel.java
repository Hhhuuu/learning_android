package ru.mamapapa.task14;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import static ru.mamapapa.task14.GalleryAdapter.EXTRA_PARAM_ID;

public class GalleryViewModel extends ViewModel {
    private Context context;
    private GalleryModel model;
    private GalleryView view;

    public GalleryViewModel() {
        super();
        model = new GalleryModel();
    }

    public void attachView(GalleryView view){
        this.view = view;
        this.view.setViewModel(this);
    }

    public void getGallery(){
        List<PictureInfo> pictures = model.getPictures();
        view.showPictures(pictures);
    }

    public void onClick(PictureInfo info){
        startActivity(info.getId());
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void startActivity(int pictureId) {
        Intent intent = new Intent(context, ViewPicturesActivity.class);
        intent.putExtra(EXTRA_PARAM_ID, pictureId);
        context.startActivity(intent);
    }

    @Override
    protected void onCleared() {
        view = null;
    }
}
