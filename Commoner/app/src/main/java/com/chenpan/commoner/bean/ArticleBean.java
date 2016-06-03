package com.chenpan.commoner.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticleBean implements Parcelable {
    public String title;
    public String href;
    public String text;
    public String auth;
    public ArticleBean(){}
    protected ArticleBean(Parcel in) {
        title = in.readString();
        href = in.readString();
        text = in.readString();
        auth = in.readString();
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel in) {
            return new ArticleBean(in);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(href);
        parcel.writeString(text);
        parcel.writeString(auth);
    }
}
