package com.yl.baiduren.data;

import android.content.Context;

import com.yl.baiduren.App;

/**
 * Created by Android_apple on 2018/3/15.
 */

public class DataWarehouse {

    public  static App getApplication(Context ctx){
        return (App) ctx.getApplicationContext();
    }

    public static BaseRequest getBaseRequest(Context context){
        return getApplication(context).baseRequest;
    }
}
