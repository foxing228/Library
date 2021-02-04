package com.dima.library.Service;

import com.dima.library.Repository.BookRepository;
import com.dima.library.entity.Book;
import com.dima.library.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book add(Book book) {
        book.setStatus(Status.FREE);

        bookRepository.save(book);

        return book;
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book update(Book oldBook, Book newBook) {
        oldBook.setName(newBook.getName());
        oldBook.setAuthor(newBook.getAuthor());
        
        Book updatedBook = bookRepository.save(oldBook);

        return updatedBook;
    }

    public Book update(Book book) {


        Book updatedBook = bookRepository.save(book);

        return book;
    }

    public Book findByNameAndAuthor(String name, String author) {
        return bookRepository.findByNameAndAuthor(name, author).stream().findFirst().orElse(null);
    }


}
