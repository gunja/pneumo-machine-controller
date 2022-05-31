package com.example.manager_pneumo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.manager_pneumo.databinding.FragmentSettingsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements ChangePasswordDialogFragment.EditNameDialogListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String PASS_NOW = "cur_pass";
    public static final String AP_NAME_NOW = "cur_ap_name";
    private FragmentSettingsBinding binding;
    private AppCompatActivity ma;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String cur_pass;
    private String cur_ap_name;

    public void setMA(AppCompatActivity _ma)
    {
        ma = _ma;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(AP_NAME_NOW, param1);
        args.putString(PASS_NOW, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cur_ap_name = getArguments().getString(AP_NAME_NOW);
            cur_pass = getArguments().getString(PASS_NOW);
        }

    }

    private void unMarkButtons()
    {
        binding.pressureButton.setBackgroundColor(Color.BLUE);
        binding.pointsButton.setBackgroundColor(Color.BLUE);
        binding.apButton.setBackgroundColor(Color.BLUE);
        binding.passButton.setBackgroundColor(Color.BLUE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if(getArguments() != null)
        {
            System.out.println("cur_P=" +  getArguments().getString(PASS_NOW));
        }
        View.OnClickListener onc = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Button b = (Button) view;
                System.out.println("Кнопка " + b.getText() +" надавлена. Снимаем подсведку с других");
                unMarkButtons();
                b.setBackgroundColor(Color.RED);

                if(view == binding.pressureButton)
                {
                    System.out.println("pressureButton");
                    LayoutInflater ltInflater = getLayoutInflater();
                    //binding.view.inflate(getContext(), R.layout.layout_manual, null);
                    //binding.pages.addView(viewPressure);
                    View child = getLayoutInflater().inflate(R.layout.layout_manual, null);
                    binding.view.removeAllViews();
                    binding.view.addView(child);

                } else if (view == binding.pointsButton)
                {
                    System.out.println("pointButton");
                    LayoutInflater ltInflater = getLayoutInflater();
                    View viewPoints = ltInflater.inflate(R.layout.points_settings, null, false);
                    //binding.view.inflate(getContext(), R.layout.points_settings, null);
                    binding.view.removeAllViews();
                    binding.view.addView(viewPoints);

                } else if (view == binding.apButton)
                {
                    System.out.println("Access point button");
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    AccessPointDialogFragment ap_name = AccessPointDialogFragment.newInstance(cur_ap_name);
                    ap_name.setTargetFragment(SettingsFragment.this, 300);
                    ap_name.show(fm, "");
                } else if(view == binding.passButton)
                {
                    System.out.println("password button");
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    System.out.println("fm = "+ fm);
                    ChangePasswordDialogFragment changePass = ChangePasswordDialogFragment.newInstance("Some Title", cur_pass);
                    changePass.setTargetFragment(SettingsFragment.this, 300);
                    changePass.show(fm, "fragment_edit_name");
                }


            }
        };

        binding.pressureButton.setOnClickListener(onc);
        binding.pointsButton.setOnClickListener(onc);
        binding.apButton.setOnClickListener(onc);
        binding.passButton.setOnClickListener(onc);

        return root;
    }

    @Override
    public void onDestroyView() {
        System.out.println("SettingsFragment::onDestroyView called ");
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        System.out.println("dialog returned new password" + inputText);
    }
}