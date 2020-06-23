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

public class CategoryRepository {

    private AppDatabase mAppDatabase;

    public CategoryRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
    }

    public LiveData<List<Category>> retrieveAllCategoriesTask() {
        return mAppDatabase.getCategoryDao().getAllCategories();
    }

    public LiveData<List<String>> retrieveAllCategoryNamesTask() {
        return mAppDatabase.getCategoryDao().getCategoryNames();
    }

    public LiveData<Category> retrieveCategoryWithIdTask(int id) {
        return mAppDatabase.getCategoryDao().getCategoryWithId(id);
    }

    public long insertCategoryTask(Category category) {
        Callable<Long> insertCallable = () -> mAppDatabase.getCategoryDao().insertCategory(category);
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

    public void updateCategoriesTask(Category... categories) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            mAppDatabase.getCategoryDao().updateCategories(categories);
        });
    }

    public void deleteCategoriesTask(Category... categories) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            mAppDatabase.getCategoryDao().deleteCategories(categories);
        });
    }

    public int deleteAllCategoriesTask() {
        Callable<Integer> insertCallable = () -> mAppDatabase.getCategoryDao().deleteAllCategories();
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
