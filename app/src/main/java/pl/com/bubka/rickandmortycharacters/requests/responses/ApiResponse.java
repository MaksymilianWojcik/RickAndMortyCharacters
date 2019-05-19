package pl.com.bubka.rickandmortycharacters.requests.responses;

import java.io.IOException;

import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.RickAndMortyApplication;
import retrofit2.Response;
import timber.log.Timber;

public class ApiResponse<T> {

    public ApiResponse<T> create(Throwable error) {
        Timber.w(error);
        return new ErrorResponse<>(RickAndMortyApplication.getInstance().getResources().getString(R.string.error_getting_results));
    }

    public ApiResponse<T> create(Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();

            if (body == null || response.code() == 204) {
                return new EmptyResponse<>();
            } else {
                return new SuccesResponse<>(body);
            }
        } else {
            String errorMessage = "";
            try {
                errorMessage = response.errorBody().string();
            } catch (IOException e) {
                Timber.e(e);
                errorMessage = response.message();
            }
            return new ErrorResponse<>(errorMessage);
        }
    }


    public class SuccesResponse<T> extends ApiResponse<T> { //T to to co z api dosatniemy

        private T body;

        public SuccesResponse(T body) {
            this.body = body;
        }

        public T getBody() {
            return body;
        }
    }

    public class ErrorResponse<T> extends ApiResponse<T> {
        private String errorMessage;

        public ErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public class EmptyResponse<T> extends ApiResponse<T> {

    }
}
