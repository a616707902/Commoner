package com.example.chenpan.library.model.interfaces;

import android.graphics.drawable.Drawable;

import com.example.chenpan.library.holder.ImageHolder;
import com.mikepenz.iconics.typeface.IIcon;

/**
 * Created by mikepenz on 03.02.15.
 */
public interface Iconable<T> {
    T withIcon(Drawable icon);

    T withIcon(IIcon iicon);

    T withIcon(ImageHolder icon);

    ImageHolder getIcon();
}
