package cn.icarowner.icarowner.activity.viewmodel.Handler;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.icarowner.icarowner.adapter.BaseRecyclerVMAdapter;

/**
 * RecyclerViewHandler
 * create by 崔婧
 * create at 2017/5/18 上午11:45
 */
public class RecyclerViewHandler {
    @BindingAdapter("layoutManager")
    public static void bindLayoutManager(RecyclerView rv, RecyclerView.LayoutManager layoutManager) {
        rv.setLayoutManager(layoutManager);
    }

    @BindingAdapter("dividerItemDecoration")
    public static void bindDividerItemDecoration(RecyclerView rv, DividerItemDecoration dividerItemDecoration) {
        rv.addItemDecoration(dividerItemDecoration);
    }

    @BindingAdapter("adapter")
    public static void bindAdapter(RecyclerView rv, BaseRecyclerVMAdapter adapter) {
        rv.setAdapter(adapter);
    }

    @BindingAdapter("items")
    public static void items(RecyclerView rv, List items) {
        ((BaseRecyclerVMAdapter) rv.getAdapter()).notifyDataChanged(items, true);
    }
}
