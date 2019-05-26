package pl.com.bubka.rickandmortycharacters.requests;

import androidx.lifecycle.LiveData;
import pl.com.bubka.rickandmortycharacters.models.Character;
import pl.com.bubka.rickandmortycharacters.models.Episode;
import pl.com.bubka.rickandmortycharacters.models.Location;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.CharacterSearchResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.EpisodeSearchResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.LocationSearchResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RickAndMortyApi {

    @GET("api/character/")
    LiveData<ApiResponse<CharacterSearchResponse>> searchCharacters(
            @Query("name") String name,
            @Query("page") String page
    );

    @GET("api/character/{id}")
    LiveData<ApiResponse<Character>> searchCharacter(
            @Path("id") String id
    );

    @GET("api/location/")
    LiveData<ApiResponse<LocationSearchResponse>> searchLocations(
            @Query("name") String name,
            @Query("page") String page
    );

    @GET("api/location/{id}")
    LiveData<ApiResponse<Location>> searchLocation(
            @Path("name") String id
    );

    @GET("api/episode/")
    LiveData<ApiResponse<EpisodeSearchResponse>> searchEpisodes(
            @Query("name") String name,
            @Query("page") String page
    );

    @GET("api/episode/{id}")
    LiveData<ApiResponse<Episode>> searchEpisode(
            @Path("name") String id
    );

}
