package com.oracleone.ducklib.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Enumerated(EnumType.STRING)
    private Language languages;
    private Integer downloads;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Book() {}

    public Book(BookData bookData) {
        this.title = bookData.title();
        this.languages = Language.fromString(bookData.languages().toString().split(",")[0].trim());
        this.downloads = bookData.downloads();
    }
//    public Book(BookData bookData, Author author) {
//        this.title = bookData.title();
//        this.languages = bookData.languages();
//        this.downloads = bookData.downloads();
//        this.author = author;
//    }
//
//    public Book(Optional<BookData> data) {
//        this.title = data.get().title();;
//        this.languages = data.get().languages();;
//        this.downloads = data.get().downloads();
//    }
//
//    public void createBook(Optional<BookData> bookData, Author author) {
//        this.title = bookData.get().title();
//        this.languages = bookData.get().languages();
//        this.downloads = bookData.get().downloads();
//        this.author = author;
//
//    }


    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguages() {
        return languages;
    }

    public void setLanguages(Language languages) {
        this.languages = languages;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }


    // to string

    @Override
    public String toString() {
        return
                "title='" + title + '\'' +
                ", languages=" + languages +
                ", downloads=" + downloads;
    }
}
