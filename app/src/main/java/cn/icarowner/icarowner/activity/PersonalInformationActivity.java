package cn.icarowner.icarowner.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.tendcloud.tenddata.TCAgent;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.icarowner.icarowner.Constant;
import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.UserSharedPreference;
import cn.icarowner.icarowner.activity.view.PersonalInfoV;
import cn.icarowner.icarowner.activity.viewmodel.PersonalInfoVM;
import cn.icarowner.icarowner.databinding.ActivityPersonalInformationBinding;
import cn.icarowner.icarowner.entity.UserEntity;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * PersonalInformationActivity 个人资料
 * create by 崔婧
 * create at 2017/5/18 下午1:02
 */
public class PersonalInformationActivity extends BaseActivity implements PersonalInfoV {

    private File file;
    private UserEntity userEntity;
    private static final int REQUEST_IMAGE = 2;

    private ActivityPersonalInformationBinding binding;
    private PersonalInfoVM personalInfoVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStatusBarColor(R.color.color_black_0e1214);
        TCAgent.onPageStart(this, "个人资料");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_information);
        personalInfoVM = new PersonalInfoVM(this);
        binding.setPersonalInfo(personalInfoVM);
        this.setViewModel(personalInfoVM);
        setObservers();
    }


    /**
     * 选择上传图片
     */
    public void onChangeImageClick() {
        MultiImageSelector.create()
                .showCamera(true)
                .single()
                .start(this, REQUEST_IMAGE);
    }

    /**
     * Set observers for fields in view model
     */
    private void setObservers() {
        personalInfoVM.isLoading.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                if (personalInfoVM.isLoading.get()) {
                    showWaitingDialog(true);
                } else {
                    showWaitingDialog(false);
                }
            }
        });

        personalInfoVM.toastMsg.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                toast(personalInfoVM.toastMsg.get());
                personalInfoVM.toastMsg.set(null);
            }
        });
    }

    /**
     * 设置个人信息回调和选择上传图片回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            userEntity = UserSharedPreference.getInstance().getUser();
            personalInfoVM.setPersonalInfo(userEntity);
        }

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            List<String> headPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            file = new File(Constant.UPLOAD_FILES_DIR_PATH, System.currentTimeMillis() + ".jpg");
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Crop.of(Uri.fromFile(new File(headPath.get(0))), Uri.fromFile(file))
                    .asSquare().start(this, Crop.REQUEST_CROP);
        } else if (requestCode == REQUEST_IMAGE && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            personalInfoVM.attemptUploadAvatar(file);
        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消上传", Toast.LENGTH_SHORT).show();
            file.delete();
        }
    }

    /**
     * 跳转到修改信息页面
     *
     * @param type
     */
    @Override
    public void jumpToModifyMyInfo(String type) {
        Intent intent = new Intent(this, ModifyMyInfoActivity.class);
        intent.putExtra("type", type);
        startActivityForResult(intent, 1);
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
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(this, "个人资料");
    }

}
