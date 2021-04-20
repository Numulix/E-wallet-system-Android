package rs.raf.projekat1.jovan_babic_rn3018.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.rafwalletprojekat.R;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editNameEt;
    private EditText editSurnameEt;
    private EditText editBankEt;

    private Button cancelButton;
    private Button saveButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        initViews();
        initListeners();
    }

    private void initViews() {
        editNameEt = findViewById(R.id.edit_name);
        editSurnameEt = findViewById(R.id.edit_surname);
        editBankEt = findViewById(R.id.edit_bank);

        cancelButton = findViewById(R.id.button_cancel);
        saveButton = findViewById(R.id.button_save);

        editNameEt.setHint(sharedPreferences.getString("name", ""));
        editSurnameEt.setHint(sharedPreferences.getString("surname", ""));
        editBankEt.setHint(sharedPreferences.getString("bank", ""));
    }

    private void initListeners() {
        cancelButton.setOnClickListener(v -> {
            finish();
        });

        saveButton.setOnClickListener(v -> {
            if (!editNameEt.getText().toString().isEmpty())
                sharedPreferences.edit().putString("name", editNameEt.getText().toString()).commit();

            if (!editSurnameEt.getText().toString().isEmpty())
                sharedPreferences.edit().putString("surname", editSurnameEt.getText().toString()).commit();

            if (!editBankEt.getText().toString().isEmpty()) {
                sharedPreferences.edit().putString("bank", editBankEt.getText().toString()).commit();
            }

//            Intent intent = new Intent(this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
            finish();
        });
    }
}