package ru.mamapapa.task13;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mamapapa.task13.dto.Weather;


public class WeatherIntentService extends IntentService {
    private static final String BASE_URL = "https://api.github.com/";

    private static final String ACTION_FOO = "ru.mamapapa.task13.action.FOO";
    private static final String ACTION_BAZ = "ru.mamapapa.task13.action.BAZ";

    private static final String EXTRA_PARAM1 = "ru.mamapapa.task13.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "ru.mamapapa.task13.extra.PARAM2";

    public WeatherIntentService() {
        super("WeatherIntentService");
    }

    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, WeatherIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Weather getWeather(){
        WeatherApi weatherApi = getRetrofit().create(WeatherApi.class);
        Call<Weather> weather = weatherApi.getWeather("", "");
//        if (!reposResponse.isSuccessful())
//            return;
        return null;
    }

    private Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        WebAPI webAPI = retrofit.create(WebAPI.class);
//        Call<List<Repo>> reposCall = webAPI.getRepos("SergeyVinyar");
//        Response<List<Repo>> reposResponse = reposCall.execute();
//        if (!reposResponse.isSuccessful())
//            return;
//        List<Repo> repos = reposResponse.body();
//        Call<Readme> readmeCall =
//                webAPI.getReadme("SergeyVinyar", repos.get(0).getName());
//        Response<Readme> readmeResponse = readmeCall.execute();
//        if (!readmeResponse.isSuccessful())
//            return;
//        Readme readme = readmeResponse.body();
    }
}
