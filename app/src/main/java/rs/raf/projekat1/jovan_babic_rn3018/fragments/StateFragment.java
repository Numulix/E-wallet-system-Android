package rs.raf.projekat1.jovan_babic_rn3018.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.TextView;

import com.example.rafwalletprojekat.R;
import rs.raf.projekat1.jovan_babic_rn3018.viewmodel.MainViewModel;


public class StateFragment extends Fragment {

    private MainViewModel mainViewModel;

    private TextView incomeTv;
    private TextView expenseTv;
    private TextView diffTv;

    public StateFragment() {
        super(R.layout.fragment_state);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        initViews(view);
        initObserver();
        updateData();
    }

    private void initViews(View view) {
        incomeTv = view.findViewById(R.id.income_tv);
        expenseTv = view.findViewById(R.id.expense_tv);
        diffTv = view.findViewById(R.id.diff_tv);
    }

    private void updateData() {
//        incomeTv.setText(incomeTv.getText().toString() + " " + mainViewModel.getPrihodiSum());
//        expenseTv.setText(expenseTv.getText().toString() + " " + mainViewModel.getRashodiSum());
        int razlika = mainViewModel.getPrihodiSum() - mainViewModel.getRashodiSum();
        diffTv.setText("Razlika: " + razlika);
        if (razlika < 0) diffTv.setTextColor(getResources().getColor(R.color.red, null));
        else diffTv.setTextColor(getResources().getColor(R.color.green, null));
    }

    private void initObserver() {
        mainViewModel.getPrihodi().observe(getActivity(), v -> {
            incomeTv.setText("");
            incomeTv.setText("Prihodi: " + mainViewModel.getPrihodiSum());
            updateData();
        });

        mainViewModel.getRashodi().observe(getActivity(), v -> {
            expenseTv.setText("");
            expenseTv.setText("Rashodi: " + mainViewModel.getRashodiSum());
            updateData();
        });

    }
}