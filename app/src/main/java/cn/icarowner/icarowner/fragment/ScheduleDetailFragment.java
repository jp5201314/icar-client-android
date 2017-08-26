package cn.icarowner.icarowner.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.FindManagerActivity;
import cn.icarowner.icarowner.activity.view.ScheduleDetailV;
import cn.icarowner.icarowner.activity.viewmodel.ScheduleDetailVM;
import cn.icarowner.icarowner.databinding.FragmentScheduleDetailBinding;
import cn.icarowner.icarowner.entity.OvertimeCompensateEntity;
import cn.icarowner.icarowner.event.RefreshScheduleDetailEvent;

/**
 * ScheduleDetailFragment
 * create by 崔婧
 * create at 2017/5/18 下午1:27
 */
public class ScheduleDetailFragment extends BaseFragment implements ScheduleDetailV {

    private FragmentScheduleDetailBinding binding;
    private ScheduleDetailVM scheduleDetailVM;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_detail, container, false);
        scheduleDetailVM = new ScheduleDetailVM(this, getArguments().getString("orderId"), getArguments().getBoolean("isFromList"));
        binding.setScheduleDetail(scheduleDetailVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        timer = new Timer();
        this.initTimer();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        super.onDestroyViewLazy();
        EventBus.getDefault().unregister(this);
        closeTimer();
    }

    /**
     * 收到消息后刷进度
     *
     * @param refreshScheduleDetailEven 刷新进度详情的推送
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshSchedule(RefreshScheduleDetailEvent refreshScheduleDetailEven) {
        if (null != scheduleDetailVM && !scheduleDetailVM.isLoading.get()
                && scheduleDetailVM.serviceOrderId.equals(refreshScheduleDetailEven.getServiceOrderId())) {
            scheduleDetailVM.attemptGetServiceOrderDetail();
        }
    }

    @Override
    public void replaceByScheduleListFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ScheduleListFragment()).commitAllowingStateLoss();
    }

    @Override
    public void jumpToFindManagerPage(String orderId, String estimateTimeToTakingCar, String actOutFactoryAt, OvertimeCompensateEntity overtimeCompensate) {
        Intent intent = new Intent(getActivity(), FindManagerActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("estimatedTimeToPickUp", estimateTimeToTakingCar);
        intent.putExtra("actualTimeToPickUp", actOutFactoryAt);
        intent.putExtra("overtimeCompensate", overtimeCompensate);
        startActivity(intent);
    }

    /**
     * 判断是否显示找经理提示弹窗
     */
    @Override
    public void whetherShowGuideWindow() {
        if (-1 == UserSharedPreference.getInstance().getFindManagerTimFromSharedPreference()) {
            showFindManagerTip();
            UserSharedPreference.getInstance().putFindManagerTimeToSharedPreference(1);
        }
    }

    @Override
    public void closePage() {
        getActivity().finish();
    }

    /**
     * 显示/删除 找经理提示窗口的显示
     */
    private void showFindManagerTip() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_find_manager_tip, null);
        binding.flScheduleDetail.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.flScheduleDetail.removeView(v);
            }
        });
    }

    private void initTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (null != scheduleDetailVM) {
                    scheduleDetailVM.guessOvertimeCompensate();
                }
            }
        };
        timer.schedule(timerTask, 3000, 3000);
    }

    private void closeTimer() {
        if (null != timer) {
            timer.cancel();
        }

        if (null != timerTask) {
            timerTask.cancel();
        }
    }
}
