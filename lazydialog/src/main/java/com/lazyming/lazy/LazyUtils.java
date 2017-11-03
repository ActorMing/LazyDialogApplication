package com.lazyming.lazy;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 需要用到的一些方法
 * Created by lazyMing on 2017/10/31.
 */

public class LazyUtils {

    /**
     * dp转像素
     *
     * @param context 上下文对象
     * @param dpValue dpi
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dpValue + 0.5f);
    }


    /**
     * 根据传递过来的Context获取屏幕的宽度
     *
     * @param context 上下文对象
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

}
