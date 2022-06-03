package com.example.manager_pneumo;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.manager_pneumo.databinding.PointsSettingsBinding;

public class pointsSettingsFragment  extends Fragment {

    private PointsSettingsBinding binding;
    private int selectedD;
    private int selectedWD;

    private pointsSettingsFragment()
    {
        super();
        selectedD = 0;
        selectedWD = 0;
    }

    public static pointsSettingsFragment newInstance() {
        pointsSettingsFragment fragment = new pointsSettingsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        System.out.println("newInstance pointsSettingsFragment called with " );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("pointsSettingsFragment::onCreate called " );
    }

    private void unMarkDXButtons()
    {
        binding.d1Button.setBackgroundColor(Color.BLUE);
        binding.d2Button.setBackgroundColor(Color.BLUE);
        binding.d3Button.setBackgroundColor(Color.BLUE);
        binding.d4Button.setBackgroundColor(Color.BLUE);
    }

    private void unMarkWDButtons()
    {
        binding.fwdButton.setBackgroundColor(Color.BLUE);
        binding.bwdButton.setBackgroundColor(Color.BLUE);
    }

    private void setPointsInputDisabled()
    {
        binding.editT1.setEnabled(false);
        binding.editT2.setEnabled(false);
        binding.editT3.setEnabled(false);
        binding.editT4.setEnabled(false);
        binding.editT5.setEnabled(false);
        binding.editT6.setEnabled(false);
        binding.editT7.setEnabled(false);
        binding.editT8.setEnabled(false);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        System.out.println("manualFragment::onCreateView called " );

        binding = PointsSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setPointsInputDisabled();

        View.OnClickListener oncD = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final Button b = (Button) view;
                unMarkDXButtons();
                b.setBackgroundColor(Color.RED);
                if (b == binding.d1Button) {
                    selectedD = 1;
                } else if (b == binding.d2Button) {
                    selectedD = 2;
                } else if (b == binding.d3Button) {
                    selectedD = 3;
                } else if (b == binding.d4Button) {
                    selectedD = 4;
                }
                if (selectedD != 0 && selectedWD != 0) {
                    enablePointsInput();
                }
            }
        };
        binding.d1Button.setOnClickListener(oncD);
        binding.d2Button.setOnClickListener(oncD);
        binding.d3Button.setOnClickListener(oncD);
        binding.d4Button.setOnClickListener(oncD);

        View.OnClickListener oncDir = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final Button b = (Button) view;
                unMarkWDButtons();
                b.setBackgroundColor(Color.RED);
                if (b == binding.fwdButton) {
                    selectedWD = 1;
                } else if (b == binding.bwdButton) {
                    selectedWD = -1;
                }
                if (selectedD != 0 && selectedWD != 0) {
                    enablePointsInput();
                }
            }
        };
        binding.fwdButton.setOnClickListener(oncDir);
        binding.bwdButton.setOnClickListener(oncDir);

        return root;
    }
    private void enablePointsInput() {
        updateValues(selectedD, selectedWD);
        boolean gotReading56 = false;
        boolean gotReading78 = false;
        binding.editT1.setEnabled(true);
        binding.editT2.setEnabled(true);
        binding.editT3.setEnabled(true);
        binding.editT4.setEnabled(true);
        binding.editT5.setEnabled(gotReading56);
        binding.editT6.setEnabled(gotReading56);
        binding.editT7.setEnabled(gotReading78);
        binding.editT7.setEnabled(gotReading78);
     }

    private void updateValues(int selectedD, int selectedWD) {
        //TODO fill in values for certain sensor + direction
        binding.editT1.setText(new Integer(110 +  selectedWD * selectedD).toString());
        binding.editT2.setText(new Integer(120 +  selectedWD * selectedD).toString());
        binding.editT3.setText(new Integer(130 +  selectedWD * selectedD).toString());
        binding.editT4.setText(new Integer(140 +  selectedWD * selectedD).toString());
        binding.editT5.setText(new Integer(150 +  selectedWD * selectedD).toString());
        binding.editT6.setText(new Integer(160 +  selectedWD * selectedD).toString());
        binding.editT7.setText(new Integer(170 +  selectedWD * selectedD).toString());
        binding.editT8.setText(new Integer(180 +  selectedWD * selectedD).toString());

    }
}


