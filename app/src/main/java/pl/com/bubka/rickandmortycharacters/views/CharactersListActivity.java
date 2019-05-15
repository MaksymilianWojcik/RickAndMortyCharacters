package pl.com.bubka.rickandmortycharacters.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import pl.com.bubka.rickandmortycharacters.AppExecutors;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.database.CharacterDatabase;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.requests.RickAndMortyApi;
import pl.com.bubka.rickandmortycharacters.requests.ServiceGenerator;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.CharacterSearchResponse;
import pl.com.bubka.rickandmortycharacters.utils.NetworkBoundResources;
import pl.com.bubka.rickandmortycharacters.utils.Resource;
import pl.com.bubka.rickandmortycharacters.viewmodels.CharactersListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class CharactersListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;

    private CharactersListViewModel charactersListViewModel;

    public static final String TAG = "CharacterListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_list);

        recyclerView = findViewById(R.id.characters_list);
        searchView = findViewById(R.id.search_view);

        charactersListViewModel = ViewModelProviders.of(this).get(CharactersListViewModel.class);

        initSearchView();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        subscribeObservers();
    }


    private void subscribeObservers(){
        charactersListViewModel.getCharacters().observe(this, new Observer<Resource<List<Character>>>() {
            @Override
            public void onChanged(Resource<List<Character>> listResource) {
                if (listResource != null){
                    Log.i(TAG, "onChanged: status: " + listResource.status);
                    if (listResource.data != null){
                        switch(listResource.status){
                            case LOADING:
                                if(charactersListViewModel.getPageNumber() > 1){
                                    //displayloading
                                } else {
                                    //szukamy pierwsza storne
                                }
                                break;
                            case ERROR:
                                Log.e(TAG, "onChanged: ERROR: " + listResource.message);
                                List<Character> charactersLocal = listResource.data;
                                for(Character character : charactersLocal){
                                    Log.i(TAG, "onChanged: characterlocal: " + character.toString());
                                }
                                break;
                            case SUCCESS:
                                Log.i(TAG, "onChanged: CACJE REFRESJED: " + listResource.data.size());
                                List<Character> characters = listResource.data;
                                for(Character character : characters){
                                    Log.i(TAG, "onChanged: character: " + character.toString());
                                }
                                break;
                        }
                    }
                }
            }
        });
    }

    private void searchCharactersApi(String name) {
        charactersListViewModel.searchCharactersApi(name, 1);
        searchView.clearFocus();
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                searchCharactersApi(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

}
