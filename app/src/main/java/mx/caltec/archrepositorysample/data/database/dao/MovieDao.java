package mx.caltec.archrepositorysample.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import mx.caltec.archrepositorysample.data.model.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void inserMovies(Movie... movies);

    @Query("SELECT * FROM movies WHERE movies.title like :filter")
    LiveData<List<Movie>> loadMoviesLike(String filter);

}
