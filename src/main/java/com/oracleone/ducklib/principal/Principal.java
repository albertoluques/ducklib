package com.oracleone.ducklib.principal;

import com.oracleone.ducklib.model.*;
import com.oracleone.ducklib.repository.AuthorRepository;
import com.oracleone.ducklib.repository.BookRepository;
import com.oracleone.ducklib.service.ConsumeAPI;
import com.oracleone.ducklib.service.DataConversion;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private DataConversion conversor = new DataConversion();
    private Scanner scan = new Scanner(System.in);
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private List<Author> authors;
    private List<Book> books;

    public Principal(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search by title
                    2 - Show registered books
                    3 - Show registered authors
                    4 - Show alive authors according by year
                    5 - List books by language
                    6 - Top 10 most downloaded books
                    
                    0 - Exit application
                    """;
            System.out.println(menu);
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    searchBookData();
                    break;
                case 2:
                    showBooks();
                    break;
                case 3:
                    showAuthors();
                    break;
                case 4:
                    aliveAuthorsPerYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 6:
                    topDownloadedBooks();
                    break;


                case 0:
                    System.out.println("Closing the application");
                    break;
                default:
                    System.out.println("Option unavailable!");
                    break;
            }
        }
    }

    // Methods

    private Data getBookData() {
        System.out.println("Name of the Book you want to search on the web: ");
        var bookName = scan.nextLine();
        var json = consumeAPI.obtainData(URL_BASE + "?search=" + bookName.replace(" ", "%20"));
        var data = conversor.obtainData(json, Data.class);
        return data;
    }

    private void searchBookData() {
        Data data = getBookData();
        if (data != null && !data.results().isEmpty()) {
            BookData myBook = data.results().get(0);

            Book book = new Book(myBook);
            System.out.println(book);

            Optional<Book> bookRegistered = bookRepository.findByTitle(book.getTitle());
            if (bookRegistered.isPresent()) {
                System.out.println("The book is already on our database!");
            } else {
                if (!myBook.author().isEmpty()) {
                    AuthorData authorData = myBook.author().get(0);
                    Author author = new Author(authorData);
                    Optional<Author> authorOptional = authorRepository.findByName(author.getName());

                    if (authorOptional.isPresent()) {
                        Author authorPresent = authorOptional.get();
                        book.setAuthor(authorPresent);
                        bookRepository.save(book);
                    } else {
                        Author newAuthor = authorRepository.save(author);
                        book.setAuthor(newAuthor);
                        bookRepository.save(book);
                    }

                    Integer downloadAmount = book.getDownloads() != null ? book.getDownloads() : 0;
                    System.out.println("""
                            Book:
                            Title: %s
                            Author: %s
                            Language: %s
                            Downloads: %s
                            """.formatted(book.getTitle(), author.getName(), book.getLanguages(), book.getDownloads()));
                } else {
                    System.out.println("without author");
                }
            }
        } else {
            System.out.println("Book not found :/");
        }
    }

    private void showBooks() {
        books = bookRepository.findAll();
        books.stream().forEach(System.out::println);
    }

    private void showAuthors() {
        authors = authorRepository.findAll();
        authors.stream().forEach(System.out::println);
    }

    private void aliveAuthorsPerYear(){
        System.out.println("Select the year to show all authors alive on the current year selected");
        var year = scan.nextInt();
        scan.nextLine();
        authors = authorRepository.authorsAliveByYear(year);
        authors.stream().forEach(System.out::println);
    }

    private List<Book> languageData(String lang) {
        var data = Language.fromString(lang);
        System.out.println("Language searched: " + data);

        List<Book> bookByLanguage = bookRepository.findByLanguages(data);
        return bookByLanguage;
    }

    private void listBooksByLanguage() {
        var option = -1;
        while (option != 0) {
            var languageMenu = """
                
                Select an option (with number) to list books on that language
                
                1. EN - ENGLISH 
                2. ES - SPANISH
                3. FR - FRENCH
                4. PT - PORTUGUESE 
                
                0 - Return to the main option menu
                
                """;
            System.out.println(languageMenu);
            while (!scan.hasNextInt()) {
                System.out.println("Invalid input, please select one of the available options");
                scan.nextLine();
            }
            option = scan.nextInt();
            scan.nextLine();
            switch (option) {
                case 1:
                    List<Book> englishBooks = languageData("[en]");
                    englishBooks.forEach(System.out::println);
                    break;
                case 2:
                    List<Book> spanishBooks = languageData("[es]");
                    spanishBooks.forEach(System.out::println);
                    break;
                case 3:
                    List<Book> frenchBooks = languageData("[fr]");
                    frenchBooks.forEach(System.out::println);
                    break;
                case 4:
                    List<Book> portugueseBooks = languageData("[pt]");
                    portugueseBooks.forEach(System.out::println);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("No language selected");
            }
        }
    }

    private void topDownloadedBooks() {
        List<Book> topBooks = bookRepository.top10DownloadedBooks();
        topBooks.forEach(System.out::println);
    }

}
