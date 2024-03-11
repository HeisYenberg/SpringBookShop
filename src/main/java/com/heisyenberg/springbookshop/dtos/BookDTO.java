package com.heisyenberg.springbookshop.dtos;

import com.heisyenberg.springbookshop.models.Book;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {
    private final static String LOAD_FOLDER = "public/images/";
    @NotEmpty(message = "Необходимо указать название")
    private String title;
    @NotEmpty(message = "Необходимо указать автора")
    private String author;
    @NotEmpty(message = "Необходимо указать жанр")
    private String genre;
    @Min(0)
    private Double price;
    @Size(min = 10, max = 500, message = "Описание должно быть в пределах от" +
            " 10 до 500 символов")
    private String description;
    private MultipartFile imageFile;
    private String imageName;

    public BookDTO(Book book) {
        title = book.getTitle();
        author = book.getAuthor();
        genre = book.getGenre();
        price = book.getPrice();
        description = book.getDescription();
        imageName = book.getImageName();
    }

    public void reUploadImageFile() {
        try {
            Path path = Paths.get(LOAD_FOLDER + imageName);
            Files.delete(path);
            loadImageFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadImageFile() {
        MultipartFile image = getImageFile();
        Date date = new Date();
        imageName = date.getTime() + "_" + image.getOriginalFilename();
        try {
            Path path = Paths.get(LOAD_FOLDER);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            InputStream inputStream = image.getInputStream();
            Files.copy(inputStream, Paths.get(LOAD_FOLDER + imageName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
