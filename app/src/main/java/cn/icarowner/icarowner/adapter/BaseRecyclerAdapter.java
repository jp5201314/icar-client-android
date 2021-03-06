package cn.icarowner.icarowner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.shizhefei.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseRecyclerAdapter
 * create by 崔婧
 * create at 2017/5/18 下午1:11
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDataAdapter<List<T>> {
    protected List<T> list = new ArrayList<T>();
    protected Context context;
    protected ItemClickCallback itemClickCallback;

    public BaseRecyclerAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void notifyDataChanged(List<T> data, boolean isRefresh) {
        if (isRefresh) {
            list.clear();
        }
        if (null != data) list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public List<T> getData() {
        return list;
    }

    @Override
    public boolean isEmpty() {
        if (null == list) return true;
        return list.isEmpty();
    }

    protected abstract class OnItemClick extends OnItemClickListener<T> {

        public OnItemClick(int p, T m) {
            super(p, m);
        }

        @Override
        public void onItemClick(int position, T entity) {
            onItemClicked(position, entity);
            if (null != itemClickCallback) {
                itemClickCallback.onItemClicked(position, entity);
            }
        }

        protected abstract void onItemClicked(int position, T entity);
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public interface ItemClickCallback<T> {
        public abstract void onItemClicked(int position, T entity);
    }
}
