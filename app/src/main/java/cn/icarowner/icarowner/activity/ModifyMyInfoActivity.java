package cn.icarowner.icarowner.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.ModifyMyInfoV;
import cn.icarowner.icarowner.activity.viewmodel.ModifyMyInfoVM;
import cn.icarowner.icarowner.databinding.ActivityModifyMyInfoBinding;
import cn.icarowner.icarowner.dialog.DialogCreater;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.event.RefreshPersonalInfoEvent;
import cn.icarowner.icarowner.utils.DateUtil;

/**
 * ModifyMyInfoActivity 修改资料
 * create by 崔婧
 * create at 2017/5/18 下午1:00
 */
public class ModifyMyInfoActivity extends BaseActivity implements ModifyMyInfoV {

    private ActivityModifyMyInfoBinding binding;
    private ModifyMyInfoVM modifyMyInfoVM;

    private DatePickerDialog datePickerDialog;
    private NormalListDialog normalListDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "修改个人资料");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_my_info);
        modifyMyInfoVM = new ModifyMyInfoVM(this, getIntent().getStringExtra("type"));
        binding.setModifyInfo(modifyMyInfoVM);
        this.setViewModel(modifyMyInfoVM);
        setObservers();
    }

    /**
     * Set observers for fields in view model
     */
    private void setObservers() {
        modifyMyInfoVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (modifyMyInfoVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        modifyMyInfoVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(modifyMyInfoVM.toastMsg.get());
                modifyMyInfoVM.toastMsg.set(null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "修改个人资料");
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void setResult() {
        setResult(RESULT_OK, new Intent());
        EventBus.getDefault().post(new RefreshPersonalInfoEvent());
        finish();
    }

    /**
     * 选择性别
     */
    public void showGenderChoice(final UserEntity userEntity) {
        if (null == normalListDialog) {
            normalListDialog = DialogCreater.createTextListDialog(
                    this, "选择性别",
                    new String[]{"男", "女"}, new OnOperItemClickL() {
                        @Override
                        public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                            switch (position) {
                                case 0:
                                    userEntity.setGender(1);
                                    binding.tvGender.setText("男");
                                    break;
                                case 1:
                                    userEntity.setGender(2);
                                    binding.tvGender.setText("女");
                                    break;
                            }
                            normalListDialog.dismiss();
                        }
                    });
        }
        normalListDialog.show();
    }

    /**
     * 生日日期选择对话框
     */
    public void showBirthDatePickerDialog(final UserEntity userEntity) {
        if (null == datePickerDialog) {
            final Calendar calendar = Calendar.getInstance();
            if (!TextUtils.isEmpty(userEntity.getBirth())) {
                calendar.setTime(DateUtil.getDate(userEntity.getBirth(), "yyyy-MM-dd"));
            }

            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar setCalendar = Calendar.getInstance();
                    setCalendar.set(year, monthOfYear, dayOfMonth, 0, 0);
                    if (setCalendar.after(Calendar.getInstance())) {
                        toast("出生日期错误");
                    } else {
                        userEntity.setBirth(DateUtil.format(setCalendar.getTime(), "yyyy-MM-dd"));
                        binding.tvBirth.setText(userEntity.getBirth());
                    }
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        datePickerDialog.show();
    }

}
