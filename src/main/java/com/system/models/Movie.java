package com.system.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    @NotNull
    private String title;
    private String directer;
    private String writer;
    private String image;
    private int copiesAvailable;
    private boolean r18;

    public Movie() {
    }

    public Movie(String title, String directer, String writer, String image, int copiesAvailable, boolean r18) {
        this.title = title;
        this.directer = directer;
        this.writer = writer;
        this.image = image;
        this.copiesAvailable = copiesAvailable;
        this.r18 = r18;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirecter() {
        return directer;
    }

    public void setDirecter(String directer) {
        this.directer = directer;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public boolean isR18() {
        return r18;
    }

    public void setR18(boolean r18) {
        this.r18 = r18;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
