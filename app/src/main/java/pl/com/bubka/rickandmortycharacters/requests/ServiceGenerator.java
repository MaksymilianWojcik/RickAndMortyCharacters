package pl.com.bubka.rickandmortycharacters.requests;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.com.bubka.rickandmortycharacters.utils.Constants;
import pl.com.bubka.rickandmortycharacters.utils.LiveDataCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

import static pl.com.bubka.rickandmortycharacters.utils.Constants.CONNECTION_TIMEOUT;
import static pl.com.bubka.rickandmortycharacters.utils.Constants.READ_TIMEOUT;
import static pl.com.bubka.rickandmortycharacters.utils.Constants.WRITE_TIMEOUT;

public class ServiceGenerator {



    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Timber.tag("ApiRequest").d(message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .retryOnConnectionFailure(false)
            .build();

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RickAndMortyApi rickAndMortyApi = retrofit.create(RickAndMortyApi.class);

    public static RickAndMortyApi getRickAndMortyApi() {
        return rickAndMortyApi;
    }
}
