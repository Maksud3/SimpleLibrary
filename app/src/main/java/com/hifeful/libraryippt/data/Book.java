package com.hifeful.libraryippt.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String isbn = "";
    @NonNull
    private String name = "";
    private int pageCount = 0;
    private double price = 0.0;
    @NonNull
    private String level = "";
    private int count = 0;
    @NonNull
    private String state = "";
    private long author_id;
    private long category_id;

    @Ignore
    public Book() {
    }

    public Book(@NonNull String isbn, @NonNull String name, int pageCount, double price,
                @NonNull String level, int count, @NonNull String state,
                long author_id, long category_id) {
        this.isbn = isbn;
        this.name = name;
        this.pageCount = pageCount;
        this.price = price;
        this.level = level;
        this.count = count;
        this.state = state;
        this.author_id = author_id;
        this.category_id = category_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NonNull String isbn) {
        this.isbn = isbn;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @NonNull
    public String getLevel() {
        return level;
    }

    public void setLevel(@NonNull String level) {
        this.level = level;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    public String getState() {
        return state;
    }

    public void setState(@NonNull String state) {
        this.state = state;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }
}
