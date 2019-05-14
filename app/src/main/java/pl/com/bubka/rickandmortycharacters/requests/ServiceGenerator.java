package pl.com.bubka.rickandmortycharacters.requests;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pl.com.bubka.rickandmortycharacters.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .retryOnConnectionFailure(false) //TODO: timeouts
            .build();

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .client(okHttpClient)
//                    .addCallAdapterFactory(new LiveDataCallAdapterFactory()) //TODO: change Call for Livedata
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RickAndMortyApi rickAndMortyApi = retrofit.create(RickAndMortyApi.class);

    public static RickAndMortyApi getRickAndMortyApi() {
        return rickAndMortyApi;
    }
}
