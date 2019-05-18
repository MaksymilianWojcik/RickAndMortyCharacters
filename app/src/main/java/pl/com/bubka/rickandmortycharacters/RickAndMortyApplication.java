package pl.com.bubka.rickandmortycharacters;

import android.app.Application;

import timber.log.Timber;

public class RickAndMortyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
