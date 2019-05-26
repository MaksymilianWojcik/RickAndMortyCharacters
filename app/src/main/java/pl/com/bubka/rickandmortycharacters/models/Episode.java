package pl.com.bubka.rickandmortycharacters.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "episodes")
public class Episode {

    @ColumnInfo(name = "episode_id")
    @PrimaryKey
    @NonNull
    String id;
    String name;
    @SerializedName("air_date")
    String airDate;
    String episode;
    List<Character> charactersList;
    String url;
    @ColumnInfo(name = "created_date")
    @SerializedName("created")
    String createdDate;

    public Episode() {}

    public Episode(@NonNull String id, String name, String airDate, String episode, List<Character> charactersList, String url, String createdDate) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episode = episode;
        this.charactersList = charactersList;
        this.url = url;
        this.createdDate = createdDate;
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

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public List<Character> getCharactersList() {
        return charactersList;
    }

    public void setCharactersList(List<Character> charactersList) {
        this.charactersList = charactersList;
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
}
