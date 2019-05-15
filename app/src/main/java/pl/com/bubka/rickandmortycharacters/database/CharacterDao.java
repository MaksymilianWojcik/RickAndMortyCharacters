package pl.com.bubka.rickandmortycharacters.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import pl.com.bubka.rickandmortycharacters.models.Character;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CharacterDao {

    @Insert(onConflict = IGNORE)
    long[] insertCharacters(Character... characters);

    @Insert(onConflict = REPLACE)
    void insertCharacter(Character character);

    //TODO: Figure out why @query with String[] is not working
//    @TypeConverters({Converters.class})
//    @Query("UPDATE characters SET name = :name, status = :status, species = :species, type = :type, gender = :gender, imageUrl = :imageUrl, episodes = " + ":episodes, url = :url, createdDate = :createdDate " +
//            "WHERE character_id = :character_id")
//    void updateCharacter(String character_id, String name, String status, String species, String type, String gender, String imageUrl, String[] episodes, String url, String createdDate);

    @Update
    void updateCharacter(Character characters);

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :name || '%' " +
            "ORDER BY name DESC LIMIT (:pageNumber * 20)")
    LiveData<List<Character>> searchCharacters(String name, int pageNumber);

    @Query("SELECT * FROM characters WHERE character_id = :character_id")
    LiveData<Character> getCharacter(String character_id);
}
