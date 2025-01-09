package com.heisyenberg.springbookshop.repositories;

import com.heisyenberg.springbookshop.models.Book;
import com.heisyenberg.springbookshop.models.CartItem;
import com.heisyenberg.springbookshop.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItem, Long> {
  List<CartItem> findAllByUser(User user, Sort sort);

  Optional<CartItem> findByUserAndBook(User user, Book book);

  void deleteByUserAndBook(User user, Book book);

  int countAllByUser(User user);
}
