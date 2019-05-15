package pl.com.bubka.rickandmortycharacters.viewmodels;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.repositories.CharactersRepository;
import pl.com.bubka.rickandmortycharacters.utils.Resource;

public class CharactersListViewModel extends AndroidViewModel {

    public static final String QUERY_EXHAUSTED = "No more results.";
    private static final String TAG = "CharactersListViewModel";

    private boolean isQueryExhausted;
    private boolean isPerformingQuery;
    private boolean cancelRequest;

    private int pageNumber;
    private String name;
    private CharactersRepository charactersRepository;

    private MediatorLiveData<Resource<List<Character>>> characters = new MediatorLiveData<>();

    public CharactersListViewModel(@NonNull Application application) {
        super(application);
        charactersRepository = CharactersRepository.getInstance(application);
        init();
    }

    private void init(){ //TODO: start with getting all characters
//        searchCharactersApi(null, 1);
    }

    public LiveData<Resource<List<Character>>> getCharacters(){
        return characters;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void searchCharactersApi(String name, int pageNumber){
        if(!isPerformingQuery) {
            if (pageNumber == 0) {
                pageNumber = 1;
            }
            this.pageNumber = pageNumber;
            this.name = name; //TODO: QUERY
            isQueryExhausted = false;
            executeSearch();
        }
    }

    public void searchNextPage(){
        if (!isQueryExhausted && !isPerformingQuery) {
            pageNumber++;
            executeSearch();
        }
    }

    private void executeSearch(){
        cancelRequest = false;
        isPerformingQuery = true;
        final LiveData<Resource<List<Character>>> characterSource = charactersRepository.searchCharactersApi(name, pageNumber);
        characters.addSource(characterSource, new Observer<Resource<List<Character>>>() {
            @Override
            public void onChanged(Resource<List<Character>> listResource) {
                if(!cancelRequest) {
                    if (listResource != null) {
                        characters.setValue(listResource);
                        if (listResource.status == Resource.Status.SUCCESS) {
                            isPerformingQuery = false;
                            if (listResource.data != null) {
                                if (listResource.data.size() == 0) {
                                    //koniec wynikow
                                    characters.setValue(new Resource<List<Character>>(Resource.Status.ERROR, listResource.data, QUERY_EXHAUSTED));
                                }
                            }
                            characters.removeSource(characterSource);
                        } else if (listResource.status == Resource.Status.ERROR) {
                            isPerformingQuery = false;
                            characters.removeSource(characterSource);
                        }
                    } else {
                        characters.removeSource(characterSource);
                    }
                } else {
                    characters.removeSource(characterSource);
                }
            }
        });
    }

    public void cancelSearchRequest(){
        if(isPerformingQuery){
            Log.d(TAG, "cancelSearchRequest: canceling the search request.");
            cancelRequest = true;
            isPerformingQuery = false;
            pageNumber = 1;
        }
    }

}
