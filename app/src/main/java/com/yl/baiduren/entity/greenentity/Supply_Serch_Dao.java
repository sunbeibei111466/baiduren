package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sunbeibei on 2018/1/23.
 */
@Entity
public class Supply_Serch_Dao {
    @Id
    private Long id;
    @Property(nameInDb = "supply_serch")
    private String supply_serch;
    @Generated(hash = 623387735)
    public Supply_Serch_Dao(Long id, String supply_serch) {
        this.id = id;
        this.supply_serch = supply_serch;
    }
    @Generated(hash = 824690356)
    public Supply_Serch_Dao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSupply_serch() {
        return this.supply_serch;
    }
    public void setSupply_serch(String supply_serch) {
        this.supply_serch = supply_serch;
    }
}
