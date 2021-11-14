package com.system.DTO;

import com.system.models.Quantity;
import lombok.Data;

@Data
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
}
