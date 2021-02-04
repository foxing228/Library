package com.dima.library.entity;

import lombok.*;


import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {


    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Book> books = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setUser(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setUser(null);
    }
}
