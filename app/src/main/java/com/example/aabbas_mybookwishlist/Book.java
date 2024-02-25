package com.example.aabbas_mybookwishlist;

import java.io.Serializable;
/*
Represents a book in the application. This class models and encapsulates
all the properties of the book as required by the specifications.
It consists of getters and setters for each attribute to modify or
access them.
 */
public class Book implements Serializable {
    //Book's Attributes
    private String title;
    private String author;
    private String genre;
    private int year;
    private boolean isRead;

    public Book(String title, String author, String genre, int year, boolean isRead) {
        //Constructor for the Book class
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.isRead = isRead;
    }

    //getters and setters for the attributes
    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {

        this.author = author;
    }

    public String getGenre() {

        return genre;
    }

    public void setGenre(String genre) {

        this.genre = genre;
    }

    public int getYear() {

        return year;
    }

    public void setYear(int year) {

        this.year = year;
    }

    public boolean isRead() {

        return isRead;
    }

    public void setRead(boolean read) {

        isRead = read;
    }
}
