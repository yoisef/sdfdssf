package werpx.marketopia;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Retrofitclient {

    //create an object of SingleObject
    private static Retrofitclient instance = new Retrofitclient();

    //make the constructor private so that this class cannot be
    //instantiated
    private Retrofitclient(){}

    //Get the only object available
    public static Retrofitclient getInstance(){
        return instance;
    }

    public Retrofit getretro(){
        OkHttpClient.Builder builderr = new OkHttpClient.Builder();
        builderr.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES);


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builderr.addInterceptor(loggingInterceptor);
        Retrofit retrofitt = new Retrofit.Builder()
                .baseUrl("https://test.ojo.company/api/v1/")
              .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builderr.build())
                .build();

        return retrofitt;
    }
}