package com.example.manager_pneumo;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.manager_pneumo.databinding.ChangePasswordDialogBinding;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class ChangePasswordDialogFragment extends DialogFragment implements  OnClickListener {

    private EditText mEditText;
    private ChangePasswordDialogBinding binding;
    private String curPass;

    public ChangePasswordDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
        }

    public static String TAG = "PurchaseConfirmationDialog";


    public static ChangePasswordDialogFragment newInstance(String title, String existingPass)
    {
        ChangePasswordDialogFragment frag = new ChangePasswordDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString(SettingsFragment.PASS_NOW, existingPass);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        if(getArguments() != null)
        {
            curPass = getArguments().getString(SettingsFragment.PASS_NOW);
        }
        getDialog().setTitle("Задать пароль");
        binding = ChangePasswordDialogBinding.inflate(inflater, null, false);

        View root = binding.getRoot();
        binding.button.setOnClickListener(this);
        binding.button3.setOnClickListener(this);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.editTextTextPassword);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

    @Override
    public void onClick(View view) {
        System.out.println("got click on "+ view.getTransitionName());
        if (view == binding.button)
        {
            String pass = binding.editTextTextPassword.getText().toString();
            System.out.println("текущий пароль" + curPass + "  содержимое текстового поля: " + pass);
            System.out.println("equals returns " + (pass.equals(curPass)?" true": false));
            if(! pass.equals(curPass))
            {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "Текущий пароль не верен", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if( ! binding.editTextTextPassword2.getText().toString().equals(binding.editTextTextPassword3.getText().toString()))
            {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "Новый пароль не совпадает", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (binding.editTextTextPassword2.getText().toString().length() < 4)
            {
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        "Длина должна быть больше 4 символов", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            Bundle result = new Bundle();
            result.putString(SettingsFragment.REQ_KEY_PASS, binding.editTextTextPassword2.getText().toString());
            getParentFragmentManager().setFragmentResult(SettingsFragment.REQ_KEY_PASS, result);
            dismiss();

        }
        if (view == binding.button3)
        {
            dismiss();
        }
    }
}