package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/1/24.
 */

public class My_Supply_Result {


    private List<DataListBean> dataList;

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * id : 1
         * name : 家居
         * num : 3
         * valueStr : 5555
         * image :
         * userId : 54
         * userName : 孙测试
         * userImg : http://60.205.184.33:8991/54/0f4a546d2b0b4e9491f8acf4b1d91230.jpg
         * isCollect : null
         */

        private Long id;
        private String name;
        private int num;
        private String valueStr;
        private String image;
        private int userId;
        private String userName;
        private String userImg;
        private Boolean isCollect;
        private String code;
        private String area;
        private String areaName;
        private int categoryId;
        private String categoryImg;
        private String describe;
        private String categoryName;
        private String angleimg;//角标

        public String getAngleimg() {
            return angleimg;
        }

        public void setAngleimg(String angleimg) {
            this.angleimg = angleimg;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }

        public Long getId() {
            return id;

        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getValueStr() {
            return valueStr;
        }

        public void setValueStr(String valueStr) {
            this.valueStr = valueStr;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImg() {
            return userImg;
        }

        public void setUserImg(String userImg) {
            this.userImg = userImg;
        }

        public Boolean getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(Boolean isCollect) {
            this.isCollect = isCollect;
        }
    }
}
