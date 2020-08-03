package me.r1411.finances.ui.elements;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Spinner;

public class FakeSpinner extends androidx.appcompat.widget.AppCompatSpinner {

    private FakeSpinnerClickListener listener;

    public FakeSpinner(Context context) {
        super(context);
    }

    public FakeSpinner(Context context, int mode) {
        super(context, mode);
    }

    public FakeSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FakeSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FakeSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public FakeSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    @Override
    public boolean performClick() {
        if (this.listener != null)
            listener.onFakeSpinnerClick();
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE)
            return true;
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
            ev.setAction(MotionEvent.ACTION_CANCEL);
        return super.dispatchTouchEvent(ev);
    }

    public void setFakeSpinnerClickListener(FakeSpinnerClickListener listener) {
        this.listener = listener;
    }
}
