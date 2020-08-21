package me.r1411.finances.ui.actionInfoBottomSheetDialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import me.r1411.finances.R;

public class ActionInfoBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public ActionInfoBottomSheetDialogFragment() {

    }

    public static ActionInfoBottomSheetDialogFragment newInstance(String category, double sum, String comment, long ts) {
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putDouble("sum", sum);
        args.putString("comment", comment);
        args.putLong("ts", ts);
        ActionInfoBottomSheetDialogFragment fragment = new ActionInfoBottomSheetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_action_info_sheet, container, false);
        TextView categoryTextView = view.findViewById(R.id.action_info_category);
        TextView sumTextView = view.findViewById(R.id.action_info_sum);
        TextView commentTextViewTitle = view.findViewById(R.id.action_info_comment_title);
        TextView commentTextView = view.findViewById(R.id.action_info_comment);
        TextView dateTextView = view.findViewById(R.id.action_info_date);
        Button deleteButton = view.findViewById(R.id.action_info_delete_button);
        String category = getArguments().getString("category");
        double sum = getArguments().getDouble("sum");
        String comment = getArguments().getString("comment");
        long ts = getArguments().getLong("ts");

        categoryTextView.setText(category);
        String sumDisplay;
        if (sum == (long) sum) {
            sumDisplay = String.format(Locale.getDefault(), "%d",(long)sum);
        } else {
            sumDisplay = String.format("%s", sum);
        }
        sumDisplay += getContext().getString(R.string.currency_postfix);
        sumTextView.setText(sumDisplay);

        if(comment.isEmpty()) {
            commentTextViewTitle.setVisibility(View.GONE);
            commentTextView.setVisibility(View.GONE);
        } else {
            commentTextView.setText(comment);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date(ts * 1000L));

        dateTextView.setText(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT).format(c.getTime()));

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setWhiteNavigationBar(@NonNull Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            DisplayMetrics metrics = new DisplayMetrics();
            window.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            GradientDrawable dimDrawable = new GradientDrawable();

            GradientDrawable navigationBarDrawable = new GradientDrawable();
            navigationBarDrawable.setShape(GradientDrawable.RECTANGLE);
            navigationBarDrawable.setColor(Color.WHITE);

            Drawable[] layers = {dimDrawable, navigationBarDrawable};

            LayerDrawable windowBackground = new LayerDrawable(layers);
            windowBackground.setLayerInsetTop(1, metrics.heightPixels);

            window.setBackgroundDrawable(windowBackground);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setWhiteNavigationBar(dialog);
        }

        return dialog;
    }


}
