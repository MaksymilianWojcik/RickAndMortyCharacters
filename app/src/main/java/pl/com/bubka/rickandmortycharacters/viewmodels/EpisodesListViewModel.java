package pl.com.bubka.rickandmortycharacters.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import pl.com.bubka.rickandmortycharacters.models.Episode;
import pl.com.bubka.rickandmortycharacters.repositories.EpisodesRepository;
import pl.com.bubka.rickandmortycharacters.utils.Resource;
import timber.log.Timber;


public class EpisodesListViewModel extends AndroidViewModel {

    private boolean isQueryExhausted;
    private boolean isPerformingQuery;
    private boolean cancelRequest;

    private int pageNumber;
    private String name;
    private EpisodesRepository episodesRepository;


    private MediatorLiveData<Resource<List<Episode>>> episodes = new MediatorLiveData<>();

    public EpisodesListViewModel(Application application){
        super(application);
        episodesRepository = EpisodesRepository.getInstance(application);
    }

    public LiveData<Resource<List<Episode>>> getEpisodes(){
        return episodes;
    }

    public int getPageNumber(){
        return pageNumber;
    }

    public void searchEpisodesApi(String name, int pageNumber){
        if(!isPerformingQuery){
            if(pageNumber == 0){
                pageNumber = 1;
            }
            this.pageNumber = pageNumber;
            this.name = name;
            isQueryExhausted = false;
            executeSearch();
        }
    }

    public void searchNextPage(){
        if (!isQueryExhausted && !isPerformingQuery){
            pageNumber++;
            executeSearch();
        }
    }

    private void executeSearch(){
        cancelRequest = false;
        isPerformingQuery = true;
        final LiveData<Resource<List<Episode>>> episodeSource = episodesRepository.searchEpisodesApi(name, pageNumber);
        episodes.addSource(episodeSource, new Observer<Resource<List<Episode>>>() {
            @Override
            public void onChanged(Resource<List<Episode>> listResource) {
                if (!cancelRequest){
                    if (listResource != null) {
                        episodes.setValue(listResource);
                        if (listResource.status == Resource.Status.SUCCESS){
                            isPerformingQuery = false;
                            if (listResource.data != null) {
                                if (listResource.data.size() == 0) {
                                    episodes.setValue(new Resource<List<Episode>>(Resource.Status.ERROR, listResource.data, "There is nothing here"));
                                }
                            }
                            episodes.removeSource(episodeSource);
                        } else if (listResource.status == Resource.Status.ERROR){
                            isPerformingQuery = false;
                            episodes.removeSource(episodeSource);
                        }
                    } else {
                        episodes.removeSource(episodeSource);
                    }
                } else {
                    episodes.removeSource(episodeSource);
                }
            }
        });
    }

    public void cancelSearchRequest() {
        if (isPerformingQuery) {
            Timber.d("cancelSearchRequest: canceling the search request");
            cancelRequest = true;
            isPerformingQuery = false;
            pageNumber = 1;
        }
    }

}
