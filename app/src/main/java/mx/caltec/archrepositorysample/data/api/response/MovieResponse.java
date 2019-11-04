package mx.caltec.archrepositorysample.data.api.response;

import com.google.gson.annotations.Expose;

import java.util.List;

import mx.caltec.archrepositorysample.data.api.MovieApi;


public class MovieResponse {
    @Expose
    public List<MovieApi> movieList;
}
