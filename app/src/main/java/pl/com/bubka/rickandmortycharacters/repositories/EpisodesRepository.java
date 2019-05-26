package pl.com.bubka.rickandmortycharacters.repositories;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import pl.com.bubka.rickandmortycharacters.AppExecutors;
import pl.com.bubka.rickandmortycharacters.database.CharacterDatabase;
import pl.com.bubka.rickandmortycharacters.database.EpisodeDao;
import pl.com.bubka.rickandmortycharacters.models.Episode;
import pl.com.bubka.rickandmortycharacters.requests.ServiceGenerator;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.EpisodeSearchResponse;
import pl.com.bubka.rickandmortycharacters.utils.NetworkBoundResources;
import pl.com.bubka.rickandmortycharacters.utils.Resource;
import timber.log.Timber;

public class EpisodesRepository {

    private static EpisodesRepository instance;
    private EpisodeDao episodeDao;

    public static EpisodesRepository getInstance(Context context){
        if (instance == null){
            instance = new EpisodesRepository(context);
        }
        return instance;
    }

    private EpisodesRepository(Context context){
        episodeDao = CharacterDatabase.getInstance(context).getEpisodeDao();
    }

    public LiveData<Resource<List<Episode>>> searchEpisodesApi(final String name, final int pageNumber){
        return new NetworkBoundResources<List<Episode>, EpisodeSearchResponse>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull EpisodeSearchResponse item) {
                if (item.getEpisodesList() != null) {
                    episodeDao.insertEpisodes(item.getEpisodesList().toArray(new Episode[item.getEpisodesList().size()]));
                    Timber.d("saveCallResult: Episodes saved");
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Episode> data) {
                return true; //TODO: timestamps
            }

            @NonNull
            @Override
            protected LiveData<List<Episode>> loadFromDb() {
                Timber.d("loadFromDb: loading episodes");
                return episodeDao.searchEpisodes(name, pageNumber);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<EpisodeSearchResponse>> createCall() {
                return ServiceGenerator.getRickAndMortyApi().searchEpisodes(name, String.valueOf(pageNumber));
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<Episode>> searchEpisodeApi(final String id){
        return new NetworkBoundResources<Episode, Episode>(AppExecutors.getInstance()){

            @Override
            protected void saveCallResult(@NonNull Episode item) {
                if (item != null) {
                    episodeDao.insertEpisode(item);
                    Timber.d("saveCallResult: saved episode: " + item.toString());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable Episode data) {
                return true; //TODO: timestamps
            }

            @NonNull
            @Override
            protected LiveData<Episode> loadFromDb() {
                return episodeDao.searchEpisode(id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Episode>> createCall() {
                return ServiceGenerator.getRickAndMortyApi().searchEpisode(id);
            }
        }.getAsLiveData();
    }

}
