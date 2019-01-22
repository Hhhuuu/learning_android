// SharedPreferenceManager.aidl
package ru.mamapapa.shared_preference_aidl;

// Declare any non-default types here with import statements

interface SharedPreferenceManager {
    void add(String key, String value);

    List<String> getPropertiesKeys();
}

