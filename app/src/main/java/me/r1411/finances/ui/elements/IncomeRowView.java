package me.r1411.finances.ui.elements;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import me.r1411.finances.FinancesApp;
import me.r1411.finances.R;
import me.r1411.finances.objects.ActionType;
import me.r1411.finances.objects.Income;
import me.r1411.finances.ui.actionInfoBottomSheetDialogFragment.ActionInfoBottomSheetDialogFragment;
import me.r1411.finances.ui.fragments.home.HomeFragment;
import me.r1411.finances.ui.fragments.stats_pie.StatsPieFragment;
import me.r1411.finances.ui.fragments.stats_pie.StatsPiePagerAdapter;

public class IncomeRowView extends LinearLayout {
    private long itemId;
    private String category;
    private double sum;
    private long ts;
    private String comment;

    public IncomeRowView(Context context, long id, String category, double sum, long ts, String comment) {
        super(context);
        inflate(context, R.layout.layout_action_row, this);

        this.itemId = id;
        this.category = category;
        this.sum = sum;
        this.ts = ts;
        this.comment = comment;

        TextView categoryTextView = this.findViewById(R.id.category_text);
        TextView sumTextView = this.findViewById(R.id.sum_text);
        TextView tsTextView = this.findViewById(R.id.ts_text);
        TextView commentTextView = this.findViewById(R.id.comment_text);

        sumTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.incomeColor));
        categoryTextView.setText(this.category);

        String sumDisplay;
        if (this.sum == (long) sum) {
            sumDisplay = String.format(Locale.getDefault(), "%d",(long)this.sum);
        } else {
            sumDisplay = String.format("%s", this.sum);
        }
        sumDisplay += context.getString(R.string.currency_postfix);
        sumTextView.setText(sumDisplay);

        tsTextView.setText(DateUtils.getRelativeTimeSpanString(ts * 1000L).toString());
        commentTextView.setText(this.comment);
        if(this.comment.isEmpty()) {
            commentTextView.setVisibility(GONE);
        }
    }

    @Override
    public boolean performClick() {
        ActionInfoBottomSheetDialogFragment dialogFragment = ActionInfoBottomSheetDialogFragment.newInstance(this.getCategory(), this.getSum(), this.getComment(), this.getTs());
        dialogFragment.show(FragmentManager.findFragment(this).getChildFragmentManager(), "action_info_dialog_fragment");
        dialogFragment.setDeleteButtonClickListener(() -> {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Income income = FinancesApp.getInstance().getDatabase().incomeDao().getById(this.itemId);
                if (income != null)
                    FinancesApp.getInstance().getDatabase().incomeDao().delete(income);
                Fragment fragment = FragmentManager.findFragment(this);
                if (fragment instanceof HomeFragment) {
                    HomeFragment homeFragment = (HomeFragment) fragment;
                    List<Income> latestIncomes = FinancesApp.getInstance().getDatabase().incomeDao().getLatest();
                    homeFragment.getHomeViewModel().getLatestIncomes().postValue(latestIncomes);
                    StatsPieFragment statsPieFragment = ((StatsPiePagerAdapter) homeFragment.getStatsPieViewPager().getAdapter()).getIncomesPieFragment();
                    if ((statsPieFragment != null) && (statsPieFragment.getStatsPieViewModel() != null))
                        statsPieFragment.getStatsPieViewModel().updatePieData(ActionType.INCOME);
                }
            });
        });
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                performClick();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        this.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }


    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
