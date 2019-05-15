package pl.com.bubka.rickandmortycharacters.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import pl.com.bubka.rickandmortycharacters.models.Character;

@Database(entities = {Character.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class CharacterDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "characters_db";

    private static CharacterDatabase instance;

    public static CharacterDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CharacterDatabase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    //nie potrzebny prywatny konstruktor, w koncu to klasa abtrakcyjna

    public abstract CharacterDao getCharacterDao();
}