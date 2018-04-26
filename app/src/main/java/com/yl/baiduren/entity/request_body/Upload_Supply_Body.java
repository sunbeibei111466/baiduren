package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/24.
 */

public class Upload_Supply_Body extends BaseRequest {

    private Long id;
    private String name;
    private Long categoryId;
    private int type;//1 供应 2备案资产 3处置

    public Upload_Supply_Body(BaseRequest baseRequest) {
        super(baseRequest);
    }

    /**
     * 供应数量
     */

    private Integer num;
    /**
     * 供应估值
     */
   private String valueStr;
    /**
     * 评估机构
     */
    private String evaluation;
    /**
     * 归属地
     */
    private String area;
    /**
     * 证明图片
     */
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
