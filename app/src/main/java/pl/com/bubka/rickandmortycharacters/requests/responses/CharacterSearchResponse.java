package pl.com.bubka.rickandmortycharacters.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.com.bubka.rickandmortycharacters.models.Character;

public class CharacterSearchResponse {


    @SerializedName("info")
    @Expose()
    private InfoResponse infoResponse;

    @SerializedName("results")
    @Expose()
    private List<Character> resultsList;

    public InfoResponse getInfoResponse() {
        return infoResponse;
    }

    public List<Character> getCharactersList() {
        return resultsList;
    }

    @Override
    public String toString() {
        return "CharacterSearchResponse{" +
                "infoResponse=" + infoResponse.toString() +
                ", resultsList=" + resultsList +
                '}';
    }
}
