package pl.com.bubka.rickandmortycharacters.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import pl.com.bubka.rickandmortycharacters.models.Location;

@Dao
public interface LocationDao {

    @Insert
    long[] insertLocations(Location... locations);

    @Query("SELECT * FROM locations WHERE name LIKE '%' || :name || '%' " +
            "ORDER BY name ASC LIMIT (:pageNumber * 20)")
    LiveData<List<Location>> searchLocations(String name, int pageNumber);

    @Query("SELECT * FROM locations WHERE location_id = :location_id")
    LiveData<Location> searchLocation(String location_id);
}
