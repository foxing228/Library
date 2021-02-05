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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RequestMapping("/return/user")
@RestController
public class ReturnBookController {

    private final BookService bookService;
    private final UserService userService;


    public ReturnBookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> returnBook(
            @PathVariable("id") User user,
            @RequestBody @Valid BookDto bookDto) {
        if (bookDto == null || user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Book book = bookService.findByNameAndAuthor(bookDto.getName(), bookDto.getAuthor());

        if (book == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        if (book.getUser() != null) {
            book.setUser(null);
            book.setStatus(Status.FREE);
            user.removeBook(book);
            bookService.update(book);
        }

        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
