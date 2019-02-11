package ru.mamapapa.task14;

import java.util.List;

public interface GalleryView {

    void setViewModel(GalleryViewModel viewModel);

    void showPictures(List<PictureInfo> pictures);
}
