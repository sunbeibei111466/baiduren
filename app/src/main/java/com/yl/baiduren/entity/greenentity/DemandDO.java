package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/19.
 */
@Entity
public class DemandDO {

    @Id(autoincrement = true)
    private Long id;//条目Id
    @Property(nameInDb = "categoryName")
    private String categoryName;//类别name
    @Property(nameInDb = "categoryId")
    private int categoryId;//类别Id
    @Property(nameInDb = "name")
    private String name;//需求名称
    @Property(nameInDb = "value")
    private int value;//需求金额
    @Property(nameInDb = "valueStr")
    private String valueStr;//需求金额
    @Property(nameInDb = "area")
    private String area;//需求所在地 code
    @Property(nameInDb = "dizhi")
    private String dizhi;//需求所在地 name string
    @Property(nameInDb = "remark")
    private String remark;//备注
    @Generated(hash = 1335987778)
    public DemandDO(Long id, String categoryName, int categoryId, String name,
            int value, String valueStr, String area, String dizhi, String remark) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.name = name;
        this.value = value;
        this.valueStr = valueStr;
        this.area = area;
        this.dizhi = dizhi;
        this.remark = remark;
    }
    @Generated(hash = 1090435334)
    public DemandDO() {
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
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getValueStr() {
        return this.valueStr;
    }
    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
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




}