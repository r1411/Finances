package me.r1411.finances.ui.elements;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.r1411.finances.R;

public class ExpenseRowView extends LinearLayout {
    private long itemId;
    private String category;
    private double sum;
    private long ts;

    public ExpenseRowView(Context context, long id, String category, double sum, long ts) {
        super(context);
        inflate(context, R.layout.layout_expense_row, this);

        this.itemId = id;
        this.category = category;
        this.sum = sum;
        this.ts = ts;

        TextView categoryTextView = this.findViewById(R.id.category_text);
        TextView sumTextView = this.findViewById(R.id.sum_text);
        TextView tsTextView = this.findViewById(R.id.ts_text);

        categoryTextView.setText(this.category);

        String sumDisplay = "";
        if (this.sum == (long) sum) {
            sumDisplay = String.format(Locale.getDefault(), "%d",(long)this.sum);
        } else {
            sumDisplay = String.format("%s", this.sum);
        }
        sumDisplay += context.getString(R.string.currency_postfix);
        sumTextView.setText(sumDisplay);

        tsTextView.setText(DateUtils.getRelativeTimeSpanString(ts * 1000L).toString());

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

    /*public ExpenseRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_expense_row, this);

        TextView categoryTextView = this.findViewById(R.id.category_text);
        TextView tsTextView = this.findViewById(R.id.ts_text);
        TextView sumTextView = this.findViewById(R.id.sum_text);

        TypedArray attributes =  context.obtainStyledAttributes(attrs, R.styleable.ExpenseRowView);
        categoryTextView.setText(attributes.getString(R.styleable.ExpenseRowView_category));
        tsTextView.setText(attributes.getString(R.styleable.ExpenseRowView_ts_str));
        sumTextView.setText(String.valueOf(attributes.getInteger(R.styleable.ExpenseRowView_sum, 0)));
        this.id = attributes.getInteger(R.styleable.ExpenseRowView_id, 0);
        this.ts = attributes.getInteger(R.styleable.ExpenseRowView_ts, 0);

        attributes.recycle();
    }*/
}
