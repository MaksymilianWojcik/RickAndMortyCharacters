package pl.com.bubka.rickandmortycharacters.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.com.bubka.rickandmortycharacters.models.Location;

public class LocationSearchResponse {

    @SerializedName("info")
    @Expose()
    private InfoResponse infoResponse;

    @SerializedName("results")
    @Expose()
    private List<Location> locations;

    public InfoResponse getInfoResponse() {
        return infoResponse;
    }

    public List<Location> getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return "LocationSearchResponse{" +
                "infoResponse=" + infoResponse.toString() +
                ", locations=" + locations +
                '}';
    }
}
