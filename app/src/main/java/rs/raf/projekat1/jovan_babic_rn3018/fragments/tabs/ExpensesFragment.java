package rs.raf.projekat1.jovan_babic_rn3018.fragments.tabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafwalletprojekat.R;
import rs.raf.projekat1.jovan_babic_rn3018.activities.EditIncomeExpenseActivity;
import rs.raf.projekat1.jovan_babic_rn3018.activities.IncomeExpenseInfoActivity;
import rs.raf.projekat1.jovan_babic_rn3018.recycler.adapter.ExpenseAdapter;
import rs.raf.projekat1.jovan_babic_rn3018.recycler.diff.RashodDiffItemCallback;
import rs.raf.projekat1.jovan_babic_rn3018.viewmodel.MainViewModel;

public class ExpensesFragment extends Fragment {

    private ActivityResultLauncher<Intent> resultLauncher;

    private MainViewModel mainViewModel;
    private ExpenseAdapter expenseAdapter;
    private RecyclerView recyclerView;

    public ExpensesFragment() {
        super(R.layout.fragment_expenses);
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
                        mainViewModel.changeRashod(id, newNaziv, newKolicina, newOpis);
                    } else {
                        String audioFilePath = data.getStringExtra("audioFile");
                        mainViewModel.changeRashodWithAudio(id, newNaziv, newKolicina, audioFilePath);
                    }
                    expenseAdapter.notifyDataSetChanged();
                }
        );
    }

    private void init(View view) {
        initView(view);
        initRecycler(view);
        initObserver();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.expense_recyclerview);
    }

    private void initRecycler(View view) {
        expenseAdapter = new ExpenseAdapter(new RashodDiffItemCallback(), expense -> {
//            Toast.makeText(view.getContext(), expense.getId(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), IncomeExpenseInfoActivity.class);
            intent.putExtra("naziv", expense.getNaslov());
            intent.putExtra("kolicina", expense.getKolicina());
            if (expense.getOpis() != null)
                intent.putExtra("opis", expense.getOpis());
            else
                intent.putExtra("audioFile", expense.getAudioFilePath());
            startActivity(intent);
            return null;
        }, expense -> {
            mainViewModel.removeRashod(expense);
            return null;
        }, expense -> {

            Intent intent = new Intent(getActivity(), EditIncomeExpenseActivity.class);

            intent.putExtra("id", expense.getId());
            intent.putExtra("naziv", expense.getNaslov());
            intent.putExtra("kolicina", expense.getKolicina());
            if (expense.getOpis() != null)
                intent.putExtra("opis", expense.getOpis());
            else
                intent.putExtra("audioFile", expense.getAudioFilePath());

            resultLauncher.launch(intent);

            return null;
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(expenseAdapter);
    }

    private void initObserver() {
        mainViewModel.getRashodi().observe(requireActivity(), expenses -> {
            expenseAdapter.submitList(expenses);
        });
    }
}
