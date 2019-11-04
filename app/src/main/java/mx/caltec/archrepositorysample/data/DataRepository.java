package mx.caltec.archrepositorysample.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Observable;
import mx.caltec.archrepositorysample.data.api.FakeApi;
import mx.caltec.archrepositorysample.data.api.MovieApi;
import mx.caltec.archrepositorysample.data.api.NetworkBoundResource;
import mx.caltec.archrepositorysample.data.database.AppDatabase;
import mx.caltec.archrepositorysample.data.model.Movie;
import mx.caltec.archrepositorysample.util.AppExecutors;

public class DataRepository {
    private static final String TAG = "DataRepository";

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private final FakeApi mFakeApi;

    private AppExecutors mAppExecutors;

    private DataRepository(final AppDatabase appDatabase, final FakeApi fakeApi,
                           final AppExecutors appExecutors) {
        mDatabase = appDatabase;
        //mObservableMovies = new MediatorLiveData<>();
        mAppExecutors = appExecutors;
        mFakeApi = fakeApi;

        /*mObservableMovies.addSource(loadAllMovies(), resource -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableMovies.postValue(resource);
                    }
                });*/
    }

    public static DataRepository getInstance(final AppDatabase database,
                                             final FakeApi fakeApi,
                                             final AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, fakeApi, appExecutors);
                }
            }
        }
        return sInstance;
    }

    /*public LiveData<Resource<List<Movie>>> getMovies() {
        return mObservableMovies;
    }*/

    public LiveData<Resource<List<Movie>>> getMovies() {
        return new NetworkBoundResource<List<Movie>, List<MovieApi>>() {
            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return true;
            }

            @Override
            protected void saveCallResult(@NonNull List<MovieApi> remoteMovies) {
                Log.d(TAG, "remote movies size: " + remoteMovies.size());
                //random movies

                Movie remoteMovie = new Movie();
                remoteMovie.setTitle(String.valueOf(Math.random()));
                remoteMovie.setSypnosis(String.valueOf(Math.random()));

                mDatabase.movieDao().insertMovie(remoteMovie);
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return mDatabase.movieDao().loadAllMovies();
            }

            @NonNull
            @Override
            protected Observable<List<MovieApi>> createCall() {
                return mFakeApi.getMovies();
            }
        }.getAsLiveData();
    }

    public void insertMovie(final Movie movie) {
        mAppExecutors.diskIO().execute(() ->
                mDatabase.runInTransaction(() -> mDatabase.movieDao().insertMovie(movie)));
    }

}
