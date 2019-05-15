package pl.com.bubka.rickandmortycharacters.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.com.bubka.rickandmortycharacters.AppExecutors;
import pl.com.bubka.rickandmortycharacters.BaseActivity;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.adapters.CharactersRecyclerAdapter;
import pl.com.bubka.rickandmortycharacters.database.CharacterDatabase;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.requests.RickAndMortyApi;
import pl.com.bubka.rickandmortycharacters.requests.ServiceGenerator;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.CharacterSearchResponse;
import pl.com.bubka.rickandmortycharacters.utils.NetworkBoundResources;
import pl.com.bubka.rickandmortycharacters.utils.Resource;
import pl.com.bubka.rickandmortycharacters.utils.VerticalSpacingItemDecorator;
import pl.com.bubka.rickandmortycharacters.viewmodels.CharactersListViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CharactersListActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private CharactersRecyclerAdapter adapter;
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

        initRecyclerView();
        initSearchView();
        subscribeObservers();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
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
                                    adapter.displayLoading();
                                } else {
                                    adapter.displayOnlyLoading();
                                    //szukamy pierwsza storne
                                }
                                break;
                            case ERROR:
                                Log.e(TAG, "onChanged: ERROR: " + listResource.message);
                                adapter.hideLoading();
                                adapter.setCharacters(listResource.data);
                                Toast.makeText(CharactersListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();

                                if(listResource.message.equals("No more results.")){
                                    adapter.setQueryExhausted();
                                }
                                break;
                            case SUCCESS:
                                Log.i(TAG, "onChanged: CACJE REFRESJED: " + listResource.data.size());
                                adapter.hideLoading();
                                adapter.setCharacters(listResource.data);
                                break;
                        }
                    }
                }
            }
        });
    }

    private void initRecyclerView(){
        adapter = new CharactersRecyclerAdapter(initGlide());
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    charactersListViewModel.searchNextPage();
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private RequestManager initGlide(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background); //TODO: icons

        return Glide.with(this).setDefaultRequestOptions(requestOptions);
    }

    private void searchCharactersApi(String name) {
        recyclerView.smoothScrollToPosition(0);
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

    @Override
    public void onBackPressed() {
//        charactersListViewModel.cancelSearchRequest(); //TODO: after choosing CHARACTERS, LOCATIONS, EPISODES etc.
        super.onBackPressed();
    }
}
