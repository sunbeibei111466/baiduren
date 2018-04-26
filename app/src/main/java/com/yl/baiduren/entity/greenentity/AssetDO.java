package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/19.
 */
@Entity
public class AssetDO {

    @Id(autoincrement = true)
    private Long id;//条目Id
    @Property(nameInDb = "categoryName")
    private String categoryName;//类别name
    @Property(nameInDb = "categoryId")
    private int categoryId;//类别Id
    @Property(nameInDb = "name")
    private String name;//资产名称
    @Property(nameInDb = "priceStr")
    private String priceStr;//资产金额
    @Property(nameInDb = "num")
    private int num;//资产数量
    @Property(nameInDb = "totalStr")
    private String totalStr;//资产总额
    @Property(nameInDb = "evaluation")
    private String evaluation;//评估机构
    @Property(nameInDb = "area")
    private String area;//资产所属地 code
    @Property(nameInDb = "dizhi")
    private String dizhi;//需求所在地 name string
    @Property(nameInDb = "remark")
    private String remark;//备注
    @Property(nameInDb = "imgUrl")//图片地址
    private String imgUrl;
    @Generated(hash = 1494390578)
    public AssetDO(Long id, String categoryName, int categoryId, String name,
            String priceStr, int num, String totalStr, String evaluation,
            String area, String dizhi, String remark, String imgUrl) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.name = name;
        this.priceStr = priceStr;
        this.num = num;
        this.totalStr = totalStr;
        this.evaluation = evaluation;
        this.area = area;
        this.dizhi = dizhi;
        this.remark = remark;
        this.imgUrl = imgUrl;
    }
    @Generated(hash = 1693335360)
    public AssetDO() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCategoryName() {
        return this.categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public int getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPriceStr() {
        return this.priceStr;
    }
    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getTotalStr() {
        return this.totalStr;
    }
    public void setTotalStr(String totalStr) {
        this.totalStr = totalStr;
    }
    public String getEvaluation() {
        return this.evaluation;
    }
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getDizhi() {
        return this.dizhi;
    }
    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
