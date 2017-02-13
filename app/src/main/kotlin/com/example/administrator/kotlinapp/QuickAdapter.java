package com.example.administrator.kotlinapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by ct on 2017/1/1.
 * 通用适配器，支持header、footer
 * 支持不同类型item
 */

public abstract class QuickAdapter<T> extends Adapter<CustomHolder> {
    private int headerId = 0,footerId = 0;
    private int commonLayoutId;
    private List<T> mList;
    private Context mContext;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_FOOTER = 2;
    private GridLayoutManager mGridLayoutManager;
    private OnItemClickListener mOnItemClickListener;
    private int spanSize = 0;

    /**
     * 跨几行显示
     * @param spanSize
     */
    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    private SparseArray<Integer> rIds = new SparseArray<>();
    public QuickAdapter(List<T> list, Context context, @LayoutRes int layoutId, LayoutManager layoutManager) {
        this.mList = list;
        this.mContext = context;
        commonLayoutId = layoutId;
        if(layoutManager == null)
            return;
        //如果是GridLayoutManager,设置头布局充满一行
        if(layoutManager instanceof GridLayoutManager) {
            mGridLayoutManager = (GridLayoutManager) layoutManager;
            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if(type == TYPE_HEADER) {
                        return mGridLayoutManager.getSpanCount();
                    } else if(type == TYPE_FOOTER) {
                        return mGridLayoutManager.getSpanCount();
                    }
                    int layoutId = getItemViewId(type);
                    if (layoutId == getSpanLayoutId(position)) {
                            return spanSize == 0 ? mGridLayoutManager.getSpanCount() : spanSize ;
                    }
                    return 1;
                }
            });
        }
    }

    /**
     *通过type返回layoutId，跨列显示
     * @param position
     * @return
     */
    public @LayoutRes
    int getSpanLayoutId(int position) {
        return -1;
    }

    public void addHeader(@LayoutRes @NonNull int rId) {
        headerId = rId;
    }

    public void addFoofer(@LayoutRes @NonNull int rId) {
        footerId = rId;
    }

    public void updateView(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if(headerId > 0 && footerId > 0) {
            return mList.size() + 2;
        } else {
            if(headerId > 0) {
                return mList.size() + 1;
            } else if(footerId > 0) {
                return mList.size() + 1;
            } else {
                return mList.size();
            }
        }
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        int layoutId = 0;
        if(getItemViewId(viewType) > 0) {
            //有多种类型的item
            view = LayoutInflater.from(mContext).inflate(getItemViewId(viewType), parent, false);
            layoutId = getItemViewId(viewType);
        } else {
            //只有一种item
            view = LayoutInflater.from(mContext).inflate(commonLayoutId, parent, false);
            layoutId = commonLayoutId;
        }
        //有header
        if(viewType == TYPE_HEADER && headerId > 0) {
            view = LayoutInflater.from(mContext).inflate(headerId, parent, false);
            layoutId = headerId;
        }
        //有footer
        if (viewType == TYPE_FOOTER && footerId > 0) {
            view = LayoutInflater.from(mContext).inflate(footerId, parent, false);
            layoutId = footerId;
        }
        rIds.put(viewType, layoutId);
        if(mOnItemClickListener != null){
            return new CustomHolder(view, mOnItemClickListener, layoutId, commonLayoutId);
        } else {
            return new CustomHolder(view, null, layoutId, commonLayoutId);
        }
    }

    /**
     * 调用绑定数据的方法
     * @param holder
     * @param position
     */
    private void executeBindData(CustomHolder holder, int position) {
        if(headerId > 0 && footerId > 0) {//当header和footer同时存在
            if(position > 0) {
                if(position <= mList.size()) {
                    onBindData(holder, position - 1, mList.get(position - 1));
                } else {
                    onBindFooter(holder.getItemView(footerId));
                }
            } else {
                onBindHeader(holder.getItemView(headerId));
            }
        } else {
            if(headerId > 0) {//只有header
                if(position > 0) {
                    onBindData(holder, position - 1, mList.get(position - 1));
                    onBindHeader(holder.getItemView(headerId));
                }
            } else if(footerId > 0) {//只有footer
                if(position < mList.size()) {
                    onBindData(holder, position, mList.get(position));
                    onBindFooter(holder.getItemView(footerId));
                }
            } else{//以上都没有
                onBindData(holder, position, mList.get(position));
            }
        }
    }

    @Override
   final public void onBindViewHolder(CustomHolder holder, int position) {
        executeBindData(holder, position);
    }

    public abstract void onBindData(CustomHolder holder, int position, T t);
    //绑定headerview数据
    public void onBindHeader(View headerView) {

    }
    //绑定footerview数据
    public void onBindFooter(View footerView) {

    }

    @Override
    public int getItemViewType(int position) {
        if(headerId > 0 && position < 1) {
            return TYPE_HEADER;
        }else if (footerId > 0 && position == getItemCount() -1 ){
            return TYPE_FOOTER;
        } else {
            return getViewType(position);
        }
    }

    /**
     * 获取不同的viewtype
     * @param position
     * @return
     */
    public int getViewType(int position) {
        return commonLayoutId;
    }

    /**
     * 通过viewType返回对应的layoutId
     * @param viewType
     * @return layoutId
     */
    public int getItemViewId(int viewType) {
        if(viewType == TYPE_HEADER) {
            return headerId;
        } else if(viewType == TYPE_FOOTER) {
            return footerId;
        }
        return commonLayoutId;
    }

    public interface OnItemClickListener{
       public void onClick(View view, int position);
    }
}
