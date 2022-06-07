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
import androidx.fragment.app.FragmentResultListener;

import com.example.manager_pneumo.databinding.LayoutManualBinding;

public class pressureSettingsFragment extends Fragment  implements FragmentResultListener {
    private LayoutManualBinding binding;
    public static final String REQ_FEED_HDR_RESULT = "feedResultRequest";
    public static final String REQ_SENSOR_RESULT = "feedSensorRequest";
    public static final String REQ_FEED_HDR_NAME = "feedName";
    public static final String REQ_FEED_HDR_Id = "feedId";
    public static final String REQ_FEED_HDR_RAW_V1 = "feedRaw1";
    public static final String REQ_FEED_HDR_MSG_V1 = "feedVal1";
    public static final String REQ_FEED_HDR_RAW_V2 = "feedRaw2";
    public static final String REQ_FEED_HDR_MSG_V2 = "feedVal2";

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

        getChildFragmentManager().setFragmentResultListener(REQ_FEED_HDR_RESULT, this, this );
        getChildFragmentManager().setFragmentResultListener(REQ_SENSOR_RESULT, this, this );

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
                HeaderSetterDialogFragment ap_name = null;
                if (view == binding.globa1)
                {
                    ap_name = HeaderSetterDialogFragment.newInstance(1, vv.getTitleText());
                } else if (view == binding.globa2) {
                    ap_name = HeaderSetterDialogFragment.newInstance(2, vv.getTitleText());
                } else if (view == binding.globa3) {
                     ap_name = HeaderSetterDialogFragment.newInstance(3, vv.getTitleText());
                } else if (view == binding.globa4) {
                    ap_name = HeaderSetterDialogFragment.newInstance(4, vv.getTitleText());
                } else if (view == binding.globa5) {
                    ap_name = HeaderSetterDialogFragment.newInstance(5, vv.getTitleText());
                } else if (view == binding.globa6) {
                    ap_name = HeaderSetterDialogFragment.newInstance(6, vv.getTitleText());
                } else if (view == binding.globa7) {
                    ap_name = HeaderSetterDialogFragment.newInstance(7, vv.getTitleText());
                }  else if (view == binding.globa8) {
                    ap_name = HeaderSetterDialogFragment.newInstance(8, vv.getTitleText());
                }
                ap_name.show(getChildFragmentManager(), "");

            }
        };

        binding.globa1.setOnClickListener(hdrONC);
        binding.globa2.setOnClickListener(hdrONC);
        binding.globa3.setOnClickListener(hdrONC);
        binding.globa4.setOnClickListener(hdrONC);
        binding.globa5.setOnClickListener(hdrONC);
        binding.globa6.setOnClickListener(hdrONC);
        binding.globa7.setOnClickListener(hdrONC);
        binding.globa8.setOnClickListener(hdrONC);

        View.OnClickListener sensONC = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                ExecutionSensorSettingDialogFragment essdf = null;
                if (view == binding.left1.getRoot())
                {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(1,"l", binding.left1.pneumoTitle.getText().toString());
                } else if (view == binding.right1.getRoot()) {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(1,"r", binding.right1.pneumoTitle.getText().toString());
                } else if (view == binding.left2.getRoot()) {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(2,"l", binding.left2.pneumoTitle.getText().toString());
                }
                else if (view == binding.right2.getRoot()) {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(2,"r", binding.right2.pneumoTitle.getText().toString());
                }
                else if (view == binding.left3.getRoot()) {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(3,"l", binding.left3.pneumoTitle.getText().toString());
                } else if (view == binding.right3.getRoot()) {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(3,"r", binding.right3.pneumoTitle.getText().toString());
                }else if (view == binding.left4.getRoot()) {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(4,"l", binding.left4.pneumoTitle.getText().toString());
                } else if (view == binding.right4.getRoot()) {
                    essdf = ExecutionSensorSettingDialogFragment.newInstance(4,"r", binding.right4.pneumoTitle.getText().toString());
                }

                essdf.show(getChildFragmentManager(), "");

            }
        };

        binding.left1.getRoot().setOnClickListener(sensONC);
        binding.right1.getRoot().setOnClickListener(sensONC);
        binding.left2.getRoot().setOnClickListener(sensONC);
        binding.right2.getRoot().setOnClickListener(sensONC);
        binding.left3.getRoot().setOnClickListener(sensONC);
        binding.right3.getRoot().setOnClickListener(sensONC);
        binding.left4.getRoot().setOnClickListener(sensONC);
        binding.right4.getRoot().setOnClickListener(sensONC);



        return root;
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        switch (requestKey)
        {
            case REQ_FEED_HDR_RESULT:
                System.out.println("feeder header result exited");
                result.getString(REQ_FEED_HDR_NAME);
                result.getInt(REQ_FEED_HDR_Id);
                result.getInt(REQ_FEED_HDR_RAW_V1);
                result.getFloat(REQ_FEED_HDR_MSG_V1);
                result.getInt(REQ_FEED_HDR_RAW_V2);
                result.getFloat(REQ_FEED_HDR_MSG_V2);
                //TODO отправить это всё на запись в контроллер
                break;
            case REQ_SENSOR_RESULT:
                System.out.println("sensor result returned");
                break;
        }

    }
}
