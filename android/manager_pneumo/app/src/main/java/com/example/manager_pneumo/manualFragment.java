package com.example.manager_pneumo;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.manager_pneumo.databinding.LayoutManualBinding;

public class manualFragment extends Fragment  {

    private static final String ARG_SECTION = "show_desired";
    private LayoutManualBinding binding;
    //private PageViewModel pageViewModelCHANGE;
    private Boolean desiredDisplayed;
    FeedsViewModel[] fwms;

    public static manualFragment newInstance(boolean displayDesired) {
        manualFragment fragment = new manualFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_SECTION, displayDesired);
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
        //pageViewModelCHANGE.setIndex(1);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        System.out.println("manualFragment::onCreateView called " );

        binding = LayoutManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

         final Button btn = binding.left1.button2;
        final Button btn_r2 = binding.right1.button2;
        btn.setText("Ню");
        btn_r2.setText("Па");

        binding.globa1.setTitleText("Насос");
        binding.globa2.setTitleText("Общее давление");
        binding.globa3.setTitleText("Ресивер");

        binding.globa1.setValueText("0.0 бар");
        binding.globa2.setValueText("10.0 бар");
        
        
        if (! desiredDisplayed) {
            binding.left1.targetValue.setText(""); binding.left2.targetValue.setText(""); binding.left3.targetValue.setText("");
            binding.left4.targetValue.setText("");

            binding.left1.targetValue.setInputType(InputType.TYPE_NULL); binding.left2.targetValue.setInputType(InputType.TYPE_NULL); binding.left3.targetValue.setInputType(InputType.TYPE_NULL);
            binding.left4.targetValue.setInputType(InputType.TYPE_NULL);


            binding.right1.targetValue.setText(""); binding.right2.targetValue.setText(""); binding.right3.targetValue.setText("");
            binding.right4.targetValue.setText("");

            binding.right1.targetValue.setInputType(InputType.TYPE_NULL); binding.right2.targetValue.setInputType(InputType.TYPE_NULL); binding.right3.targetValue.setInputType(InputType.TYPE_NULL);
            binding.right4.targetValue.setInputType(InputType.TYPE_NULL);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("manualFragment::onViewCreated  called");
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

        fwms[0].getTitle().observe(getViewLifecycleOwner(), title -> binding.globa1.setTitleText(title));
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
    }

}
