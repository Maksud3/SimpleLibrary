package com.hifeful.libraryippt.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "clients")
public class Client {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String login = "";
    @NonNull
    private String password = "";
    @NonNull
    private String phoneNumber = "";
    @NonNull
    private String registrationDate = "";
    private String birthDate;
    private String firstName;
    private String lastName;

    @Ignore
    public Client() {
    }

    public Client(@NonNull String login, @NonNull String password,
                  @NonNull String phoneNumber, @NonNull String registrationDate,
                  String birthDate, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(@NonNull String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
