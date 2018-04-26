package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.base.BaseRequest;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class Debt4 extends BaseRequest{

    private Integer process;//步骤 1.保存第一页数据 2.保存第二页数据 3.保存第三页数据 4.保存第四页数据
    private DebtRelationVo4 debtRelationVo4;

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public DebtRelationVo4 getDebtRelationVo4() {
        return debtRelationVo4;
    }

    public void setDebtRelationVo4(DebtRelationVo4 debtRelationVo4) {
        this.debtRelationVo4 = debtRelationVo4;
    }
}
