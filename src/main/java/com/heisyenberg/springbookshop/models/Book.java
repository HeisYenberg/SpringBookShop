package com.heisyenberg.springbookshop.models;

import com.heisyenberg.springbookshop.dtos.BookDTO;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Double price;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imageName;

    public Book(BookDTO bookDTO) {
        title = bookDTO.getTitle();
        author = bookDTO.getAuthor();
        genre = bookDTO.getGenre();
        price = bookDTO.getPrice();
        description = bookDTO.getDescription();
        imageName = bookDTO.getImageName();
    }
}
