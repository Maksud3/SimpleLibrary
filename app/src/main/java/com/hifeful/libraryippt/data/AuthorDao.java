package com.hifeful.libraryippt.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AuthorDao {
    @Query("SELECT * FROM authors")
    LiveData<List<Author>> getAllAuthors();

    @Query("SELECT * FROM authors WHERE id = :id")
    LiveData<Author> getAuthorById(long id);

    @Insert
    long insertAuthor(Author author);

    @Update
    int updateAuthors(Author... authors);

    @Delete
    int deleteAuthors(Author... authors);

    @Query("DELETE FROM authors WHERE id = :id")
    int deleteAuthorById(long id);
    @Query("DELETE FROM authors")
    int deleteAllAuthors();
}
