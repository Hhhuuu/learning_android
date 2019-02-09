package ru.mamapapa.task14;

import java.util.ArrayList;
import java.util.List;

public class GalleryModel {
    private static final int COUNT_FILE = 6;

    public List<PictureInfo> getPictures() {
        List<PictureInfo> result = new ArrayList<>();
        for (int i = 1; i <= COUNT_FILE; i++) {
            PictureInfo pictureInfo = new PictureInfo();
            pictureInfo.setId(i);
            pictureInfo.setName("Picture " + i);
            result.add(pictureInfo);
        }
        return result;
    }
}
