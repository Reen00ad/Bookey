package com.example.bookybookey.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;


  //  @NotEmpty(message = "ISBN cannot be empty")
    @Column(columnDefinition = "varchar(100) unique")
    private String ISBN;//int


    @NotEmpty(message = "title cannot be empty")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String bookTitle;


    @NotEmpty(message = "bookGenre cannot be empty")
    @Column(columnDefinition = "varchar(100) not null")
    private String authorName;


    @NotNull(message = "number of pages cannot be empty")
    @Column(columnDefinition = "int not null")
    private Integer noOfPages;

  //private String coverImageUrl;

    //relations


    @ManyToMany(cascade =CascadeType.ALL )
    @JsonIgnore
    private Set<ReadingList> readingLists;


}
