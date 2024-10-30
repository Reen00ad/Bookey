package com.example.bookybookey.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class ReadingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer readingListId;

    @NotEmpty(message = "title cannot be empty")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String name;


    //relations


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private User user;
//
    @ManyToMany(cascade =CascadeType.ALL ,mappedBy = "readingLists")
    private Set<Book> books;
}
