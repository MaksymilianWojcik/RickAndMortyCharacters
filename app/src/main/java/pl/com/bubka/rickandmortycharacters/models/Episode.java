package pl.com.bubka.rickandmortycharacters.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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
    @SerializedName("characters")
    @Ignore //TODO: Handle it and best would be to store as id
    List<String> characterUrlList;
    String url;
    @ColumnInfo(name = "created_date")
    @SerializedName("created")
    String createdDate;

    public Episode() {}

    public Episode(@NonNull String id, String name, String airDate, String episode, List<String> characterUrlList, String url, String createdDate) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episode = episode;
        this.characterUrlList = characterUrlList;
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

    public List<String> getCharacterUrlList() {
        return characterUrlList;
    }

    public void setCharacterUrlList(List<String> characterUrlList) {
        this.characterUrlList = characterUrlList;
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
