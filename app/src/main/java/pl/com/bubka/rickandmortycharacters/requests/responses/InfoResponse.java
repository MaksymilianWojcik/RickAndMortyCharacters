package pl.com.bubka.rickandmortycharacters.requests.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
