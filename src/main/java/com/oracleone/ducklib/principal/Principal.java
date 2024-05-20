package com.oracleone.ducklib.principal;

import com.oracleone.ducklib.model.BookData;
import com.oracleone.ducklib.service.ConsumeAPI;
import com.oracleone.ducklib.service.DataConversion;

import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private DataConversion conversor = new DataConversion();
    private Scanner scan = new Scanner(System.in);
    public void showMenu() {
        var json = consumeAPI.obtainData(URL_BASE);
        System.out.println(json);
        var data = conversor.obtainData(json, BookData.class);
        System.out.println(data);


    }
}
