package com.hifeful.libraryippt.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class BookMinimal implements Parcelable {
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "isbn")
    private String isbn;
    @ColumnInfo(name = "name")
    private String name = "";
    @ColumnInfo(name = "authorFirstName")
    private String authorFirstName = "";
    @ColumnInfo(name = "authorLastName")
    private String authorLastName = "";
    @ColumnInfo(name = "category")
    private String category = "";

    @Ignore
    public BookMinimal() {
    }

    public BookMinimal(long id, String isbn, String name, String authorFirstName, String authorLastName,
                       String category) {
        this.id = id;
        this.isbn = isbn;
        this.name = name;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.category = category;
    }

    protected BookMinimal(Parcel in) {
        id = in.readLong();
        isbn = in.readString();
        name = in.readString();
        authorFirstName = in.readString();
        authorLastName = in.readString();
        category = in.readString();
    }

    public static final Creator<BookMinimal> CREATOR = new Creator<BookMinimal>() {
        @Override
        public BookMinimal createFromParcel(Parcel in) {
            return new BookMinimal(in);
        }

        @Override
        public BookMinimal[] newArray(int size) {
            return new BookMinimal[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(isbn);
        parcel.writeString(name);
        parcel.writeString(authorFirstName);
        parcel.writeString(authorLastName);
        parcel.writeString(category);
    }
}
