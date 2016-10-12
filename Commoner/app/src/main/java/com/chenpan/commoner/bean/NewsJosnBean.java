package com.chenpan.commoner.bean;

import java.util.List;

/**
 * 作者：chenpan
 * 时间：2016/10/11 16:58
 * 邮箱：616707902@qq.com
 * 描述：
 */

public class NewsJosnBean {

    /**
     * postid : PHOT238JD000100A
     * hasCover : false
     * hasHead : 1
     * replyCount : 2758
     * hasImg : 1
     * digest :
     * hasIcon : false
     * docid : 9IG74V5H00963VRO_C35TDHMTbjlishidaiupdateDoc
     * title : 上海屹立20年鸽棚开拆 结构复杂似古堡
     * order : 1
     * priority : 350
     * lmodify : 2016-10-12 09:49:12
     * boardid : photoview_bbs
     * ads : [{"title":"北京西站北下沉广场将投用 5分钟出站","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/4b2221a187444cfd8e5d20c3cd20854120161012094801.jpeg","subtitle":"","url":"00AP0001|2204213"},{"title":"探秘世界第一人工洞体：816地下核工程","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/2274212123ca41bd9f3dab338d8fe2f320161012073441.jpeg","subtitle":"","url":"00AP0001|2204206"},{"title":"希腊开办接收中心 专门容纳未成年难民","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/eefa4ab6e3cd4e94be59dfcc947d3b9220161012101053.jpeg","subtitle":"","url":"00AO0001|2204260"},{"title":"意大利首都移民游行 抗议关闭移民中心","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/62d01e54d7114666b1c40914f114021420161012101303.jpeg","subtitle":"","url":"00AO0001|2204259"},{"title":"镜头记录器官捐献者生命的最后一程","tag":"photoset","imgsrc":"http://cms-bucket.nosdn.127.net/ded74179a5664b6a9f578fbb8bbf630020161011204021.jpeg","subtitle":"","url":"00AP0001|2204159"}]
     * photosetID : 00AP0001|2204269
     * template : normal1
     * votecount : 2607
     * skipID : 00AP0001|2204269
     * alias : Top News
     * skipType : photoset
     * cid : C1348646712614
     * hasAD : 1
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/fd469ddea39d49dd8e7944ae5d889bf920161012094453.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/b6ce635f2491492994900fc0db2fd27d20161012094454.jpeg"}]
     * source : 网易原创
     * ename : androidnews
     * imgsrc : http://cms-bucket.nosdn.127.net/0d42d6797055461f8b896a42a5cad8ea20161012094453.jpeg
     * tname : 头条
     * ptime : 2016-10-12 09:45:10
     */

    private List<T1348647909107Bean> T1348647909107;

    public List<T1348647909107Bean> getT1348647909107() {
        return T1348647909107;
    }

    public void setT1348647909107(List<T1348647909107Bean> T1348647909107) {
        this.T1348647909107 = T1348647909107;
    }

    public static class T1348647909107Bean {
        private String postid;
        private boolean hasCover;
        private int hasHead;
        private int replyCount;
        private int hasImg;
        private String digest;
        private boolean hasIcon;
        private String docid;
        private String title;
        private int order;
        private int priority;
        private String lmodify;
        private String boardid;
        private String photosetID;
        private String template;
        private int votecount;
        private String skipID;
        private String alias;
        private String skipType;
        private String cid;
        private int hasAD;
        private String source;
        private String ename;
        private String imgsrc;
        private String tname;
        private String ptime;
        /**
         * title : 北京西站北下沉广场将投用 5分钟出站
         * tag : photoset
         * imgsrc : http://cms-bucket.nosdn.127.net/4b2221a187444cfd8e5d20c3cd20854120161012094801.jpeg
         * subtitle :
         * url : 00AP0001|2204213
         */

        private List<AdsBean> ads;
        /**
         * imgsrc : http://cms-bucket.nosdn.127.net/fd469ddea39d49dd8e7944ae5d889bf920161012094453.jpeg
         */

        private List<ImgextraBean> imgextra;

        public String getPostid() {
            return postid;
        }

        public void setPostid(String postid) {
            this.postid = postid;
        }

        public boolean isHasCover() {
            return hasCover;
        }

        public void setHasCover(boolean hasCover) {
            this.hasCover = hasCover;
        }

        public int getHasHead() {
            return hasHead;
        }

        public void setHasHead(int hasHead) {
            this.hasHead = hasHead;
        }

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getHasImg() {
            return hasImg;
        }

        public void setHasImg(int hasImg) {
            this.hasImg = hasImg;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public boolean isHasIcon() {
            return hasIcon;
        }

        public void setHasIcon(boolean hasIcon) {
            this.hasIcon = hasIcon;
        }

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public String getLmodify() {
            return lmodify;
        }

        public void setLmodify(String lmodify) {
            this.lmodify = lmodify;
        }

        public String getBoardid() {
            return boardid;
        }

        public void setBoardid(String boardid) {
            this.boardid = boardid;
        }

        public String getPhotosetID() {
            return photosetID;
        }

        public void setPhotosetID(String photosetID) {
            this.photosetID = photosetID;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public int getVotecount() {
            return votecount;
        }

        public void setVotecount(int votecount) {
            this.votecount = votecount;
        }

        public String getSkipID() {
            return skipID;
        }

        public void setSkipID(String skipID) {
            this.skipID = skipID;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getSkipType() {
            return skipType;
        }

        public void setSkipType(String skipType) {
            this.skipType = skipType;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getHasAD() {
            return hasAD;
        }

        public void setHasAD(int hasAD) {
            this.hasAD = hasAD;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public List<ImgextraBean> getImgextra() {
            return imgextra;
        }

        public void setImgextra(List<ImgextraBean> imgextra) {
            this.imgextra = imgextra;
        }

        public static class AdsBean {
            private String title;
            private String tag;
            private String imgsrc;
            private String subtitle;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ImgextraBean {
            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }
        }
    }
}
