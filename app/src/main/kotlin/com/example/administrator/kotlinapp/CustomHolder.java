package com.example.administrator.kotlinapp;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/1/2.
 */

public class CustomHolder extends RecyclerView.ViewHolder {
    private QuickAdapter.OnItemClickListener mOnItemClickListener;
    private SparseArray<View> mViews = new SparseArray<>();
    private int commonLayoutId;
    public <T extends View> T findView(@IdRes @NonNull int id) {
       return findView(id, commonLayoutId);
    }

    public <T extends View> T findView(@IdRes @NonNull int id, @LayoutRes int rId) {
        if(getItemView(rId) == null) {
           return null;
        }
       return (T) getItemView(rId).findViewById(id);
    }

    public CustomHolder(final View itemView, QuickAdapter.OnItemClickListener onItemClickListener, @LayoutRes int rId, @LayoutRes int cId) {
        super(itemView);
        this.mOnItemClickListener = onItemClickListener;
        commonLayoutId = cId;
        mViews.put(rId, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null) {
                    /**
                     * 这里需要注意一下，如果有添加header的情况下，要把position - 1
                     */
                    mOnItemClickListener.onClick(itemView, getAdapterPosition());
                }
            }
        });
    }

    public View getItemView(@LayoutRes int rId) {
        return mViews.get(rId);
    }

    public void setText(@IdRes int id, String text) {
        setText(id, commonLayoutId, text);
    }

    public void setText(@IdRes int id, @LayoutRes int layoutId, String text) {
        TextView textView = (TextView) getItemView(layoutId).findViewById(id);
        textView.setText(text);
    }

    public void setImage(@IdRes int id, @LayoutRes int layoutId, @DrawableRes int res) {
        ImageView imageView = findView(id, layoutId);
        imageView.setImageResource(res);
    }
}
