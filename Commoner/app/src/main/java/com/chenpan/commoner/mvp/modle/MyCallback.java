package com.chenpan.commoner.mvp.modle;

public interface MyCallback<T> {
        public void onSccuss(T data);

        public void onFaild();
    }