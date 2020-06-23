package com.hifeful.libraryippt.data;

import android.content.Context;

import androidx.lifecycle.LiveData;

public class ClientRepository {
    private AppDatabase mAppDatabase;

    public ClientRepository(Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
    }

    public LiveData<Client> getClientByLoginTask(String login) {
        return mAppDatabase.getClientDao().getClientByLogin(login);
    }
}
