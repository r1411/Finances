package me.r1411.finances.ui.elements;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import java.util.Locale;

import me.r1411.finances.R;
import me.r1411.finances.ui.actionInfoBottomSheetDialogFragment.ActionInfoBottomSheetDialogFragment;

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
        ActionInfoBottomSheetDialogFragment addPhotoBottomDialogFragment = ActionInfoBottomSheetDialogFragment.newInstance(this.getCategory(), this.getSum(), this.getComment(), this.getTs());
        addPhotoBottomDialogFragment.show(FragmentManager.findFragment(this).getChildFragmentManager(), "action_info_dialog_fragment");
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
