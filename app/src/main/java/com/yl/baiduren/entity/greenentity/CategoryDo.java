package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2018/1/2.
 */
@Entity
public class CategoryDo {

    @Id
    private Long Cg_Id;
    @Property(nameInDb = "id")
    private String id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "image")
    private String image;
    @Generated(hash = 1111031512)
    public CategoryDo(Long Cg_Id, String id, String name, String image) {
        this.Cg_Id = Cg_Id;
        this.id = id;
        this.name = name;
        this.image = image;
    }
    @Generated(hash = 2107011481)
    public CategoryDo() {
    }
    public Long getCg_Id() {
        return this.Cg_Id;
    }
    public void setCg_Id(Long Cg_Id) {
        this.Cg_Id = Cg_Id;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }



}
