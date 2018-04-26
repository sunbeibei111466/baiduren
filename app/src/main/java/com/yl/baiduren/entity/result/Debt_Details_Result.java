package com.yl.baiduren.entity.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/12/22.
 */

public class Debt_Details_Result implements Serializable {


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    /**
     * debtRelationResponse1 : {"fromName":"王测试","fromCardNumber":"150105199702264117","toName":"王测试公司","toCardNumber":null,"genreName":"混合","typeName":"货币","amout":null,"amoutStr":"1000.0","recordTime":"2017-08-01 00:00:00"}
     * demandResponses : [{"id":38,"category":"美妆","name":"我现在","amout":null,"amoutStr":"100.0","area":null,"areaName":"内蒙古呼和浩特市市辖区","remark":"个"}]
     * assetResponses : [{"id":18,"category":"家居","name":"我们","price":null,"priceStr":"3000.0","num":1,"total":null,"totalStr":"3000.0","area":null,"areaName":"北京市北京市东城区","remark":"头"}]
     * debtRelation4Responses : [{"\u201cid\u201d":1,"\u201ccategory\u201d":5,"\u201ctitle\u201d":"\u201c资产抵押\u201d","\u201cfirst\u201d":"\u201c测试\u201d","\u201csecond\u201d":"\u201c3333.1\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":2,"\u201ccategory\u201d":5,"\u201ctitle\u201d":"\u201c资产抵押\u201d","\u201cfirst\u201d":"\u201c测试\u201d","\u201csecond\u201d":"\u201c3333.1\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":3,"\u201ccategory\u201d":5,"\u201ctitle\u201d":"\u201c资产抵押\u201d","\u201cfirst\u201d":"\u201c测试\u201d","\u201csecond\u201d":"\u201c3333.1\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":4,"\u201ccategory\u201d":5,"\u201ctitle\u201d":"\u201c资产抵押\u201d","\u201cfirst\u201d":"\u201c测试\u201d","\u201csecond\u201d":"\u201c3333.1\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":5,"\u201ccategory\u201d":5,"\u201ctitle\u201d":"\u201c资产抵押\u201d","\u201cfirst\u201d":"\u201c测试\u201d","\u201csecond\u201d":"\u201c3333.1\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":6,"\u201ccategory\u201d":5,"\u201ctitle\u201d":"\u201c资产抵押\u201d","\u201cfirst\u201d":"\u201c段测试\u201d","\u201csecond\u201d":"\u201c2557.0\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":1,"\u201ccategory\u201d":4,"\u201ctitle\u201d":"\u201c担保人\u201d","\u201cfirst\u201d":"\u201c段测试\u201d","\u201csecond\u201d":"\u201c河北省石家庄市桥东区\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":2,"\u201ccategory\u201d":4,"\u201ctitle\u201d":"\u201c担保人\u201d","\u201cfirst\u201d":"\u201c段测试\u201d","\u201csecond\u201d":"\u201c河北省石家庄市桥东区\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":3,"\u201ccategory\u201d":4,"\u201ctitle\u201d":"\u201c担保人\u201d","\u201cfirst\u201d":"\u201c段测试\u201d","\u201csecond\u201d":"\u201c河北省石家庄市桥东区\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":4,"\u201ccategory\u201d":4,"\u201ctitle\u201d":"\u201c担保人\u201d","\u201cfirst\u201d":"\u201c段\u201d","\u201csecond\u201d":"\u201c河北省石家庄市桥西区\u201d","\u201cthird\u201d":null},{"\u201cid\u201d":1,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201chigh\u201d","\u201csecond\u201d":"\u201c12.0\u201d","\u201cthird\u201d":"\u201c236.0\u201d"},{"\u201cid\u201d":2,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201chigh\u201d","\u201csecond\u201d":"\u201c12.0\u201d","\u201cthird\u201d":"\u201c236.0\u201d"},{"\u201cid\u201d":3,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c1\u201d","\u201csecond\u201d":"\u201c2.0\u201d","\u201cthird\u201d":"\u201c12.0\u201d"},{"\u201cid\u201d":4,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c1\u201d","\u201csecond\u201d":"\u201c2.0\u201d","\u201cthird\u201d":"\u201c12.0\u201d"},{"\u201cid\u201d":5,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c122\u201d","\u201csecond\u201d":"\u201c213.0\u201d","\u201cthird\u201d":"\u201c324.0\u201d"},{"\u201cid\u201d":6,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c1\u201d","\u201csecond\u201d":"\u201c11.0\u201d","\u201cthird\u201d":"\u201c2.0\u201d"},{"\u201cid\u201d":7,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c22\u201d","\u201csecond\u201d":"\u201c33.0\u201d","\u201cthird\u201d":"\u201c33332.0\u201d"},{"\u201cid\u201d":8,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c1\u201d","\u201csecond\u201d":"\u201c2.0\u201d","\u201cthird\u201d":"\u201c123.0\u201d"},{"\u201cid\u201d":9,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c1\u201d","\u201csecond\u201d":"\u201c2.0\u201d","\u201cthird\u201d":"\u201c123.0\u201d"},{"\u201cid\u201d":10,"\u201ccategory\u201d":1,"\u201ctitle\u201d":"\u201c货币借贷\u201d","\u201cfirst\u201d":"\u201c12\u201d","\u201csecond\u201d":"\u201c123.0\u201d","\u201cthird\u201d":"\u201c13.0\u201d"},{"\u201cid\u201d":1,"\u201ccategory\u201d":2,"\u201ctitle\u201d":"\u201c实物借贷\u201d","\u201cfirst\u201d":"\u201c123\u201d","\u201csecond\u201d":"\u201c23.0\u201d","\u201cthird\u201d":"\u201c123.0\u201d"},{"\u201cid\u201d":2,"\u201ccategory\u201d":2,"\u201ctitle\u201d":"\u201c实物借贷\u201d","\u201cfirst\u201d":"\u201c123\u201d","\u201csecond\u201d":"\u201c23.0\u201d","\u201cthird\u201d":"\u201c123.0\u201d"},{"\u201cid\u201d":3,"\u201ccategory\u201d":2,"\u201ctitle\u201d":"\u201c实物借贷\u201d","\u201cfirst\u201d":"\u201c3123\u201d","\u201csecond\u201d":"\u201c123.0\u201d","\u201cthird\u201d":"\u201c123.0\u201d"},{"\u201cid\u201d":4,"\u201ccategory\u201d":2,"\u201ctitle\u201d":"\u201c实物借贷\u201d","\u201cfirst\u201d":"\u201c123\u201d","\u201csecond\u201d":"\u201c123.0\u201d","\u201cthird\u201d":"\u201c123.0\u201d"},{"\u201cid\u201d":1,"\u201ccategory\u201d":3,"\u201ctitle\u201d":"\u201c产权借贷\u201d","\u201cfirst\u201d":"\u201c测试\u201d","\u201csecond\u201d":"\u201c5866.0\u201d","\u201cthird\u201d":"\u201c56681.0\u201d"},{"\u201cid\u201d":2,"\u201ccategory\u201d":3,"\u201ctitle\u201d":"\u201c产权借贷\u201d","\u201cfirst\u201d":"\u201c测试\u201d","\u201csecond\u201d":"\u201c5866.0\u201d","\u201cthird\u201d":"\u201c56681.0\u201d"}]
     */
    private int flag;
    private int userType;//0 普通用户 1解债人
    private boolean isVip;//是否是vip
    private Long userId;
    private DebtRelationElseResponseBean debtRelationElseResponse;
    private DebtRelationResponse1Bean debtRelationResponse1;
    private List<DemandResponsesBean> demandResponses;
    private List<AssetResponsesBean> assetResponses;
    private List<DebtRelation4ResponsesBean> debtRelation4Responses;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DebtRelationElseResponseBean getDebtRelationElseResponse() {
        return debtRelationElseResponse;
    }

    public DebtRelationResponse1Bean getDebtRelationResponse1() {
        return debtRelationResponse1;
    }

    public void setDebtRelationElseResponse(DebtRelationElseResponseBean debtRelationElseResponse) {
        this.debtRelationElseResponse = debtRelationElseResponse;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public void setDebtRelationResponse1(DebtRelationResponse1Bean debtRelationResponse1) {
        this.debtRelationResponse1 = debtRelationResponse1;
    }

    public List<DemandResponsesBean> getDemandResponses() {
        return demandResponses;
    }

    public void setDemandResponses(List<DemandResponsesBean> demandResponses) {
        this.demandResponses = demandResponses;
    }

    public List<AssetResponsesBean> getAssetResponses() {
        return assetResponses;
    }

    public void setAssetResponses(List<AssetResponsesBean> assetResponses) {
        this.assetResponses = assetResponses;
    }

    public List<DebtRelation4ResponsesBean> getDebtRelation4Responses() {
        return debtRelation4Responses;
    }

    public void setDebtRelation4Responses(List<DebtRelation4ResponsesBean> debtRelation4Responses) {
        this.debtRelation4Responses = debtRelation4Responses;
    }

    public static class DebtRelationElseResponseBean implements Serializable {
        private String status;
        private String createDays;
        private String matchDays;
        private String matchResult;
        private String settleName;
        private String settleMobile;
        private int isHasSettle;
        private String settleImage;
        private String progress;
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }


        public String getSettleImage() {
            return settleImage;
        }

        public void setSettleImage(String settleImage) {
            this.settleImage = settleImage;
        }

        public int getIsHasSettle() {
            return isHasSettle;
        }

        public void setIsHasSettle(int isHasSettle) {
            this.isHasSettle = isHasSettle;
        }


        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreateDays() {
            return createDays;
        }

        public void setCreateDays(String createDays) {
            this.createDays = createDays;
        }

        public String getMatchDays() {
            return matchDays;
        }

        public void setMatchDays(String matchDays) {
            this.matchDays = matchDays;
        }

        public String getMatchResult() {
            return matchResult;
        }

        public void setMatchResult(String matchResult) {
            this.matchResult = matchResult;
        }

        public String getSettleName() {
            return settleName;
        }

        public void setSettleName(String settleName) {
            this.settleName = settleName;
        }

        public String getSettleMobile() {
            return settleMobile;
        }

        public void setSettleMobile(String settleMobile) {
            this.settleMobile = settleMobile;
        }
    }

    public static class DebtRelationResponse1Bean implements Serializable {
        /**
         * fromName : 王测试
         * fromCardNumber : 150105199702264117
         * toName : 王测试公司
         * toCardNumber : null
         * genreName : 混合
         * typeName : 货币
         * amout : null
         * amoutStr : 1000.0
         * recordTime : 2017-08-01 00:00:00
         */

        private String fromName;
        private String fromCardNumber;
        private String toName;
        private String toCardNumber;
        private String genreName;
        private String typeName;
        private String amout;
        private String amoutStr;
        private String recordTime;
        private String phoneNumber;//备案人手机号
        private String endTime;//债事结束时间
        private String commission;//解债佣金
        private String fromType;
        private String toType;


        public String getFromType() {
            return fromType;
        }

        public void setFromType(String fromType) {
            this.fromType = fromType;
        }

        public String getToType() {
            return toType;
        }

        public void setToType(String toType) {
            this.toType = toType;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getFromName() {
            return fromName;
        }

        public void setFromName(String fromName) {
            this.fromName = fromName;
        }

        public String getFromCardNumber() {
            return fromCardNumber;
        }

        public void setFromCardNumber(String fromCardNumber) {
            this.fromCardNumber = fromCardNumber;
        }

        public String getToName() {
            return toName;
        }

        public void setToName(String toName) {
            this.toName = toName;
        }

        public String getToCardNumber() {
            return toCardNumber;
        }

        public void setToCardNumber(String toCardNumber) {
            this.toCardNumber = toCardNumber;
        }

        public String getGenreName() {
            return genreName;
        }

        public void setGenreName(String genreName) {
            this.genreName = genreName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public Object getAmout() {
            return amout;
        }

        public void setAmout(String amout) {
            this.amout = amout;
        }

        public String getAmoutStr() {
            return amoutStr;
        }

        public void setAmoutStr(String amoutStr) {
            this.amoutStr = amoutStr;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }
    }

    public static class DemandResponsesBean implements Serializable {
        /**
         * id : 38
         * category : 美妆
         * name : 我现在
         * amout : null
         * amoutStr : 100.0
         * area : null
         * areaName : 内蒙古呼和浩特市市辖区
         * remark : 个
         */

        private Long id;
        private String category;
        private String name;
        private Object amout;
        private String amoutStr;
        private String area;
        private String areaName;
        private String remark;
        private String categoryImg;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAmout() {
            return amout;
        }

        public void setAmout(Object amout) {
            this.amout = amout;
        }

        public String getAmoutStr() {
            return amoutStr;
        }

        public void setAmoutStr(String amoutStr) {
            this.amoutStr = amoutStr;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }
    }

    public static class AssetResponsesBean implements Serializable {
        /**
         * id : 18
         * category : 家居
         * name : 我们
         * price : null
         * priceStr : 3000.0
         * num : 1
         * total : null
         * totalStr : 3000.0
         * area : null
         * areaName : 北京市北京市东城区
         * remark : 头
         */

        private Long id;
        private String category;
        private String name;
        private Object price;
        private String priceStr;
        private int num;
        private Object total;
        private String totalStr;
        private String area;
        private String areaName;
        private String remark;
        private String categoryImg;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public String getPriceStr() {
            return priceStr;
        }

        public void setPriceStr(String priceStr) {
            this.priceStr = priceStr;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public Object getTotal() {
            return total;
        }

        public void setTotal(Object total) {
            this.total = total;
        }

        public String getTotalStr() {
            return totalStr;
        }

        public void setTotalStr(String totalStr) {
            this.totalStr = totalStr;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCategoryImg() {
            return categoryImg;
        }

        public void setCategoryImg(String categoryImg) {
            this.categoryImg = categoryImg;
        }
    }

    public static class DebtRelation4ResponsesBean implements Serializable {

        private Long id;
        private int category;
        private String title;
        private String first;
        private String second;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }

        public String getThird() {
            return third;
        }

        public void setThird(String third) {
            this.third = third;
        }

        private String third;
    }
}
