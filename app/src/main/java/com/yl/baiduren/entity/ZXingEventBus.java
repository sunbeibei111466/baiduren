package com.yl.baiduren.entity;

/**
 * Created by Android_apple on 2017/12/14.
 */

public class ZXingEventBus {

    private boolean isClick;

    public ZXingEventBus(boolean isClick) {
        this.isClick = isClick;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
