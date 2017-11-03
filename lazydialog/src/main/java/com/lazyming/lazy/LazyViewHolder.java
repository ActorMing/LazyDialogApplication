package com.lazyming.lazy;

import android.util.SparseArray;
import android.view.View;

/**
 * view工具类
 * Created by lazyMing on 2017/11/1.
 */

public class LazyViewHolder {

    private View convertView; // 转换的view
    private SparseArray<View> views; // 替代HashMap来进行保存 viewId和viewObject

    private LazyViewHolder(View view) {
        this.convertView = view;
        views = new SparseArray<>();
    }

    public static LazyViewHolder create(View view) {
        return new LazyViewHolder(view);
    }

    /**
     * 获取转换的view对象
     *
     * @return
     */
    public View getConvertView() {
        return this.convertView;
    }


    /**
     * 根据viewId获取view对象
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
