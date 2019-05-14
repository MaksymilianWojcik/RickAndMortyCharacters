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


    //TODO: Take this out
    public class InfoResponse {

        @SerializedName("count")
        @Expose()
        private int count;

        @SerializedName("pages")
        @Expose()
        private int pages;

        @SerializedName("next")
        @Expose()
        String nextPageUrl;

        @SerializedName("prev")
        @Expose()
        String prevPageUrl;

        public int getCount() {
            return count;
        }

        public int getPages() {
            return pages;
        }

        public String getNextPageUrl() {
            return nextPageUrl;
        }

        public String getPrevPageUrl() {
            return prevPageUrl;
        }

        @Override
        public String toString() {
            return "InfoResponse{" +
                    "count=" + count +
                    ", pages=" + pages +
                    ", nextPageUrl='" + nextPageUrl + '\'' +
                    ", prevPageUrl='" + prevPageUrl + '\'' +
                    '}';
        }
    }

    public InfoResponse getInfoResponse() {
        return infoResponse;
    }

    public List<Character> getResultsList() {
        return resultsList;
    }

    @Override
    public String toString() {
        return "CharacterSearchResponse{" +
                "infoResponse=" + infoResponse +
                ", resultsList=" + resultsList +
                '}';
    }
}
