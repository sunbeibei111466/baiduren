package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2018/1/30.
 */

@Entity
public class AssetsnDetailesInformation {

    @Id
    private Long aId;
    @Property(nameInDb = "Text")
    private String text;
    @Generated(hash = 112308463)
    public AssetsnDetailesInformation(Long aId, String text) {
        this.aId = aId;
        this.text = text;
    }
    @Generated(hash = 1130616411)
    public AssetsnDetailesInformation() {
    }
    public Long getAId() {
        return this.aId;
    }
    public void setAId(Long aId) {
        this.aId = aId;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
