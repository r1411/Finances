package me.r1411.finances.ui.fragments.actions_lit;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.Action;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.Income;

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ActionViewHolder> {
    private List<Action> actionList = new ArrayList<>();

    public void setItems(Collection<Action> actions) {
        actionList.addAll(actions);
        notifyDataSetChanged();
    }

    public void clearItems() {
        actionList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_action_row, parent, false);;
        return new ActionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
        holder.bind(actionList.get(position));
    }

    @Override
    public int getItemCount() {
        return actionList.size();
    }

    public class ActionViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTextView;
        private TextView tsTextView;
        private TextView sumTextView;

        public void bind(Action action) {
            categoryTextView.setText(action.getCategory());
            String sumDisplay = "";

            if (action.getSum() == (long) action.getSum()) {
                sumDisplay = String.format(Locale.getDefault(), "%d",(long)action.getSum());
            } else {
                sumDisplay = String.format("%s", action.getSum());
            }
            sumDisplay += FinancesApp.getContext().getString(R.string.currency_postfix);
            sumTextView.setText(sumDisplay);

            tsTextView.setText(DateUtils.getRelativeTimeSpanString(action.getTs() * 1000L).toString());

        }

        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.categoryTextView = itemView.findViewById(R.id.category_text);
            this.tsTextView = itemView.findViewById(R.id.ts_text);
            this.sumTextView = itemView.findViewById(R.id.sum_text);
        }
    }

}
