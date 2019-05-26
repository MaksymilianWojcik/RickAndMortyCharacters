package pl.com.bubka.rickandmortycharacters.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.repositories.CharactersRepository;
import pl.com.bubka.rickandmortycharacters.utils.Resource;

public class CharacterDetailsViewModel extends AndroidViewModel {

    private CharactersRepository charactersRepository;

    public CharacterDetailsViewModel(@NonNull Application application) {
        super(application);
        charactersRepository = CharactersRepository.getInstance(application);
    }

    public LiveData<Resource<Character>> searchCharacterApi(String characterId){
        return charactersRepository.searchCharacterApi(characterId);
    }
}
