package com.example.manager_pneumo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

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

    public static final String REQ_FEED_HDR_RAW_KGS_V1 = "feedRaw1Kgs";
    public static final String REQ_FEED_HDR_MSG_KGS_V1 = "feedVal1Kgs";
    public static final String REQ_FEED_HDR_RAW_KGS_V2 = "feedRaw2Kgs";
    public static final String REQ_FEED_HDR_MSG_KGS_V2 = "feedVal2Kgs";

    MainActivity ma;

    FeedsViewModel[] fwms;

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

        fwms[0].getTitle().observe(getViewLifecycleOwner(), title ->
        {
            System.out.println("observer callback if FWM_0 of PressureSettingsFragmwnt");
            binding.globa1.setTitleText(title);
        });
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

        /*binding.globa1.setTitleText("бара");
        binding.globa2.setTitleText("замени");
        binding.globa3.setTitleText("Чё");

        binding.globa1.setValueText("0.0 бар");
        binding.globa2.setValueText("12.3 бар");
*/
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
                ActuatorSettingDialogFragment essdf = ActuatorSettingDialogFragment.newInstance(((ActuatorView)view).getOwnId());
                essdf.show(getChildFragmentManager(), "");

            }
        };

        binding.left1.setOnClickListener(sensONC);
        binding.right1.setOnClickListener(sensONC);
        binding.left2.setOnClickListener(sensONC);
        binding.right2.setOnClickListener(sensONC);
        binding.left3.setOnClickListener(sensONC);
        binding.right3.setOnClickListener(sensONC);
        binding.left4.setOnClickListener(sensONC);
        binding.right4.setOnClickListener(sensONC);



        return root;
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        switch (requestKey)
        {
            case REQ_FEED_HDR_RESULT:
                System.out.println("feeder header result exited");
                String hdrName = result.getString(REQ_FEED_HDR_NAME);
                int id = result.getInt(REQ_FEED_HDR_Id);
                result.getInt(REQ_FEED_HDR_RAW_V1);
                result.getFloat(REQ_FEED_HDR_MSG_V1);
                result.getInt(REQ_FEED_HDR_RAW_V2);
                result.getFloat(REQ_FEED_HDR_MSG_V2);
                //TODO отправить это всё на запись в контроллер
                fwms[id-1].setTitle(hdrName);
                ma.sendHeaderProperties(id, hdrName, result.getInt(REQ_FEED_HDR_RAW_V1),
                        result.getInt(REQ_FEED_HDR_RAW_V2),
                        result.getFloat(REQ_FEED_HDR_MSG_V1), result.getFloat(REQ_FEED_HDR_MSG_V2) );
                break;
            case REQ_SENSOR_RESULT:
                onActuatorDialogReturned(result);

                break;
        }

    }

    private void onActuatorDialogReturned(Bundle result) {
        String hdrName = result.getString(REQ_FEED_HDR_NAME);
        int id = result.getInt(REQ_FEED_HDR_Id);
        System.out.println("sensor result returned with name=" + result.getString(REQ_FEED_HDR_NAME) + "  for id=" + result.getInt(REQ_FEED_HDR_Id));
        ma.sendActuatorSettings(id, hdrName, result.getInt(REQ_FEED_HDR_RAW_V1),
                result.getInt(REQ_FEED_HDR_RAW_V2),
                result.getFloat(REQ_FEED_HDR_MSG_V1), result.getFloat(REQ_FEED_HDR_MSG_V2),
                result.getInt(REQ_FEED_HDR_RAW_KGS_V1),
                result.getInt(REQ_FEED_HDR_RAW_KGS_V2),
                result.getFloat(REQ_FEED_HDR_MSG_KGS_V1), result.getFloat(REQ_FEED_HDR_MSG_KGS_V2)
        );
        fwms[id-1].setTitle(hdrName);

    }

    public void setMA(MainActivity _ma) {
        ma = _ma;
    }
}
