package com.effizent.esplapp.cusFnt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by shree on 4/20/2018.
 */

@SuppressLint("AppCompatCustomView")
public class ButtonAirenRegular extends Button
{
    public ButtonAirenRegular(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public ButtonAirenRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public ButtonAirenRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/BioRhyme-Bold.otf");
        setTypeface(customFont);
    }
}
