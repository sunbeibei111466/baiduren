package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2017/12/28.
 */

public class Create_Order_Result {

    /**
     * id : 1
     * billCode : BA201712271812483065623
     * price : 888
     * priceStr : 8.88
     * createTime : 1514369568465
     * userImage : 123,345,789
     * userNickName : 王测试
     * userPhone : 13544445555
     */

    private Long id;
    private String billCode;
    private int price;
    private String priceStr;
    private long createTime;
    private String userImage;
    private String userNickName;
    private String userPhone;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
