package pl.com.bubka.rickandmortycharacters.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import pl.com.bubka.rickandmortycharacters.models.Character;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CharacterDao {

    @Insert(onConflict = IGNORE)
    long[] insertCharacters(Character... characters);
    // ex. {-1, id2, -1, id4, id5}

    @Insert(onConflict = REPLACE)
    void insertCharacter(Character character);

    @Query("UPDATE characters SET id = :id, name = :name, species = :species, type = :type, gender = :gender, imageUrl = :imageUrl, episodes = :episodes, url = :url, createdDate = :createdDate " +
            "WHERE id = :id")
    void updateCharacter(int id, String name, String species, String type, String gender, String imageUrl, String[] episodes, String url, String createdDate);

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%' OR episodes LIKE '%' || :query || '%' " +
            "ORDER BY name DESC LIMIT (:pageNumber * 30)")
    LiveData<List<Character>> searchCharacters(String query, int pageNumber);

    @Query("SELECT * FROM characters WHERE id = :id")
    LiveData<Character> getCharacter(String id);
}
