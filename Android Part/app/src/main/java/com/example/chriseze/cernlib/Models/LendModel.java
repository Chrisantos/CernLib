package com.example.chriseze.cernlib.Models;

/**
 * Created by CHRIS EZE on 6/27/2018.
 */

public class LendModel {
    private String book, student, regno, isbn;

    public LendModel(String book, String student, String regno, String isbn){
        this.book = book;
        this.student = student;
        this.regno = regno;
        this.isbn = isbn;
    }

    public String getBook(){
        return book;
    }
    public String getStudent(){
        return student;
    }
    public String getRegno(){
        return regno;
    }
    public String getIsbn(){
        return isbn;
    }
}
