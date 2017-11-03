package com.lazyming.lazy;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * dialog
 * <p>
 * Created by lazyMing on 2017/11/1.
 */

public class LazyDialog extends BaseLazyDialog {

    private final String LISTENER = "convert_listener";

    private ConvertViewListener viewListener;

    public static LazyDialog init(){
        return new LazyDialog();
    }

    @Override
    public int intLayoutId() {
        return layoutId;
    }

    @Override
    public void convertView(LazyViewHolder viewHolder, BaseLazyDialog dialog) {
        if (viewListener != null) {
            viewListener.convertView(viewHolder, dialog);
        }
    }

    /**
     * 设置需要展示的布局资源
     *
     * @param layoutId
     * @return
     */
    public LazyDialog setLayoutId(@LayoutRes int layoutId) {
        this.layoutId = layoutId;
        return this;
    }

    /**
     * 设置接口
     *
     * @param convertListener
     * @return
     */
    public LazyDialog setConvertListener(ConvertViewListener convertListener) {
        if (convertListener != null) {
            this.viewListener = convertListener;
        }
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            viewListener = (ConvertViewListener) savedInstanceState.getSerializable(LISTENER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewListener != null) {
            outState.putSerializable(LISTENER, viewListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewListener = null;
    }
}
