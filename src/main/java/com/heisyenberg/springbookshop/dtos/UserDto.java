package com.heisyenberg.springbookshop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    @Size(min = 3, max = 20,
            message = "Длина имени должен быть от 3 до 20 символов")
    private String username;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Email(message = "Укажите адрес электронной почты")
    private String email;
    @Size(min = 8, max = 30,
            message = "Длина пароля должен быть от 8 до 30 символов")
    private String password;
}
