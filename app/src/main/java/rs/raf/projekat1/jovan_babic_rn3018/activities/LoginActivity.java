package rs.raf.projekat1.jovan_babic_rn3018.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafwalletprojekat.R;

public class LoginActivity extends AppCompatActivity {

    private static final String SIFRA = "s4f3p4ss";

    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText bankEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameEditText = findViewById(R.id.login_name_edit_text);
        surnameEditText = findViewById(R.id.login_surname_edit_text);
        bankEditText = findViewById(R.id.login_bank_edit_text);
        passwordEditText = findViewById(R.id.login_password_edit_text);
        loginButton = findViewById(R.id.login_button);

        initListeners();
    }

    private void initListeners() {
        loginButton.setOnClickListener(v -> {

            boolean flag = !(nameEditText.getText().toString().isEmpty()
                            || surnameEditText.getText().toString().isEmpty()
                            || bankEditText.getText().toString().isEmpty()
                            || passwordEditText.getText().toString().isEmpty());

            if (flag) {
                if (passwordEditText.getText().toString().length() < 5) {
                    Toast.makeText(this, R.string.toast_short_password, Toast.LENGTH_LONG).show();
                    return;
                } else if (!passwordEditText.getText().toString().equals(SIFRA)) {
                    Toast.makeText(this, R.string.toast_wrong_password, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("name", nameEditText.getText().toString())
                        .putExtra("surname", surnameEditText.getText().toString())
                        .putExtra("bank", bankEditText.getText().toString());


                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
                sharedPreferences
                        .edit()
                        .putString("name", nameEditText.getText().toString())
                        .putString("surname", surnameEditText.getText().toString())
                        .putString("bank", bankEditText.getText().toString())
                        .apply();

                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, R.string.toast_empty_fields, Toast.LENGTH_LONG).show();
            }
        });
    }
}