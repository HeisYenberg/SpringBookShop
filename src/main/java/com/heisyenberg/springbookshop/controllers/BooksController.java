package com.heisyenberg.springbookshop.controllers;

import com.heisyenberg.springbookshop.models.Book;
import com.heisyenberg.springbookshop.models.User;
import com.heisyenberg.springbookshop.services.BooksService;
import com.heisyenberg.springbookshop.services.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class BooksController {
    private final BooksService booksService;
    private final CartItemsService cartItemsService;

    @Autowired
    public BooksController(BooksService booksService,
                           CartItemsService cartItemsService) {
        this.booksService = booksService;
        this.cartItemsService = cartItemsService;
    }

    @GetMapping(path = {"/", "/home"})
    public String getHomePage() {
        return "redirect:/books";
    }

    @GetMapping("/books")
    public String getBooks(HttpSession session, Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "18") int size,
                           @AuthenticationPrincipal User user) {
        session.setAttribute("cartItemsCount",
                cartItemsService.getCartItemsCount(user));
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> books = booksService.getBooks(pageable);
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book/{id}")
    public String showBook(@PathVariable("id") Long id, Model model) {
        Book book = booksService.getBook(id);
        model.addAttribute("book", book);
        return "book";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "18") int size,
                              @RequestParam(value = "query") String query,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Book> books = booksService.searchBooks(query, pageable);
        if (books.getContent().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "К сожалению, по вашему запросу книг не найдено.");
            return "redirect:/books";
        }
        model.addAttribute("books", books);
        return "books";
    }
}
