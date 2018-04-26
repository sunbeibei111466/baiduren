package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 资产抵押
 */

@Entity
public class MortgageDO {

    @Id(autoincrement = true)
    private Long id;//条目Id
    @Property(nameInDb = "name")
    private String name;//资产名称
    @Property(nameInDb = "amountStr")
    private String amountStr;//评估价值
    @Property(nameInDb = "num")
    private int num;//数量
    @Property(nameInDb = "evaluation")
    private String evaluation;//评估机构
    @Property(nameInDb = "image")
    private String image;//资产抵押证明图片
    @Generated(hash = 1628633562)
    public MortgageDO(Long id, String name, String amountStr, int num,
            String evaluation, String image) {
        this.id = id;
        this.name = name;
        this.amountStr = amountStr;
        this.num = num;
        this.evaluation = evaluation;
        this.image = image;
    }
    @Generated(hash = 462078904)
    public MortgageDO() {
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
    public String getAmountStr() {
        return this.amountStr;
    }
    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }
    public int getNum() {
        return this.num;
    }
    public void setNum(int num) {
        this.num = num;
    }
    public String getEvaluation() {
        return this.evaluation;
    }
    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }

}
