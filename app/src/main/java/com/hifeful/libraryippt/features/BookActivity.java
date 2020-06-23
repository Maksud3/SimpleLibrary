package com.hifeful.libraryippt.features;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.LinearLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hifeful.libraryippt.R;
import com.hifeful.libraryippt.data.Author;
import com.hifeful.libraryippt.data.AuthorRepository;
import com.hifeful.libraryippt.data.Book;
import com.hifeful.libraryippt.data.BookMinimal;
import com.hifeful.libraryippt.data.BookRepository;
import com.hifeful.libraryippt.data.Category;
import com.hifeful.libraryippt.data.CategoryRepository;
import com.hifeful.libraryippt.util.MaterialSpinnerAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BookActivity extends AppCompatActivity {
    private static final String TAG = "BookActivity";

    public static final int EDIT_MODE_ENABLED = 1;
    public static final int EDIT_MODE_DISABLED = 0;

    // UI components
    private TextInputEditText mBookISBN;
    private TextInputEditText mBookName;
    private TextInputEditText mBookAuthor;
    private MaterialAutoCompleteTextView mBookCategory;
    private TextInputEditText mBookPageCount;
    private MaterialAutoCompleteTextView mBookLevel;
    private MaterialAutoCompleteTextView mBookState;
    private TextInputEditText mBookCount;
    private TextInputEditText mBookPrice;


    // Variables
    private BookMinimal mBookMinimal;
    private String fullName;
    private Book mInitialBook;
    private Book mFinalBook;
    private int mMode;
    private boolean mIsNewBook;
    private BookRepository mBookRepository;
    private CategoryRepository mCategoryRepository;
    private AuthorRepository mAuthorRepository;
    private ArrayList<View> mBookLayouts = new ArrayList<>();
    private ArrayList<View> mBookFields = new ArrayList<>();
    private ArrayList<Category> mCategoriesList = new ArrayList<>();
    private ArrayList<Author> mAuthorsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setSupportActionBar(findViewById(R.id.toolbar_book));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mBookISBN = findViewById(R.id.book_isbn);
        mBookLayouts.add(findViewById(R.id.book_layout_isbn));
        mBookFields.add(mBookISBN);
        mBookName = findViewById(R.id.book_name);
        mBookLayouts.add(findViewById(R.id.book_layout_name));
        mBookFields.add(mBookName);
        mBookAuthor = findViewById(R.id.book_author);
        mBookLayouts.add(findViewById(R.id.book_layout_author));
        mBookFields.add(mBookAuthor);
        mBookCategory = findViewById(R.id.book_category);
        mBookLayouts.add(findViewById(R.id.book_layout_category));
        mBookFields.add(mBookCategory);
        mBookPageCount = findViewById(R.id.book_page_count);
        mBookLayouts.add(findViewById(R.id.book_layout_page_count));
        mBookFields.add(mBookPageCount);
        mBookLevel = findViewById(R.id.book_level);
        mBookLayouts.add(findViewById(R.id.book_layout_level));
        mBookFields.add(mBookLevel);
        mBookState = findViewById(R.id.book_state);
        mBookLayouts.add(findViewById(R.id.book_layout_state));
        mBookFields.add(mBookState);
        mBookCount = findViewById(R.id.book_count);
        mBookLayouts.add(findViewById(R.id.book_layout_count));
        mBookFields.add(mBookCount);
        mBookPrice = findViewById(R.id.book_price);
        mBookLayouts.add(findViewById(R.id.book_layout_price));
        mBookFields.add(mBookPrice);

        mBookRepository = new BookRepository(this);
        mCategoryRepository = new CategoryRepository(this);
        mAuthorRepository = new AuthorRepository(this);

        if (getIncomingIntent()) {
            // New book.
            setNewNoteProperties();
            setUpDropDownMenus();
        } else {
            // Edit book.
            setNoteProperties();
            setUpDropDownMenus();
        }
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);

        MenuItem menuItem;
        if (mMode == EDIT_MODE_DISABLED) {
            menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);

            menuItem = menu.findItem(R.id.action_save);
            menuItem.setVisible(false);
        }

        if (mIsNewBook) {
            menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_save:
                saveChanges();
                return true;
            case R.id.action_delete:
                confirmDeleteDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (checkChanges()) {
            new MaterialAlertDialogBuilder(this)
                    .setMessage(getResources().getString(R.string.discard_book))
                    .setPositiveButton(getResources().getString(R.string.discard), (dialogInterface, i) -> {
                        super.onBackPressed();

                    })
                    .setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> {

                    })
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    private boolean getIncomingIntent() {
        if (getIntent().getIntExtra("client", 0 ) == 1) {
            mMode = EDIT_MODE_ENABLED;
        } else {
            mMode = EDIT_MODE_DISABLED;
            disableFields();
        }

        if (getIntent().hasExtra("book_minimal_info")) {
            mBookMinimal = getIntent().getParcelableExtra("book_minimal_info");

            mIsNewBook = false;
            return false;
        }

        mIsNewBook = true;
        return true;
    }

    private void disableFields() {
        for (View view : mBookLayouts) {
            view.setEnabled(false);
        }
    }

    private void setListeners() {
        for (int i = 0; i < mBookFields.size(); i++) {
            if (mBookFields.get(i) instanceof TextInputEditText) {
                int finalI = i;
                ((TextInputEditText) mBookFields.get(i)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ((TextInputLayout) mBookLayouts.get(finalI)).setError(null);
                    }
                });
            } else if (mBookFields.get(i) instanceof AutoCompleteTextView) {
                int finalI = i;
                ((AutoCompleteTextView) mBookFields.get(i)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ((TextInputLayout) mBookLayouts.get(finalI)).setError(null);
                    }
                });
            }
        }
    }

    private void setNoteProperties() {
        mBookISBN.setText(mBookMinimal.getIsbn());
        mBookName.setText(mBookMinimal.getName());
        setTitle(mBookMinimal.getName());

        if (!mBookMinimal.getAuthorLastName().equals("")) {
            fullName = mBookMinimal.getAuthorFirstName() + " " + mBookMinimal.getAuthorLastName();
        } else {
            fullName = mBookMinimal.getAuthorFirstName();
        }
        mBookAuthor.setText(fullName);

        mBookCategory.setText(mBookMinimal.getCategory());

        mBookRepository.retrieveBookByIdTask(mBookMinimal.getId()).observe(this, book -> {
            if (book != null) {
                mInitialBook = new Book(book.getIsbn(), book.getName(), book.getPageCount(),
                        book.getPrice(), book.getLevel(), book.getCount(), book.getState(),
                        book.getAuthor_id(), book.getCategory_id());
                mInitialBook.setId(book.getId());
                mFinalBook = new Book(book.getIsbn(), book.getName(), book.getPageCount(),
                        book.getPrice(), book.getLevel(), book.getCount(), book.getState(),
                        book.getAuthor_id(), book.getCategory_id());
                mFinalBook.setId(book.getId());

                mBookPageCount.setText(String.valueOf(book.getPageCount()));
                mBookLevel.setText(book.getLevel());
                mBookState.setText(book.getState());
                mBookCount.setText(String.valueOf(book.getCount()));
                mBookPrice.setText(String.valueOf(book.getPrice()));
            }
        });
    }

    private void setNewNoteProperties() {
        setTitle(getResources().getString(R.string.new_book));
        fullName = "";
        mBookMinimal = new BookMinimal();
        mInitialBook = new Book();
        mFinalBook = new Book();
    }

    private void setUpDropDownMenus() {
        String[] states = getResources().getStringArray(R.array.states);
        MaterialSpinnerAdapter<String> stateAdapter = new MaterialSpinnerAdapter<>(this,
                android.R.layout.select_dialog_item, Arrays.asList(states));
        mBookState.setAdapter(stateAdapter);

        String[] levels = getResources().getStringArray(R.array.levels);
        MaterialSpinnerAdapter<String> levelAdapter = new MaterialSpinnerAdapter<>(this,
                android.R.layout.select_dialog_item, Arrays.asList(levels));
        mBookLevel.setAdapter(levelAdapter);

        mCategoryRepository.retrieveAllCategoriesTask().observe(this, categories -> {
            mCategoriesList.addAll(categories);
            ArrayList<String> categoryNames = new ArrayList<>();
            for (Category category: categories) {
                categoryNames.add(category.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.select_dialog_item, categoryNames);

            mBookCategory.setAdapter(adapter);
        });

        mAuthorRepository.retrieveAllAuthorsTask().observe(this, authors -> {
            mAuthorsList.addAll(authors);
        });
    }

    private void saveChanges() {
        if (isAllFieldsTyped()) {
            if (checkChanges()) {
                if (mIsNewBook) {
                    mBookRepository.insertBookTask(mFinalBook);
                } else {
                    Log.i(TAG, "updated rows: " + mBookRepository.updateBookTask(mFinalBook));
                }
            }
            finish();
        }
    }

    private void confirmDeleteDialog() {
        new MaterialAlertDialogBuilder(this)
                .setMessage(getResources().getString(R.string.delete_confirmation))
                .setPositiveButton(getResources().getString(R.string.delete), (dialogInterface, i) -> {
                    mBookRepository.deleteBookByIdTask(mBookMinimal.getId());
                    finish();
                })
                .setNegativeButton(getResources().getString(R.string.cancel), (dialogInterface, i) -> {

                })
                .show();
    }

    private boolean isAllFieldsTyped() {
        int count = 0;
        for (int i = 0; i < mBookFields.size(); i++) {
            if (mBookFields.get(i) instanceof TextInputEditText) {
                if (((TextInputEditText) mBookFields.get(i)).getText().toString().trim().isEmpty()) {
                    ((TextInputLayout) mBookLayouts.get(i)).setError(getResources().getString(R.string.required_field));
                } else {
                    count++;
                }
            }
            else if (mBookFields.get(i) instanceof AutoCompleteTextView) {
                if (((AutoCompleteTextView) mBookFields.get(i)).getText().toString().trim().isEmpty()) {
                    ((TextInputLayout) mBookLayouts.get(i)).setError(getResources().getString(R.string.required_field));
                } else {
                    count++;
                }
            }
        }

        return count == 9;
    }

    private void setEasyFinalChanges() {
        mFinalBook.setIsbn(mBookISBN.getText().toString());
        mFinalBook.setName(mBookName.getText().toString());
        String pageCountFinal = Objects.requireNonNull(mBookPageCount.getText()).toString();
        mFinalBook.setPageCount(parseIntWithDefault(pageCountFinal, 0));
        mFinalBook.setLevel(mBookLevel.getText().toString());
        mFinalBook.setState(mBookState.getText().toString());
        String countFinal = Objects.requireNonNull(mBookCount.getText()).toString();
        mFinalBook.setCount(parseIntWithDefault(countFinal, 0));
        String priceFinal = Objects.requireNonNull(mBookPrice.getText()).toString();
        mFinalBook.setPrice(parseDoubleWithDefault(priceFinal, 0.0));
    }

    private boolean checkChanges() {
        setEasyFinalChanges();


        boolean isHardChanges = checkHardChanges();
        return !mFinalBook.getIsbn().equals(mInitialBook.getIsbn()) ||
                !mFinalBook.getName().equals(mInitialBook.getName()) ||
                mFinalBook.getPageCount() != mInitialBook.getPageCount() ||
                !mFinalBook.getLevel().equals(mInitialBook.getLevel()) ||
                !mFinalBook.getState().equals(mInitialBook.getState()) ||
                mFinalBook.getCount() != mInitialBook.getCount() ||
                mFinalBook.getPrice() != mInitialBook.getPrice() ||
                isHardChanges;
    }

    private boolean checkHardChanges() {
        boolean changes = false;

        boolean isCategoryFound = false;
        if (!mBookMinimal.getCategory().equals(mBookCategory.getText().toString())) {
            for (Category category : mCategoriesList) {
                if (category.getName().equals(mBookCategory.getText().toString())) {
                    mFinalBook.setCategory_id(category.getId());
                    isCategoryFound = true;
                }
            }
            if (!isCategoryFound) {
                mFinalBook.setCategory_id(mCategoryRepository.insertCategoryTask(new Category(
                        mBookCategory.getText().toString(), "")));
            }
            changes = true;
        }


        String changedName = mBookAuthor.getText().toString().trim();

        boolean isAuthorFound = false;
        if (!fullName.equals(changedName)) {
            String firstName;
            Author changedAuthor;
            if (changedName.contains(" ")) {
                firstName = changedName.substring(0, changedName.indexOf(" "));
                String lastName = changedName.substring(changedName.indexOf(" ") + 1);

                changedAuthor = new Author(firstName, lastName);
            } else {
                changedAuthor = new Author(changedName, "");
            }

            for (Author author : mAuthorsList) {
                if (author.getFirstName().equals(changedAuthor.getFirstName()) &&
                        author.getLastName().equals(changedAuthor.getLastName())) {
                    mFinalBook.setAuthor_id(author.getId());
                    isAuthorFound = true;
                }
            }

            if (!isAuthorFound) {
                mFinalBook.setAuthor_id(mAuthorRepository.insertAuthorTask(changedAuthor));
            }
            changes = true;
        }
        return changes;
    }

    public static int parseIntWithDefault(String number, int defaultVal) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    public static double parseDoubleWithDefault(String number, double defaultVal) {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
