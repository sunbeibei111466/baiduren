package com.yl.baiduren.activity.pay_for;

import android.app.Activity;


import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yl.baiduren.entity.CallWxBean;

import static com.yl.baiduren.utils.Constant.WEIXIN_ADDID;

/**
 * Created by Android_apple on 2018/3/9.
 */

public class WXPay {

    public static void weixPay(final Activity context, CallWxBean callWxBean) {

        IWXAPI api = WXAPIFactory.createWXAPI(context, null);
//         将该app注册到微信
        api.registerApp(WEIXIN_ADDID);

        PayReq payReq = new PayReq();
        payReq.appId = callWxBean.getAppid();
        payReq.partnerId = callWxBean.getPartnerid();
        payReq.prepayId = callWxBean.getPrepayid();
        payReq.packageValue = callWxBean.getPackageX();
        payReq.nonceStr = callWxBean.getNoncestr();
        payReq.timeStamp = callWxBean.getTimestamp();
        payReq.sign = callWxBean.getSign();
        api.sendReq(payReq);


//        if (api.sendReq(payReq)) {
//            ToastUtil.showShort(context, "调起微信成功");
//        } else {
//            ToastUtil.showShort(context, "调起微信失败");
//        }
//        LUtils.e("----api.sendReq(payReq)------" + api.sendReq(payReq));
    }
}
