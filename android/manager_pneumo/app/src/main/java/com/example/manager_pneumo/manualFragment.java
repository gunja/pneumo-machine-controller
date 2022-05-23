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

        final TextView textView = binding.pump.feedTitle;
        final EditText editT = binding.pump.feedValue;
        final Button btn = binding.left1.button2;
        final Button btn_r2 = binding.right1.button2;
        btn.setText("Ню");
        btn_r2.setText("Па");

        binding.pump.feedTitle.setText("Насос");
        binding.commonRail.feedTitle.setText("Общее давление");
        binding.receiver.feedTitle.setText("Ресивер");

        binding.pump.feedValue.setText("0.0 бар");
        binding.commonRail.feedValue.setText("10.0 бар");
        
        
        if (! desiredDisplayed) {
            binding.left1.targetValue.setText(""); binding.left2.targetValue.setText(""); binding.left3.targetValue.setText("");
            binding.left4.targetValue.setText(""); binding.left5.targetValue.setText("");binding.left6.targetValue.setText("");
            binding.left7.targetValue.setText("");binding.left8.targetValue.setText("");

            binding.left1.targetValue.setInputType(InputType.TYPE_NULL); binding.left2.targetValue.setInputType(InputType.TYPE_NULL); binding.left3.targetValue.setInputType(InputType.TYPE_NULL);
            binding.left4.targetValue.setInputType(InputType.TYPE_NULL); binding.left5.targetValue.setInputType(InputType.TYPE_NULL); binding.left6.targetValue.setInputType(InputType.TYPE_NULL);
            binding.left7.targetValue.setInputType(InputType.TYPE_NULL); binding.left8.targetValue.setInputType(InputType.TYPE_NULL);


            binding.right1.targetValue.setText(""); binding.right2.targetValue.setText(""); binding.right3.targetValue.setText("");
            binding.right4.targetValue.setText(""); binding.right5.targetValue.setText("");binding.right6.targetValue.setText("");
            binding.right7.targetValue.setText("");binding.right8.targetValue.setText("");

            binding.right1.targetValue.setInputType(InputType.TYPE_NULL); binding.right2.targetValue.setInputType(InputType.TYPE_NULL); binding.right3.targetValue.setInputType(InputType.TYPE_NULL);
            binding.right4.targetValue.setInputType(InputType.TYPE_NULL); binding.right5.targetValue.setInputType(InputType.TYPE_NULL); binding.right6.targetValue.setInputType(InputType.TYPE_NULL);
            binding.right7.targetValue.setInputType(InputType.TYPE_NULL); binding.right8.targetValue.setInputType(InputType.TYPE_NULL);
        }

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("manualFragment::onViewCreated  called");
    }

}
