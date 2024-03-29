package com.upc.Finanzas.controller;

import com.upc.Finanzas.dto.UserDto;
import com.upc.Finanzas.exception.ResourceNotFoundException;
import com.upc.Finanzas.exception.ValidationException;
import com.upc.Finanzas.model.User;
import com.upc.Finanzas.repository.UserRepository;
import com.upc.Finanzas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bank/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    private final UserRepository userRepository;
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    //URL:http://localhost:8080/api/bank/v1/users
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/users/{userId}
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "userId") Long userId){
        existsUserByUserId(userId);
        User user = userService.getById(userId);
        UserDto userDto = convertToDto(user);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    //URL:http://localhost:8080/api/bank/v1/users/{userId}
    //Method: DELETE
    @Transactional
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") Long userId){
        existsUserByUserId(userId);
        userService.delete(userId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    private User convertToEntity(UserDto userDto){
        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setClients(userDto.getClients());
        return user;
    }

    private UserDto convertToDto(User user){
        return UserDto.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .password(user.getPassword())
                .clients(user.getClients())
                .build();
    }

    private void existsUserByUserId(Long userId) {
        if (userService.getById(userId) == null) {
            throw new ResourceNotFoundException("No existe un usuario con el id " + userId);
        }
    }

    private void validateUser(User user){
        if(user.getFirstname() == null || user.getFirstname().isEmpty()){
            throw new ValidationException("El nombre del usuario es obligatorio");
        }
        if(user.getFirstname().length() > 25){
            throw new ValidationException("El nombre del usuario no debe exceder los 25 caracteres");
        }
        if(user.getLastname() == null || user.getLastname().isEmpty()){
            throw new ValidationException("El apellido del usuario es obligatorio");
        }
        if(user.getLastname().length() > 25){
            throw new ValidationException("El apellido del usuario no debe exceder los 25 caracteres");
        }
        if(user.getEmail() == null || user.getEmail().isEmpty()){
            throw new ValidationException("El correo del usuario es obligatorio");
        }
        if(user.getEmail().length() > 35){
            throw new ValidationException("El correo del usuario no debe exceder los 35 caracteres");
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()){
            throw new ValidationException("La contraseña del usuario es obligatoria");
        }
        if(user.getPassword().length() > 25){
            throw new ValidationException("La contraseña del usuario no debe exceder los 25 caracteres");
        }
    }

    private void existsUserByEmail(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Ya existe un usuario con el email " + user.getEmail());
        }
    }
}

