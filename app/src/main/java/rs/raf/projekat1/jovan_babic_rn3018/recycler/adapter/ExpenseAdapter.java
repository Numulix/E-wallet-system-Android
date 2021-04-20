package rs.raf.projekat1.jovan_babic_rn3018.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafwalletprojekat.R;

import rs.raf.projekat1.jovan_babic_rn3018.models.Rashod;

import java.util.function.Function;

public class ExpenseAdapter extends ListAdapter<Rashod, ExpenseAdapter.ViewHolder> {

    private final Function<Rashod, Void> onIncomeClicked;
    private final Function<Rashod, Void> onDeleteExpenseClick;
    private final Function<Rashod, Void> onEditExpenseClick;

    public ExpenseAdapter(@NonNull DiffUtil.ItemCallback<Rashod> diffCallback,
                          Function<Rashod, Void> onIncomeClicked,
                          Function<Rashod, Void> onDeleteExpenseClick,
                          Function<Rashod, Void> onEditExpenseClick) {
        super(diffCallback);
        this.onIncomeClicked = onIncomeClicked;
        this.onDeleteExpenseClick = onDeleteExpenseClick;
        this.onEditExpenseClick = onEditExpenseClick;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_list_item, parent, false);
        return new ExpenseAdapter.ViewHolder(view, parent.getContext(), position -> {
            Rashod expense = getItem(position);
            onIncomeClicked.apply(expense);
            return null;
        }, position -> {
            Rashod expense = getItem(position);
            onDeleteExpenseClick.apply(expense);
            return null;
        }, position -> {
            Rashod expense = getItem(position);
            onEditExpenseClick.apply(expense);
            return null;
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Rashod expense = getItem(position);
        holder.bind(expense);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context,
                          Function<Integer, Void> onItemClicked,
                          Function<Integer, Void> onDeleteExpenseClicked,
                          Function<Integer, Void> onEditExpenseClick) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onItemClicked.apply(getAdapterPosition());
            });

            itemView.findViewById(R.id.delete_button).setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onDeleteExpenseClicked.apply(getAdapterPosition());
            });

            itemView.findViewById(R.id.edit_button).setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onEditExpenseClick.apply(getAdapterPosition());
            });
        }

        public void bind(Rashod rashod) {
            ((TextView)itemView.findViewById(R.id.expense_title)).setText(rashod.getNaslov());
            ((TextView)itemView.findViewById(R.id.list_expense_amount)).setText(rashod.getKolicina()+"");
        }
    }

}
