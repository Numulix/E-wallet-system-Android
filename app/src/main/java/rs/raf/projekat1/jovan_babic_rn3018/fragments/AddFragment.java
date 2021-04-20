package rs.raf.projekat1.jovan_babic_rn3018.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rafwalletprojekat.R;
import rs.raf.projekat1.jovan_babic_rn3018.viewmodel.MainViewModel;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class AddFragment extends Fragment {

    private MainViewModel mainViewModel;

    private Spinner spinner;
    private EditText nameEt;
    private EditText amountEt;
    private EditText descriptionEt;
    private CheckBox audioCb;

    private ImageView micButtonEmpty;
    private ImageView micButtonRecording;

    private MediaRecorder mediaRecorder;
    private File audioFile;

    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private Button addButton;

    private boolean recorded;

    public AddFragment() {
        super(R.layout.fragment_add);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        recorded = false;
        initSpinner(view);
        initViews(view);
        if (hasPermissions(requireContext(), PERMISSIONS)) {
            initAudioFile();
        }
        initListener();
    }

    private void initSpinner(View view) {
        spinner = view.findViewById(R.id.add_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.spinner_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initViews(View view) {
        nameEt = view.findViewById(R.id.income_expense_name);
        amountEt = view.findViewById(R.id.income_expense_amount);
        descriptionEt = view.findViewById(R.id.income_expense_description);
        addButton = view.findViewById(R.id.button_add);
        audioCb = view.findViewById(R.id.audio_checkbox);
        micButtonEmpty = view.findViewById(R.id.mic_button_empty);
        micButtonRecording = view.findViewById(R.id.mic_button_recording);
    }

    private void initListener() {
        addButton.setOnClickListener(v -> {
            boolean flag = !(nameEt.getText().toString().isEmpty()
                            || amountEt.getText().toString().isEmpty());

            boolean audioOrDescFlag = recorded || !descriptionEt.getText().toString().isEmpty();

            flag = flag && audioOrDescFlag;

            int spinnerPos = spinner.getSelectedItemPosition();

            if (flag) {
                if (spinnerPos == 0) {
                    if (!audioCb.isChecked())
                        mainViewModel.addPrihod(nameEt.getText().toString(),
                                Integer.parseInt(amountEt.getText().toString()),
                                descriptionEt.getText().toString());
                    else
                        if (recorded)
                            mainViewModel.addPrihodWithAudio(nameEt.getText().toString(),
                                    Integer.parseInt(amountEt.getText().toString()),
                                    audioFile.getAbsolutePath());
                        else {
                            Toast.makeText(getContext(), R.string.audio_empty, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    recorded = false;
                    initAudioFile();
                    clearFields();
                    Toast.makeText(getContext(), R.string.income_success, Toast.LENGTH_SHORT).show();
                } else {
                    if (!audioCb.isChecked())
                        mainViewModel.addRashod(nameEt.getText().toString(),
                                Integer.parseInt(amountEt.getText().toString()),
                                descriptionEt.getText().toString());
                    else
                        if (recorded)
                            mainViewModel.addRashodWithAudio(nameEt.getText().toString(),
                                    Integer.parseInt(amountEt.getText().toString()),
                                    audioFile.getAbsolutePath());
                        else {
                            Toast.makeText(getContext(), R.string.audio_empty, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    recorded = false;
                    initAudioFile();
                    clearFields();
                    Toast.makeText(getContext(), R.string.expense_success, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), R.string.toast_empty_fields, Toast.LENGTH_LONG).show();
            }
        });

        audioCb.setOnClickListener(v -> {
            if (audioCb.isChecked()) {
                if (!hasPermissions(requireContext(), PERMISSIONS)) {
                    requestPermissions(PERMISSIONS, PERMISSION_ALL);
                    return;
                }
                descriptionEt.setVisibility(View.GONE);
                micButtonEmpty.setVisibility(View.VISIBLE);
            } else {
                descriptionEt.setVisibility(View.VISIBLE);
                micButtonEmpty.setVisibility(View.GONE);
            }
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
            recorded = true;
        });
    }

    private void clearFields() {
        nameEt.getText().clear();
        amountEt.getText().clear();
        descriptionEt.getText().clear();
        audioCb.toggle();
        micButtonEmpty.setVisibility(View.GONE);
        descriptionEt.setVisibility(View.VISIBLE);
    }

    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission: permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                    return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0) {
                StringBuilder permissionsDenied = new StringBuilder();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                        permissionsDenied.append("\n").append(permissions[i]);
                }

                if (permissionsDenied.toString().length() == 0) {
                    micButtonEmpty.setVisibility(View.VISIBLE);
                    descriptionEt.setVisibility(View.GONE);
                    initAudioFile();
                } else {
                    Toast.makeText(getContext(), "Missing permissions! " + permissionsDenied.toString(),
                            Toast.LENGTH_LONG).show();
                    if (audioCb.isChecked()) {
                        audioCb.toggle();
                    }
                }
            }
        }
    }

    private void initAudioFile() {
        File folder = new File(requireActivity().getFilesDir(), "sounds");
        if (!folder.exists()) folder.mkdir();
        audioFile = new File(folder, UUID.randomUUID().toString() + ".3gg");
    }

    private void initMediaRecorder(File file) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(file);
    }
}