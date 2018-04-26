package com.yl.baiduren.entity;

import java.util.List;

/**
 * Created by Android_apple on 2018/1/4.
 */

public class AsstesStatusProject {

    String lndustryName;
    List<AsstesEntity> asstesEntityList;

    public String getLndustryName() {
        return lndustryName;
    }

    public void setLndustryName(String lndustryName) {
        this.lndustryName = lndustryName;
    }

    public List<AsstesEntity> getAsstesEntityList() {
        return asstesEntityList;
    }

    public void setAsstesEntityList(List<AsstesEntity> asstesEntityList) {
        this.asstesEntityList = asstesEntityList;
    }
}
