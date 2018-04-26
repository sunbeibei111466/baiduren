package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/26.
 */

public class DebtPersonList {

    private List<Mode> dataList;

    public static class Mode{
        private Long id;
        private String areaName;//地址
        private String name;//债事自然人姓名或者债事机构名称
        private String phoneNumber;//债事自然人手机号
        private String companyCategory;//债事机构名称所属行业
        private int type;
        private Integer describe;//1.自己；2.子账号

        public Integer getDescribe() {
            return describe;
        }

        public void setDescribe(Integer describe) {
            this.describe = describe;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getCompanyCategory() {
            return companyCategory;
        }

        public void setCompanyCategory(String companyCategory) {
            this.companyCategory = companyCategory;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public List<Mode> getDataList() {
        return dataList;
    }

    public void setDataList(List<Mode> dataList) {
        this.dataList = dataList;
    }
}
