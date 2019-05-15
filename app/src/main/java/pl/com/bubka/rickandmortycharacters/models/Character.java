package pl.com.bubka.rickandmortycharacters.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import pl.com.bubka.rickandmortycharacters.database.Converters;


@Entity(tableName = "characters")
public class Character implements Parcelable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @ColumnInfo(name = "character_id")
    private String character_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "species")
    private String species;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "gender")
    private String gender;

//TODO:    private Origin origin;
//TODO:    private Location location;

    @ColumnInfo(name = "imageUrl")
    @SerializedName("image")
    private String imageUrl;

    @ColumnInfo(name = "episodes")
    @SerializedName("episode")
    private String[] episodes;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "createdDate")
    @SerializedName("created")
    private String createdDate;

    public Character(){}

    public Character(String character_id, String name, String status, String species, String type, String gender, String imageUrl, String[] episodes, String url, String createdDate) {
        this.character_id = character_id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.episodes = episodes;
        this.url = url;
        this.createdDate = createdDate;
    }

    protected Character(Parcel in){
        character_id = in.readString();
        name = in.readString();
        status = in.readString();
        species = in.readString();
        type = in.readString();
        gender = in.readString();
        imageUrl = in.readString();
        episodes = in.createStringArray();
        url = in.readString();
        createdDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(character_id);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(species);
        dest.writeString(type);
        dest.writeString(gender);
        dest.writeString(imageUrl);
        dest.writeStringArray(episodes);
        dest.writeString(url);
        dest.writeString(createdDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public String getCharacter_id() {
        return character_id;
    }

    public void setCharacter_id(String character_id) {
        this.character_id = character_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String[] getEpisodes() {
        return episodes;
    }

    public void setEpisodes(String[] episodes) {
        this.episodes = episodes;
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

    @Override
    public String toString() {
        return "Character{" +
                "id=" + character_id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", species='" + species + '\'' +
                ", type='" + type + '\'' +
                ", gender='" + gender + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", episodes=" + Arrays.toString(episodes) +
                ", url='" + url + '\'' +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}


/*
"id": 1,
      "name": "Rick Sanchez",
      "status": "Alive",
      "species": "Human",
      "type": "",
      "gender": "Male",
      "origin": {
        "name": "Earth",
        "url": "https://rickandmortyapi.com/api/location/1"
      },
      "location": {
        "name": "Earth",
        "url": "https://rickandmortyapi.com/api/location/20"
      },
      "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
      "episode": [
        "https://rickandmortyapi.com/api/episode/1",
        "https://rickandmortyapi.com/api/episode/2",
        // ...
      ],
      "url": "https://rickandmortyapi.com/api/character/1",
      "created": "2017-11-04T18:48:46.250Z"
 */