package com.heisyenberg.springbookshop.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

public class ImagesFilesHelper {
    private final static String IMAGES_FOLDER = "public/images/";

    public static String loadImage(final MultipartFile image) {
        Date date = new Date();
        String imageName = date.getTime() + "_" + image.getOriginalFilename();
        try {
            Path path = Paths.get(IMAGES_FOLDER);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            InputStream inputStream = image.getInputStream();
            Files.copy(
                    inputStream,
                    Paths.get(IMAGES_FOLDER + imageName),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageName;
    }

    public static String reloadImage(final String imageName, final MultipartFile image) {
        deleteImage(imageName);
        return loadImage(image);
    }

    public static void deleteImage(final String imageName) {
        try {
            Path path = Paths.get(IMAGES_FOLDER + imageName);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
