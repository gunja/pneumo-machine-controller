package com.example.manager_pneumo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.manager_pneumo.databinding.LayoutReadingBinding;

/**
 * TODO: document your custom view class.
 */
public class ActuatorView extends LinearLayout implements View.OnClickListener {

    private int own_id;
    private LayoutReadingBinding binding;
    private boolean shouldShow;

    public ActuatorView(Context context) {
        super(context);
        init(null, 0);
    }

    public ActuatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ActuatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        binding = LayoutReadingBinding.inflate(LayoutInflater.from(getContext()), this, true);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ActuatorView, defStyle, 0);
        own_id = a.getInteger(R.styleable.ActuatorView_aw_own_id, 0);
        a.recycle();

        shouldShow = true;
    }

    public void setTitleText(String title)
    {
        binding.pneumoTitle.setText(title);
    }


    public void setShowDesired(boolean shouldShow) {
        this.shouldShow = shouldShow;
        if (! this.shouldShow) {
            binding.targetValue.setText("");
            binding.targetValue.setInputType(InputType.TYPE_NULL);
        }
    }

    public int getOwnId() { return own_id;};

    @Override
    public void onClick(View view) {
        if (view !=  binding.switchUnitBtn) {
            performClick();
            return;
        }
        // TODO implement switching of displayed units
    }

    public void setValueText(String value) {
        binding.actualValue.setText(value);
    }

    public void setInSettingsMode() {
        binding.targetValue.setInputType(InputType.TYPE_NULL);
        binding.targetValue.setOnClickListener(this);
        binding.pneumoTitle.setOnClickListener(this);
        binding.actualValue.setOnClickListener(this);
        binding.switchUnitBtn.setOnClickListener(this);
    }

}