package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.entity.greenentity.DemandDO;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/19.
 */

public class DebtRelationVo2 {

    private List<DemandDO> demand;
    private Long debtRelationId;
    public List<DemandDO> getDemand() {
        return demand;
    }

    public void setDemand(List<DemandDO> demand) {
        this.demand = demand;
    }

    public Long getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(Long debtRelationId) {
        this.debtRelationId = debtRelationId;
    }
}
