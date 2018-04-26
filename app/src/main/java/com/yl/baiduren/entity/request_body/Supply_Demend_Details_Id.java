package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by sunbeibei on 2018/1/25.
 */

public class Supply_Demend_Details_Id extends BaseRequest{

    private Long id;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Supply_Demend_Details_Id(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
