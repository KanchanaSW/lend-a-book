package com.system.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class MovieDTO {
    private Long movieId;
    private String title;
    private String status;
    private String length;
    private String image;
    private boolean r18;
    private Integer noOfCopies;
    private String description;
}
