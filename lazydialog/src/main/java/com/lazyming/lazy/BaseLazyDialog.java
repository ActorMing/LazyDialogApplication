package com.lazyming.lazy;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 基Dialog
 * <p>
 * Created by lazyMing on 2017/10/31.
 */

public abstract class BaseLazyDialog extends DialogFragment {

    /**
     * 防止旋转屏幕是否一些数据丢失,需要保存
     * <p>
     * 边距
     * 宽度
     * 高度
     * 暗屏度
     * 是否底部显示dialog
     * 点击外部是否可以dismiss
     * 动画样式
     * 布局id
     */
    private final static String MARGIN = "margin";
    private final static String WIDTH = "width";
    private final static String HEIGHT = "height";
    private final static String DIM_AMOUNT = "dim_amount";
    private final static String BOTTOM_SHOW = "bottom_show";
    private final static String OUT_DISMISS = "outer_dismiss";
    private final static String ANIM_STYLE = "anim_style";
    private final static String LAYOUT_ID = "layout_id";

    private int margin; // 左右边距
    private int height; // 高度
    private int width; // 宽度
    private float dimAmount = 0.5f; // 灰度
    private boolean showBottom; // 是否显示在底部
    private boolean outCancel; // 点击外部是否可以消失
    @StyleRes
    private int animStyle;
    @LayoutRes
    protected int layoutId;


    /*  布局资源的id **/
    public abstract int intLayoutId();

    /* 视图操作 **/
    public abstract void convertView(LazyViewHolder viewHolder, BaseLazyDialog dialog);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置dialog的样式
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.LazyDialogTheme);
        // 实例化布局资源
        layoutId = intLayoutId();

        // 判断是否有旋转屏幕保存下来的数据
        if (savedInstanceState != null) {
            /**
             * 边距 margin
             * 宽度 width
             * 高度 height
             * 暗屏度 dimAmount
             * 是否底部显示dialog showButton
             * 点击外部是否可以dismiss outCancel
             * 动画样式 animStyle
             * 布局id layoutId
             */
            margin = savedInstanceState.getInt(MARGIN);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            dimAmount = savedInstanceState.getFloat(DIM_AMOUNT);
            showBottom = savedInstanceState.getBoolean(BOTTOM_SHOW);
            outCancel = savedInstanceState.getBoolean(OUT_DISMISS);
            animStyle = savedInstanceState.getInt(ANIM_STYLE);
            layoutId = savedInstanceState.getInt(LAYOUT_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MARGIN, margin);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putFloat(DIM_AMOUNT, dimAmount);
        outState.putBoolean(BOTTOM_SHOW, showBottom);
        outState.putBoolean(OUT_DISMISS, outCancel);
        outState.putInt(ANIM_STYLE, animStyle);
        outState.putInt(LAYOUT_ID, layoutId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId, container, false);
        convertView(LazyViewHolder.create(view), this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // 每次启动都去初始化参数
        initParams();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        // 获取dialog的窗口对象
        Window window = getDialog().getWindow();
        if (window != null) {
            // 获取窗口对象的属性
            WindowManager.LayoutParams lp = window.getAttributes();
            // 调节灰色背景透明度[0-1]，默认0.5f
            lp.dimAmount = dimAmount;

            // 是否显示在底部
            if (showBottom) {
                window.setGravity(Gravity.BOTTOM);

                // 没有赋值动画,则赋值进出场动画
                if (animStyle == 0) {
                    animStyle = R.style.LazyDialogAnimation;
                }
            }

            // 设置宽度
            if (width == 0) { // 宽度为0,直接获取屏幕宽度减去左右边距
                int screenWidth = LazyUtils.getScreenWidth(getContext());
                int marginInt = 2 * LazyUtils.dp2px(getContext(), margin);
                lp.width = screenWidth - marginInt;
            } else if (width == -1) { // 宽度为-1,那么就内容包裹
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.width = LazyUtils.dp2px(getContext(), width);
            }

            // 设置高度
            if (height == 0) {
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            } else {
                lp.height = LazyUtils.dp2px(getContext(), height);
            }

            // 设置window的进出场动画
            window.setWindowAnimations(animStyle);
            // 设置重新赋值的属性
            window.setAttributes(lp);
        }
        // 点击外部是否可以dismiss
        getDialog().setCanceledOnTouchOutside(outCancel);
    }

    /**
     * 设置边距
     *
     * @param margin
     * @return
     */
    public BaseLazyDialog setMargin(int margin) {
        this.margin = margin;
        return this;
    }

    /**
     * 设置宽度
     *
     * @param width
     * @return
     */
    public BaseLazyDialog setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * 设置高度
     *
     * @param height
     * @return
     */
    public BaseLazyDialog setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 设置暗屏度
     *
     * @param dimAmount
     * @return
     */
    public BaseLazyDialog setDimAmount(float dimAmount) {
        if (dimAmount < 0) {
            this.dimAmount = 0f;
        } else if (dimAmount > 1) {
            this.dimAmount = 1f;
        } else {
            this.dimAmount = dimAmount;
        }
        return this;
    }

    /**
     * 是否显示在底部
     *
     * @param showBottom
     * @return
     */
    public BaseLazyDialog setShowBottom(boolean showBottom) {
        this.showBottom = showBottom;
        return this;
    }

    /**
     * 点击外部是否可以消失
     *
     * @param outCancel
     * @return
     */
    public BaseLazyDialog setOutCancel(boolean outCancel) {
        this.outCancel = outCancel;
        return this;
    }

    /**
     * 设置动画样式
     *
     * @param animStyle
     * @return
     */
    public BaseLazyDialog setAnimStyle(@StyleRes int animStyle) {
        this.animStyle = animStyle;
        return this;
    }

    /**
     * 显示
     *
     * @param manager
     * @return
     */
    public BaseLazyDialog show(FragmentManager manager) {
        // 开始事务对象
        FragmentTransaction ft = manager.beginTransaction();
        // 已经添加过了需要移除
        if (this.isAdded()) {
            ft.remove(this).commit();
        }
        ft.add(this, String.valueOf(System.currentTimeMillis()));
        ft.commitAllowingStateLoss();
        return this;
    }
}
