package mx.caltec.archrepositorysample.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String sypnosis;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSypnosis() {
        return sypnosis;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSypnosis(String sypnosis) {
        this.sypnosis = sypnosis;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sypnosis='" + sypnosis + '\'' +
                '}';
    }
}
