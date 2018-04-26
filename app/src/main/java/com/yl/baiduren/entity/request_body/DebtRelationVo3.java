package com.yl.baiduren.entity.request_body;

import com.yl.baiduren.entity.greenentity.AssetDO;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/20.
 */

public class DebtRelationVo3 {

    private List<AssetDO> asset;
    private Long debtRelationId;

    public List<AssetDO> getAsset() {
        return asset;
    }

    public void setAsset(List<AssetDO> asset) {
        this.asset = asset;
    }

    public Long getDebtRelationId() {
        return debtRelationId;
    }

    public void setDebtRelationId(Long debtRelationId) {
        this.debtRelationId = debtRelationId;
    }
}
