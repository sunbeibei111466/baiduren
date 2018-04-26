package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2017/12/28.
 */

public class Open_Member_Result {


    private String nickName;//昵称
    private Long vipTime;//vip截止时间
    private String userImage;//用户图片
    private boolean isSolveMan;//是否解债人
    private boolean isVip;//是否vip
    private boolean isFull;//用户资料是否齐全 false：不全；  true：全
    private Long toVipTimeAfterPay;//支付多少分钟之后开通vip
    private String personVipExplain;//个人vip开通说明
    private String mechanisVipExplain;//企业vip开通说名
    //个人vip
    private List<PersonVipListBean> personVipList;
    //企业vip（6个账号，买一带五）
    private List<MechanisSixtListBean> mechanisSixtList;
    //企业vip（11个账号，买一带10）
    private List<MechanisElevenListBean> mechanisElevenList;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getVipTime() {
        return vipTime;
    }

    public void setVipTime(Long vipTime) {
        this.vipTime = vipTime;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public boolean isIsSolveMan() {
        return isSolveMan;
    }

    public void setIsSolveMan(boolean isSolveMan) {
        this.isSolveMan = isSolveMan;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public boolean isIsFull() {
        return isFull;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

    public Long getToVipTimeAfterPay() {
        return toVipTimeAfterPay;
    }

    public void setToVipTimeAfterPay(Long toVipTimeAfterPay) {
        this.toVipTimeAfterPay = toVipTimeAfterPay;
    }

    public String getPersonVipExplain() {
        return personVipExplain;
    }

    public void setPersonVipExplain(String personVipExplain) {
        this.personVipExplain = personVipExplain;
    }

    public String getMechanisVipExplain() {
        return mechanisVipExplain;
    }

    public void setMechanisVipExplain(String mechanisVipExplain) {
        this.mechanisVipExplain = mechanisVipExplain;
    }

    public List<PersonVipListBean> getPersonVipList() {
        return personVipList;
    }

    public void setPersonVipList(List<PersonVipListBean> personVipList) {
        this.personVipList = personVipList;
    }

    public List<MechanisSixtListBean> getMechanisSixtList() {
        return mechanisSixtList;
    }

    public void setMechanisSixtList(List<MechanisSixtListBean> mechanisSixtList) {
        this.mechanisSixtList = mechanisSixtList;
    }

    public List<MechanisElevenListBean> getMechanisElevenList() {
        return mechanisElevenList;
    }

    public void setMechanisElevenList(List<MechanisElevenListBean> mechanisElevenList) {
        this.mechanisElevenList = mechanisElevenList;
    }

    public static class PersonVipListBean {
        /**
         * id : 1
         * priceStr : 30
         * originalPriceStr : 50
         * information : /月
         */

        private Long id;
        private String priceStr;
        private String originalPriceStr;
        private String information;
        private int month;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }

        public String getOriginalPriceStr() {
            return originalPriceStr;
        }

        public void setOriginalPriceStr(String originalPriceStr) {
            this.originalPriceStr = originalPriceStr;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }
    }

    public static class MechanisSixtListBean {
        /**
         * id : 19
         * priceStr : 168
         * originalPriceStr : 280
         * information : /月
         */

        private Long id;
        private String priceStr;
        private String originalPriceStr;
        private String information;
        private int month;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }

        public String getOriginalPriceStr() {
            return originalPriceStr;
        }

        public void setOriginalPriceStr(String originalPriceStr) {
            this.originalPriceStr = originalPriceStr;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }
    }

    public static class MechanisElevenListBean {
        /**
         * id : 23
         * priceStr : 300
         * originalPriceStr : 500
         * information : /月
         */

        private Long id;
        private String priceStr;
        private String originalPriceStr;
        private String information;
        private int month;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }

        public String getOriginalPriceStr() {
            return originalPriceStr;
        }

        public void setOriginalPriceStr(String originalPriceStr) {
            this.originalPriceStr = originalPriceStr;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }
    }
}
