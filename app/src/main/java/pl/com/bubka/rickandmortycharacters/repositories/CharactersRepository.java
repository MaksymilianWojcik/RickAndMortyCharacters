package pl.com.bubka.rickandmortycharacters.repositories;

import android.content.Context;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import pl.com.bubka.rickandmortycharacters.AppExecutors;
import pl.com.bubka.rickandmortycharacters.database.CharacterDao;
import pl.com.bubka.rickandmortycharacters.database.CharacterDatabase;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.requests.ServiceGenerator;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.CharacterSearchResponse;
import pl.com.bubka.rickandmortycharacters.utils.NetworkBoundResources;
import pl.com.bubka.rickandmortycharacters.utils.Resource;
import timber.log.Timber;

public class CharactersRepository {

    private static final String TAG = "CharacterRepository";
    private static CharactersRepository instance;
    private CharacterDao characterDao;

    public static CharactersRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CharactersRepository(context);
        }
        return instance;
    }

    private CharactersRepository(Context context) {
        characterDao = CharacterDatabase.getInstance(context).getCharacterDao();
    }

    public LiveData<Resource<List<Character>>> searchCharactersApi(final String name, final int pageNumber) {
        return new NetworkBoundResources<List<Character>, CharacterSearchResponse>(AppExecutors.getInstance()) {

            @Override
            protected void saveCallResult(@NonNull CharacterSearchResponse item) {
                //retrofit -> cache
                if (item.getCharactersList() != null) {
                    Character[] characters = new Character[item.getCharactersList().size()];
                    int index = 0;
                    for (long rowId : characterDao.insertCharacters((Character[]) (item.getCharactersList().toArray(characters)))) {
                        if (rowId == -1) {
                            Timber.d("saveCallResult: Already in cache");
                            characterDao.updateCharacter(characters[index]);
                        }
                        index++;
                    }
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Character> data) {
                return true; //TODO: timestamp??
            }

            @NonNull
            @Override
            protected LiveData<List<Character>> loadFromDb() {
                Timber.d("loadFromDb: ");
                return characterDao.searchCharacters(name, pageNumber);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CharacterSearchResponse>> createCall() {
                return ServiceGenerator.getRickAndMortyApi().searchCharacters(name, String.valueOf(pageNumber));
            }
        }.getAsLiveData();
    }
}
