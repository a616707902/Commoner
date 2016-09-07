package com.example.chenpan.library.skinmanager.listener;

import android.view.View;

import com.example.chenpan.library.skinmanager.entity.DynamicAttr;

import java.util.List;


public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
