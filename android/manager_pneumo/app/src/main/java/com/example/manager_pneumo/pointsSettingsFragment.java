package com.example.manager_pneumo;

import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.net.ParseException;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.manager_pneumo.databinding.PointsSettingsBinding;

public class pointsSettingsFragment  extends Fragment {

    private PointsSettingsBinding binding;
    private int selectedD;
    private int selectedWD;
    private MainActivity ma;
    PointsSettingViewModel psvm;
    CounterGroupViewModel cgvm;

    Observer<Integer> obsrv1;
    Observer<Integer> obsrv2;
    Observer<Integer> obsrv3;
    Observer<Integer> obsrv4;
    Observer<Integer> obsrv5;
    Observer<Integer> obsrv6;
    Observer<Integer> obsrv7;
    Observer<Integer> obsrv8;

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

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPointsInputDisabled();
        psvm= new ViewModelProvider(requireActivity()).get("1000", PointsSettingViewModel.class);
        cgvm = new ViewModelProvider(requireActivity()).get("2010", CounterGroupViewModel.class);


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

        obsrv1 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {  binding.editT1.setText(String.format("%d", val));  }      };
        obsrv2 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) { binding.editT2.setText(String.format("%d", val)); }      };
        obsrv3 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) { binding.editT3.setText(String.format("%d", val)); }       };
        obsrv4 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) {  binding.editT4.setText(String.format("%d", val));  }      };
        obsrv5 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) { binding.editT5.setText(String.format("%d", val)); }      };
        obsrv6 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) { binding.editT6.setText(String.format("%d", val)); }       };
        obsrv7 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) { binding.editT7.setText(String.format("%d", val)); }      };
        obsrv8 = new Observer<Integer>() {
            @Override
            public void onChanged(Integer val) { binding.editT8.setText(String.format("%d", val)); }       };

        binding.editT1.setOnFocusChangeListener( (v, f) ->
                {
                    if (!f){
                        int v1;
                        try {
                            v1 =  NumberFormat.getInstance().parse(binding.editT1.getText().toString()).intValue();
                        } catch (ParseException | java.text.ParseException e) {
                            v1 =  0;
                        }
                        if(selectedWD > 0) {
                            psvm.setFWDValue(selectedD-1, 1, v1 );
                        } else {
                            psvm.setBWDValue(selectedD-1, 1, v1 );
                        }
                        ma.updateReactionPositionValue(selectedWD > 0, selectedD, 1, v1);
                    }
                });
        binding.editT2.setOnFocusChangeListener( (v, f) ->  {  if (!f){   int v2; try { v2 =  NumberFormat.getInstance().parse(binding.editT2.getText().toString()).intValue();
                } catch (ParseException | java.text.ParseException e) { v2 =  0; }  if(selectedWD > 0) { psvm.setFWDValue(selectedD-1, 2, v2); } else { psvm.setBWDValue(selectedD-1, 2, v2 );}
                ma.updateReactionPositionValue(selectedWD > 0, selectedD, 2, v2);
            }});
        binding.editT3.setOnFocusChangeListener( (v, f) ->  {  if (!f){   int v3; try { v3 =  NumberFormat.getInstance().parse(binding.editT3.getText().toString()).intValue();
        } catch (ParseException | java.text.ParseException e) { v3 =  0; }  if(selectedWD > 0) { psvm.setFWDValue(selectedD-1, 3, v3); } else { psvm.setBWDValue(selectedD-1, 3, v3 );}
            ma.updateReactionPositionValue(selectedWD > 0, selectedD, 3, v3);
        }});
        binding.editT4.setOnFocusChangeListener( (v, f) ->  {  if (!f){   int v4; try { v4 =  NumberFormat.getInstance().parse(binding.editT4.getText().toString()).intValue();
        } catch (ParseException | java.text.ParseException e) { v4 =  0; }  if(selectedWD > 0) { psvm.setFWDValue(selectedD-1, 4, v4); } else { psvm.setBWDValue(selectedD-1, 4, v4 );}
            ma.updateReactionPositionValue(selectedWD > 0, selectedD, 4, v4);
        }});
        binding.editT5.setOnFocusChangeListener( (v, f) ->  {  if (!f){   int v5; try { v5 =  NumberFormat.getInstance().parse(binding.editT5.getText().toString()).intValue();
        } catch (ParseException | java.text.ParseException e) { v5 =  0; }  if(selectedWD > 0) { psvm.setFWDValue(selectedD-1, 5, v5); } else { psvm.setBWDValue(selectedD-1, 5, v5 );}
            ma.updateReactionPositionValue(selectedWD > 0, selectedD, 5, v5);
        }});
        binding.editT6.setOnFocusChangeListener( (v, f) ->  {  if (!f){   int v6; try { v6 =  NumberFormat.getInstance().parse(binding.editT6.getText().toString()).intValue();
        } catch (ParseException | java.text.ParseException e) { v6 =  0; }  if(selectedWD > 0) { psvm.setFWDValue(selectedD-1, 6, v6); } else { psvm.setBWDValue(selectedD-1, 6, v6 );}
            ma.updateReactionPositionValue(selectedWD > 0, selectedD, 6, v6);
        }});
        binding.editT7.setOnFocusChangeListener( (v, f) ->  {  if (!f){   int v7; try { v7 =  NumberFormat.getInstance().parse(binding.editT7.getText().toString()).intValue();
        } catch (ParseException | java.text.ParseException e) { v7 =  0; }  if(selectedWD > 0) { psvm.setFWDValue(selectedD-1, 7, v7); } else { psvm.setBWDValue(selectedD-1, 7, v7 );}
            ma.updateReactionPositionValue(selectedWD > 0, selectedD, 7, v7);
        }});
        binding.editT8.setOnFocusChangeListener( (v, f) ->  {  if (!f){   int v8; try { v8 =  NumberFormat.getInstance().parse(binding.editT8.getText().toString()).intValue();
        } catch (ParseException | java.text.ParseException e) { v8 =  0; }  if(selectedWD > 0) { psvm.setFWDValue(selectedD-1, 8, v8); } else { psvm.setBWDValue(selectedD-1, 8, v8 );}
            ma.updateReactionPositionValue(selectedWD > 0, selectedD, 8, v8);
        }});

        binding.editT8.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                int v8;
                try {
                    v8 =  NumberFormat.getInstance().parse(binding.editT8.getText().toString()).intValue();
                } catch (ParseException | java.text.ParseException e) { v8 =  0; }
                if(selectedWD > 0) {
                    psvm.setFWDValue(selectedD-1, 8, v8); }
                else { psvm.setBWDValue(selectedD-1, 8, v8 );}
                ma.updateReactionPositionValue(selectedWD > 0, selectedD, 8, v8);
            }
            return false;
        });
    }

    private void enablePointsInput() {
        updateValues(selectedD, selectedWD);
        boolean gotReading56 = true;
        boolean gotReading78 = true;
        binding.editT1.setEnabled(true);
        binding.editT2.setEnabled(true);
        binding.editT3.setEnabled(true);
        binding.editT4.setEnabled(true);
        binding.editT5.setEnabled(gotReading56);
        binding.editT6.setEnabled(gotReading56);
        binding.editT7.setEnabled(gotReading78);
        binding.editT8.setEnabled(gotReading78);
     }

    private void updateValues(int selectedD, int selectedWD) {
        for(int i = 0; i < 4; ++i)
        {
            psvm.getFWD_X_Y(i, 1).removeObserver(obsrv1);
            psvm.getBWD_X_Y(i, 1).removeObserver(obsrv1);

            psvm.getFWD_X_Y(i, 2).removeObserver(obsrv2);
            psvm.getBWD_X_Y(i, 2).removeObserver(obsrv2);

            psvm.getFWD_X_Y(i, 3).removeObserver(obsrv3);
            psvm.getBWD_X_Y(i, 3).removeObserver(obsrv3);

            psvm.getFWD_X_Y(i, 4).removeObserver(obsrv4);
            psvm.getBWD_X_Y(i, 4).removeObserver(obsrv4);

            psvm.getFWD_X_Y(i, 5).removeObserver(obsrv5);
            psvm.getBWD_X_Y(i, 5).removeObserver(obsrv5);

            psvm.getFWD_X_Y(i, 6).removeObserver(obsrv6);
            psvm.getBWD_X_Y(i, 6).removeObserver(obsrv6);

            psvm.getFWD_X_Y(i, 7).removeObserver(obsrv7);
            psvm.getBWD_X_Y(i, 7).removeObserver(obsrv7);

            psvm.getFWD_X_Y(i, 8).removeObserver(obsrv8);
            psvm.getBWD_X_Y(i, 8).removeObserver(obsrv8);
        }

        if(selectedWD > 0)
        {
            psvm.getFWD_X_Y(selectedD - 1, 1).observe(getViewLifecycleOwner(), obsrv1);
            psvm.getFWD_X_Y(selectedD - 1, 2).observe(getViewLifecycleOwner(), obsrv2);
            psvm.getFWD_X_Y(selectedD - 1, 3).observe(getViewLifecycleOwner(), obsrv3);
            psvm.getFWD_X_Y(selectedD - 1, 4).observe(getViewLifecycleOwner(), obsrv4);
            psvm.getFWD_X_Y(selectedD - 1, 5).observe(getViewLifecycleOwner(), obsrv5);
            psvm.getFWD_X_Y(selectedD - 1, 6).observe(getViewLifecycleOwner(), obsrv6);
            psvm.getFWD_X_Y(selectedD - 1, 7).observe(getViewLifecycleOwner(), obsrv7);
            psvm.getFWD_X_Y(selectedD - 1, 8).observe(getViewLifecycleOwner(), obsrv8);

        } else {
            psvm.getBWD_X_Y(selectedD - 1, 1).observe(getViewLifecycleOwner(), obsrv1);
            psvm.getBWD_X_Y(selectedD - 1, 2).observe(getViewLifecycleOwner(), obsrv2);
            psvm.getBWD_X_Y(selectedD - 1, 3).observe(getViewLifecycleOwner(), obsrv3);
            psvm.getBWD_X_Y(selectedD - 1, 4).observe(getViewLifecycleOwner(), obsrv4);
            psvm.getBWD_X_Y(selectedD - 1, 5).observe(getViewLifecycleOwner(), obsrv5);
            psvm.getBWD_X_Y(selectedD - 1, 6).observe(getViewLifecycleOwner(), obsrv6);
            psvm.getBWD_X_Y(selectedD - 1, 7).observe(getViewLifecycleOwner(), obsrv7);
            psvm.getBWD_X_Y(selectedD - 1, 8).observe(getViewLifecycleOwner(), obsrv8);
        }
    }

    public void setMA(MainActivity _ma) {
        ma = _ma;
    }
}


