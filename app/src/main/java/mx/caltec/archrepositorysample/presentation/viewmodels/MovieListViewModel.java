package mx.caltec.archrepositorysample.presentation.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import mx.caltec.archrepositorysample.data.DataRepository;
import mx.caltec.archrepositorysample.data.Resource;
import mx.caltec.archrepositorysample.data.model.Movie;

public class MovieListViewModel extends ViewModel {
    private static final String TAG = "MovieListViewModel";

    private final DataRepository mRepository;

    private String mLike;

    private final MediatorLiveData<Resource<List<Movie>>> mObservableMovies;

    @Inject
    public MovieListViewModel(DataRepository dataRepository) {
        mRepository = dataRepository;
        mObservableMovies = new MediatorLiveData<>();
        mObservableMovies.addSource(mRepository.getMovies(), mObservableMovies::setValue);
    }

    /**
     * Expose the LiveData Movies query so the UI can observe it.
     */
    public LiveData<Resource<List<Movie>>> getMovies() {
        return mObservableMovies;
    }

    public void getMoviesLike(String like) {
        this.mLike = like;

        if (mLike==null||mLike.trim().isEmpty()) {
            mObservableMovies.removeSource(mRepository.getMoviesLike(""));
            mObservableMovies.addSource(mRepository.getMovies(), mObservableMovies::setValue);
            return;
        }

        mObservableMovies.removeSource(mRepository.getMovies());
        mObservableMovies.addSource(mRepository.getMoviesLike(mLike), resource -> {
            Log.d("xy", (resource.data!=null ? resource.data.size() + "" : "0"));
            mObservableMovies.setValue(resource);
        });
    }

    public void reloadMovies() {
        mObservableMovies.removeSource(mRepository.getMovies());
        mObservableMovies.addSource(mRepository.getMovies(), mObservableMovies::setValue);
    }

    public void addMovie(String title, String synopsis) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setSypnosis(synopsis);
        mRepository.insertMovie(movie);
    }

}
