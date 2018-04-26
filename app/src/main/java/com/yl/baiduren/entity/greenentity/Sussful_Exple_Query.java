package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sunbeibei on 2018/1/5.
 */
@Entity
public class Sussful_Exple_Query {
    @Id
    private Long id;
    @Property(nameInDb ="querysufull" )
    private String querysufull;
    @Generated(hash = 1098396056)
    public Sussful_Exple_Query(Long id, String querysufull) {
        this.id = id;
        this.querysufull = querysufull;
    }
    @Generated(hash = 1463934247)
    public Sussful_Exple_Query() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuerysufull() {
        return this.querysufull;
    }
    public void setQuerysufull(String querysufull) {
        this.querysufull = querysufull;
    }

}
