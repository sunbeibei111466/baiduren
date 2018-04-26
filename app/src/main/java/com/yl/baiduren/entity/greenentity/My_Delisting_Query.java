package com.yl.baiduren.entity.greenentity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by sunbeibei on 2018/1/5.
 */
@Entity
public class My_Delisting_Query {
    @Id
    private Long id;
    @Property(nameInDb = "query_delisting")
    private String query_delisting;
    @Generated(hash = 1974397104)
    public My_Delisting_Query(Long id, String query_delisting) {
        this.id = id;
        this.query_delisting = query_delisting;
    }
    @Generated(hash = 1835035700)
    public My_Delisting_Query() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuery_delisting() {
        return this.query_delisting;
    }
    public void setQuery_delisting(String query_delisting) {
        this.query_delisting = query_delisting;
    }
}
