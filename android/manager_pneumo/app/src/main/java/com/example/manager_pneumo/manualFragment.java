package com.example.manager_pneumo;

import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.manager_pneumo.databinding.LayoutManualBinding;

public class manualFragment extends Fragment  {

    private static final String ARG_SECTION = "show_desired";
    private static final String ARG_TYPE = "tab_type";
    private LayoutManualBinding binding;
    //private PageViewModel pageViewModelCHANGE;
    private Boolean desiredDisplayed;
    private int mode;
    FeedsViewModel[] fwms;
    ActuatorViewModel[] awms;
    private int selectedCounter;
    private int on_off_manual_state;
    private MainActivity ma;
    AutoSensorSelectedViewModel ass;

    public static manualFragment newInstance(boolean displayDesired, int t) {
        manualFragment fragment = new manualFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_SECTION, displayDesired);
        bundle.putInt(ARG_TYPE, t);
        fragment.setArguments(bundle);
        System.out.println("newInstance __manualFragment___ called with " );
        return fragment;
    }

    public void setMA(MainActivity ma)
    {
        this.ma = ma;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("manualFragment::onCreate called " );
        //pageViewModelCHANGE = new ViewModelProvider(this).get(PageViewModel.class);
        desiredDisplayed = getArguments().getBoolean(ARG_SECTION);
        mode = getArguments().getInt(ARG_TYPE);
        selectedCounter = 0;
    }

    public int getMode() { return mode;}

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        System.out.println("manualFragment::onCreateView called " );

        binding = LayoutManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.left1.setShowDesired(desiredDisplayed);
        binding.left2.setShowDesired(desiredDisplayed);
        binding.left3.setShowDesired(desiredDisplayed);
        binding.left4.setShowDesired(desiredDisplayed);

        binding.right1.setShowDesired(desiredDisplayed);
        binding.right2.setShowDesired(desiredDisplayed);
        binding.right3.setShowDesired(desiredDisplayed);
        binding.right4.setShowDesired(desiredDisplayed);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("manualFragment::onViewCreated  called");
        ViewModelProvider vmp = new ViewModelProvider(requireActivity());
        Log.d("__manualFragment___", "создание fwms при requireActivity=" + requireActivity() + "  для VMP ="+ vmp );

        fwms = new FeedsViewModel[]{
                vmp.get("1", FeedsViewModel.class),
                vmp.get("2", FeedsViewModel.class),
                vmp.get("3", FeedsViewModel.class),
                vmp.get("4", FeedsViewModel.class),
                vmp.get("5", FeedsViewModel.class),
                vmp.get("6", FeedsViewModel.class),
                vmp.get("7", FeedsViewModel.class),
                vmp.get("8", FeedsViewModel.class)
        };

        fwms[0].getTitle().observe(getViewLifecycleOwner(), title -> {
            System.out.println("callback setting  title for globa1. "+ title);
            binding.globa1.setTitleText(title);});
        fwms[1].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa2.setTitleText(title));
        fwms[2].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa3.setTitleText(title));
        fwms[3].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa4.setTitleText(title));
        fwms[4].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa5.setTitleText(title));
        fwms[5].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa6.setTitleText(title));
        fwms[6].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa7.setTitleText(title));
        fwms[7].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa8.setTitleText(title));

        fwms[0].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa1.setValueText(value));
        fwms[1].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa2.setValueText(value));
        fwms[2].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa3.setValueText(value));
        fwms[3].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa4.setValueText(value));
        fwms[4].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa5.setValueText(value));
        fwms[5].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa6.setValueText(value));
        fwms[6].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa7.setValueText(value));
        fwms[7].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.globa8.setValueText(value));

        awms = new ActuatorViewModel[] {
                vmp.get("11", ActuatorViewModel.class),
                vmp.get("12", ActuatorViewModel.class),
                vmp.get("13", ActuatorViewModel.class),
                vmp.get("14", ActuatorViewModel.class),
                vmp.get("15", ActuatorViewModel.class),
                vmp.get("16", ActuatorViewModel.class),
                vmp.get("17", ActuatorViewModel.class),
                vmp.get("18", ActuatorViewModel.class)
        };

        awms[0].getTitle().observe(getViewLifecycleOwner(), title -> binding.left1.setTitleText(title));
        awms[1].getTitle().observe(getViewLifecycleOwner(), title -> binding.right1.setTitleText(title));
        awms[2].getTitle().observe(getViewLifecycleOwner(), title -> binding.left2.setTitleText(title));
        awms[3].getTitle().observe(getViewLifecycleOwner(), title -> binding.right2.setTitleText(title));
        awms[4].getTitle().observe(getViewLifecycleOwner(), title -> binding.left3.setTitleText(title));
        awms[5].getTitle().observe(getViewLifecycleOwner(), title -> binding.right3.setTitleText(title));
        awms[6].getTitle().observe(getViewLifecycleOwner(), title -> binding.left4.setTitleText(title));
        awms[7].getTitle().observe(getViewLifecycleOwner(), title -> binding.right4.setTitleText(title));

        awms[0].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.left1.setValueText(value));
        awms[1].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.right1.setValueText(value));
        awms[2].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.left2.setValueText(value));
        awms[3].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.right2.setValueText(value));
        awms[4].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.left3.setValueText(value));
        awms[5].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.right3.setValueText(value));
        awms[6].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.left4.setValueText(value));
        awms[7].getValueAsString().observe(getViewLifecycleOwner(), value -> binding.right4.setValueText(value));

        assignListenersOnMeasureUnitsToggle();

        if (desiredDisplayed)
        {
            awms[0].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.left1.setTargetValue(value));
            awms[1].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.right1.setTargetValue(value));
            awms[2].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.left2.setTargetValue(value));
            awms[3].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.right2.setTargetValue(value));
            awms[4].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.left3.setTargetValue(value));
            awms[5].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.right3.setTargetValue(value));
            awms[6].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.left4.setTargetValue(value));
            awms[7].getRQValueAsText().observe(getViewLifecycleOwner(), value -> binding.right4.setTargetValue(value));
        }

        ass = vmp.get("2001", AutoSensorSelectedViewModel.class);

        if (mode == 2)
        {
            binding.autoBtns.setVisibility(View.VISIBLE);
            binding.autoDetailBtns.setVisibility(View.GONE);
            ass.getVal().observe(getViewLifecycleOwner(), value -> switchSelectedCounter(value));
        }

        View.OnClickListener d1_d4_onc = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == binding.d1Btn){
                    selectedCounter = 1;
                    ma.setSelectedCounter(selectedCounter);
                    binding.autoBtns.setVisibility(View.GONE);
                }
                if(view == binding.d2Btn){
                    selectedCounter = 2;
                    ma.setSelectedCounter(selectedCounter);
                    binding.autoBtns.setVisibility(View.GONE);
                }
                if(view == binding.d3Btn){
                    selectedCounter = 3;
                    ma.setSelectedCounter(selectedCounter);
                    binding.autoBtns.setVisibility(View.GONE);
                }
                if(view == binding.d4Btn){
                    selectedCounter = 4;
                    ma.setSelectedCounter(selectedCounter);
                    binding.autoDetailBtns.setVisibility(View.VISIBLE);
                    binding.d4Btn.setBackgroundColor(Color.RED);
                    binding.manOffBtn.setEnabled(false);
                    binding.manOnBtn.setEnabled(true);
                }
                informAllViewOnSelectedCounter();
                ma.sendTabSelectedValue(2, selectedCounter);
            }
        };
        binding.d1Btn.setOnClickListener(d1_d4_onc);
        binding.d2Btn.setOnClickListener(d1_d4_onc);
        binding.d3Btn.setOnClickListener(d1_d4_onc);
        binding.d4Btn.setOnClickListener(d1_d4_onc);

        View.OnClickListener manPresentSensor_onc = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == binding.manOnBtn)
                {
                    binding.manOffBtn.setEnabled(true);
                    binding.manOnBtn.setEnabled(false);
                    ma.toggleManualDetailPresence(true);
                }
                if (view == binding.manOffBtn)
                {
                    binding.autoBtns.setVisibility(View.GONE);
                    ma.toggleManualDetailPresence(false);
                }
            }
        };
        binding.manOnBtn.setOnClickListener(manPresentSensor_onc);
        binding.manOffBtn.setOnClickListener(manPresentSensor_onc);


        if (mode == 1 || mode == 2) {
            assignListenersOnTargetDone();
        }

    }

    private void switchSelectedCounter(int val) {
        switch(val)
        {
            case 1:
                binding.d1Btn.performClick();
                break;
            case 2:
                binding.d2Btn.performClick();
                break;
            case 3:
                binding.d3Btn.performClick();
                break;
            case 4:
                binding.d4Btn.performClick();
                break;
        }
    }

    private void assignListenersOnTargetDone() {
        //TODO
        ActuatorView.targetEditDoneListener tgt1 = new ActuatorView.targetEditDoneListener() {
            @Override
            public void onDone(float val) {
                awms[0].setRequestedValue(mode, selectedCounter, val);
                ma.setRequestedValueInsideController(0, mode, selectedCounter, awms[0].getLatestRequestedValue(mode, selectedCounter));
            }
        };
        ActuatorView.targetEditDoneListener tgt2 = new ActuatorView.targetEditDoneListener() {  @Override public void onDone(float val) { awms[1].setRequestedValue(mode, selectedCounter, val);
            ma.setRequestedValueInsideController(1, mode, selectedCounter, awms[1].getLatestRequestedValue(mode, selectedCounter)); } };
        ActuatorView.targetEditDoneListener tgt3 = new ActuatorView.targetEditDoneListener() {  @Override public void onDone(float val) { awms[2].setRequestedValue(mode, selectedCounter, val);
            ma.setRequestedValueInsideController(2, mode, selectedCounter, awms[2].getLatestRequestedValue(mode, selectedCounter));} };
        ActuatorView.targetEditDoneListener tgt4 = new ActuatorView.targetEditDoneListener() {  @Override public void onDone(float val) { awms[3].setRequestedValue(mode, selectedCounter, val);
            ma.setRequestedValueInsideController(3, mode, selectedCounter, awms[3].getLatestRequestedValue(mode, selectedCounter));} };
        ActuatorView.targetEditDoneListener tgt5 = new ActuatorView.targetEditDoneListener() {  @Override public void onDone(float val) { awms[4].setRequestedValue(mode, selectedCounter, val);
            ma.setRequestedValueInsideController(4, mode, selectedCounter, awms[4].getLatestRequestedValue(mode, selectedCounter));} };
        ActuatorView.targetEditDoneListener tgt6 = new ActuatorView.targetEditDoneListener() {  @Override public void onDone(float val) { awms[5].setRequestedValue(mode, selectedCounter, val);
            ma.setRequestedValueInsideController(5, mode, selectedCounter, awms[5].getLatestRequestedValue(mode, selectedCounter));} };
        ActuatorView.targetEditDoneListener tgt7 = new ActuatorView.targetEditDoneListener() {  @Override public void onDone(float val) { awms[6].setRequestedValue(mode, selectedCounter, val);
            ma.setRequestedValueInsideController(6, mode, selectedCounter, awms[6].getLatestRequestedValue(mode, selectedCounter));} };
        ActuatorView.targetEditDoneListener tgt8 = new ActuatorView.targetEditDoneListener() {  @Override public void onDone(float val) { awms[7].setRequestedValue(mode, selectedCounter, val);
            ma.setRequestedValueInsideController(7, mode, selectedCounter, awms[7].getLatestRequestedValue(mode, selectedCounter));} };

        binding.left1.setOnDoneListener(tgt1);
        binding.right1.setOnDoneListener(tgt2);
        binding.left2.setOnDoneListener(tgt3);
        binding.right2.setOnDoneListener(tgt4);
        binding.left3.setOnDoneListener(tgt5);
        binding.right3.setOnDoneListener(tgt6);
        binding.left4.setOnDoneListener(tgt7);
        binding.right4.setOnDoneListener(tgt8);
    }

    private void assignListenersOnMeasureUnitsToggle() {
        ActuatorView.tgButtonListener tgL1 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[0].setShowInKg(! awms[0].getShowInKgValue());
            }
        };
        binding.left1.setToggleListener(tgL1);
        ActuatorView.tgButtonListener tgR1 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[1].setShowInKg(! awms[1].getShowInKgValue());
            }
        };

        binding.right1.setToggleListener(tgR1);
        ActuatorView.tgButtonListener tgL2 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[2].setShowInKg(! awms[2].getShowInKgValue());
            }
        };
        binding.left2.setToggleListener(tgL2);
        ActuatorView.tgButtonListener tgR2 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[3].setShowInKg(! awms[3].getShowInKgValue());
            }
        };

        binding.right2.setToggleListener(tgR2);
        ActuatorView.tgButtonListener tgL3 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[4].setShowInKg(! awms[4].getShowInKgValue());
            }
        };
        binding.left3.setToggleListener(tgL3);
        ActuatorView.tgButtonListener tgR3 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[5].setShowInKg(! awms[5].getShowInKgValue());
            }
        };
        binding.right3.setToggleListener(tgR3);
        ActuatorView.tgButtonListener tgL4 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[6].setShowInKg(! awms[6].getShowInKgValue());
            }
        };
        binding.left4.setToggleListener(tgL4);
        ActuatorView.tgButtonListener tgR4 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[7].setShowInKg(! awms[7].getShowInKgValue());
            }
        };
        binding.right4.setToggleListener(tgR4);

        awms[0].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.left1.setOptionKg(value));
        awms[1].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.right1.setOptionKg(value));
        awms[2].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.left2.setOptionKg(value));
        awms[3].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.right2.setOptionKg(value));
        awms[4].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.left3.setOptionKg(value));
        awms[5].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.right3.setOptionKg(value));
        awms[6].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.left4.setOptionKg(value));
        awms[7].getShowInKg().observe(getViewLifecycleOwner(), value -> binding.right4.setOptionKg(value));
    }

    private void informAllViewOnSelectedCounter() {
        //TODO implement this method
        for(int i = 0; i < 8; ++i)
            awms[i].setMode(selectedCounter);
    }

    public void resetButtonsHeader() {
        selectedCounter = 0;
        setCounterButtonBlue();
        binding.autoDetailBtns.setVisibility(View.GONE);
        setOnOffConuterBlue();
        binding.autoBtns.setVisibility(View.VISIBLE);
    }

    private void setOnOffConuterBlue() {
        binding.manOffBtn.setBackgroundColor(Color.BLUE);
        binding.manOnBtn.setBackgroundColor(Color.BLUE);
    }

    private void setCounterButtonBlue() {
        binding.d1Btn.setBackgroundColor(Color.BLUE);
        binding.d2Btn.setBackgroundColor(Color.BLUE);
        binding.d3Btn.setBackgroundColor(Color.BLUE);
        binding.d4Btn.setBackgroundColor(Color.BLUE);
    }
}
