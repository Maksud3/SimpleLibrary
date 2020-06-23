package com.hifeful.libraryippt.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT name FROM categories")
    LiveData<List<String>> getCategoryNames();

    @Query("SELECT * FROM categories WHERE id = :id")
    LiveData<Category> getCategoryWithId(long id);

    @Insert
    long insertCategory(Category category);

    @Update
    int updateCategories(Category... categories);

    @Delete
    int deleteCategories(Category... categories);

    @Query("DELETE FROM categories")
    int deleteAllCategories();
}
