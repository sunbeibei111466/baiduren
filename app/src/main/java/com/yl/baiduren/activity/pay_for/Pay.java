package com.yl.baiduren.activity.pay_for;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Break_Debt_Activity;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.CallWxBean;
import com.yl.baiduren.entity.request_body.My_Bill_Details_Body;
import com.yl.baiduren.entity.result.My_Bill_Details_Result;
import com.yl.baiduren.entity.result.PayEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.utils.Constant.ALIPAY_PAY_TYPE;
import static com.yl.baiduren.utils.Constant.WEIXIN_PAY_TYPE;

/**
 * Created by sunbeibei on 2017/12/18.
 * 支付页
 */

public class Pay extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private ImageView pay_back;
    private TextView pay_num;
    private TextView pay_timer;
    private ImageView pay_header;
    private TextView pay_type;
    private TextView pay_name;
    private TextView pay_phone;
    private TextView pay_amout;
    private RadioGroup pay_group;
    private Button cancle_pay;
    private Button pay;
    private TextView pay_argnment;
    private Long orderId;//订单Id
    private boolean isSelect = true;
    private int payType = 0;
    private LinearLayout id_num;
    private TextView detail_code_num;
    private Long id;//债事id


    @Override
    public int loadWindowLayout() {
        return R.layout.pay;
    }

    @Override
    public void initViews() {
        orderId = getIntent().getLongExtra("orderId", 0);
        pay_back = findViewById(R.id.pay_back);
        pay_back.setOnClickListener(listener);
        //订单号
        pay_num = findViewById(R.id.pay_num);
        //创建时间
        pay_timer = findViewById(R.id.pay_timer);
        //备案标号布局
        id_num = findViewById(R.id.id_num);
        //备案标号
        detail_code_num = findViewById(R.id.detail_code_num);
        detail_code_num.setOnClickListener(listener);
        //支付头像
        pay_header = findViewById(R.id.pay_header);
        //开通类型
        pay_type = findViewById(R.id.pay_type);
        //昵称
        pay_name = findViewById(R.id.pay_name);
        //支付手机号
        pay_phone = findViewById(R.id.pay_phone);
        pay_argnment = findViewById(R.id.pay_argnment);//支付协议
        pay_argnment.setOnClickListener(listener);
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);

        //付款金额
        pay_amout = findViewById(R.id.pay_amout);
        //支付方式
        pay_group = findViewById(R.id.pay_group);
        pay_group.setOnCheckedChangeListener(changeListener);
        //取消支付
        cancle_pay = findViewById(R.id.cancle_pay);
        cancle_pay.setOnClickListener(listener);

        //支付
        pay = findViewById(R.id.pay);
        pay.setOnClickListener(listener);

        if (orderId != 0) {
            requestBill_Details();
        }
    }


    private void requestBill_Details() {//查看详情
        My_Bill_Details_Body body = new My_Bill_Details_Body(DataWarehouse.getBaseRequest(this));
        body.setBillId(orderId);
        String json = Util.toJson(body);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getMyBill_Details(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<My_Bill_Details_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<My_Bill_Details_Result>(this) {
                    @Override
                    protected void onSuccees(String code, My_Bill_Details_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(Pay.this, baseResponse);
                            id=data.getDebtrelationId();
                            pay_num.setText(data.getBillCode());
                            pay_timer.setText(Util.LongParseString(data.getCreateTime()));
                            Glide.with(Pay.this)
                                    .load(data.getUserImage())
                                    .bitmapTransform(new CropCircleTransformation(Pay.this))
                                    .crossFade(1000)
                                    .into(pay_header);
                            if (data.getType() == 1) {
                                pay_type.setText("开通企业VIP");
                            } else if (data.getType() == 2) {
                                pay_type.setText("开通个人VIP");
                            } else if (data.getType() == 3) {
                                pay_type.setText("解债人摘牌");
                                id_num.setVisibility(View.VISIBLE);
                                detail_code_num.setText(data.getDebtRelationCode());
                            } else if (data.getType() == 4) {
                                pay_type.setText("充值余额");
                            } else if (data.getType() == 5) {
                                pay_type.setText("债权转让");
                            }else if (data.getType()==6){
                                pay_type.setText(data.getSearchName()+"大众版征信报告");
                            }else if(data.getType()==7){
                                pay_type.setText(data.getSearchName()+"基础版征信报告");
                            }else if (data.getType()==8){
                                pay_type.setText(data.getSearchName()+"深度版征信报告");
                            }else if (data.getType()==9){
                                pay_type.setText(data.getSearchName()+"征信报告");
                            }
                            pay_name.setText(data.getUserNickName());
                            pay_phone.setText(data.getMobile());
                            pay_amout.setText("-￥" + data.getPriceStr());

                        }

                    }
                });
    }

    private void cancle_Order() {//取消订单

        My_Bill_Details_Body body = new My_Bill_Details_Body(DataWarehouse.getBaseRequest(this));
        body.setId(orderId);
        String json = Util.toJson(body);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .cancle_Bill(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(Pay.this, baseResponse);
                            ToastUtil.showShort(Pay.this, "订单取消成功");
                            finish();
                        }
                    }
                });


    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == pay_back) {
                finish();
            } else if (v == cancle_pay) {//取消订单
                if (orderId != 0) {
                    cancle_Order();
                }

            } else if (v == pay_argnment) {
                Intent intent = new Intent();
                intent.setClass(Pay.this, Break_Debt_Activity.class);
                intent.putExtra("url", ServiceUrl.H5_PAY_AGREEMENT);
                intent.putExtra("type", 4);
                startActivity(intent);

            } else if (v == pay) {
                if (orderId == 0) {
                    ToastUtil.showShort(Pay.this, "获取订单Id失败");
                    return;
                }

                if (!isSelect) {
                    ToastUtil.showShort(Pay.this, "请勾选支付协议");
                    return;
                }

                if (payType == 0) {
                    ToastUtil.showShort(Pay.this, "请选择支付方式");
                    return;
                }

                if (UserInfoUtils.IsLogin(Pay.this)) {
                    PayEntity entity = new PayEntity(DataWarehouse.getBaseRequest(Pay.this));
                    entity.setBillId(orderId);
                    entity.setPayType(payType);
                    if (payType == ALIPAY_PAY_TYPE) { //支付宝
                        getOrderInfoZFB(entity);
                    }else {//微信
                        getOrderInfoWx(entity);
                    }
                } else {
                    ToastUtil.showShort(Pay.this, "请先登录");
                }
            }else if (v==detail_code_num){
                Intent intent =new Intent(Pay.this,Debt_Details.class);
                intent.putExtra("id",id);
                intent.putExtra("comePager",Constant.PAGER_DEBTHALL_WZP);
                startActivity(intent);
            }

        }
    };
    private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (group == pay_group) {
                if (checkedId == R.id.rdb_wetch_pay) {//微信
                    payType = WEIXIN_PAY_TYPE;
                } else if (checkedId == R.id.rab_ail_pay) {//支付宝
                    payType = ALIPAY_PAY_TYPE;
                }
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isSelect = isChecked;
    }

    public void getOrderInfoZFB(PayEntity entity) {

        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(Pay.this).getServer()
                .getCreatePay(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(Pay.this) {

                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            AlipayPay.pay(Pay.this, data);
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String mess) throws Exception {
                        super.onCodeError(code, mess);
                        ToastUtil.showShort(Pay.this, "获取订单失败,请稍后再试");
                    }
                });
    }

    public void getOrderInfoWx(PayEntity entity) {

        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(Pay.this).getServer()
                .getWxCreatePay(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<CallWxBean>>bindToLifecycle()))
                .subscribe(new BaseObserver<CallWxBean>(Pay.this) {

                    @Override
                    protected void onSuccees(String code, CallWxBean data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            WXPay.weixPay(Pay.this,data);
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String mess) throws Exception {
                        super.onCodeError(code, mess);
                        ToastUtil.showShort(Pay.this, "获取订单失败,请稍后再试");
                    }
                });
    }
}
