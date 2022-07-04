package com.example.manager_pneumo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.text.InputType;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.core.net.ParseException;

import com.example.manager_pneumo.databinding.LayoutReadingBinding;

/**
 * TODO: document your custom view class.
 */
public class ActuatorView extends LinearLayout implements View.OnClickListener {

    private int own_id;
    private LayoutReadingBinding binding;
    private boolean shouldShow;
    private int latestTarget;
    private boolean inSettings;

    public void setOptionKg(Boolean value) {
        if(value)
        {
            binding.switchUnitBtn.setText("bars");
        } else {
            binding.switchUnitBtn.setText("kgs");
        }
    }

    public interface tgButtonListener {
        public void onToggle();
    };

    public interface targetEditDoneListener {
        public void onDone(float val);
    };

    private tgButtonListener listener;
    private targetEditDoneListener targetListener;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init(AttributeSet attrs, int defStyle) {
        binding = LayoutReadingBinding.inflate(LayoutInflater.from(getContext()), this, true);
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ActuatorView, defStyle, 0);
        own_id = a.getInteger(R.styleable.ActuatorView_aw_own_id, 0);
        a.recycle();

        shouldShow = true;
        listener = null;
        targetListener = null;
        binding.switchUnitBtn.setOnClickListener(this);
        latestTarget = 0;
        inSettings = false;

        binding.targetValue.setOnClickListener(this);
        binding.pneumoTitle.setOnClickListener(this);
        binding.actualValue.setOnClickListener(this);
        binding.switchUnitBtn.setOnClickListener(this);

        binding.targetValue.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                float v8;
                try {
                    v8 =  NumberFormat.getInstance().parse(binding.targetValue.getText().toString()).floatValue();
                } catch (ParseException | java.text.ParseException e) { v8 =  0.f; }
                if (targetListener != null)
                    targetListener.onDone(v8);
            }
            return false;
        });
    }

    public void setTitleText(String title)
    {
        binding.pneumoTitle.setText(title);
    }

    public void setToggleListener(tgButtonListener ltn)
    {
        listener = ltn;
    }

    public void setOnDoneListener(targetEditDoneListener ltn) {targetListener = ltn;}

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
            if (inSettings) {
                performClick();
                return;
            }
            //TODO set focus on targetValue and capture input after input is done
            binding.targetValue.requestFocus();
            binding.targetValue.setSelection(binding.targetValue.getText().length());
            InputMethodManager imm = (InputMethodManager)  getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            return;
        }
        // TODO implement switching of displayed units
        if (view == binding.switchUnitBtn)
        {
            if (listener != null)
                listener.onToggle();
        }
    }

    public void setValueText(String value) {
        binding.actualValue.setText(value);
    }

    public void setInSettingsMode() {
        binding.targetValue.setInputType(InputType.TYPE_NULL);
        inSettings = true;
    }

    public void setTargetValue(String value)
    {
        binding.targetValue.setText(value);
    }

}