package com.lazyming.lazy;

import java.io.Serializable;

/**
 * Created by lazyMing on 2017/11/1.
 */

public abstract class ConvertViewListener implements Serializable {

    protected abstract void convertView(LazyViewHolder holder, BaseLazyDialog dialog);
}
