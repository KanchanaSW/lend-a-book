package com.system.Integration.Models;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import lombok.Data;
import org.springframework.test.context.TestPropertySource;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "ExBooks")
public class ExBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @NotNull
    public Long isbn;
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private String publisher;
    @NotNull
    private String status;
    private String coverPage;
    @Column(columnDefinition = "text")
    private String summary;
    private Integer noOfCopies;
}

