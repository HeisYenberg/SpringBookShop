package com.heisyenberg.springbookshop.services;

import com.heisyenberg.springbookshop.models.User;
import com.heisyenberg.springbookshop.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService implements UserDetailsService {
  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
    this.usersRepository = usersRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = usersRepository.findUserByEmail(email.toLowerCase());
    user.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return user.get();
  }

  public void register(User user) {
    user.setEmail(user.getEmail().toLowerCase());
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    usersRepository.save(user);
  }
}
