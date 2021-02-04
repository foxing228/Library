package com.dima.library.Controller;

import com.dima.library.Service.BookService;
import com.dima.library.dto.BookDto;
import com.dima.library.dto.UserDto;
import com.dima.library.entity.Book;
import com.dima.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/books")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooks() {

        List<Book> books = bookService.findAll();

        if(books.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return books;
    }



    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getOnBook(@PathVariable("id") Long id) {
        if (id == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Book book = bookService.findById(id);

        if (book == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return book;
    }


    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book addBook(@RequestBody @Valid BookDto bookDto) {
        if (bookDto == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Book book = new Book(bookDto.getName(), bookDto.getAuthor());

        return bookService.add(book);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book updateBook(
            @PathVariable("id") Book bookFromDb,
            @RequestBody @Valid BookDto bookDto) {
        if (bookDto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return bookService.update(bookFromDb, new Book(bookDto.getName(), bookDto.getAuthor()));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable("id") Book book) {
        if (book == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        bookService.deleteById(book.getId());
    }

}
