package com.example.manager_pneumo;

import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.net.ParseException;
import androidx.fragment.app.DialogFragment;

import com.example.manager_pneumo.databinding.FragmentFeedsSettingsDialogBinding;

import java.nio.charset.StandardCharsets;

public class HeaderSetterDialogFragment extends DialogFragment {
    private static final String PROP_ID = "Id";
    private static final String TITLE_ID = "titleText";
    private int property_id;
    private String title;
    private FragmentFeedsSettingsDialogBinding binding;
    private int rcValue1;
    private int rcValue;
    private int rcValue2;
    private float v1;
    private float v2;

    private HeaderSetterDialogFragment()
    {
        System.out.println("HeaderSetterDialogFragment called");
    }

    public static HeaderSetterDialogFragment newInstance(int i, String title) {
        HeaderSetterDialogFragment rv = new HeaderSetterDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PROP_ID, i);
        bundle.putString(TITLE_ID, title);
        rv.setArguments(bundle);
        return rv;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("manualFragment::onCreate called " );
        property_id = getArguments().getInt(PROP_ID);
        title = getArguments().getString(TITLE_ID);

        //подвязаться к LiveData с этим id
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        System.out.println("manualFragment::onCreateView called ");


        binding = FragmentFeedsSettingsDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.feedNameText.setText(title);

        View.OnClickListener ok_cancel_onc = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                if (b == binding.okFeedHdrButton) {
                    // TODO send data to parent
                    System.out.println("OK button pressed");
                    Bundle result = new Bundle();
                    result.putString(pressureSettingsFragment.REQ_FEED_HDR_NAME, binding.feedNameText.getText().toString());
                    result.putInt(pressureSettingsFragment.REQ_FEED_HDR_Id, property_id);
                    result.putInt(pressureSettingsFragment.REQ_FEED_HDR_RAW_V1, rcValue1);
                    result.putInt(pressureSettingsFragment.REQ_FEED_HDR_RAW_V2, rcValue2);
                    result.putFloat(pressureSettingsFragment.REQ_FEED_HDR_MSG_V1, v1);
                    result.putFloat(pressureSettingsFragment.REQ_FEED_HDR_MSG_V2, v2);
                    getParentFragmentManager().setFragmentResult(pressureSettingsFragment.REQ_FEED_HDR_RESULT, result);
                    dismiss();
                } else if (b == binding.cancelFeedHdrBtn) {
                    System.out.println("Cancel called");
                    dismiss();
                }
            }
        };
        binding.cancelFeedHdrBtn.setOnClickListener(ok_cancel_onc);
        binding.okFeedHdrButton.setOnClickListener(ok_cancel_onc);

        View.OnClickListener set_value_onc = new View.OnClickListener()
        {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                if (binding.point1AcceptBtn == b)
                {
                    //values for point 1
                    rcValue1 = rcValue;
                    try {
                    v1 =  NumberFormat.getInstance().parse(binding.hdrCalibData1.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v1 =  0.0f;
                    }
                } else if (binding.point2AcceptBtn == b)
                {
                    // values fo point 2
                    rcValue2 = rcValue;
                    try {
                        v2 =  NumberFormat.getInstance().parse(binding.hdrCalibData2.getText().toString()).floatValue();
                    } catch (ParseException | java.text.ParseException e) {
                        v2 =  0.0f;
                    }
                }
            }
        };
        binding.point1AcceptBtn.setOnClickListener(set_value_onc);
        binding.point2AcceptBtn.setOnClickListener(set_value_onc);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                String val = s.toString();
                byte[] converted = val.getBytes(StandardCharsets.UTF_8);
                if (converted.length > 20) {
                    while (converted.length > 20) {
                        val = val.substring(0, val.length() - 1);
                        converted = val.getBytes(StandardCharsets.UTF_8);
                    }
                    s.replace(0, s.length(), val, 0, val.length());
                    binding.feedNameText.setError("Превышена допустимая длина записи");
                }
            }
        };
        binding.feedNameText.addTextChangedListener(afterTextChangedListener);

        return root;
    }
}
