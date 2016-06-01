package com.example.chenpan.library.model.interfaces;


import com.example.chenpan.library.holder.StringHolder;

/**
 * Created by mikepenz on 03.02.15.
 */
public interface Nameable<T> {
    T withName(String name);

    T withName(int nameRes);

    T withName(StringHolder name);

    StringHolder getName();
}
