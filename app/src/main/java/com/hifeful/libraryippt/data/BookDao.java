package com.hifeful.libraryippt.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT books.id AS id, books.isbn AS isbn, books.name AS name, " +
            "authors.firstName AS authorFirstName, authors.lastName AS authorLastName, " +
            "categories.name AS category " +
            "FROM books, authors, categories " +
            "WHERE books.author_id = authors.id " +
            "AND books.category_id = categories.id")
    LiveData<List<BookMinimal>> getBookMinimals();

    @Query("SELECT * FROM books WHERE id = :id")
    LiveData<Book> getBookById(long id);

    @Insert
    long[] insertBooks(Book... books);

    @Update
    int update(Book... books);

    @Query("DELETE FROM books WHERE id = :id")
    int deleteById(long id);

    @Query("DELETE FROM BOOKS")
    int deleteAllBooks();
}
