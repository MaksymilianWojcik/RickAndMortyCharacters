package pl.com.bubka.rickandmortycharacters.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import pl.com.bubka.rickandmortycharacters.models.Episode;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface EpisodeDao {

    @Insert(onConflict = REPLACE)
    long[] insertEpisodes(Episode... locations);

    @Insert(onConflict = REPLACE)
    void insertEpisode(Episode episode);

    @Query("SELECT * FROM episodes WHERE name LIKE '%' || :name || '%' " +
            "ORDER BY name ASC LIMIT (:pageNumber * 20)")
    LiveData<List<Episode>> searchEpisodes(String name, int pageNumber);

    @Query("SELECT * FROM episodes WHERE episode_id = :episode_id")
    LiveData<Episode> searchEpisode(String episode_id);
}
