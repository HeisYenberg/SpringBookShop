package com.heisyenberg.springbookshop.services;

import com.heisyenberg.springbookshop.helpers.ImagesFilesHelper;
import com.heisyenberg.springbookshop.models.Book;
import com.heisyenberg.springbookshop.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.heisyenberg.springbookshop.helpers.ImagesFilesHelper.loadImage;
import static com.heisyenberg.springbookshop.helpers.ImagesFilesHelper.reloadImage;

@Service
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public Page<Book> getBooks(Pageable pageable) {
        return booksRepository.findAll(pageable);
    }

    public Book getBook(Long id) {
        return booksRepository.getReferenceById(id);
    }

    public Page<Book> searchBooks(String search, Pageable pageable) {
        return booksRepository.findBySearch(search, pageable);
    }

    public void saveBook(Book book, MultipartFile image) {
        book.setImageName(loadImage(image));
        booksRepository.save(book);
    }

    public void updateBook(Book book, MultipartFile image) {
        if (!image.isEmpty()) {
            book.setImageName(reloadImage(book.getImageName(), image));
        }
        booksRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Optional<Book> book = booksRepository.findById(id);
        book.orElseThrow(RuntimeException::new);
        ImagesFilesHelper.deleteImage(book.get().getImageName());
        booksRepository.delete(book.get());
    }
}
