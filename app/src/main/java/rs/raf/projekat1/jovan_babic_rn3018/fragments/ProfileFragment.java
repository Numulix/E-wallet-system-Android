package rs.raf.projekat1.jovan_babic_rn3018.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rafwalletprojekat.R;
import rs.raf.projekat1.jovan_babic_rn3018.activities.EditProfileActivity;
import rs.raf.projekat1.jovan_babic_rn3018.activities.LoginActivity;

public class ProfileFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    private TextView nameTv;
    private TextView surnameTv;
    private TextView bankTv;

    private Button editButton;
    private Button logoutButton;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = this.requireActivity().getSharedPreferences(this.requireActivity().getPackageName(), Context.MODE_PRIVATE);

        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals("name") || key.equals("surname") || key.equals("bank"))
                    setData();
            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);

        nameTv = view.findViewById(R.id.name_textview);
        surnameTv = view.findViewById(R.id.surname_textview);
        bankTv = view.findViewById(R.id.bank_textview);

        editButton = view.findViewById(R.id.edit_button);
        logoutButton = view.findViewById(R.id.logout_button);

        initListeners();
        setData();
    }

    private void initListeners() {
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.requireContext(), LoginActivity.class);
            sharedPreferences
                    .edit()
                    .clear()
                    .apply();
            startActivity(intent);
            this.requireActivity().finish();
        });

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this.requireContext(), EditProfileActivity.class);
            startActivity(intent);
        });
    }


    private void setData() {
        nameTv.setText(sharedPreferences.getString("name", ""));
        surnameTv.setText(sharedPreferences.getString("surname", ""));
        bankTv.setText(sharedPreferences.getString("bank", ""));
    }

}