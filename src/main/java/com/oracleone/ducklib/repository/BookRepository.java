package com.oracleone.ducklib.repository;

import com.oracleone.ducklib.model.Book;
import com.oracleone.ducklib.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByLanguages(Language language);

    Optional<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b ORDER BY b.downloads DESC LIMIT 10")
    List<Book> top10DownloadedBooks();
}
