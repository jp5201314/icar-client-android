package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.view.ModifyMyInfoV;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.httptask.SaveModifyMyInfoTask;
import cn.icarowner.icarowner.httptask.callback.Callback;

/**
 * ModifyMyInfoVM
 * create by 崔婧
 * create at 2017/5/18 上午11:58
 */
public class ModifyMyInfoVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<Boolean> isShowName = new ObservableField<>();
    public final ObservableField<Boolean> isShowGender = new ObservableField<>();
    public final ObservableField<Boolean> isShowBirth = new ObservableField<>();

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableField<String> birth = new ObservableField<>();


    public static final String MODIFY_NAME = "2";
    public static final String MODIFY_GENDER = "3";
    public static final String MODIFY_BIRTHDAY = "4";
    public TextWatcher addTextChangedListener;

    private String modifyType;
    private UserEntity userEntity;

    private ModifyMyInfoV modifyMyInfoV;

    public ModifyMyInfoVM(ModifyMyInfoV modifyMyInfoV, String modifyType) {
        this.modifyMyInfoV = modifyMyInfoV;
        this.modifyType = modifyType;
        this.userEntity = UserSharedPreference.getInstance().getUser();
        initInfo();
        addTextChangedListener();
    }


    /**
     * 初始化信息
     */
    private void initInfo() {
        if (null == modifyType || TextUtils.isEmpty(modifyType)) {
            return;
        }
        switch (modifyType) {
            case MODIFY_NAME:
                title.set("姓名");
                isShowName.set(true);
                isShowGender.set(false);
                isShowBirth.set(false);
                if (TextUtils.isEmpty(userEntity.getName())) {
                    name.set("");
                } else {
                    name.set(userEntity.getName());
                }
                break;
            case MODIFY_GENDER:
                title.set("性别");
                isShowGender.set(true);
                isShowName.set(false);
                isShowBirth.set(false);
                if (userEntity.getGender() == 2) {
                    gender.set("女");
                } else if (userEntity.getGender() == 1) {
                    gender.set("男");
                } else {
                    gender.set("");
                }
                break;
            case MODIFY_BIRTHDAY:
                title.set("生日");
                isShowBirth.set(true);
                isShowGender.set(false);
                isShowName.set(false);
                if (TextUtils.isEmpty(userEntity.getBirth())) {
                    birth.set("");
                } else {
                    birth.set(userEntity.getBirth());
                }
                break;
        }
    }

    /**
     * 返回键关闭当前页面
     *
     * @param view
     */
    public void onBackClick(View view) {
        modifyMyInfoV.closePage();
    }


    /**
     * 保存修改信息
     *
     * @param view
     */
    public void onSaveInfoClick(View view) {
        if (!checkContent()) {
            return;
        }
        SaveModifyMyInfoTask saveModifyMyInfoTask = new SaveModifyMyInfoTask(this);
        saveModifyMyInfoTask.updateUserInfo(modifyType, userEntity.getName(), userEntity.getGender(), userEntity.getBirth(), new Callback<UserEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(UserEntity userEntity) {
                super.onDataSuccess(userEntity);
                toastMsg.set("更新成功");
                UserSharedPreference.getInstance().removeUser();
                UserSharedPreference.getInstance().setUser(userEntity);
                modifyMyInfoV.setResult();
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
                //toastMsg.set(msg);
            }

            @Override
            public void onFail(String msg) {
                super.onFail(msg);
                toastMsg.set(msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                isLoading.set(false);
            }
        });
    }

    /**
     * 清除姓名
     *
     * @param view
     */
    public void clearName(View view) {
        name.set("");
    }

    /**
     * 选择性别
     *
     * @param view
     */
    public void chooseGender(View view) {
        modifyMyInfoV.showGenderChoice(userEntity);
    }

    /**
     * 选择生日
     *
     * @param view
     */
    public void chooseBirth(View view) {
        modifyMyInfoV.showBirthDatePickerDialog(userEntity);
    }

    @BindingAdapter("textChangedListener")
    public static void setTextChangedListener(EditText editText, TextWatcher addTextChangedListener) {
        editText.addTextChangedListener(addTextChangedListener);
    }

    public void addTextChangedListener() {
        addTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                name.set(editable.toString());
            }
        };
    }

    /**
     * 检测输入内容
     */
    private boolean checkContent() {
        boolean isOk = false;
        switch (modifyType) {
            case MODIFY_NAME:
                if (userEntity == null) {
                    break;
                }
                if (TextUtils.isEmpty(name.get())) {
                    toastMsg.set("请输入姓名");
                } else {
                    userEntity.setName(name.get());
                    isOk = true;
                }
                break;
            case MODIFY_GENDER:
                if (userEntity == null) {
                    break;
                }
                if (1 == userEntity.getGender() || 2 == userEntity.getGender()) {
                    isOk = true;
                }
                break;
            case MODIFY_BIRTHDAY:
                if (userEntity == null) {
                    break;
                }
                if (TextUtils.isEmpty(userEntity.getBirth())) {
                    toastMsg.set("请设置生日");
                } else {
                    isOk = true;
                }
                break;
        }
        return isOk;
    }
}
