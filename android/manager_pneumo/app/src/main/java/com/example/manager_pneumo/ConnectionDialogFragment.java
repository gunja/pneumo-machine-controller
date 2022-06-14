package com.example.manager_pneumo;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.manager_pneumo.databinding.FragmentConnectionDialogBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConnectionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConnectionDialogFragment extends DialogFragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button exit;
    private Button repeat;
    FragmentConnectionDialogBinding binding;

    public ConnectionDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConnectionDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConnectionDialogFragment newInstance(String param1, String param2) {
        ConnectionDialogFragment fragment = new ConnectionDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentConnectionDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        binding.cancelConnectionBtn.setOnClickListener(this);
        binding.repeatBnt.setOnClickListener(this);
        setConnectingState();
    }

    public void setConnectingState()
    {
        binding.btnsLayout.setVisibility(View.GONE);
        binding.messageText.setText("Подключение");
    }

    @Override
    public void onClick(View view) {
        if (view == binding.cancelConnectionBtn)
        {
            Bundle result = new Bundle();
            getParentFragmentManager().setFragmentResult(MainActivity.EXIT_REQUESTED, result);
            dismiss();
        }
        if (view == binding.repeatBnt)
        {
            //TODO set return value to re-initiate connection
            setConnectingState();

            //dismiss();
            Bundle result = new Bundle();
            getParentFragmentManager().setFragmentResult(MainActivity.REPEAR_REQUESTED, result);
        }
    }

    public void enableButtons()
    {
        binding.messageText.setText("Подключение не удалось.");
        binding.btnsLayout.setVisibility(View.VISIBLE);
    }

    public void setConnectedMessage() {
        binding.messageText.setText("Подключено. Вычитываем");
    }
}