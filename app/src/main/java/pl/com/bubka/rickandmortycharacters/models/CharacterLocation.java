package pl.com.bubka.rickandmortycharacters.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import androidx.room.ColumnInfo;

public class CharacterLocation implements Parcelable {

    @SerializedName("name")
    @ColumnInfo(name = "location_name")
    String name;

    @SerializedName("url")
    @ColumnInfo(name = "location_url")
    String url;

    public CharacterLocation(){}

    public CharacterLocation(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected CharacterLocation(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<CharacterLocation> CREATOR = new Creator<CharacterLocation>() {
        @Override
        public CharacterLocation createFromParcel(Parcel in) {
            return new CharacterLocation(in);
        }

        @Override
        public CharacterLocation[] newArray(int size) {
            return new CharacterLocation[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CharacterLocation{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(url);
    }
}
