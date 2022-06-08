package com.example.manager_pneumo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.manager_pneumo.databinding.LayoutFeedsBinding;

/**
 * TODO: document your custom view class.
 */
public class FeedsView extends LinearLayout implements View.OnClickListener {

    private int own_id;

    private LayoutFeedsBinding binding;
    private EditText title;
    private EditText value;

    public FeedsView(Context context) {
        super(context);
        init(null, 0);
    }

    public FeedsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public FeedsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        View.inflate(getContext(), R.layout.layout_feeds, this);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FeedsView,
                0, 0);

        try {
            own_id = a.getInteger(R.styleable.FeedsView_own_id, 0);
        } finally {
            a.recycle();
        }

        title = (EditText) findViewById(R.id.feed_title);
        value = (EditText) findViewById(R.id.feed_value);


    }

    public void setTitleText(String title) {
        System.out.println("setting title of " + this + " to "+ title);
        this.title.setText(title);
    }

    public String getTitleText() { return this.title.getText().toString();};

    public void setValueText(String s) {
        System.out.println("setting value of " + this + " to "+ s);
        this.value.setText(s);
    }

    @Override
    public void setOnClickListener(View.OnClickListener ocl)
    {
        super.setOnClickListener(ocl);
        title.setOnClickListener(this);
        value.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        performClick();
    }
}