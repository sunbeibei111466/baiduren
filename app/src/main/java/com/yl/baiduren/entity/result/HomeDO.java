package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by Android_apple on 2018/1/11.
 */

public class HomeDO  {


    /**
     * bannerDOs : [{"id":1,"name":"banner1","imgUrl":"http://60.205.184.33:8991/banner/banner1.png","jumpUrl":"http://www.baidu.com","weight":1,"isActive":true,"isDelete":false},{"id":2,"name":"banenr2","imgUrl":"http://60.205.184.33:8991/banner/banner2.png","jumpUrl":"http://www.baidu.com","weight":2,"isActive":true,"isDelete":false}]
     * debtTotal : 10
     * drAmountTotalStr : 13650122
     * drTotal : 17
     * debtNums : [0]
     * drNums : [0]
     * dates : ["20180123","20180124","20180125","20180126","20180127","20180128","20180129"]
     * nums : [20,40,60,80,100]
     */

    private int debtTotal;//债事人总数
    private String drAmountTotalStr;
    private int drTotal; //债事总数
    private List<BannerDOsBean> bannerDOs;//轮播图
    private List<Integer> debtNums;
    private List<Integer> drNums;
    private List<String> dates;//x轴
    private List<Integer> nums;//y轴
    private String yearAndMonth;
    private HomeRecode homeRecord;

    public static class HomeRecode{
        private int enterpriseNum;
        private int debtNum;
        private List<HomeR> list;

        public List<HomeR> getList() {
            return list;
        }

        public void setList(List<HomeR> list) {
            this.list = list;
        }

        public int getEnterpriseNum() {
            return enterpriseNum;
        }

        public void setEnterpriseNum(int enterpriseNum) {
            this.enterpriseNum = enterpriseNum;
        }

        public int getDebtNum() {
            return debtNum;
        }

        public void setDebtNum(int debtNum) {
            this.debtNum = debtNum;
        }

        public static class HomeR{
            private String name;
            private String percent;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPercent() {
                return percent;
            }

            public void setPercent(String percent) {
                this.percent = percent;
            }
        }
    }

    public HomeRecode getHomeRecord() {
        return homeRecord;
    }

    public void setHomeRecord(HomeRecode homeRecord) {
        this.homeRecord = homeRecord;
    }

    public String getYearAndMonth() {
        return yearAndMonth;
    }

    public void setYearAndMonth(String yearAndMonth) {
        this.yearAndMonth = yearAndMonth;
    }

    public int getDebtTotal() {
        return debtTotal;
    }

    public void setDebtTotal(int debtTotal) {
        this.debtTotal = debtTotal;
    }

    public String getDrAmountTotalStr() {
        return drAmountTotalStr;
    }

    public void setDrAmountTotalStr(String drAmountTotalStr) {
        this.drAmountTotalStr = drAmountTotalStr;
    }

    public int getDrTotal() {
        return drTotal;
    }

    public void setDrTotal(int drTotal) {
        this.drTotal = drTotal;
    }

    public List<BannerDOsBean> getBannerDOs() {
        return bannerDOs;
    }

    public void setBannerDOs(List<BannerDOsBean> bannerDOs) {
        this.bannerDOs = bannerDOs;
    }

    public List<Integer> getDebtNums() {
        return debtNums;
    }

    public void setDebtNums(List<Integer> debtNums) {
        this.debtNums = debtNums;
    }

    public List<Integer> getDrNums() {
        return drNums;
    }

    public void setDrNums(List<Integer> drNums) {
        this.drNums = drNums;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Integer> getNums() {
        return nums;
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }

    public static class BannerDOsBean {
        /**
         * id : 1
         * name : banner1
         * imgUrl : http://60.205.184.33:8991/banner/banner1.png
         * jumpUrl : http://www.baidu.com
         * weight : 1
         * isActive : true
         * isDelete : false
         */

        private int id;
        private String name;
        private String imgUrl;
        private String jumpUrl;
        private int weight;
        private boolean isActive;
        private boolean isDelete;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public boolean isIsDelete() {
            return isDelete;
        }

        public void setIsDelete(boolean isDelete) {
            this.isDelete = isDelete;
        }


    }
}
