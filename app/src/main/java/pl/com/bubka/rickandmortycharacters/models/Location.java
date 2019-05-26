package pl.com.bubka.rickandmortycharacters.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class Location {

    @ColumnInfo(name = "location_id")
    @PrimaryKey
    @NonNull
    String id;
    String name;
    String type;
    String dimension;
    String url;
    @ColumnInfo(name = "created_date")
    @SerializedName("created")
    String createdDate;
    String[] residents; //TODO transform every string url form list into character or at least id

    public Location(){}

    public Location(@NonNull String id, String name, String type, String dimension, String url, String createdDate, String[] residents) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.dimension = dimension;
        this.url = url;
        this.createdDate = createdDate;
        this.residents = residents;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String[] getResidents() {
        return residents;
    }

    public void setResidents(String[] residents) {
        this.residents = residents;
    }
}

/*


"results": [
    {
      "id": 1,
      "name": "Earth",
      "type": "Planet",
      "dimension": "Dimension C-137",
      "residents": [
        "https://rickandmortyapi.com/api/character/1",
        "https://rickandmortyapi.com/api/character/2",
        // ...
      ],
      "url": "https://rickandmortyapi.com/api/location/1",
      "created": "2017-11-10T12:42:04.162Z"
    }
    // ...
  ]
 */