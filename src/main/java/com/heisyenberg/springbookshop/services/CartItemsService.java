package com.heisyenberg.springbookshop.services;

import com.heisyenberg.springbookshop.models.Book;
import com.heisyenberg.springbookshop.models.CartItem;
import com.heisyenberg.springbookshop.models.User;
import com.heisyenberg.springbookshop.repositories.BooksRepository;
import com.heisyenberg.springbookshop.repositories.CartItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemsService {
  private final CartItemsRepository cartItemsRepository;
  private final BooksRepository booksRepository;

  @Autowired
  public CartItemsService(
      CartItemsRepository cartItemsRepository, BooksRepository booksRepository) {
    this.cartItemsRepository = cartItemsRepository;
    this.booksRepository = booksRepository;
  }

  public List<CartItem> getCartItems(User user) {
    Sort sort = Sort.by("id");
    return cartItemsRepository.findAllByUser(user, sort);
  }

  public void addItem(User user, Long bookId) {
    Book book = booksRepository.getReferenceById(bookId);
    Optional<CartItem> cartItem = cartItemsRepository.findByUserAndBook(user, book);
    if (cartItem.isPresent()) {
      Integer quantity = cartItem.get().getQuantity();
      cartItem.get().setQuantity(quantity + 1);
      cartItemsRepository.save(cartItem.get());
    } else {
      cartItemsRepository.save(new CartItem(null, user, book, 1));
    }
  }

  @Transactional
  public void updateItemQuantity(User user, Long bookId, String action) {
    Book book = booksRepository.getReferenceById(bookId);
    CartItem cartItem = cartItemsRepository.findByUserAndBook(user, book).get();
    Integer quantity = cartItem.getQuantity();
    if (quantity.equals(1) && action.equals("decrement")) {
      cartItemsRepository.deleteByUserAndBook(user, book);
    } else if (action.equals("decrement")) {
      cartItem.setQuantity(quantity - 1);
      cartItemsRepository.save(cartItem);
    } else {
      cartItem.setQuantity(quantity + 1);
      cartItemsRepository.save(cartItem);
    }
  }

  @Transactional
  public void removeItem(User user, Long bookId) {
    Book book = booksRepository.getReferenceById(bookId);
    cartItemsRepository.deleteByUserAndBook(user, book);
  }

  public int getCartItemsCount(User user) {
    return cartItemsRepository.countAllByUser(user);
  }
}
