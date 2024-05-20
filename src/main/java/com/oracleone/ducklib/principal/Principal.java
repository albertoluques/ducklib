package com.oracleone.ducklib.principal;

import com.oracleone.ducklib.model.BookData;
import com.oracleone.ducklib.model.Data;
import com.oracleone.ducklib.service.ConsumeAPI;
import com.oracleone.ducklib.service.DataConversion;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private DataConversion conversor = new DataConversion();
    private Scanner scan = new Scanner(System.in);

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Search by title
                    2 - Show registered books
                    3 - Show registered authors
                    4 - Show alive authors according by year
                    5 - List books by language
                    
                    0 - Exit application
                    """;
            System.out.println(menu);
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    searchByName();
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

    private void searchByName() {
        System.out.println("What's the name of your book?");
        var bookTitle = scan.nextLine();
        var json = consumeAPI.obtainData(URL_BASE + "?search" + bookTitle.replace(" ", "+"));
        var dataSearch = conversor.obtainData(json, Data.class);
        Optional<BookData> searchedBook = dataSearch.results().stream()
                .filter(l -> l.title().toUpperCase().contains(bookTitle.toUpperCase()))
                .findFirst();
        if (searchedBook.isPresent()) {
            System.out.println("Book is in our DataBase!");
            System.out.println(searchedBook.get());
        } else {
            System.out.println("Book is not in our DataBase");
        }
    }
}
