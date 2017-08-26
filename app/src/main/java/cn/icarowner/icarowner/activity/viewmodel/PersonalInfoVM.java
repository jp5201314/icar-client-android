package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.io.File;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.view.PersonalInfoV;
import cn.icarowner.icarowner.entity.UserEntity;
import cn.icarowner.icarowner.httptask.GetPersonalInfoTask;
import cn.icarowner.icarowner.httptask.UpdateHeadImageTask;
import cn.icarowner.icarowner.httptask.UploadAvatarTask;
import cn.icarowner.icarowner.httptask.callback.Callback;
import cn.icarowner.icarowner.httptask.model.GetAvatarModel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * PersonalInfoVM
 * create by 崔婧
 * create at 2017/5/18 上午11:58
 */
public class PersonalInfoVM extends BaseVM {

    public final ObservableField<Boolean> isLoading = new ObservableField<>();
    public final ObservableField<String> toastMsg = new ObservableField<>();

    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> phoneNum = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableField<String> userBirth = new ObservableField<>();

    public static final String MODIFY_NAME = "2";
    public static final String MODIFY_GENDER = "3";
    public static final String MODIFY_BIRTHDAY = "4";

    private PersonalInfoV personalInfoV;
    public ToolBarTitleVM toolBarTitleVM;

    public PersonalInfoVM(PersonalInfoV personalInfoV) {
        isLoading.set(false);
        this.personalInfoV = personalInfoV;
        initToolBar();
        initPersonalInfo();
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        toolBarTitleVM = new ToolBarTitleVM("个人资料") {
            @Override
            public void onBackClick(View view) {
                super.onBackClick(view);
                personalInfoV.closePage();
            }
        };
    }

    /**
     * 设置圆形图片头像
     *
     * @param cricleImage
     * @param imageUrl
     */
    @BindingAdapter("cricleImageUrl")
    public static void setCricleImage(CircleImageView cricleImage, String imageUrl) {
        if (null == cricleImage) {
            return;
        }
        Picasso.with(cricleImage.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.default_head_shallow_gray)
                .error(R.drawable.default_head_shallow_gray)
                .into(cricleImage);
    }

    /**
     * 初始化个人信息
     */
    private void initPersonalInfo() {
        attemptGetPersonalInfo();
    }

    /**
     * 尝试获取个人信息
     */
    private void attemptGetPersonalInfo() {

        GetPersonalInfoTask getPersonalInfoTask = new GetPersonalInfoTask(this);
        getPersonalInfoTask.getPersonalInfo(new Callback<UserEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(UserEntity userEntity) {
                super.onDataSuccess(userEntity);
                imageUrl.set(userEntity.getAvatarUrl());
                phoneNum.set(userEntity.getMobile());
                setPersonalInfo(userEntity);
                UserSharedPreference.getInstance().setUser(userEntity);
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
     * 设置个人信息
     *
     * @param userEntity
     */
    public void setPersonalInfo(UserEntity userEntity) {
        if (TextUtils.isEmpty(userEntity.getName())) {
            name.set("");
        } else {
            name.set(null);
            name.set(userEntity.getName());
        }
        if (userEntity.getGender() == 1) {
            gender.set(null);
            gender.set("男");
        } else if (userEntity.getGender() == 2) {
            gender.set(null);
            gender.set("女");
        } else {
            gender.set("");
        }
        if (TextUtils.isEmpty(userEntity.getBirth())) {
            userBirth.set("");
        } else {
            userBirth.set(null);
            userBirth.set(userEntity.getBirth());
        }
    }

    /**
     * 尝试上传头像
     *
     * @param file
     */
    public void attemptUploadAvatar(File file) {
        UploadAvatarTask uploadAvatarTask = new UploadAvatarTask(this);
        uploadAvatarTask.uploadAvatar(file, new Callback<GetAvatarModel>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(GetAvatarModel getAvatarModel) {
                super.onDataSuccess(getAvatarModel);
                updateHeadImage(getAvatarModel);
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
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
     * 更新头像
     *
     * @param getAvatarModel
     */
    public void updateHeadImage(GetAvatarModel getAvatarModel) {
        UpdateHeadImageTask updateHeadImageTask = new UpdateHeadImageTask(this);
        updateHeadImageTask.updateHead(getAvatarModel.getImageUrl(), new Callback<UserEntity>() {
            @Override
            public void onStart() {
                super.onStart();
                isLoading.set(true);
            }

            @Override
            public void onDataSuccess(UserEntity userEntity) {
                super.onDataSuccess(userEntity);
                imageUrl.set(null);
                imageUrl.set(userEntity.getAvatarUrl());
                toastMsg.set("头像更新成功");
            }

            @Override
            public void onDataErrorOrFail(String msg) {
                super.onDataErrorOrFail(msg);
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
     * 选择图片upload
     *
     * @param view
     */
    public void changeImageClick(View view) {
        personalInfoV.onChangeImageClick();
    }

    /**
     * 更新姓名
     *
     * @param view
     */
    public void toModifyName(View view) {
        personalInfoV.jumpToModifyMyInfo(MODIFY_NAME);
    }

    /**
     * 更新性别
     *
     * @param view
     */
    public void toModifyGender(View view) {
        personalInfoV.jumpToModifyMyInfo(MODIFY_GENDER);
    }

    /**
     * 更新生日
     *
     * @param view
     */
    public void toModifyBirth(View view) {
        personalInfoV.jumpToModifyMyInfo(MODIFY_BIRTHDAY);
    }
}
