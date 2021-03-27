package com.phcbest.smartcitydemo.pojo;

import java.util.List;

public class SpecialNewsPojo {

    /**
     * total : 4
     * rows : [{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":22,"title":"中国与世卫并肩抗疫，体现责任与担当","content":" 本报讯（大连新闻传媒集团记者鹿道铭）昨日上午，由市政府外办组织成立的大连市外文公示语审核委员会举行专家委员聘任仪式暨座谈会，来自大连海事大学、大连民族大学、辽宁师范大学、大连工业大学、大连外国语大学、大连大学等高校的9名专家学者获聘为专家委员，他们将负责对我市主要街面、商铺及机场、火车站等重点涉外区域的英、日、韩公示语译审工作。","imgUrl":"/profile/zt.jpg","pressCategory":null,"isRecommend":0,"likeNumber":0,"viewsNumber":0,"userId":null,"pressStatus":null},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":23,"title":"旨在实现标本兼治、归口管理","content":"外文公示语是城市公共信息导向系统的组成部分，是一座城市的名片，彰显城市的文化内涵，对促进对外经济文化交流起着尤为重要的作用。\u201d据市政府外办工作人员介绍，近年来，我市各级政府和服务部门在外文公示语方面持续投入，充分体现了我市对来连外国友人安全与便利的关怀和关照。然而，在实际应用中，由于翻译水平参差不齐、译文质量缺乏管控，致使频遭吐槽的\u201c神翻译\u201d屡现街头，非但没有达到信息传递的语言服务目的，而且造成了\u201c语言污染\u201d，在一定程度上影响了我市对外交流的形象。有鉴于此，去年底，我市出台了《大连市外文公示语管理方案》，旨在实现标本兼治、归口管理、提高公示语终端译文质量，以及为社会提供标准公示语译品，同时不过度使用外文公示语。","imgUrl":"/profile/zt1.jpg","pressCategory":null,"isRecommend":0,"likeNumber":0,"viewsNumber":0,"userId":null,"pressStatus":null},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":24,"title":"完善并规范环境保护","content":"市政府外办将逐步完善并规范环境保护、危机管理、安检防恐、无障碍设施、规约制度、应急救助等热点领域的公示语翻译；由外而内地渐次推进自然景观、文博陈列、历史名胜、文化遗产等解说性公示语的翻译、规范与应用。","imgUrl":"/profile/zt4.jpg","pressCategory":null,"isRecommend":0,"likeNumber":0,"viewsNumber":0,"userId":null,"pressStatus":null},{"searchValue":null,"createBy":null,"createTime":null,"updateBy":null,"updateTime":null,"remark":null,"params":{},"id":25,"title":"我市启动外文公示语管理规范工作","content":"  本报讯（大连新闻传媒集团记者鹿道铭）昨日上午，由市政府外办组织成立的大连市外文公示语审核委员会举行专家委员聘任仪式暨座谈会，来自大连海事大学、大连民族大学、辽宁师范大学、大连工业大学、大连外国语大学、大连大学等高校的9名专家学者获聘为专家委员，他们将负责对我市主要街面、商铺及机场、火车站等重点涉外区域的英、日、韩公示语译审工作。","imgUrl":"/profile/zt3.jpg","pressCategory":null,"isRecommend":0,"likeNumber":0,"viewsNumber":0,"userId":null,"pressStatus":null}]
     * code : 200
     * msg : 查询成功
     */

    private int total;
    private int code;
    private String msg;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * searchValue : null
         * createBy : null
         * createTime : null
         * updateBy : null
         * updateTime : null
         * remark : null
         * params : {}
         * id : 22
         * title : 中国与世卫并肩抗疫，体现责任与担当
         * content :  本报讯（大连新闻传媒集团记者鹿道铭）昨日上午，由市政府外办组织成立的大连市外文公示语审核委员会举行专家委员聘任仪式暨座谈会，来自大连海事大学、大连民族大学、辽宁师范大学、大连工业大学、大连外国语大学、大连大学等高校的9名专家学者获聘为专家委员，他们将负责对我市主要街面、商铺及机场、火车站等重点涉外区域的英、日、韩公示语译审工作。
         * imgUrl : /profile/zt.jpg
         * pressCategory : null
         * isRecommend : 0
         * likeNumber : 0
         * viewsNumber : 0
         * userId : null
         * pressStatus : null
         */

        private Object searchValue;
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsBean params;
        private int id;
        private String title;
        private String content;
        private String imgUrl;
        private Object pressCategory;
        private int isRecommend;
        private int likeNumber;
        private int viewsNumber;
        private Object userId;
        private Object pressStatus;

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getPressCategory() {
            return pressCategory;
        }

        public void setPressCategory(Object pressCategory) {
            this.pressCategory = pressCategory;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public int getLikeNumber() {
            return likeNumber;
        }

        public void setLikeNumber(int likeNumber) {
            this.likeNumber = likeNumber;
        }

        public int getViewsNumber() {
            return viewsNumber;
        }

        public void setViewsNumber(int viewsNumber) {
            this.viewsNumber = viewsNumber;
        }

        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public Object getPressStatus() {
            return pressStatus;
        }

        public void setPressStatus(Object pressStatus) {
            this.pressStatus = pressStatus;
        }

        public static class ParamsBean {
        }
    }
}
