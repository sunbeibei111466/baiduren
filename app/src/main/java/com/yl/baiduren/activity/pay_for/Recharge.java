package com.yl.baiduren.activity.pay_for;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Greate_Order_Body;
import com.yl.baiduren.entity.request_body.My_Record_Entity;
import com.yl.baiduren.entity.result.Create_Order_Result;
import com.yl.baiduren.entity.result.Recharge_Price_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/18.
 * 备案充值
 */

public class Recharge extends BaseActivity implements TextWatcher{

    private EditText put_info_num;
    private TextView recharge_price;
    private Button pay_recharge;
    private ImageView record_back;
    private String beian_num;
    private TextView sing_price_yj,sing_price_hdjg,recharge_content;
    private String priceStr;

    @Override
    public int loadWindowLayout() {
        return R.layout.recharge;
    }

    @Override
    public void initViews() {
        record_back = findViewById(R.id.record_back);
        record_back.setOnClickListener(listener);
        recharge_content=findViewById(R.id.recharge_content);//充值内容


        //输入备案次数
        put_info_num = findViewById(R.id.put_info_num);
        put_info_num.addTextChangedListener(this);//输入次数改变价钱的监听
        //总计
        recharge_price = findViewById(R.id.recharge_price);
        sing_price_hdjg = findViewById(R.id.sing_price_hdjg);//活动价格没有时 此处原价  有活动价格
        sing_price_yj=findViewById(R.id.sing_price_yj);//有活动价格时  此处原价
        //支付
        pay_recharge = findViewById(R.id.pay_recharge);
        pay_recharge.setOnClickListener(listener);
           requestWord();



    }
    private void requestWord() {
        My_Record_Entity entity = new My_Record_Entity(DataWarehouse.getBaseRequest(this));
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        final String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getDrSingle(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Recharge_Price_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Recharge_Price_Result>(this) {

                    protected void onSuccees(String code, Recharge_Price_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            if (!TextUtils.isEmpty(data.getDetail())){
                                recharge_content.setText(data.getDetail());
                            }
                          if (!TextUtils.isEmpty(data.getPriceStr())){

                              priceStr=data.getPriceStr();
                              if(!data.getPriceStr().equals(data.getOriginalPriceStr())){//有活动价格
                                  sing_price_hdjg.setText("现价:¥"+data.getPriceStr()+"/次");//活动价格
                                  sing_price_yj.setText("原价:¥"+data.getOriginalPriceStr()+"/次");//原价
                                  sing_price_yj.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
                              }else{
                                  sing_price_hdjg.setText("现价:¥"+data.getOriginalPriceStr()+"/次");//原价
                              }
                          }

                        }

                    }
                });
    }
    private View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v==pay_recharge){//跳转支付页生成订单
                if (TextUtil.isEmpty(put_info_num .getText().toString().trim())){
                    ToastUtil.showShort(Recharge.this,"请输入备案次数");
                    return;
                }
                create_Order();


            }else if (v==record_back){
                finish();
            }
        }
    };
    private void create_Order() {//生成订单
        Greate_Order_Body entity = new Greate_Order_Body(DataWarehouse.getBaseRequest(this));
        entity.setType(4);
        entity.setDebtrelationTimes(Integer.valueOf(beian_num));
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .create_Order(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Create_Order_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Create_Order_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Create_Order_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(Recharge.this, baseResponse);
                            Intent intent =new Intent();
                            intent.setClass(Recharge.this, Pay.class);
                            intent.putExtra("orderId",data.getId());
                            startActivity(intent);
                        }
                    }
                });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        beian_num = s.toString();
            if (!TextUtils.isEmpty(beian_num)){ //天数不为空
                put_info_num.setSelection(put_info_num.getText().toString().length());
                if (!TextUtils.isEmpty(priceStr)){ //钱数
                    long total_price = Long.valueOf(beian_num) * Integer.valueOf(priceStr);
                    recharge_price.setText("￥" + total_price);
                }

            }




    }
}
