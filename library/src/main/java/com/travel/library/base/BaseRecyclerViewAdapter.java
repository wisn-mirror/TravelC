package com.travel.library.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.travel.library.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lxs on 2016/8/8.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    public Context mContext;
    public LayoutInflater mInflater;
    public List<T> mDatas = new LinkedList<T>();
    public OnItemClickListener<T> mOnItemClickListener;
    public View emptyView;
    private int count = 0;
    private boolean addFooter = false;
    private boolean hasEmptyView = false;
    protected boolean scroll = false;


    public enum ITEM_TYPE {
        ITEM1,//emptyView
        ITEM2,//normal
        ITEM3//footerview
    }

    public BaseRecyclerViewAdapter(Context context, List<T> datas) {
        mContext = context;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
        if (datas != null) {
            mDatas = datas;
        }
    }

    public BaseRecyclerViewAdapter(Context context){
        mContext = context;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.size() > 0) {
            if (addFooter && position == mDatas.size()) {
                return ITEM_TYPE.ITEM3.ordinal();
            } else {
                return ITEM_TYPE.ITEM2.ordinal();
            }
        } else {
            return ITEM_TYPE.ITEM1.ordinal();
        }
    }

    public void addFooter(boolean addFooter) {
        this.addFooter = addFooter;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDatas.size() > 0) {
            if (addFooter) {
                count = mDatas.size() + 1;
            } else {
                count = mDatas.size();
            }
        } else {
            if (hasEmptyView) {
                count = 1;
            } else {
                count = 0;
            }
        }
        return count;
    }

    public void addItemLast(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addItemToTop(List<T> datas) {
        mDatas.add(0, (T) datas);
        notifyDataSetChanged();
    }

    public void addItemTop(List<T> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas) {
        mDatas.clear();
        if (null != datas) {
            mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getDatas() {
        return mDatas;
    }


    // 点击事件接口
    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T model);

        void onItemLongClick(View view, int position, T model);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal() && emptyView != null) {
            return new EmptyViewHolder(emptyView);
        } else if (viewType == ITEM_TYPE.ITEM3.ordinal()) {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.not_loading, parent, false));
        } else {
            return createViewHold(parent, viewType);
        }
    }

    private class FooterViewHolder extends BaseRecyclerViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class EmptyViewHolder extends BaseRecyclerViewHolder {
        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    if (mDatas != null && mDatas.size() > 0 && position < mDatas.size()) {
                        mOnItemClickListener.onItemClick(holder.itemView, position, mDatas.get(position));
                    }
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    if (mDatas != null && mDatas.size() > 0 && position < mDatas.size()) {
                        mOnItemClickListener.onItemLongClick(holder.itemView, position, mDatas.get(position));
                    }
                }
                return false;
            }
        });
        if(mDatas.size()>0){
            if(addFooter&&position!=mDatas.size()){
                showViewHolder(holder, position);
            }else if(!addFooter){
                showViewHolder(holder, position);
            }
        }
//        if (mDatas.size() > 0 && !addFooter) {
//            showViewHolder(holder, position);
//        }
    }

    protected abstract void showViewHolder(BaseRecyclerViewHolder holder, int position);

    /***
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType);


    public void setEmptyView(View emptyView) {
        hasEmptyView = true;
        this.emptyView = emptyView;
    }

    public void setScroll(boolean scroll){
        this.scroll = scroll;
    }

}
