package com.yl.baiduren.entity.result;

/**
 * Created by sunbeibei on 2017/12/13.
 */

public class Debt_Lie_Biao_Moldel {
    private int header;

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public String getDebt_adress() {
        return debt_adress;
    }

    public void setDebt_adress(String debt_adress) {
        this.debt_adress = debt_adress;
    }

    public String getDengji_timer() {
        return dengji_timer;
    }

    public void setDengji_timer(String dengji_timer) {
        this.dengji_timer = dengji_timer;
    }

    public String getDebt_amout() {
        return debt_amout;
    }

    public void setDebt_amout(String debt_amout) {
        this.debt_amout = debt_amout;
    }

    public String getAppen_timer() {
        return appen_timer;
    }

    public void setAppen_timer(String appen_timer) {
        this.appen_timer = appen_timer;
    }

    public String getBreak_armout() {
        return break_armout;
    }

    public void setBreak_armout(String break_armout) {
        this.break_armout = break_armout;
    }

    private String debt_adress;
    private String dengji_timer;
    private String debt_amout;
    private String appen_timer;
    private String break_armout;
}
