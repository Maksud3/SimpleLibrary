package com.hifeful.libraryippt.data;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;

public class BookRepository {
    private AppDatabase mAppDatabase;

    public BookRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
    }

    public LiveData<List<Book>> retrieveAllBooksTask() {
        return mAppDatabase.getBookDao().getAllBooks();
    }

    public LiveData<Book> retrieveBookByIdTask(long id) {
        return mAppDatabase.getBookDao().getBookById(id);
    }

    public LiveData<List<BookMinimal>> retrieveBookMinimals() {
        return mAppDatabase.getBookDao().getBookMinimals();
    }

    public void insertBookTask(final Book book) {
        Executor myExecutor = Executors.newSingleThreadExecutor();

        myExecutor.execute(() -> {
            mAppDatabase.getBookDao().insertBooks(book);
        });
    }

    public int updateBookTask(Book book) {
        Callable<Integer> insertCallable = () -> mAppDatabase.getBookDao().update(book);
        int updatedRows = 0;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(insertCallable);
        try {
            updatedRows = future.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return updatedRows;
    }

    public int deleteBookByIdTask(long id) {
        Callable<Integer> insertCallable = () -> mAppDatabase.getBookDao().deleteById(id);
        int deletedRows = 0;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(insertCallable);
        try {
            deletedRows = future.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return deletedRows;
    }

    public int deleteAllBooksTask() {
        Callable<Integer> insertCallable = () -> mAppDatabase.getBookDao().deleteAllBooks();
        int deletedRows = 0;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(insertCallable);
        try {
            deletedRows = future.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return deletedRows;
    }
}
