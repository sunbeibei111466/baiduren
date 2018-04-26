package com.yl.baiduren.entity.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2018/1/8.
 */

public class My_Child_Accort_Result implements Serializable{
    private List<DataListBean> dataList;

    public List<DataListBean> getDataListBeanList() {
        return dataList;
    }

    public void setDataListBeanList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean implements Serializable{
        private Long userId;
        private String nickName;
        private String mobile;
        private String image;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
}






