package pl.com.bubka.rickandmortycharacters.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Character implements Parcelable {

    private int id;
    private String name;
    private String species;
    private String type;
    private String gender;
//    private Origin origin;
//    private Location location;
    private String imareUrl;
    private String[] episodes;
    private String url;
    private String createdDate;

    public Character(){}

    public Character(int id, String name, String species, String type, String gender, String imareUrl, String[] episodes, String url, String createdDate) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.imareUrl = imareUrl;
        this.episodes = episodes;
        this.url = url;
        this.createdDate = createdDate;
    }

    protected Character(Parcel in){
        id = in.readInt();
        name = in.readString();
        species = in.readString();
        type = in.readString();
        gender = in.readString();
        imareUrl = in.readString();
        episodes = in.createStringArray();
        url = in.readString();
        createdDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(species);
        dest.writeString(type);
        dest.writeString(gender);
        dest.writeString(imareUrl);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImareUrl() {
        return imareUrl;
    }

    public void setImareUrl(String imareUrl) {
        this.imareUrl = imareUrl;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", type='" + type + '\'' +
                ", gender='" + gender + '\'' +
                ", imareUrl='" + imareUrl + '\'' +
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