package mx.caltec.archrepositorysample.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import mx.caltec.archrepositorysample.MovieApp;
import mx.caltec.archrepositorysample.data.DataRepository;
import mx.caltec.archrepositorysample.data.Resource;
import mx.caltec.archrepositorysample.data.model.Movie;

public class MovieListViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    private String mLike;

    private final MediatorLiveData<Resource<List<Movie>>> mObservableMovies;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((MovieApp) application).getRepository();
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
        mObservableMovies.addSource(mRepository.getMoviesLike(mLike), new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> resource) {
                Log.d("xy", (resource.data!=null ? resource.data.size() + "" : "0"));
                mObservableMovies.setValue(resource);
            }
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
