package sac.lobosistemas.loboventas.io;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoboVentasApiAdapter {

    public static LoboVentasApiService getApiService() {

        String baseUrl = "http://192.168.1.80/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoboVentasApiService service = retrofit.create(LoboVentasApiService.class);
        return service;
    }
}
