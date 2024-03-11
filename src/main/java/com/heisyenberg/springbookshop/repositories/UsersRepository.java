package com.heisyenberg.springbookshop.repositories;

import com.heisyenberg.springbookshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
