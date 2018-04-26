package com.yl.baiduren;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import com.tencent.smtt.sdk.QbSdk;
import com.yl.baiduren.utils.LUtils;

/**
 * 初始化第三方sdk
 */
public class MyInitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.jlgproject.MyInitializeService";

    public MyInitializeService() {
        super("MyInitializeService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MyInitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
           if(ACTION_INIT_WHEN_APP_CREATE.equals(intent.getAction())){
               performInit();
           }
        }
    }

    private void performInit() {
        LUtils.e("------初始化--sdk--------");
        //友盟

        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), null);
        LUtils.e("---完成---初始化--sdk--------");




    }
}
