package com.heisyenberg.springbookshop.controllers;

import com.heisyenberg.springbookshop.dtos.UserDto;
import com.heisyenberg.springbookshop.models.User;
import com.heisyenberg.springbookshop.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("userDto") UserDto userDto) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return "registration";
        }
        try {
            usersService.register(new User(userDto));
        } catch (DataIntegrityViolationException e) {
            bindingResult.addError(new FieldError("userDto", "email",
                    "Данная почта уже зарегистрирована"));
            return "registration";
        }
        return "redirect:/login";
    }
}
