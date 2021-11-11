package com.system.DTO;

import javax.validation.constraints.NotNull;

public class MovieDTO {
    private Long movieId;
    private String title;
    private String directer;
    private String writer;
    private int copiesAvailable;
    private boolean r18;

    public MovieDTO() {
    }

    public MovieDTO(String title, String directer, String writer, int copiesAvailable, boolean r18) {
        this.title = title;
        this.directer = directer;
        this.writer = writer;
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
}
