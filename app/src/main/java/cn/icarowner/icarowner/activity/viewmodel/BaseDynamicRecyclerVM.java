package cn.icarowner.icarowner.activity.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.icarowner.icarowner.adapter.BaseRecyclerVMAdapter;
import cn.icarowner.icarowner.httptask.callback.Callback;

/**
 * BaseDynamicRecyclerVM
 * create by 崔婧
 * create at 2017/5/18 上午11:51
 */
public abstract class BaseDynamicRecyclerVM<M> extends BaseVM {
    public final ObservableField<String> toastMsg = new ObservableField<>();
    public final ObservableField<Boolean> isRefreshing = new ObservableField<>();
    public final ObservableField<Boolean> isLoading = new ObservableField<>();

    public final ObservableList items = new ObservableArrayList<>();
    public RecyclerView.LayoutManager layoutManager;
    public BaseRecyclerVMAdapter adapter;
    public DividerItemDecoration dividerItemDecoration;
    public int page, maxPage;

    public BaseDynamicRecyclerVM(RecyclerView.LayoutManager layoutManager, BaseRecyclerVMAdapter adapter) {
        this.isRefreshing.set(false);
        this.isLoading.set(false);
        this.layoutManager = layoutManager;
        this.adapter = adapter;
    }

    public BaseDynamicRecyclerVM(RecyclerView.LayoutManager layoutManager, DividerItemDecoration dividerItemDecoration, BaseRecyclerVMAdapter adapter) {
        this.isRefreshing.set(false);
        this.isLoading.set(false);
        this.layoutManager = layoutManager;
        this.dividerItemDecoration = dividerItemDecoration;
        this.adapter = adapter;
    }

    public List getDataList() {
        int start = null == getHeaderVM() ? 0 : 1;
        int end = null == getFooterVM() ? items.size() : items.size() - 1;
        return items.subList(start, end);
    }

    public BaseVM getHeaderVM() {
        return null;
    }

    public BaseVM getFooterVM() {
        return null;
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            BaseDynamicRecyclerVM.this.onRefresh();
        }
    };

    public RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = (RecyclerView.LayoutManager) recyclerView.getLayoutManager();
            int lastVisibleItem = 0;
            if (layoutManager instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            } else {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
            int totalItemCount = layoutManager.getItemCount();
            // lastVisibleItem >= totalItemCount - 1 表示剩下1个item自动加载
            // dy>0 表示向下滑动
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                if (!isLoading.get() && hasMore()) onLoadMore(page + 1);
            }
        }
    };

    public boolean hasMore() {
        return this.page < this.maxPage;
    }

    public void setRefreshing(boolean isRefreshing) {
        this.isRefreshing.set(isRefreshing);
    }

    public void setLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void onRefresh() {
        load(1);
    }

    public void onLoadMore(int page) {
        load(page);
    }

    public abstract void load(int page);

    public Callback<M> getCallback(final int requestPage) {
        return new Callback<M>() {
            @Override
            public void onStart() {
                super.onStart();
                if (1 == requestPage) {
                    setRefreshing(true);
                } else {
                    setLoading(true);
                }
            }

            @Override
            public void onDataSuccess(M m) {
                super.onDataSuccess(m);
                page = requestPage;
                if (1 == requestPage) {
                    items.clear();
                    if (null != getHeaderVM()) {
                        items.add(getHeaderVM());
                    }
                    if (null != getFooterVM()) {
                        items.add(getFooterVM());
                    }
                }

                List dataList = BaseDynamicRecyclerVM.this.onDataSuccess(m);

                if (null != getFooterVM() && items.size() > 0) {
                    items.addAll(items.size() - 1, dataList);
                } else {
                    items.addAll(dataList);
                }
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
                if (1 == requestPage) {
                    setRefreshing(false);
                } else {
                    setLoading(false);
                }
            }
        };
    }

    public abstract List onDataSuccess(M m);
}