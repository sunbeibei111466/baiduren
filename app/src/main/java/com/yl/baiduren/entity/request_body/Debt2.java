package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2017/12/19.
 */

public class Debt2 extends BaseRequest {

    private Integer process;//步骤 1.保存第一页数据 2.保存第二页数据 3.保存第三页数据 4.保存第四页数据
    private DebtRelationVo2 debtRelationVo2;

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public DebtRelationVo2 getDebtRelationVo2() {
        return debtRelationVo2;
    }

    public void setDebtRelationVo2(DebtRelationVo2 debtRelationVo2) {
        this.debtRelationVo2 = debtRelationVo2;
    }
}
