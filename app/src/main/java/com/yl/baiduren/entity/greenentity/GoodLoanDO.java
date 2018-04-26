package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/20.
 */

@Entity
public class GoodLoanDO {

    @Id(autoincrement = true)
    private Long id;//条目Id
    @Property(nameInDb = "name")
    private String name;//物品名称
    @Property(nameInDb = "unit")
    private String unit;//计量单位
    @Property(nameInDb = "num")
    private int num;//数量
    @Property(nameInDb = "valueStr")
    private String valueStr;//评估价值
    @Property(nameInDb = "evaluation")
    private String evaluation;//评估机构
    @Property(nameInDb = "depreciation")
    private double depreciation;//折旧率
    @Property(nameInDb = "borrowTime")
    private Long borrowTime;//借用日期
    @Property(nameInDb = "useLife")
    private int useLife;//使用年限
    @Property(nameInDb = "returnNum")
    private Long returnNum;//归还数量
    @Property(nameInDb = "balanceStr")
    private Long balanceStr;//未还金额
    @Property(nameInDb = "image")
    private String image;//图片
    @Generated(hash = 1183559596)
    public GoodLoanDO(Long id, String name, String unit, int num, String valueStr,
            String evaluation, double depreciation, Long borrowTime, int useLife,
            Long returnNum, Long balanceStr, String image) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.num = num;
        this.valueStr = valueStr;
        this.evaluation = evaluation;
        this.depreciation = depreciation;
        this.borrowTime = borrowTime;
        this.useLife = useLife;
        this.returnNum = returnNum;
        this.balanceStr = balanceStr;
        this.image = image;
    }
    @Generated(hash = 518721996)
    public GoodLoanDO() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getValueStr() {
        return this.valueStr;
    }
    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
    public String getEvaluation() {
        return this.evaluation;
    }
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
    public double getDepreciation() {
        return this.depreciation;
    }
    public void setDepreciation(double depreciation) {
        this.depreciation = depreciation;
    }
    public Long getBorrowTime() {
        return this.borrowTime;
    }
    public void setBorrowTime(Long borrowTime) {
        this.borrowTime = borrowTime;
    }
    public int getUseLife() {
        return this.useLife;
    }
    public void setUseLife(int useLife) {
        this.useLife = useLife;
    }
    public Long getReturnNum() {
        return this.returnNum;
    }
    public void setReturnNum(Long returnNum) {
        this.returnNum = returnNum;
    }
    public Long getBalanceStr() {
        return this.balanceStr;
    }
    public void setBalanceStr(Long balanceStr) {
        this.balanceStr = balanceStr;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }


}
