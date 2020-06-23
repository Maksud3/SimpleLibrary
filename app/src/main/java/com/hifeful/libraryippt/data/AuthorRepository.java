package com.hifeful.libraryippt.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;

public class AuthorRepository {
    private AppDatabase mAppDatabase;

    public AuthorRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
    }

    public LiveData<List<Author>> retrieveAllAuthorsTask() {
        return mAppDatabase.getAuthorDao().getAllAuthors();
    }

    public LiveData<Author> retrieveAuthorByIdTask(long id) {
        return mAppDatabase.getAuthorDao().getAuthorById(id);
    }

    public long insertAuthorTask(Author author) {
        Callable<Long> insertCallable = () -> mAppDatabase.getAuthorDao().insertAuthor(author);
        long rowId = 0;

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Long> future = executorService.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }
        return rowId;
    }

    public void updateAuthorsTask(Author... authors) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            mAppDatabase.getAuthorDao().updateAuthors(authors);
        });
    }

    public void deleteAuthorsTask(Author... authors) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            mAppDatabase.getAuthorDao().deleteAuthors(authors);
        });
    }

    public int deleteAuthorByIdTask(long id) {
        Callable<Integer> insertCallable = () -> mAppDatabase.getAuthorDao().deleteAuthorById(id);
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

    public int deleteAllAuthorsTask() {
        Callable<Integer> insertCallable = () -> mAppDatabase.getAuthorDao().deleteAllAuthors();
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
