package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;

import com.android.volley.VolleyError;
import com.chenpan.commoner.bean.PictureBeanBaiDu;
import com.chenpan.commoner.bean.PictureBeanOther;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.mvp.modle.PictureFramentModel;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public class IPictureFragmentOtherModel implements PictureFramentModel {


    @Override
    public void parserPictureBaidu(Context context, String url, String tag, MyCallback<List<PictureBeanBaiDu>> mCallback) {

    }

    @Override
    public void parserPictureOther(Context context, String url, String tag, final MyCallback<List<PictureBeanOther>> mCallback) {

        VolleyRequest.RequestGetString(url, tag, new VolleyInterface() {
            @Override
            public void onMySuccess(String s) {
                try {
                    Document result = Jsoup.parse(s, "ISO-8859-1");
                    Elements elements = result.select("div[class^=views-row views-row]");
                    List<PictureBeanOther> list = new ArrayList<PictureBeanOther>();
                    for (Element e : elements) {
                        PictureBeanOther mPictureBeanOther = new PictureBeanOther();
                        Elements chromeimg = e.getElementsByClass("chromeimg");
                        if (chromeimg == null || chromeimg.size() == 0) continue;
                        mPictureBeanOther.url = chromeimg.get(0).attr("src");
                        Elements xlistjus = e.getElementsByClass("xlistju");
                        if (xlistjus != null && xlistjus.size() > 0) {
                            mPictureBeanOther.content = xlistjus.get(0).text();
                        }
                        Elements xqusernpops = e.getElementsByClass("xqusernpop");
                        if (xqusernpops != null && xqusernpops.size() > 0) {
                            mPictureBeanOther.sender = xqusernpops.get(0).text();
                        }
                        list.add(mPictureBeanOther);
                    }
                    if (null == list) {
                        mCallback.onFaild();
                    } else {
                        mCallback.onSccuss(list);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mCallback.onFaild();
                }
            }

            @Override
            public void onMyError(VolleyError result) {
                mCallback.onFaild();

            }
        });
    }
}
