package com.chenpan.commoner.mvp.modle.imp;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.chenpan.commoner.bean.ArticleBean;
import com.chenpan.commoner.mvp.modle.ArticleFragmentModel;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import retrofit.http.Url;

/**
 * Created by Administrator on 2016/6/3.
 */
public class IArticleFragmentModel implements ArticleFragmentModel {

    @Override
    public void parserArticle(Context context, String url, String tag, final Callback<List<ArticleBean>> mCallback) {
        VolleyRequest.RequestGetString(url, tag, new VolleyInterface() {
            @Override
            public void onMySuccess(String results) {
                try {
                    StringReader sr = new StringReader(results);
                    InputSource is = new InputSource(sr);
                   String resultSs= URLDecoder.decode(results, "UTF-8");
                    Document result= Jsoup.parse(resultSs);
                    Entities.EscapeMode.base.getMap().clear();
                   // Document result = (Document) DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
                    Elements postHeads = result.getElementsByClass("PostHead");
                    Elements postContent1s = result.getElementsByClass("PostContent1");
                    List<ArticleBean> list = new ArrayList<ArticleBean>(postHeads.size());
                    for (int i = 0; i < postHeads.size(); i++) {
                        ArticleBean lz13 = new ArticleBean();
                        Element postHead = postHeads.get(i);
                        Element a = postHead.getElementsByTag("a").get(0);
                        lz13.href = a.attr("href");
                        lz13.title = a.text();
                        Element postContent1 = postContent1s.get(i);
                        lz13.text = postContent1.text().replace(lz13.title, "");
                        String[] split = lz13.text.split("\\s{2,}");
                        StringBuffer sb = new StringBuffer();
                        for (String s : split) {
                            if (TextUtils.isEmpty(s)) {
                                continue;
                            }
                            if (lz13.title.equals(s)) {
                                continue;
                            }
                            if (s.startsWith("文/")) {
                                lz13.auth = s;
                                continue;
                            }
                            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").append(s).append("<br/>");
                        }
                        int index = sb.lastIndexOf("<br/>");
                        if (index == -1) continue;
                        lz13.text = sb.substring(0, sb.lastIndexOf("<br/>") - 1) + "......";
                        list.add(lz13);
                    }
                    if (list != null && list.size() > 0) {
                        mCallback.onSccuss(list);
                    } else {
                        mCallback.onFaild();
                    }
                } catch (Exception e) {
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
