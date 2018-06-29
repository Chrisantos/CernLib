package com.example.chriseze.cernlib.Models;

/**
 * Created by CHRIS EZE on 6/27/2018.
 */

public class BookModel {
    private String ISBN, title, author, publisher, field, quantity, _id;

    public BookModel(String ISBN, String title, String author, String publisher, String field, String quantity, String _id){
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.field = field;
        this.quantity = quantity;
        this._id = _id;
    }

    public String getISBN(){
        return ISBN;
    }
    public String getAuthor(){
        return author;
    }
    public String getPublisher(){
        return publisher;
    }
    public String getField(){
        return field;
    }
    public String getQuantity(){
        return quantity;
    }
    public String getTitle(){
        return title;
    }
    public String get_id(){
        return _id;
    }


}
