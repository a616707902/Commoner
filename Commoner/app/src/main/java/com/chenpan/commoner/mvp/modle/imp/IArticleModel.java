package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;

import com.android.volley.VolleyError;
import com.chenpan.commoner.mvp.modle.ArticleModel;
import com.chenpan.commoner.mvp.modle.MyCallback;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2016/6/20.
 */
public class IArticleModel implements ArticleModel {
    @Override
    public void parserArticle(Context context, String url, String tag, final MyCallback<String> mCallback) {
        VolleyRequest.RequestGetString(url, tag, new VolleyInterface() {
            @Override
            public void onMySuccess(String results) {
                Document result= Jsoup.parse(results, "ISO-8859-1");

                Element postContent = result.getElementsByClass("PostContent").get(0);
                Elements p = postContent.getElementsByTag("p");
                StringBuffer sb = new StringBuffer();
                for (Element e : p) {
                    sb.append("<p>").append(e.text()).append("</p>");
                }

                mCallback.onSccuss(sb.toString());
            }

            @Override
            public void onMyError(VolleyError result) {
                mCallback.onFaild();
            }
        });
    }
}
