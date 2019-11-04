package mx.caltec.archrepositorysample.data.api;

import com.google.gson.annotations.Expose;

//fake movie just to match fake api response
public class MovieApi {
    @Expose
    public Long id;
    @Expose
    public String name;
    @Expose
    public String username;
    @Expose
    public String email;

    @Override
    public String toString() {
        return "MovieApi{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
