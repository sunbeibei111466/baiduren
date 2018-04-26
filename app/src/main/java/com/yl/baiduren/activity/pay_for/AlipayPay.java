package com.yl.baiduren.activity.pay_for;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;


import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.yl.baiduren.entity.result.PayResult;
import com.yl.baiduren.utils.ToastUtil;

import java.util.Map;

import static com.yl.baiduren.utils.Constant.ALIPAY_PAY_FLAG;

/**
 * Created by Android_apple on 2018/3/9.
 */

public class AlipayPay {

    private static Activity mActivity = null;


    public static void pay(final Activity activity, final String orderInfo) {
        mActivity = activity;
        Gson gson = new Gson();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = ALIPAY_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private static MyHandler mHandler = new MyHandler(mActivity);

    private static class MyHandler extends Handler {


        public MyHandler(Activity activity) {
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case ALIPAY_PAY_FLAG://支付宝支付
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    judgmentState(payResult.getResultStatus());
                    break;
            }
        }
    }


    public static void judgmentState(String state) {
        switch (state) {
            case "9000"://订单支付成功
                mActivity.startActivity(new Intent(mActivity, PaySuccess.class));
                mActivity.finish();
                break;
            case "4000"://订单支付失败
                ToastUtil.showShort(mActivity, "订单支付失败");
                break;
            case "6001"://用户中途取消
                ToastUtil.showShort(mActivity, "用户取消操作");
                break;
            case "6002"://网络连接出错
                ToastUtil.showShort(mActivity, "网络连接出错");
                break;
            case "6004"://支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
                ToastUtil.showShort(mActivity, "支付结果未知，请查询商户订单列表中订单的支付状态");
                break;
            case "5000"://重复请求
                ToastUtil.showShort(mActivity, "重复请求");
                break;
        }
    }

}
