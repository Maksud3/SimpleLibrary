package com.hifeful.libraryippt.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ClientDao {
    @Query("SELECT * FROM clients WHERE login = :login")
    LiveData<Client> getClientByLogin(String login);
}
