package com.example.manager_pneumo;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manager_pneumo.databinding.FragmentExecutionSensorSettingDialogBinding;

public class ExecutionSensorSettingDialogFragment extends DialogFragment implements View.OnClickListener {

    private ExecutionSensorSettingDialogViewModel mViewModel;
    private FragmentExecutionSensorSettingDialogBinding binding;
    public static final String PROP_ID = "PROP_ID";
    public static final String SIDE_ID = "SIDE_ID";
    public static final String TITLE_ID = "TITLE_ID";
    private int id;
    private String side;
    private String title;

    public static ExecutionSensorSettingDialogFragment newInstance(int id, String sd, String title) {
        Bundle sts = new Bundle();
        sts.putInt(PROP_ID, id);
        sts.putString(SIDE_ID, sd);
        sts.putString(TITLE_ID, title);
        ExecutionSensorSettingDialogFragment rv = new ExecutionSensorSettingDialogFragment();
        rv.setArguments(sts);
        return rv;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        id = getArguments().getInt(PROP_ID);
        side = getArguments().getString(SIDE_ID);
        binding = FragmentExecutionSensorSettingDialogBinding.inflate(inflater, container, false);
        View v = binding.getRoot();

        binding.sensorNameText.setText(getArguments().getString(TITLE_ID));

        View.OnClickListener ok_cancel_ocl = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

            }
        };
        binding.okBtn.setOnClickListener(this);
        binding.cancelBtn.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        dismiss();
    }
}