package cn.icarowner.icarowner.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ScheduleListV;
import cn.icarowner.icarowner.activity.viewmodel.ScheduleListVM;
import cn.icarowner.icarowner.databinding.FragmentScheduleListBinding;

/**
 * ScheduleListFragment
 * create by 崔婧
 * create at 2017/5/18 下午1:27
 */
public class ScheduleListFragment extends BaseFragment implements ScheduleListV {

    FragmentScheduleListBinding binding;
    ScheduleListVM scheduleListVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_list, container, false);
        scheduleListVM = new ScheduleListVM(this);
        binding.setScheduleList(scheduleListVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        return binding.getRoot();
    }

    @Override
    public void closePage() {
        getActivity().finish();
    }
}
