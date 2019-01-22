package ru.mamapapa.task11;

import java.util.List;
import java.util.Map;

public interface OnFragmentListener {
    List<String> getPropertiesKeys();

    void setProperty(String key, String value);
}
