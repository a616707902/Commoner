package com.chenpan.skinlibrary.listener;

import android.view.View;

import com.chenpan.skinlibrary.attr.DynamicAttr;

import java.util.List;


/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:26
 */
public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
