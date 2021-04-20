package rs.raf.projekat1.jovan_babic_rn3018.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rafwalletprojekat.R;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class EditIncomeExpenseActivity extends AppCompatActivity {

    private EditText editIncomeExpenseNameEt;
    private EditText editIncomeExpenseAmountEt;
    private EditText editIncomeExpenseDescEt;

    private Button editIncomeExpenseCancelButton;
    private Button editIncomeExpenseSaveButton;

    private ImageView micButtonEmpty;
    private ImageView micButtonRecording;

    private MediaRecorder mediaRecorder;
    private File audioFile;
    private boolean audioChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_income_expense);

        initViews();
        updateData();
        initListeners();
    }

    private void initViews() {
        editIncomeExpenseCancelButton = findViewById(R.id.edit_income_expense_button_cancel);
        editIncomeExpenseSaveButton = findViewById(R.id.edit_income_expense_button_save);

        editIncomeExpenseNameEt = findViewById(R.id.edit_income_expense_name);
        editIncomeExpenseAmountEt = findViewById(R.id.edit_income_expense_amount);
        editIncomeExpenseDescEt = findViewById(R.id.edit_income_expense_description);

        micButtonEmpty = findViewById(R.id.mic_button_empty);
        micButtonRecording = findViewById(R.id.mic_button_recording);
    }

    private void updateData() {
        editIncomeExpenseNameEt.setText(getIntent().getStringExtra("naziv"));
        editIncomeExpenseAmountEt.setText(getIntent().getIntExtra("kolicina",0)+"");
        if (getIntent().getStringExtra("opis") != null)
            editIncomeExpenseDescEt.setText(getIntent().getStringExtra("opis"));
        else {
            editIncomeExpenseDescEt.setVisibility(View.GONE);
            micButtonEmpty.setVisibility(View.VISIBLE);
            initAudioFile();
        }
    }

    private void initListeners() {
        editIncomeExpenseCancelButton.setOnClickListener(v -> {
            finish();
        });

        editIncomeExpenseSaveButton.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("id", getIntent().getStringExtra("id"));
            returnIntent.putExtra("naziv", editIncomeExpenseNameEt.getText().toString());
            returnIntent.putExtra("kolicina", Integer.parseInt(editIncomeExpenseAmountEt.getText().toString()));
            if (getIntent().getStringExtra("opis") != null)
                returnIntent.putExtra("opis", editIncomeExpenseDescEt.getText().toString());
            else {
                if (audioChanged && micButtonEmpty.getVisibility() == View.VISIBLE) {
                    returnIntent.putExtra("audioFile", audioFile.getAbsolutePath());
                } else {
                    returnIntent.putExtra("audioFile", getIntent().getStringExtra("audioFile"));
                }
            }
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        micButtonEmpty.setOnClickListener(v -> {
            try {
                micButtonEmpty.setVisibility(View.GONE);
                micButtonRecording.setVisibility(View.VISIBLE);
                initMediaRecorder(audioFile);
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        micButtonRecording.setOnClickListener(v -> {
            micButtonRecording.setVisibility(View.GONE);
            micButtonEmpty.setVisibility(View.VISIBLE);
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            audioChanged = true;
        });
    }

    private void initAudioFile() {
        File folder = new File(this.getFilesDir(), "sounds");
        if (!folder.exists()) folder.mkdir();
        audioFile = new File(folder, UUID.randomUUID().toString()+".3gg");
    }

    private void initMediaRecorder(File file) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(file);
    }
}