package pl.com.bubka.rickandmortycharacters.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.models.Episode;
import pl.com.bubka.rickandmortycharacters.models.Location;

@Database(entities = {Character.class, Location.class, Episode.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class CharacterDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "characters_db";

    private static CharacterDatabase instance;

    public static CharacterDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CharacterDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract CharacterDao getCharacterDao();

    public abstract EpisodeDao getEpisodeDao();

    public abstract LocationDao getLocationDao();
}