package pl.com.bubka.rickandmortycharacters.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import pl.com.bubka.rickandmortycharacters.models.Episode;

public class EpisodeSearchResponse {


    @SerializedName("info")
    @Expose()
    private InfoResponse infoResponse;

    @SerializedName("results")
    @Expose()
    private List<Episode> episodesList;

    public InfoResponse getInfoResponse() {
        return infoResponse;
    }

    public List<Episode> getEpisodesList() {
        return episodesList;
    }

    @Override
    public String toString() {
        return "EpisodeSearchResponse{" +
                "infoResponse=" + infoResponse +
                ", episodesList=" + episodesList +
                '}';
    }
}
