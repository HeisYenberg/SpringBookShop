package com.heisyenberg.springbookshop.dtos;

import com.heisyenberg.springbookshop.models.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDto {
    @NotEmpty(message = "Необходимо указать название")
    private String title;
    @NotEmpty(message = "Необходимо указать автора")
    private String author;
    @NotEmpty(message = "Необходимо указать жанр")
    private String genre;
    @Min(0)
    private Double price;
    @Size(min = 10, max = 500,
            message = "Описание должно быть в пределах от 10 до 500 символов")
    private String description;
    private MultipartFile imageFile;
    private String imageName;

    public BookDto(Book book) {
        title = book.getTitle();
        author = book.getAuthor();
        genre = book.getGenre();
        price = book.getPrice();
        description = book.getDescription();
        imageName = book.getImageName();
    }
}
