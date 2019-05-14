package pl.com.bubka.rickandmortycharacters.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        // Check#1 upewnic sie ze CallAdapter zwraca LiveDate
        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class) {
            return null;
        }

        // Check#2 typ ktory LiveData wrapuje. CZYLI TAK NAPRAWDE DOSTAJEMY SIE DO T
        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);

        // Check czy to Type apiResponse
        Type rawObservableType = CallAdapter.Factory.getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("Type must be a defined resource");
        }

        // Check #3 sprawdzamy czy ApiResponse jest parametrized, czyli czy ApiResponse<T> istnieje? (musi byc T)
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Resource must be parametrized");
        }

        Type bodyType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<Type>(bodyType);
    }
}
