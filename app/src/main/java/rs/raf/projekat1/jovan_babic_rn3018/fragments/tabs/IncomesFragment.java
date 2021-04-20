package rs.raf.projekat1.jovan_babic_rn3018.fragments.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.rafwalletprojekat.R;
import rs.raf.projekat1.jovan_babic_rn3018.activities.EditIncomeExpenseActivity;
import rs.raf.projekat1.jovan_babic_rn3018.activities.IncomeExpenseInfoActivity;
import rs.raf.projekat1.jovan_babic_rn3018.recycler.adapter.IncomeAdapter;
import rs.raf.projekat1.jovan_babic_rn3018.recycler.diff.PrihodDiffItemCallback;
import rs.raf.projekat1.jovan_babic_rn3018.viewmodel.MainViewModel;


public class IncomesFragment extends Fragment {

    private ActivityResultLauncher<Intent> resultLauncher;

    private MainViewModel mainViewModel;
    private IncomeAdapter incomeAdapter;
    private RecyclerView recyclerView;

    public IncomesFragment() {
        super(R.layout.fragment_incomes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        initActivityForResult();
        init(view);
    }

    private void initActivityForResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) return;

                    Intent data = result.getData();
                    String id = data.getStringExtra("id");
                    String newNaziv = data.getStringExtra("naziv");
                    int newKolicina = data.getIntExtra("kolicina", -1);
                    if (data.getStringExtra("opis") != null) {
                        String newOpis = data.getStringExtra("opis");
                        mainViewModel.changePrihod(id, newNaziv, newKolicina, newOpis);
                    } else {
                        String audioFilePath = data.getStringExtra("audioFile");
                        mainViewModel.changePrihodWithAudio(id, newNaziv, newKolicina, audioFilePath);
                    }
                    incomeAdapter.notifyDataSetChanged();
                }
        );
    }

    private void init(View view) {
        initView(view);
        initRecycler(view);
        initObserver();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.income_recyclerview);
    }

    private void initRecycler(View view) {
        incomeAdapter = new IncomeAdapter(new PrihodDiffItemCallback(), income -> {
//            Toast.makeText(view.getContext(), income.getId(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), IncomeExpenseInfoActivity.class);
            intent.putExtra("naziv", income.getNaslov());
            intent.putExtra("kolicina", income.getKolicina());
            if (income.getOpis() != null)
                intent.putExtra("opis", income.getOpis());
            else
                intent.putExtra("audioFile", income.getAudioFilePath());
            startActivity(intent);
            return null;
        }, income -> {
            mainViewModel.removePrihod(income);
            return null;
        }, income -> {
            Intent intent = new Intent(getActivity(), EditIncomeExpenseActivity.class);

            intent.putExtra("id", income.getId());
            intent.putExtra("naziv", income.getNaslov());
            intent.putExtra("kolicina", income.getKolicina());
            if (income.getOpis() != null)
                intent.putExtra("opis", income.getOpis());
            else {
                intent.putExtra("audioFile", income.getAudioFilePath());
            }

            resultLauncher.launch(intent);
            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(incomeAdapter);
    }

    private void initObserver() {
        mainViewModel.getPrihodi().observe(requireActivity(), incomes -> {
            incomeAdapter.submitList(incomes);
        });
    }
}