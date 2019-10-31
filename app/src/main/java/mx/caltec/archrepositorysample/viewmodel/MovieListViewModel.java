package mx.caltec.archrepositorysample.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import mx.caltec.archrepositorysample.MovieApp;
import mx.caltec.archrepositorysample.data.DataRepository;
import mx.caltec.archrepositorysample.data.model.Movie;

public class MovieListViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Movie>> mObservableMovies;

    public MovieListViewModel(@NonNull Application application) {
        super(application);

        mObservableMovies = new MediatorLiveData<>();

        mObservableMovies.setValue(new ArrayList<>());

        mRepository = ((MovieApp) application).getRepository();
        LiveData<List<Movie>> movies = mRepository.getMovies();

        // observe the changes of the products from the database and forward them
        mObservableMovies.addSource(movies, mObservableMovies::setValue);

    }

    /**
     * Expose the LiveData Movies query so the UI can observe it.
     */
    public LiveData<List<Movie>> getMovies() {
        return mObservableMovies;
    }

    public void addMovie(String title, String synopsis) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setSypnosis(synopsis);
        mRepository.insertMovie(movie);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MovieListViewModel(mApplication);
        }
    }

}
