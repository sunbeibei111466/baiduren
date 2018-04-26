package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.data.BaseRequest;

/**
 * Created by Android_apple on 2017/12/19.
 */

public class Debt1 extends BaseRequest{

    private Integer process;//步骤 1.保存第一页数据 2.保存第二页数据 3.保存第三页数据 4.保存第四页数据

    private DebtRelationDO debtRelationDO;

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public DebtRelationDO getDebtRelationDO() {
        return debtRelationDO;
    }

    public void setDebtRelationDO(DebtRelationDO debtRelationDO) {
        this.debtRelationDO = debtRelationDO;
    }

    public Debt1(BaseRequest baseRequest) {
        super(baseRequest);
    }
}
