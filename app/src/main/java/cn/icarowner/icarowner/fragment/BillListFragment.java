package cn.icarowner.icarowner.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.icarowner.icarowner.R;
import cn.icarowner.icarowner.activity.view.BillListV;
import cn.icarowner.icarowner.activity.viewmodel.BillListVM;
import cn.icarowner.icarowner.databinding.FragmentBillListBinding;

/**
 * BillListFragment
 * create by 崔婧
 * create at 2017/5/18 下午1:26
 */
public class BillListFragment extends BaseFragment implements BillListV {

    FragmentBillListBinding binding;
    BillListVM billListVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill_list, container, false);
        billListVM = new BillListVM(this);
        binding.setBillList(billListVM);
        binding.swipe.setColorSchemeResources(R.color.color_green_3bb4bc);
        return binding.getRoot();
    }

    @Override
    public void closePage() {
        getActivity().finish();
    }
}
