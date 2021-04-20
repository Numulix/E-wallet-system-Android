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
import rs.raf.projekat1.jovan_babic_rn3018.models.Prihod;

import java.util.function.Function;

public class IncomeAdapter extends ListAdapter<Prihod, IncomeAdapter.ViewHolder> {


    private final Function<Prihod, Void> onIncomeClicked;
    private final Function<Prihod, Void> onDeleteIncomeClick;
    private final Function<Prihod, Void> onEditIncomeClick;

    public IncomeAdapter(@NonNull DiffUtil.ItemCallback<Prihod> diffCallback,
                         Function<Prihod, Void> onIncomeClicked,
                         Function<Prihod, Void> onDeleteIncomeClick,
                         Function<Prihod, Void> onEditIncomeClick) {
        super(diffCallback);
        this.onIncomeClicked = onIncomeClicked;
        this.onDeleteIncomeClick = onDeleteIncomeClick;
        this.onEditIncomeClick = onEditIncomeClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_list_item, parent, false);
        return new ViewHolder(view, parent.getContext(), position -> {
            Prihod income = getItem(position);
            onIncomeClicked.apply(income);
            return null;
        }, position -> {
            Prihod income = getItem(position);
            onDeleteIncomeClick.apply(income);
            return null;
        }, position -> {
            Prihod income = getItem(position);
            onEditIncomeClick.apply(income);
            return null;
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Prihod income = getItem(position);
        holder.bind(income);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context,
                          Function<Integer, Void> onItemClicked,
                          Function<Integer, Void> onDeleteIncomeClick,
                          Function<Integer, Void> onEditItemClick) {
            super(itemView);
            this.context = context;
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onItemClicked.apply(getAdapterPosition());
            });

            itemView.findViewById(R.id.delete_button).setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onDeleteIncomeClick.apply(getAdapterPosition());
            });

            itemView.findViewById(R.id.edit_button).setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION)
                    onEditItemClick.apply(getAdapterPosition());
            });
        }

        public void bind(Prihod prihod) {
            ((TextView)itemView.findViewById(R.id.income_expense_title)).setText(prihod.getNaslov());
            ((TextView)itemView.findViewById(R.id.list_income_expense_amount)).setText(prihod.getKolicina()+"");
        }
    }

}
