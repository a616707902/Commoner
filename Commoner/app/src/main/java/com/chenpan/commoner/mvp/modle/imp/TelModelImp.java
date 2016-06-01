package com.chenpan.commoner.mvp.modle.imp;

import com.android.volley.VolleyError;
import com.chenpan.commoner.mvp.modle.TelModle;
import com.chenpan.commoner.network.VolleyInterface;
import com.chenpan.commoner.network.VolleyRequest;


import java.util.Map;

/**
 * modle的具体实现
 * <p/>
 * Created by Administrator on 2016/5/24.
 */
public class TelModelImp implements TelModle {
    String url = "http://apis.juhe.cn/mobile/get";
    String resultFormWork = "";

    @Override
    public void queryTel(final TelLisener telLisener, Map<String, String> map) {


//加载数据
        VolleyRequest.RequestPostString(url, "postTel", map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                resultFormWork = result;
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }

            @Override
            public void onMyError(VolleyError result) {
                resultFormWork = "数据加载出错";
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }
        });
        //String url,String tag, final Map<String,String> params,VolleyInterface volleyInterface
       /* VolleyRequest.RequestPostString(url, "postTel", map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                resultFormWork = result;
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }

            @Override
            public void onMyError(VolleyError result) {
                resultFormWork="数据加载出错";
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }
        });*/
       /* VolleyRequest.RequestGetStringXml(url, "postTel", map, new VolleyXmlInterface() {


            @Override
            public void onMySuccessXml(XmlPullParser result) {
                int eventCode = 0;//获取事件类型
                try {
                    eventCode = result.getEventType();
                    while(eventCode != XmlPullParser.END_DOCUMENT)  {
                        switch (eventCode){
                            case XmlPullParser.START_DOCUMENT: //开始读取XML文档
                                //实例化集合类
                                break;
                            case XmlPullParser.START_TAG://开始读取某个标签
                                if("person".equals(result.getName())) {
                                    //通过getName判断读到哪个标签，然后通过nextText()获取文本节点值，或通过getAttributeValue(i)获取属性节点值
                                }
                                break;
                            case XmlPullParser.END_TAG://读完一个Person，可以将其添加到集合类中
                                break;
                        }
                        result.next();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }

                resultFormWork = result.getInputEncoding();
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }

            @Override
            public void onMyError(VolleyError result) {
                resultFormWork="数据加载出错";
                if (telLisener != null) {
                    telLisener.queryComppelete(resultFormWork);
                }
            }
        });*/

    }
}
