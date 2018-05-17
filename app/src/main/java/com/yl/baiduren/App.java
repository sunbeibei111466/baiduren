package com.yl.baiduren;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.google.gson.Gson;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.credit_reporting_queries.Authorization_Record;
import com.yl.baiduren.activity.credit_reporting_queries.Authorizaton_Confrim_List;
import com.yl.baiduren.activity.debtbunesshall.My_Record_Activity;
import com.yl.baiduren.activity.mypager.My_Delisting;
import com.yl.baiduren.activity.pay_for.Open_Member;
import com.yl.baiduren.data.BaseRequest;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.Gilde_ImageLoder;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SharedUtil;
import com.yl.baiduren.utils.UserInfoUtils;

import java.util.Map;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * @author 王锋 on 2017/11/15.
 */

public class App extends Application {

    private static Context context;
    private PushAgent mPushAgent;
    public BaseRequest baseRequest=new BaseRequest();//用户基础参数

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        UserInfoUtils.setBaseRequest(context);
        //友盟推送
        regisierYouMeng();
        initInfo();
        listeninf();



        //图片选择
        initCamera();
        //全局异常捕获累类
        CrashHandler.getInstance().init(this);
        //是否需要打印log  上线后关闭
        LUtils.isDebug = true;
        //初始化各类sdk等
        MyInitializeService.start(context);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 注册友盟
     */
    private void regisierYouMeng() {
        mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                LUtils.e("++++------注册成功--------++++++" + deviceToken);
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {
                LUtils.e("++++------注册失败--------++++++" + s + "____________" + s1);
            }
        });
    }

    private void initInfo() {
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            //启动app
            @Override
            public void launchApp(Context context, UMessage uMessage) {
                super.launchApp(context, uMessage);
                Gson gson = new Gson();
                String string = gson.toJson(uMessage);
                LUtils.e("----launchApp------" + string);

                for (Map.Entry<String, String> entry : uMessage.extra.entrySet()) {

                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                    if (entry.getKey().equals("type")) {
                        LUtils.e("-------标识-----" + entry.getValue());
                        //1成为vip 调到我的页面   //5 成为解债人  我的页面
                        if (entry.getValue().equals("1") || entry.getValue().equals("5")|| entry.getValue().equals("8")|| entry.getValue().equals("11")) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("currMenu", 3);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else if (entry.getValue().equals("2")) {//vip到期 调到   vip充值页
                            if (UserInfoUtils.IsLogin(context)) {
                                startActivity(new Intent(context, Open_Member.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                startActivity(new Intent(context, Login_Activity_Password.class));
                            }
                        } else if (entry.getValue().equals("3") || entry.getValue().equals("6")|| entry.getValue().equals("10")) { //备案被摘牌 提醒备案人 跳转到我的备案已摘牌页
                            if (UserInfoUtils.IsLogin(context)) {
                                startActivity(new Intent(context, My_Record_Activity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        .putExtra(Constant.MESSAGE_INDEX, true));
                            } else {
                                startActivity(new Intent(context, Login_Activity_Password.class));
                            }
                        } else if (entry.getValue().equals("4") || entry.getValue().equals("7")|| entry.getValue().equals("9")) {
                            //4 摘牌到期提醒摘牌人 6摘牌人完成提醒备案人 7备案人完成提醒摘牌人  我的摘牌
                            if (UserInfoUtils.IsLogin(context)) {
                                startActivity(new Intent(context, My_Delisting.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                startActivity(new Intent(context, Login_Activity_Password.class));
                            }
                        }else if(entry.getValue().equals("12")){//购买信用报告
//                        if (UserInfoUtils.IsLogin(context)) {
//                            context.startActivity(new Intent(context, Authorization_Record.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                        } else {
//                            context.startActivity(new Intent(context, Login_Activity_Password.class));
//                        }
                        }
                        else if(entry.getValue().equals("13")){ //給被授权申请人发 ---> 授权接收 回复
                            //
                            if (UserInfoUtils.IsLogin(context)) {
                                context.startActivity(new Intent(context, Authorization_Record.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                context.startActivity(new Intent(context, Login_Activity_Password.class));
                            }
                        }else if(entry.getValue().equals("14")){//給授权申请人发 ---> 授权申请
                            //
                            if (UserInfoUtils.IsLogin(context)) {
                                context.startActivity(new Intent(context, Authorizaton_Confrim_List.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            } else {
                                context.startActivity(new Intent(context, Login_Activity_Password.class));
                            }
                        }
                    }
                }
            }

            //打开url
            @Override
            public void openUrl(Context context, UMessage uMessage) {
                super.openUrl(context, uMessage);
                Gson gson = new Gson();
                String string = gson.toJson(uMessage);
                LUtils.e("----openUrl------" + string);
            }

        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
    }

    /**
     * 监听消息到达
     */
    private void listeninf(){
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage msg) {
                switch (msg.builder_id) {
                    case 1:
                    default:
                        //消息到设置达标记
                        SharedUtil.getSharedUtil().putBoolean(context, Constant.IS_INFO, true);
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
                        sendBroadcast(mIntent);
                        //默认为0，若填写的builder_id并不存在，也使用默认。
                        return super.getNotification(context, msg);
                }
            }
        };
        mPushAgent.setMessageHandler(messageHandler);
    }

    private void initCamera() {
        /**
         * 调用相机动态权限
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        /*照片选着
          设置主题
         */
        //ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(R.color.material_blue)
                .setTitleBarTextColor(R.color.light_hei).build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(9).build();

        //配置imageloader
        ImageLoader imageloader = new Gilde_ImageLoder();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloader, theme)
               /* .setDebug(BuildConfig.DEBUG)*/
                .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(coreConfig);
    }


}
