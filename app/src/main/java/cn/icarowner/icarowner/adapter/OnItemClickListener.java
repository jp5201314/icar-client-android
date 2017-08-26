package cn.icarowner.icarowner.adapter;

import android.view.View;

/**
 * OnItemClickListener
 * create by 崔婧
 * create at 2017/5/18 下午1:13
 */
public abstract class OnItemClickListener<E> implements View.OnClickListener {
    private E model;
    private int position;

    public OnItemClickListener(int p, E m) {
        this.model = m;
        this.position = p;
    }


    @Override
    public void onClick(View view) {
        onItemClick(position, model);
    }

    public abstract void onItemClick(int position, E e);
}
