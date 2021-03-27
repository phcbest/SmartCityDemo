package com.phcbest.smartcitydemo.pojo;

import java.util.List;

public class HomeImagePojo {

    /**
     * total : 5
     * rows : [{"id":5,"imgUrl":"/profile/1-yingdao.jpg","type":"47","createTime":"2020-10-12T22:55:17.000+0800","sort":"1","display":"N"},{"id":6,"imgUrl":"/profile/2-yingdao.jpg","type":"47","createTime":"2020-10-12T22:55:17.000+0800","sort":"2","display":"N"},{"id":7,"imgUrl":"/profile/3-yingdao.jpg","type":"47","createTime":"2020-10-12T22:55:17.000+0800","sort":"3","display":"N"},{"id":8,"imgUrl":"/profile/4-yingdao.jpg","type":"47","createTime":"2020-10-12T22:55:17.000+0800","sort":"4","display":"N"},{"id":9,"imgUrl":"/profile/5-yingdao.jpg","type":"47","createTime":"2020-10-12T22:55:17.000+0800","sort":"5","display":"N"}]
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
         * id : 5
         * imgUrl : /profile/1-yingdao.jpg
         * type : 47
         * createTime : 2020-10-12T22:55:17.000+0800
         * sort : 1
         * display : N
         */

        private int id;
        private String imgUrl;
        private String type;
        private String createTime;
        private String sort;
        private String display;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }
    }
}
