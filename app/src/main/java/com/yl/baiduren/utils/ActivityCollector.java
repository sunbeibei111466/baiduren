package com.yl.baiduren.utils;

import android.app.Activity;
import android.content.Intent;

import com.yl.baiduren.activity.debtbunesshall.Debt_Buness_Hall;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android_apple on 2017/11/29.
 */

public class ActivityCollector {


    //定义一个用于存放Activity的集合
    public final static List<Activity> activityList = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    /**
     * 跳转到债事详情页
     * @param context
     */
    public static void intentIndex(Activity context){
        context.startActivity(new Intent(context, Debt_Buness_Hall.class).putExtra("back",1));
        context.finish();
    }

    /**
     * 点击返回，中断备案，统一返回债事列表
     * @param activity
     * @param debtId
     */
    public static void back(Activity activity,Long debtId){
        Intent intent=new Intent(activity, Debt_Details.class);
        intent.putExtra("id",debtId);
        intent.putExtra("back",1);
        intent.putExtra("comePager",Constant.PAGER_MYDEBT_WZP);
        activity.startActivity(intent);
    }

}
