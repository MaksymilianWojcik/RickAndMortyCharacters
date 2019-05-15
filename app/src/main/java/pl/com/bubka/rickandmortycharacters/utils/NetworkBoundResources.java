package pl.com.bubka.rickandmortycharacters.utils;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import pl.com.bubka.rickandmortycharacters.AppExecutors;
import pl.com.bubka.rickandmortycharacters.requests.responses.ApiResponse;

public abstract class NetworkBoundResources<CacheObject, RequestObject> {

    private AppExecutors executors;

    private MediatorLiveData<Resource<CacheObject>> results = new MediatorLiveData<>();


    public NetworkBoundResources(AppExecutors executors) {
        this.executors = executors;
        init();
    }

    private void init() {

        results.setValue((Resource<CacheObject>) Resource.loading(null)); //we have nothing yet
        final LiveData<CacheObject> dbSource = loadFromDb();

        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                results.removeSource(dbSource);

                if (shouldFetch(cacheObject)) {
                    fetchFromNetwork(dbSource);
                } else {
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(CacheObject cacheObject) {
                            setValue(Resource.success(cacheObject));
                        }
                    });
                }
            }
        });
    }

    private void fetchFromNetwork(final LiveData<CacheObject> dbSource) {
        results.addSource(dbSource, new Observer<CacheObject>() {
            @Override
            public void onChanged(CacheObject cacheObject) {
                setValue(Resource.loading(cacheObject));
            }
        });

        final LiveData<ApiResponse<RequestObject>> apiResponse = createCall();
        results.addSource(apiResponse, new Observer<ApiResponse<RequestObject>>() {
            @Override
            public void onChanged(final ApiResponse<RequestObject> requestObjectApiResponse) {
                results.removeSource(dbSource);
                results.removeSource(apiResponse);

                if (requestObjectApiResponse instanceof ApiResponse.SuccesResponse) {

                    executors.diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            saveCallResult((RequestObject) processResponse((ApiResponse.SuccesResponse) requestObjectApiResponse));
                            executors.mainThread().execute(new Runnable() {
                                @Override
                                public void run() {
                                    results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                        @Override
                                        public void onChanged(@Nullable CacheObject cacheObject) {
                                            setValue(Resource.success(cacheObject));
                                        }
                                    });
                                }
                            });
                        }
                    });

                } else if (requestObjectApiResponse instanceof ApiResponse.EmptyResponse) {
                    executors.mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            results.addSource(loadFromDb(), new Observer<CacheObject>() {
                                @Override
                                public void onChanged(@Nullable CacheObject cacheObject) {
                                    setValue(Resource.success(cacheObject)); //empty - request succesfull, ale nic nie zwrocilo, dlatego chcemy cache
                                }
                            });
                        }
                    });
                } else if (requestObjectApiResponse instanceof ApiResponse.ErrorResponse) {
                    results.addSource(dbSource, new Observer<CacheObject>() {
                        @Override
                        public void onChanged(@Nullable CacheObject cacheObject) {
                            setValue(Resource.error(((ApiResponse.ErrorResponse) requestObjectApiResponse).getErrorMessage(), cacheObject));
                        }
                    });
                }

            }
        });
    }

    private CacheObject processResponse(ApiResponse.SuccesResponse response) {
        return (CacheObject) response.getBody();
    }

    private void setValue(Resource<CacheObject> newValue) {
        if (results.getValue() != newValue) {
            results.setValue(newValue);
        }
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestObject item);

    @MainThread
    protected abstract boolean shouldFetch(@Nullable CacheObject data);

    @NonNull
    @MainThread
    protected abstract LiveData<CacheObject> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestObject>> createCall();

    public final LiveData<Resource<CacheObject>> getAsLiveData() {
        return results;
    }
}
