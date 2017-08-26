package cn.icarowner.icarowner.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shizhefei.mvc.IDataAdapter;

import java.util.List;

/**
 * BaseRecyclerVMAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:11
 */
public abstract class BaseRecyclerVMAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDataAdapter<List<T>> {
    protected static final int TYPE_HEADER = 0;
    protected static final int TYPE_ITEM = 1;
    protected static final int TYPE_FOOTER = 2;

    protected Context context;
    protected List<T> list;

    public BaseRecyclerVMAdapter(Context context) {
        this.context = context;
    }

    @Override
    public List<T> getData() {
        return list;
    }

    @Override
    public boolean isEmpty() {
        return (null == list) || list.isEmpty();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void notifyDataChanged(List<T> data, boolean isRefresh) {
        list = data;

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (0 == position) {
            return (-1 == getHeaderLayout()) ? ((-1 == getFooterLayout()) ? TYPE_ITEM : TYPE_FOOTER) : TYPE_HEADER;
        }

        return (-1 == getFooterLayout()) ? TYPE_ITEM : ((position + 1 == getItemCount()) ? TYPE_FOOTER : TYPE_ITEM);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (TYPE_HEADER == viewType) {
            ViewDataBinding bindingHeader = DataBindingUtil.inflate(LayoutInflater.from(context), getHeaderLayout(), parent, false);
            BindingHeaderViewHolder headerViewHolder = new BindingHeaderViewHolder(bindingHeader.getRoot());
            headerViewHolder.setBinding(bindingHeader);
            return headerViewHolder;
        } else if (TYPE_FOOTER == viewType) {
            ViewDataBinding bindingFooter = DataBindingUtil.inflate(LayoutInflater.from(context), getFooterLayout(), parent, false);
            BindingFooterViewHolder footerViewHolder = new BindingFooterViewHolder(bindingFooter.getRoot());
            footerViewHolder.setBinding(bindingFooter);
            return footerViewHolder;
        } else {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayout(), parent, false);
            BindingViewHolder viewHolder = new BindingViewHolder(binding.getRoot());
            viewHolder.setBinding(binding);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BindingHeaderViewHolder) {
            BindingHeaderViewHolder bindingHeaderViewHolder = (BindingHeaderViewHolder) holder;
            bindingHeaderViewHolder.getBinding().setVariable(getHeaderVariable(), list.get(position));
            bindingHeaderViewHolder.getBinding().executePendingBindings();
        } else if (holder instanceof BindingFooterViewHolder) {
            BindingFooterViewHolder bindingFooterViewHolder = (BindingFooterViewHolder) holder;
            bindingFooterViewHolder.getBinding().setVariable(getFooterVariable(), list.get(position));
            bindingFooterViewHolder.getBinding().executePendingBindings();
        } else {
            BindingViewHolder bindingViewHolder = (BindingViewHolder) holder;
            Object data = list.get(position);
            bindingViewHolder.getBinding().setVariable(getVariable(), data);
            bindingViewHolder.getBinding().executePendingBindings();
            onRenderDataView(bindingViewHolder, position, data);
        }
    }

    public void onRenderDataView(BindingViewHolder bindingViewHolder, int position, Object viewModel) {

    }

    /**
     * Layout for this recycler view
     *
     * @return
     */
    public abstract int getLayout();

    /**
     * Variable of viem model binding in layout
     *
     * @return
     */
    public abstract int getVariable();

    /**
     * Layout for header of this recycler view
     *
     * @return
     */
    public int getHeaderLayout() {
        return -1;
    }

    /**
     * Variable of viem model for header binding in layout
     *
     * @return
     */
    public int getHeaderVariable() {
        return -1;
    }

    /**
     * Layout for footer of this recycler view
     *
     * @return
     */
    public int getFooterLayout() {
        return -1;
    }

    /**
     * Variable of viem model for footer binding in layout
     *
     * @return
     */
    public int getFooterVariable() {
        return -1;
    }

    /**
     * BindingViewHolder
     */
    static class BindingViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingViewHolder(View view) {
            super(view);
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }

    /**
     * BindingHeaderViewHolder
     */
    static class BindingHeaderViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHeaderViewHolder(View view) {
            super(view);
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }

    /**
     * BindingFooterViewHolder
     */
    static class BindingFooterViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingFooterViewHolder(View view) {
            super(view);
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return this.binding;
        }
    }
}
