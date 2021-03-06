package pl.com.bubka.rickandmortycharacters.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.bubka.rickandmortycharacters.BaseActivity;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.adapters.CharactersRecyclerAdapter;
import pl.com.bubka.rickandmortycharacters.adapters.OnCharacterClickListener;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.utils.Resource;
import pl.com.bubka.rickandmortycharacters.utils.SpacingItemDecorator;
import pl.com.bubka.rickandmortycharacters.viewmodels.CharactersListViewModel;
import timber.log.Timber;

import static pl.com.bubka.rickandmortycharacters.adapters.CharactersRecyclerAdapter.EXHAUSTED_TYPE_TEXT;
import static pl.com.bubka.rickandmortycharacters.adapters.CharactersRecyclerAdapter.NO_CONNECTION_TYPE_TEXT;
import static pl.com.bubka.rickandmortycharacters.views.CharacterDetailsActivity.INTENT_EXTRA_CHARACTER;

public class CharactersListActivity extends BaseActivity implements OnCharacterClickListener {

    @BindView(R.id.characters_list) RecyclerView recyclerView;
    @BindView(R.id.search_view) SearchView searchView;

    private CharactersRecyclerAdapter adapter;
    private CharactersListViewModel charactersListViewModel;

    public static final String TAG = "CharacterListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_list);
        ButterKnife.bind(this);

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
                    Timber.d("onChanged: status: " + listResource.status);
                    if (listResource.data != null) {
                        switch (listResource.status) {
                            case LOADING:
                                if (charactersListViewModel.getPageNumber() > 1
                                        && adapter.getSelectedCharacter(adapter.getItemCount()-1) != null
                                        && !adapter.getSelectedCharacter(adapter.getItemCount()-1).getName().equals(EXHAUSTED_TYPE_TEXT)) {
                                    adapter.displayLoading();
                                } else if (charactersListViewModel.getPageNumber() == 1){
                                    adapter.displayOnlyLoading();
                                }
                                break;
                            case ERROR:
                                Timber.w("onChanged: ERROR: " + listResource.message);
                                adapter.hideLoading();
                                adapter.setCharacters(listResource.data);
                                if (listResource.message.equals("There is nothing here")) {
                                    adapter.setQueryExhausted();
                                } else {
                                    adapter.setNoConnection();
                                }
                                break;
                            case SUCCESS:
                                Timber.d("onChanged: cache refreshed with data size: " + listResource.data.size());
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
        adapter = new CharactersRecyclerAdapter(initGlide(), this);
        SpacingItemDecorator itemDecorator = new SpacingItemDecorator(30, 30);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)
                        && !adapter.getSelectedCharacter(adapter.getItemCount()-1).getName().equals(EXHAUSTED_TYPE_TEXT)
                        && !adapter.getSelectedCharacter(adapter.getItemCount()-1).getName().equals(NO_CONNECTION_TYPE_TEXT)) {
                    //TODO: block incrementing next page when no internet and no more results
                    charactersListViewModel.searchNextPage();
                }
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private RequestManager initGlide() {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(initCircularProgressDrawable())
                .error(R.drawable.ic_image_placeholder);
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
                return false;
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

    @Override
    public void onCharacterSelected(int position) {
        Timber.d("onCharacterClick: Character was clicked: " + adapter.getSelectedCharacter(position).toString());
        Intent intent = new Intent(this, CharacterDetailsActivity.class);
        intent.putExtra(INTENT_EXTRA_CHARACTER, adapter.getSelectedCharacter(position));
        startActivity(intent);
    }
}
