package me.r1411.finances.ui.elements;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.Locale;

import me.r1411.finances.R;

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


}
