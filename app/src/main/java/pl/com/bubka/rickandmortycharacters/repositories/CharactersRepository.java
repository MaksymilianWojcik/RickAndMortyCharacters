package pl.com.bubka.rickandmortycharacters.repositories;

import android.content.Context;

public class CharactersRepository {

    private static CharactersRepository instance;

    public static CharactersRepository getInstance(Context context) {
        if(instance == null){
            instance = new CharactersRepository(context);
        }
        return instance;
    }

    private CharactersRepository(Context context){

    }
}
