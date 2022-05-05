package com.faith.base.util;

import android.view.View;

/**
 * @ClassName: BaseUIUtil
 * @Description: java类作用描述
 * @Author: shieldzhang
 * @Date: 2022/5/3 11:15 上午
 */
public class BaseUIUtil {

    public static void bindClick(View.OnClickListener listener, View... views) {
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

}
