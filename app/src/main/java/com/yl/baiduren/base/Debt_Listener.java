package com.yl.baiduren.base;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/25.
 */

public interface Debt_Listener {

    /** 时间回掉
     *
     * @param start
     * @param end
     * @param isSettled true 为已摘牌，false为未摘牌
     */
    void onTimeListener(Long start,Long  end,boolean isSettled);


    void onTypeListener(List<Long> types,boolean isSettled);

    void onAreaListener(String quCode,boolean isSettled);

    void onSaiXuanListener(Long createTimeMin,Long createTimeMax,String areaCode,Long amount,Double
            jiezai,Integer assertId,Integer demandId,boolean isSettled);
}
