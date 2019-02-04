package ru.mamapapa.task13;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.mamapapa.task13.dto.Weather;

public interface WeatherApi {
    @GET("v1/forecast?lat={lat}&lon={lon}&lang=ru_RU&limit=7")
    Call<Weather> getWeather(@Path("lat") String lat, @Path("lon") String lon);
}
