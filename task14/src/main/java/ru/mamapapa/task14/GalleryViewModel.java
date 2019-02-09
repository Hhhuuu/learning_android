package ru.mamapapa.task14;

import android.arch.lifecycle.ViewModel;

import java.util.List;

public class GalleryViewModel extends ViewModel {
    private GalleryModel model;
    private GalleryView view;

    public GalleryViewModel() {
        super();
        model = new GalleryModel();
    }

    public void attachView(GalleryView view){
        this.view = view;
    }

    public void getGallery(){
        List<PictureInfo> pictures = model.getPictures();
        view.showPictures(pictures);
    }
}
