package com.yl.baiduren.utils;

import android.content.Context;

import com.greendao.DaoMaster;
import com.greendao.DaoSession;

/**
 * Created by Android_apple on 2017/12/1.
 */

public class GreenDaoUtils {

    public static DaoSession daoSession;
    public static GreenDaoUtils greenDaoUtils;


    public static DaoSession getInstance(Context context) {
        if (greenDaoUtils == null) {
            synchronized (GreenDaoUtils.class) {
                if (greenDaoUtils == null) {
                    DaoMaster.DevOpenHelper db = new DaoMaster.DevOpenHelper(context, "bdr-db", null);
                    DaoMaster daoMaster = new DaoMaster(db.getWritableDb());
                    daoSession = daoMaster.newSession();
                }
            }
        }
        return daoSession;
    }



}
