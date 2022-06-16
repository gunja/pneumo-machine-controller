package com.example.manager_pneumo;

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

    public static manualFragment newInstance(boolean displayDesired, int t) {
        manualFragment fragment = new manualFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_SECTION, displayDesired);
        bundle.putInt(ARG_TYPE, t);
        fragment.setArguments(bundle);
        System.out.println("newInstance __manualFragment___ called with " );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("manualFragment::onCreate called " );
        //pageViewModelCHANGE = new ViewModelProvider(this).get(PageViewModel.class);
        desiredDisplayed = getArguments().getBoolean(ARG_SECTION);
        mode = getArguments().getInt(ARG_TYPE);
        //pageViewModelCHANGE.setIndex(1);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        System.out.println("manualFragment::onCreateView called " );

        binding = LayoutManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        /*binding.globa1.setTitleText("Насос");
        binding.globa2.setTitleText("Общее давление");
        binding.globa3.setTitleText("Ресивер");

        binding.globa1.setValueText("0.0 бар");
        binding.globa2.setValueText("10.0 бар");
        */

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

        FeedsViewModel fwm1 = new ViewModelProvider(requireActivity()).get(String.format("%d", 1), FeedsViewModel.class);

        fwms = new FeedsViewModel[]{
                new ViewModelProvider(requireActivity()).get("1", FeedsViewModel.class),
                new ViewModelProvider(requireActivity()).get("2", FeedsViewModel.class),
                new ViewModelProvider(requireActivity()).get("3", FeedsViewModel.class),
                new ViewModelProvider(requireActivity()).get("4", FeedsViewModel.class),
                new ViewModelProvider(requireActivity()).get("5", FeedsViewModel.class),
                new ViewModelProvider(requireActivity()).get("6", FeedsViewModel.class),
                new ViewModelProvider(requireActivity()).get("7", FeedsViewModel.class),
                new ViewModelProvider(requireActivity()).get("8", FeedsViewModel.class)
        };

        fwm1.getTitle().observe(getViewLifecycleOwner(), title -> {
            System.out.println("callback setting  title for globa1. "+ title);
            binding.globa1.setTitleText(title);});

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
            new ViewModelProvider(requireActivity()).get("11", ActuatorViewModel.class),
            new ViewModelProvider(requireActivity()).get("12", ActuatorViewModel.class),
            new ViewModelProvider(requireActivity()).get("13", ActuatorViewModel.class),
            new ViewModelProvider(requireActivity()).get("14", ActuatorViewModel.class),
            new ViewModelProvider(requireActivity()).get("15", ActuatorViewModel.class),
            new ViewModelProvider(requireActivity()).get("16", ActuatorViewModel.class),
            new ViewModelProvider(requireActivity()).get("17", ActuatorViewModel.class),
            new ViewModelProvider(requireActivity()).get("18", ActuatorViewModel.class)
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

        ActuatorView.tgButtonListener tgL1 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[0].setShowInKg(! awms[0].showInKg.getValue());
            }
        };
        binding.left1.setToggleListener(tgL1);
        ActuatorView.tgButtonListener tgR1 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[1].setShowInKg(! awms[1].showInKg.getValue());
            }
        };

        binding.right1.setToggleListener(tgR1);
        ActuatorView.tgButtonListener tgL2 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[2].setShowInKg(! awms[2].showInKg.getValue());
            }
        };
        binding.left2.setToggleListener(tgL2);
        ActuatorView.tgButtonListener tgR2 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[3].setShowInKg(! awms[3].showInKg.getValue());
            }
        };

        binding.right2.setToggleListener(tgR2);
        ActuatorView.tgButtonListener tgL3 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[4].setShowInKg(! awms[4].showInKg.getValue());
            }
        };
        binding.left3.setToggleListener(tgL3);
        ActuatorView.tgButtonListener tgR3 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[5].setShowInKg(! awms[5].showInKg.getValue());
            }
        };
        binding.right3.setToggleListener(tgR3);
        ActuatorView.tgButtonListener tgL4 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[6].setShowInKg(! awms[6].showInKg.getValue());
            }
        };
        binding.left4.setToggleListener(tgL4);
        ActuatorView.tgButtonListener tgR4 = new ActuatorView.tgButtonListener() {
            @Override
            public void onToggle() {
                awms[7].setShowInKg(! awms[7].showInKg.getValue());
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

        if (mode == 2)
        {
            binding.autoBtns.setVisibility(View.VISIBLE);
            binding.autoDetailBtns.setVisibility(View.GONE);
        }

    }

}
