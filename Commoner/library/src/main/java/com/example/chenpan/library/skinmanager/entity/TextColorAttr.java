package com.example.chenpan.library.skinmanager.entity;

import android.view.View;
import android.widget.TextView;

import com.example.chenpan.library.skinmanager.loader.SkinManager;
import com.example.chenpan.library.util.L;


public class TextColorAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof TextView){
			TextView tv = (TextView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				L.e("attr1", "TextColorAttr");
				tv.setTextColor(SkinManager.getInstance().convertToColorStateList(attrValueRefId));
			}
		}
	}
}
