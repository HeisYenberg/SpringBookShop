package com.heisyenberg.springbookshop.dtos;

import com.heisyenberg.springbookshop.models.Book;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

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
    @NotNull(message = "Необходимо указать цену")
    @Min(value = 100, message = "Минимальная цена от 100 рублей")
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
