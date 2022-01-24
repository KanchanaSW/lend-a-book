package com.system.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    public Integer id;
    public Long isbn;
    private String title;
    private String author;
    private String publisher;
    private String status;
    private String coverPage;
    private String summary;
    private Integer noOfCopies;

    public BookDTO(Long isbn, String title, String author, String publisher, String status, String coverPage, String summary, Integer noOfCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.status = status;
        this.coverPage = coverPage;
        this.summary = summary;
        this.noOfCopies = noOfCopies;
    }
}
