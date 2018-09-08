package com.crmmarketing.hmt.common;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.crmmarketing.hmt.R;

public class TokenTextView extends AppCompatTextView {

    private Context context;

    public TokenTextView(Context context) {
        super(context);
        this.context = context;
    }

    public TokenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TokenTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(false);

        setCompoundDrawablesWithIntrinsicBounds(0, 0, selected ? R.drawable.ic_clear_grey_600_24dp : 0, 0);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= getRight() - getTotalPaddingRight()) {
                        // your action for drawable click event

                        setSelected(true);
                        return true;
                    }
                }
                return false;
            }
        });

    }


    public static int DpToPx(final Context context, final int dp) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }
}