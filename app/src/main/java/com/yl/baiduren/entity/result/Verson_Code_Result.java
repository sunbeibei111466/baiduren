package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2018/4/13.
 */

public class Verson_Code_Result {
    private Long id;

    private String versionNum;

    private String isForce;

    private String updateItems;

    private String type;

    private String downUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getUpdateItems() {
        return updateItems;
    }

    public void setUpdateItems(String updateItems) {
        this.updateItems = updateItems;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
}
