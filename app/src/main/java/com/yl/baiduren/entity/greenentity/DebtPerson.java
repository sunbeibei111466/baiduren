package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android_apple on 2017/12/29.
 */
@Entity
public class DebtPerson {

    @Id
    private Long id;
    @Property(nameInDb = "text")
    private String text;
    @Generated(hash = 3479952)
    public DebtPerson(Long id, String text) {
        this.id = id;
        this.text = text;
    }
    @Generated(hash = 1976489592)
    public DebtPerson() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }


}
