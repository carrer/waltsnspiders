package com.carr3r.waltsnspiders.styled;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by Neto on 03/11/2015.
 */
public class StyledCheckbox extends CheckBox {
    public StyledCheckbox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public StyledCheckbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StyledCheckbox(Context context) {
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