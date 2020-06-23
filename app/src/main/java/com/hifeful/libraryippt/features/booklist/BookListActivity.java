package com.hifeful.libraryippt.features.booklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hifeful.libraryippt.R;
import com.hifeful.libraryippt.data.AuthorRepository;
import com.hifeful.libraryippt.data.BookMinimal;
import com.hifeful.libraryippt.data.BookRepository;
import com.hifeful.libraryippt.data.CategoryRepository;
import com.hifeful.libraryippt.features.BookActivity;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {
    public static final String TAG = "BooksListActivity";

    // UI components
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFab;

    // Variables
    private int client; // 1 - Admin, 0 - User
    private BookRepository mBookRepository;
    private CategoryRepository mCategoryRepository;
    private AuthorRepository mAuthorRepository;
    private BookListAdapter mBookListAdapter;
    private ArrayList<BookMinimal> mBooksMinimal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        setSupportActionBar(findViewById(R.id.toolbar_book_list));

        mRecyclerView = findViewById(R.id.books_recycler);
        mFab = findViewById(R.id.fab);

        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(BookListActivity.this, BookActivity.class);
            intent.putExtra("client", client);
            startActivity(intent);
        });

        mBookRepository = new BookRepository(this);
        mCategoryRepository = new CategoryRepository(this);
        mAuthorRepository = new AuthorRepository(this);

        getIncomingIntent();
        initRecycler();
        retrieveNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_booklist, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                clearDatabase();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getIncomingIntent() {
        client = getIntent().getIntExtra("client", 0);
        if (client == 0) {
            mFab.setVisibility(View.GONE);
        }
    }

    private void retrieveNotes() {
        mBookRepository.retrieveBookMinimals().observe(this, bookMinimals -> {
            if (mBooksMinimal.size() > 0) {
                mBooksMinimal.clear();
            }

            if (bookMinimals != null) {
                mBooksMinimal.addAll(bookMinimals);
            }
            mBookListAdapter.notifyDataSetChanged();
        });
    }

    private void clearDatabase() {
        mBooksMinimal.clear();
        mBookListAdapter.notifyDataSetChanged();

        mBookRepository.deleteAllBooksTask();
        mCategoryRepository.deleteAllCategoriesTask();
        mAuthorRepository.deleteAllAuthorsTask();
    }

    private void initRecycler() {
        mBookListAdapter = new BookListAdapter(mBooksMinimal, position -> {
            Intent intent = new Intent(BookListActivity.this, BookActivity.class);

            intent.putExtra("book_minimal_info", mBooksMinimal.get(position));
            intent.putExtra("client", client);

            startActivity(intent);
        });
        mRecyclerView.setAdapter(mBookListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }
}
