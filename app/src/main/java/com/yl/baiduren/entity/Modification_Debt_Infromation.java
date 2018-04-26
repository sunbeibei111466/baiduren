package com.yl.baiduren.entity;

import com.yl.baiduren.entity.result.Debt_Details_Result;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/12/22.
 */

public class Modification_Debt_Infromation implements Serializable {
    private Integer id;
    private Debt_Details_Result.DebtRelationResponse1Bean debtRelationResponse1;

    public Modification_Debt_Infromation(Integer id, Debt_Details_Result.DebtRelationResponse1Bean debtRelationResponse1) {
        this.id = id;
        this.debtRelationResponse1 = debtRelationResponse1;

    }

    public Debt_Details_Result.DebtRelationResponse1Bean getDebtRelationResponse1() {
        return debtRelationResponse1;
    }

    public void setDebtRelationResponse1(Debt_Details_Result.DebtRelationResponse1Bean debtRelationResponse1) {
        this.debtRelationResponse1 = debtRelationResponse1;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
