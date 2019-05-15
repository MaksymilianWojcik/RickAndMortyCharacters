package pl.com.bubka.rickandmortycharacters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.ListPreloader;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.models.Character;

public class CharactersRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CHARACTERS_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int EXHAUSTED_TYPE = 3;

    private List<Character> characterList;
    private RequestManager requestManager;


    public CharactersRecyclerAdapter(RequestManager requestManager){
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        switch(viewType){
            case CHARACTERS_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_item, parent, false);
                return new CharacterViewHolder(view, requestManager);
            }
            case LOADING_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            }

            case EXHAUSTED_TYPE:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exhausted_list_item, parent, false);
                return new SearchExhaustedViewHolder(view);
            }
            default:{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_item, parent, false);
                return new CharacterViewHolder(view, requestManager);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == CHARACTERS_TYPE){
            ((CharacterViewHolder)holder).onBind(characterList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(characterList.get(position).getName().equals("LOADING...")){
            return LOADING_TYPE;
        } else if (characterList.get(position).getName().equals("EXHAUSTED...")){
            return EXHAUSTED_TYPE;
        } else {
            return CHARACTERS_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if(characterList != null){
            return characterList.size();
        }
        return 0;
    }

    /***
     * Display loading when searching request
     */
    public void displayOnlyLoading(){
        clearCharactersList();
        Character loading = new Character();
        loading.setName("LOADING...");
        characterList.add(loading);
        notifyDataSetChanged();
    }

    private void clearCharactersList(){
        if(characterList == null){
            characterList = new ArrayList<>();
        } else {
            characterList.clear();
        }
        notifyDataSetChanged();
    }

    public void setQueryExhausted(){
        hideLoading();
        Character exhausted = new Character();
        exhausted.setName("EXHAUSTED...");
        characterList.add(exhausted);
        notifyDataSetChanged();
    }

    public void hideLoading(){
        if(isLoading()){
            if(characterList.get(0).getName().equals("LOADING...")){
                characterList.remove(0);
            } else if (characterList.get(characterList.size() - 1).equals("LOADING...")){
                characterList.remove(characterList.size()-1);
            }
            notifyDataSetChanged();
        }
    }

    /***
     * Pagination loading
     */
    public void displayLoading(){
        if(characterList == null){
            characterList = new ArrayList<>();
        }

        if (!isLoading()){
            Character loading = new Character();
            loading.setName("LOADING...");
            characterList.add(loading);
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if (characterList != null){
            if (characterList.size() > 0){
                if (characterList.get(characterList.size()-1).getName().equals("LOADING...")){
                    return true;
                }
            }
        }
        return false;
    }

    public void setCharacters(List<Character> characterList){
        this.characterList = characterList;
        notifyDataSetChanged();
    }

}
