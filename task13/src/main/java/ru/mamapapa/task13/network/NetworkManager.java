package ru.mamapapa.task13.network;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mamapapa.task13.yandex.dto.Weather;

import static ru.mamapapa.task13.network.WeatherApi.BASE_URL;

/**
 * Менеджер сети
 *
 * @author Popov Maxim <m_amapapa@mail.ru>
 */
public class NetworkManager {

    /**
     * Получение погоды
     *
     * @param lat - Широта в градусах.
     * @param lon - Долгота в градусах.
     * @return погода
     * @throws IOException
     */
    public Weather getWeather(String lat, String lon) throws IOException {
        WeatherApi weatherApi = getRetrofit().create(WeatherApi.class);
        Call<Weather> weatherCall = weatherApi.getWeather(lat, lon);
        Response<Weather> response = weatherCall.execute();
        if (!response.isSuccessful())
            throw new IOException("Сервер погоды не доступен!");
        return response.body();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
