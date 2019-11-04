package mx.caltec.archrepositorysample.data.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface FakeWebservice {
    @GET("/users")
    Observable<List<MovieApi>> getMovies();
}
