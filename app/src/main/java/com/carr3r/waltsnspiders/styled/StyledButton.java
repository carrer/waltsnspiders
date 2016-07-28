package com.carr3r.waltsnspiders.styled;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Neto on 03/11/2015.
 */
public class StyledButton extends Button {
    public StyledButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public StyledButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StyledButton(Context context) {
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