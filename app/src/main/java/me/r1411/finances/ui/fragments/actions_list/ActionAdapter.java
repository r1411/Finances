package me.r1411.finances.ui.fragments.actions_list;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.Action;
import me.r1411.finances.objects.Expense;
import me.r1411.finances.objects.Income;
import me.r1411.finances.ui.actionInfoBottomSheetDialogFragment.ActionInfoBottomSheetDialogFragment;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_action_row, parent, false);
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

    public class ActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView categoryTextView;
        private TextView tsTextView;
        private TextView sumTextView;
        private TextView commentTextView;

        public void bind(Action action) {
            categoryTextView.setText(action.getCategory());
            String sumDisplay;

            if (action.getSum() == (long) action.getSum()) {
                sumDisplay = String.format(Locale.getDefault(), "%d",(long)action.getSum());
            } else {
                sumDisplay = String.format("%s", action.getSum());
            }
            sumDisplay += FinancesApp.getContext().getString(R.string.currency_postfix);
            sumTextView.setText(sumDisplay);
            if (action instanceof Expense) {
                sumTextView.setTextColor(ContextCompat.getColor(FinancesApp.getContext(), R.color.expenseColor));
            } else if (action instanceof Income) {
                sumTextView.setTextColor(ContextCompat.getColor(FinancesApp.getContext(), R.color.incomeColor));
            }
            tsTextView.setText(DateUtils.getRelativeTimeSpanString(action.getTs() * 1000L).toString());
            commentTextView.setText(action.getComment());
            if(action.getComment().isEmpty()) {
                commentTextView.setVisibility(View.GONE);
            }
        }

        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.categoryTextView = itemView.findViewById(R.id.category_text);
            this.tsTextView = itemView.findViewById(R.id.ts_text);
            this.sumTextView = itemView.findViewById(R.id.sum_text);
            this.commentTextView = itemView.findViewById(R.id.comment_text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Action action = actionList.get(getAdapterPosition());
            ActionInfoBottomSheetDialogFragment dialogFragment = ActionInfoBottomSheetDialogFragment.newInstance(action.getCategory(), action.getSum(), action.getComment(), action.getTs());
            dialogFragment.show(FragmentManager.findFragment(view).getChildFragmentManager(), "action_info_dialog_fragment");
            dialogFragment.setDeleteButtonClickListener(() -> {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    if(action instanceof Expense) {
                        FinancesApp.getInstance().getDatabase().expenseDao().delete((Expense) action);
                    } else {
                        FinancesApp.getInstance().getDatabase().incomeDao().delete((Income) action);
                    }
                    int pos = getAdapterPosition();
                    if (pos >= 0) {
                        actionList.remove(pos);
                        FragmentManager.findFragment(view).getActivity().runOnUiThread(() -> {
                            notifyItemRemoved(pos);
                        });
                    }
                });
            });
        }
    }

}
