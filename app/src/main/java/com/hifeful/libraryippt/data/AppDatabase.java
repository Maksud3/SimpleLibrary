package com.hifeful.libraryippt.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Author.class, Book.class, Category.class, Client.class}, version = 6)
abstract public class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "library.db";
    private static AppDatabase instance;

    static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract BookDao getBookDao();
    public abstract CategoryDao getCategoryDao();
    public abstract AuthorDao getAuthorDao();
    public abstract ClientDao getClientDao();
}
