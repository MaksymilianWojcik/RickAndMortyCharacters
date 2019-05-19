package pl.com.bubka.rickandmortycharacters;

import android.app.Application;

import timber.log.Timber;

public class RickAndMortyApplication extends Application {

    private static RickAndMortyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static RickAndMortyApplication getInstance() {
        return instance;
    }
}
