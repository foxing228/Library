package com.dima.library.Repository;

import com.dima.library.entity.Book;
import com.dima.library.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    public List<Book> findByNameAndAuthor(String name, String author);

}
