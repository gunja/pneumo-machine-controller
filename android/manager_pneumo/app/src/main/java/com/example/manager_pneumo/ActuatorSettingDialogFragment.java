package com.example.manager_pneumo;

import androidx.annotation.RequiresApi;
import androidx.core.net.ParseException;
import androidx.fragment.app.DialogFragment;

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

    //private ActuatorViewModel mViewModel;
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

        //mViewModel = new ViewModelProvider(requireActivity()).get(String.format("%d", own_id), ActuatorViewModel.class);

        //mViewModel.getTitle().observe(getViewLifecycleOwner(), title -> binding.sensorNameText.setText(title));

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
                        v1kgs =  NumberFormat.getInstance().parse(binding.p1KgsText.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v1bar =  0.0f;
                        v1kgs = 0.0f;
                    }

                } else if (view == binding.setP2Btn) {
                    rcVal2Bar = rcValue;
                    rcVal2Kgs = rcValue;
                    try {
                        v2bar =  NumberFormat.getInstance().parse(binding.p2BarText.getText().toString()).floatValue();
                        v2kgs =  NumberFormat.getInstance().parse(binding.p2KgsText.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v2bar =  0.0f;
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

            getParentFragmentManager().setFragmentResult(pressureSettingsFragment.REQ_SENSOR_RESULT, result);
            dismiss();
        } else
            dismiss();
    }
}