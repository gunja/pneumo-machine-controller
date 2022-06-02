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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.manager_pneumo.databinding.LayoutManualBinding;

public class pressureSettingsFragment extends Fragment {
    private LayoutManualBinding binding;

    public static pressureSettingsFragment newInstance() {
        pressureSettingsFragment fragment = new pressureSettingsFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        System.out.println("newInstance __pressureSettingFragment___ called with " );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("pressureSettingsFragment::onCreate called " );
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

        binding.globa1.setTitleText("бара");
        binding.globa2.setTitleText("замени");
        binding.globa3.setTitleText("Чё");

        binding.globa1.setValueText("0.0 бар");
        binding.globa2.setValueText("12.3 бар");

        View.OnClickListener hdrONC = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                com.example.manager_pneumo.FeedsView vv = (com.example.manager_pneumo.FeedsView) view;
                if (view == binding.globa1)
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(1);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                } else if (view == binding.globa2) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(2);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                } else if (view == binding.globa3) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(3);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                } else if (view == binding.globa4) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(4);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                } else if (view == binding.globa5) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(5);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                } else if (view == binding.globa6) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(6);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                } else if (view == binding.globa7) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(7);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                }  else if (view == binding.globa8) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    HeaderSetterDialogFragment ap_name = HeaderSetterDialogFragment.newInstance(8);
                    ap_name.setTargetFragment(pressureSettingsFragment.this, 300);
                    ap_name.show(fm, "");
                }

            }
        };

        return root;
    }

}
