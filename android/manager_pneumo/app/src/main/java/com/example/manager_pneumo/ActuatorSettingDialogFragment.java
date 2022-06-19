package com.example.manager_pneumo;

import androidx.annotation.RequiresApi;
import androidx.core.net.ParseException;
import androidx.fragment.app.DialogFragment;

import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manager_pneumo.databinding.FragmentExecutionSensorSettingDialogBinding;

public class ActuatorSettingDialogFragment extends DialogFragment implements View.OnClickListener {

    private ActuatorViewModel mViewModel;
    private FragmentExecutionSensorSettingDialogBinding binding;
    public static final String PROP_ID = "PROP_ID";
    private int own_id;

    private int rcVal1Bar;
    private int rcVal2Bar;
    private int rcVal1Kgs;
    private int rcVal2Kgs;

    private int rcValue;
    private float v1bar;
    private float v1kgs;
    private float v2bar;
    private float v2kgs;

    private boolean reactUpwards;

    public static ActuatorSettingDialogFragment newInstance(int id) {
        Bundle sts = new Bundle();
        sts.putInt(PROP_ID, id);
        ActuatorSettingDialogFragment rv = new ActuatorSettingDialogFragment();
        rv.setArguments(sts);
        return rv;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        own_id = getArguments().getInt(PROP_ID);
        binding = FragmentExecutionSensorSettingDialogBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        mViewModel = new ViewModelProvider(requireActivity()).get(String.format("%d", 10 + own_id), ActuatorViewModel.class);

        mViewModel.getTitle().observe(getViewLifecycleOwner(), title -> binding.sensorNameText.setText(title));
        mViewModel.getRaw1bar().observe(getViewLifecycleOwner(), val -> rcVal1Bar = val);
        mViewModel.getRaw2bar().observe(getViewLifecycleOwner(), val -> rcVal2Bar = val);
        mViewModel.getRaw1Kg().observe(getViewLifecycleOwner(), val -> rcVal1Kgs = val);
        mViewModel.getRaw2Kg().observe(getViewLifecycleOwner(), val -> rcVal2Kgs = val);
        mViewModel.getLastRawReading().observe(getViewLifecycleOwner(), val -> rcValue = val);
        mViewModel.getVal1Bar().observe(getViewLifecycleOwner(), val -> {
            v1bar = val;
            binding.p1BarText.setText(String.format("%f", v1bar));
        });
        mViewModel.getVal2Bar().observe(getViewLifecycleOwner(), val -> {
            v2bar = val;
            binding.p2BarText.setText(String.format("%f", v2bar));
        });
        mViewModel.getVal1Kg().observe(getViewLifecycleOwner(), val -> {
            v1kgs = val;
            binding.p1KgsText.setText(String.format("%f", v1kgs));
        });
        mViewModel.getVal2Kg().observe(getViewLifecycleOwner(), val -> {
            v2kgs = val;
            binding.p2KgsText.setText(String.format("%f", v2kgs));
        });

        mViewModel.getReactionDirection().observe(getViewLifecycleOwner(), val ->
            {
                reactUpwards = val;
                toggleButtonsForDirection();
        });

        View.OnClickListener up_dwn_ltnr = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (view == binding.actDownwardsBtn) {
                    reactUpwards = false;
                }
                if (view == binding.actUpwardsBtn)
                {
                    reactUpwards = true;
                }
                toggleButtonsForDirection();
            }
        };

        binding.actUpwardsBtn.setOnClickListener(up_dwn_ltnr);
        binding.actDownwardsBtn.setOnClickListener(up_dwn_ltnr);

        View.OnClickListener p1_p2_listner = new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (view == binding.setP1Btn) {
                    rcVal1Bar = rcValue;
                    rcVal1Kgs = rcValue;
                    try {
                        v1bar =  NumberFormat.getInstance().parse(binding.p1BarText.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v1bar = 0.0f;
                    }
                    try {
                        v1kgs =  NumberFormat.getInstance().parse(binding.p1KgsText.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v1kgs = 0.0f;
                    }
                } else if (view == binding.setP2Btn) {
                    rcVal2Bar = rcValue;
                    rcVal2Kgs = rcValue;
                    try {
                        v2bar =  NumberFormat.getInstance().parse(binding.p2BarText.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v2bar = 0.f;
                    }
                    try {
                        v2kgs =  NumberFormat.getInstance().parse(binding.p2KgsText.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v2kgs = 0.0f;
                    }
                }
            }
        };

        binding.setP1Btn.setOnClickListener(p1_p2_listner);
        binding.setP2Btn.setOnClickListener(p1_p2_listner);

        binding.okBtn.setOnClickListener(this);
        binding.cancelBtn.setOnClickListener(this);
        return v;
    }

    private void toggleButtonsForDirection() {
        if (reactUpwards){
            binding.actDownwardsBtn.setBackgroundColor(Color.BLUE);
            binding.actUpwardsBtn.setBackgroundColor(Color.RED);
        } else {
            binding.actDownwardsBtn.setBackgroundColor(Color.RED);
            binding.actUpwardsBtn.setBackgroundColor(Color.BLUE);
        }
    }


    @Override
    public void onClick(View view) {
        if (view == binding.okBtn)
        {
            Bundle result = new Bundle();
            result.putString(pressureSettingsFragment.REQ_FEED_HDR_NAME, binding.sensorNameText.getText().toString());
            result.putInt(pressureSettingsFragment.REQ_FEED_HDR_Id, own_id);
            result.putInt(pressureSettingsFragment.REQ_FEED_HDR_RAW_V1, rcVal1Bar);
            result.putInt(pressureSettingsFragment.REQ_FEED_HDR_RAW_V2, rcVal2Bar);
            result.putFloat(pressureSettingsFragment.REQ_FEED_HDR_MSG_V1, v1bar);
            result.putFloat(pressureSettingsFragment.REQ_FEED_HDR_MSG_V2, v2bar);
            result.putInt(pressureSettingsFragment.REQ_FEED_HDR_RAW_KGS_V1, rcVal1Kgs);
            result.putInt(pressureSettingsFragment.REQ_FEED_HDR_RAW_KGS_V2, rcVal2Kgs);
            result.putFloat(pressureSettingsFragment.REQ_FEED_HDR_MSG_KGS_V1, v1kgs);
            result.putFloat(pressureSettingsFragment.REQ_FEED_HDR_MSG_KGS_V2, v2kgs);
            result.putBoolean(pressureSettingsFragment.REQ_FEED_REACT_UP, reactUpwards);

            getParentFragmentManager().setFragmentResult(pressureSettingsFragment.REQ_SENSOR_RESULT, result);
            dismiss();
        } else
            dismiss();
    }
}