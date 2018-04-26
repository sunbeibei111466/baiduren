package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2018/1/2.
 */
@Entity
public class Hall_Querry  {

    @Id
    private Long id;
    @Property(nameInDb = "querryText")
    private String querryText;
    @Generated(hash = 1459202668)
    public Hall_Querry(Long id, String querryText) {
        this.id = id;
        this.querryText = querryText;
    }
    @Generated(hash = 107981028)
    public Hall_Querry() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuerryText() {
        return this.querryText;
    }
    public void setQuerryText(String querryText) {
        this.querryText = querryText;
    }


}
