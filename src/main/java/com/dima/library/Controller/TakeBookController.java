package com.dima.library.Controller;

import com.dima.library.Service.BookService;
import com.dima.library.Service.UserService;
import com.dima.library.dto.BookDto;
import com.dima.library.entity.Book;
import com.dima.library.entity.Status;
import com.dima.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RequestMapping("/rent/user")
@Controller
public class TakeBookController {

    private final BookService bookService;
    private final UserService userService;


    @Autowired
    public TakeBookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> takeBook(
            @PathVariable("id") User user,
            @RequestBody @Valid BookDto bookDto) {
        if (bookDto == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Book book = bookService.findByNameAndAuthor(bookDto.getName(), bookDto.getAuthor());

        if (book == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        if (book.getUser() != null) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Book is already taken");

        book.setUser(user);
        book.setStatus(Status.TAKEN);

        bookService.update(book);

        user.addBook(book);

        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}