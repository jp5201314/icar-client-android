package cn.icarowner.icarowner.activity.view;

import cn.icarowner.icarowner.entity.UserEntity;

/**
 * ModifyMyInfoV
 * create by 崔婧
 * create at 2017/5/18 上午11:39
 */
public interface ModifyMyInfoV extends BaseV {
    public void setResult();

    public void showGenderChoice(UserEntity userEntity);

    public void showBirthDatePickerDialog(UserEntity userEntity);
}
