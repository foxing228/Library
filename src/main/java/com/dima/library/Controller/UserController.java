package com.dima.library.Controller;

import com.dima.library.Service.UserService;
import com.dima.library.dto.UserDto;
import com.dima.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return users;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getOneUser(@PathVariable("id") Long id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        User user = userService.findById(id);

        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return user;
    }


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody @Valid UserDto userDto) {
        if (userDto == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        User user = new User(userDto.getName());

        return userService.add(user);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(
            @PathVariable("id") User userFromDb,
            @RequestBody @Valid UserDto userDto) {
        if (userDto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return userService.update(userFromDb, new User(userDto.getName()));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable("id") User user) {
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        userService.deleteById(user.getId());
    }
}
