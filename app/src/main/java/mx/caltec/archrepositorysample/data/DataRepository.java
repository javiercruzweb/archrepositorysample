package mx.caltec.archrepositorysample.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import mx.caltec.archrepositorysample.data.model.Movie;
import mx.caltec.archrepositorysample.util.AppExecutors;

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;

    private MediatorLiveData<List<Movie>> mObservableMovies;

    private AppExecutors mAppExecutors;

    private DataRepository(final AppDatabase appDatabase, final AppExecutors appExecutors) {
        mDatabase = appDatabase;
        mObservableMovies = new MediatorLiveData<>();
        mAppExecutors = appExecutors;

        mObservableMovies.addSource(mDatabase.movieDao().loadAllMovies(), movies -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableMovies.postValue(movies);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database,
                                             final AppExecutors appExecutors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, appExecutors);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of movies from the database and get notified when the data changes.
     */
    public LiveData<List<Movie>> getMovies() {
        return mObservableMovies;
    }

    public void insertMovie(final Movie movie) {
        mAppExecutors.diskIO().execute(() ->
                mDatabase.runInTransaction(() -> mDatabase.movieDao().insertMovie(movie)));
    }

}
