package com.yl.baiduren.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yl.baiduren.activity.pay_for.PaySuccess;
import com.yl.baiduren.utils.LUtils;

import static com.yl.baiduren.utils.Constant.WEIXIN_ADDID;

public class WXPayEntryActivity extends Activity
        implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WEIXIN_ADDID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            switch (resp.errCode) {
                case 0:
                    Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, PaySuccess.class));
                    break;
                case -1:
                    Toast.makeText(this, resp.errStr, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case -2:
                    Toast.makeText(this, "您取消了操作", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
        LUtils.e("---***-----微信支付--错误----------***" + resp.errCode);
    }
}
