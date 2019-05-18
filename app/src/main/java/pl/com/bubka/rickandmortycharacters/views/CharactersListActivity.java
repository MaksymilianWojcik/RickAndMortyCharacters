package pl.com.bubka.rickandmortycharacters.views;

import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import pl.com.bubka.rickandmortycharacters.BaseActivity;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.adapters.CharactersRecyclerAdapter;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.utils.Resource;
import pl.com.bubka.rickandmortycharacters.utils.SpacingItemDecorator;
import pl.com.bubka.rickandmortycharacters.viewmodels.CharactersListViewModel;

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
        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setElevation(0);

        searchCharactersApi("");
    }


    private void subscribeObservers() {
        charactersListViewModel.getCharacters().observe(this, new Observer<Resource<List<Character>>>() {
            @Override
            public void onChanged(Resource<List<Character>> listResource) {
                if (listResource != null) {
                    Log.i(TAG, "onChanged: status: " + listResource.status);
                    if (listResource.data != null) {
                        switch (listResource.status) {
                            case LOADING:
                                if (charactersListViewModel.getPageNumber() > 1) {
                                    adapter.displayLoading();
                                } else {
                                    adapter.displayOnlyLoading();
                                }
                                break;
                            case ERROR:
                                Log.e(TAG, "onChanged: ERROR: " + listResource.message);
                                adapter.hideLoading();
                                adapter.setCharacters(listResource.data);
                                Toast.makeText(CharactersListActivity.this, listResource.message, Toast.LENGTH_SHORT).show();

                                if (listResource.message.equals("No more results.")) {
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

    private void initRecyclerView() {
        adapter = new CharactersRecyclerAdapter(initGlide());
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(30, 30);
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

    private RequestManager initGlide() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(initCircularProgressDrawable())
                .error(R.drawable.ic_launcher_background); //TODO: icons
        return Glide.with(this).setDefaultRequestOptions(requestOptions);
    }

    private CircularProgressDrawable initCircularProgressDrawable(){
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    private void searchCharactersApi(String name) {
        recyclerView.smoothScrollToPosition(0);
        charactersListViewModel.searchCharactersApi(name, 1);
        searchView.clearFocus();
    }

    private void initSearchView() { //TODO: https://github.com/MiguelCatalan/MaterialSearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //query text submit isnt called when the query is empty
                searchCharactersApi(s);
                return false; //TODO: What is this boolean responsible for?
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    searchCharactersApi("");
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        charactersListViewModel.cancelSearchRequest(); //TODO: after choosing CHARACTERS, LOCATIONS, EPISODES etc. go back to menu screen
        //TODO: if results are filtered than back press invokes all characters search again
        super.onBackPressed();
    }
}
