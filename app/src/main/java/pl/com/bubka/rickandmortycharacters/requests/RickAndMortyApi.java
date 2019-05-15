package pl.com.bubka.rickandmortycharacters.requests;

import androidx.lifecycle.LiveData;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.CharacterDetailsResponse;
import pl.com.bubka.rickandmortycharacters.requests.responses.CharacterSearchResponse;
import retrofit2.Call;
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
    LiveData<ApiResponse<CharacterDetailsResponse>> getCharacterDetails(
            @Path("id") String id
    );

}
