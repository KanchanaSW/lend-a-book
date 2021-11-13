package com.system.DTO;


public class BookDTO {
    public Integer id;
    public Long isbn;
    private String title;
    private String author;
    private String publisher;
    private int copiesAvi;
    private String coverPage;

    public BookDTO(Long isbn, String title, String author, String publisher, int copiesAvi, String coverPage) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.copiesAvi = copiesAvi;
        this.coverPage = coverPage;
    }

    public BookDTO() {
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getCopiesAvi() {
        return copiesAvi;
    }

    public void setCopiesAvi(int copiesAvi) {
        this.copiesAvi = copiesAvi;
    }

    public String getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(String coverPage) {
        this.coverPage = coverPage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
