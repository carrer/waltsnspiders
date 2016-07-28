package com.carr3r.waltsnspiders.styled;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Neto on 03/11/2015.
 */
public class StyledTextview extends TextView {
    public StyledTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public StyledTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StyledTextview(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/atari.ttf");
            setTypeface(tf);
        }
    }

}