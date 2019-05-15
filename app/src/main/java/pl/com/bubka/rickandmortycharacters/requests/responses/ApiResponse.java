package pl.com.bubka.rickandmortycharacters.requests.responses;

import android.content.res.Resources;

import java.io.IOException;

import pl.com.bubka.rickandmortycharacters.R;
import retrofit2.Response;

public class ApiResponse<T> {

    public ApiResponse<T> create(Throwable error) { //TODO: ????
        return new ErrorResponse<>(error.getMessage().equals("") ?  Resources.getSystem().getString(R.string.error_getting_results) : error.getMessage());
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
            String errorMesssage;
            try {
                errorMesssage = response.errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
                errorMesssage = response.message();
            }
            return new ErrorResponse<>(errorMesssage);
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
