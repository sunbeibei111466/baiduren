package com.yl.baiduren.activity.mypager;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.FeedBackMode;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/18.
 * 意见反馈
 */

public class FeedBack extends BaseActivity {

    private ImageView iv_feed_finish;
    private Button confit;
    private EditText opinion;

    @Override
    public int loadWindowLayout() {
        return R.layout.feed_back;
    }

    @Override
    public void initViews() {
        iv_feed_finish = findViewById(R.id.iv_feed_back);
        iv_feed_finish.setOnClickListener(listener);
        confit = findViewById(R.id.feed_back_comfit);//提交
        confit.setOnClickListener(listener);
        opinion = findViewById(R.id.et_opinion);//意见


    }

    private View.OnClickListener listener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            if (v == iv_feed_finish) {
                finish();
            } else if (v == confit) {
                String message = opinion.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    ToastUtil.showShort(FeedBack.this, "请输入您的意见或建议");
                    return;
                }

                List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(FeedBack.this).getBaseRequestDao().loadAll();
                FeedBackMode feedBackMode = new FeedBackMode(DataWarehouse.getBaseRequest(FeedBack.this));
                feedBackMode.setText(message);
                feed(feedBackMode);
            }

        }
    };

    /**
     *
     * 提交意见反馈
     * @param feedBackMode
     */
    private void feed(FeedBackMode feedBackMode) {
        String json = Util.toJson(feedBackMode);//转成json
        LUtils.e("---json----", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer().
                feedback(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                         finish();
                    }
                });
    }
}
