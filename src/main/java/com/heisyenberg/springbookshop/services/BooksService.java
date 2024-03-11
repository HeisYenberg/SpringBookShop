package com.heisyenberg.springbookshop.services;

import com.heisyenberg.springbookshop.models.Book;
import com.heisyenberg.springbookshop.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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

    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Optional<Book> book = booksRepository.findById(id);
        book.orElseThrow(RuntimeException::new);
        try {
            Path path = Paths.get("public/images/" + book.get().getImageName());
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        booksRepository.delete(book.get());
    }
}
