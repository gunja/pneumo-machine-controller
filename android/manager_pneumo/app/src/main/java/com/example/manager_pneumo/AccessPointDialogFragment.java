package com.example.manager_pneumo;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manager_pneumo.databinding.FragmentAccessPointDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccessPointDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccessPointDialogFragment extends DialogFragment  implements View.OnClickListener {

    // TODO: Rename and change types of parameters
    private String cur_ap_name;
    private FragmentAccessPointDialogBinding binding;

    public AccessPointDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccessPointDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccessPointDialogFragment newInstance(String param1) {
        AccessPointDialogFragment fragment = new AccessPointDialogFragment();
        Bundle args = new Bundle();
        args.putString(SettingsFragment.AP_NAME_NOW, param1);
        fragment.setArguments(args);
        return fragment;
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            if(getArguments() != null)
            {
                cur_ap_name = getArguments().getString(SettingsFragment.AP_NAME_NOW);
            }
            getDialog().setTitle("Задать пароль");
            binding = FragmentAccessPointDialogBinding.inflate(inflater, null, false);
            binding.editTextTextPersonName.setText(cur_ap_name);

            View root = binding.getRoot();
            binding.button4.setOnClickListener(this);
            binding.button5.setOnClickListener(this);
            return root;
        }

    @Override
    public void onClick(View view) {
        System.out.println("got click");
        if(view == binding.button4) {
            //cancel pressed
            dismiss();
        }
        if (view == binding.button5)
        {
            // send resutls
            System.out.println("new AP name is set to " + binding.editTextTextPersonName.getText().toString());
            dismiss();
        }
    }
}