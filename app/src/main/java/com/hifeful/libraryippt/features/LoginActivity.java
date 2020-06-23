package com.hifeful.libraryippt.features;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hifeful.libraryippt.R;
import com.hifeful.libraryippt.data.ClientRepository;
import com.hifeful.libraryippt.features.booklist.BookListActivity;
import com.hifeful.libraryippt.features.booklist.BookListAdapter;

public class LoginActivity extends AppCompatActivity {
    public static final int CLIENT_ADMIN = 1;
    public static final int CLIENT_USER = 0;

    // UI Components
    private MaterialButton signInButton;
    private TextInputLayout usernameLayout;
    private TextInputEditText username;
    private TextInputLayout passwordLayout;
    private TextInputEditText password;

    // Variables
    private ClientRepository clientRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signInButton = findViewById(R.id.sign_in_button);
        usernameLayout = findViewById(R.id.username_layout);
        username = findViewById(R.id.username);
        passwordLayout = findViewById(R.id.password_layout);
        password = findViewById(R.id.password);

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        signInButton.setOnClickListener(view -> {
            clientRepository.getClientByLoginTask(username.getText().toString()).observe(this,
                    client -> {
                if (client != null) {
                    if (client.getPassword().equals(password.getText().toString())) {
                        Intent intent = new Intent(LoginActivity.this, BookListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        if (client.getLogin().equals("admin")) {
                            intent.putExtra("client", CLIENT_ADMIN);
                        } else {
                            intent.putExtra("client", CLIENT_USER);
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        usernameLayout.setError(" ");
                        passwordLayout.setError(getResources().getString(R.string.login_error));
                    }
                } else {
                    usernameLayout.setError(" ");
                    passwordLayout.setError(getResources().getString(R.string.login_error));
                }
            });
        });

        clientRepository = new ClientRepository(this);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            signInButton.setEnabled(!username.getText().toString().trim().isEmpty()
                    && !password.getText().toString().trim().isEmpty());
            usernameLayout.setError(null);
            passwordLayout.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
