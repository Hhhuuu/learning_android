package ru.mamapapa.task13.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import ru.mamapapa.task13.yandex.dto.Weather;

public interface WeatherApi {
    String BASE_URL = "https://api.weather.yandex.ru/";

    @Headers("X-Yandex-API-Key: 5e95aa7e-28e7-446a-8475-ad36e3084f1a")
    @GET("v1/forecast?lang=ru_RU&limit=7")
    Call<Weather> getWeather(@Query("lat") String lat, @Query("lon") String lon);
}
