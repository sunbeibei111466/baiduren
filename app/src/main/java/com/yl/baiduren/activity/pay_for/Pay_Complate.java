package com.yl.baiduren.activity.pay_for;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Debt_Details;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.My_Bill_Details_Body;
import com.yl.baiduren.entity.result.My_Bill_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/19.
 * 账单详情
 */

public class Pay_Complate extends BaseActivity {

    private ImageView pay_complate_back;
    private TextView pay_complate_num;
    private TextView pay_complate_timer;
    private ImageView pay_complate_header;
    private TextView pay_complate_type;
    private TextView pay_complate_name;
    private TextView pay_complate_phone;
    private TextView pay_complate_amout;
    private Button delete_complate_pay;
    private Button renewal_btn;
    private Long orderId;//订单Id
    private int type=0;
    private Long debtId;
    private LinearLayout pay_lay;
    private TextView pay_id_num;
    private Long id;//债事id;

    @Override
    public int loadWindowLayout() {
        return R.layout.pay_complate;
    }

    @Override
    public void initViews() {
        orderId = getIntent().getLongExtra("orderId", 0);
        pay_complate_back = findViewById(R.id.pay_complate_back);
        pay_complate_back.setOnClickListener(listener);
        //订单号
        pay_complate_num = findViewById(R.id.pay_complate_num);
        //创建时间
        pay_complate_timer = findViewById(R.id.pay_complate_timer);
        //备案编号布局
        pay_lay = findViewById(R.id.pay_lay);
        //备案编号
        pay_id_num = findViewById(R.id.pay_id_num);
        pay_id_num.setOnClickListener(listener);

        //支付头像
        pay_complate_header = findViewById(R.id.pay_complate_header);
        //支付产品类型
        pay_complate_type = findViewById(R.id.pay_complate_type);
        //昵称
        pay_complate_name = findViewById(R.id.pay_complate_name);
        //手机号
        pay_complate_phone = findViewById(R.id.pay_complate_phone);
        pay_complate_back.setOnClickListener(listener);
        //实付金额
        pay_complate_amout = findViewById(R.id.pay_complate_amout);
        //删除订单
        delete_complate_pay = findViewById(R.id.delete_complate_pay);
        delete_complate_pay.setOnClickListener(listener);
        //续费
        renewal_btn = findViewById(R.id.renewal_btn);
        renewal_btn.setOnClickListener(listener);

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
                            UserInfoUtils.setUuid(Pay_Complate.this, baseResponse);
                            id=data.getDebtrelationId();
                            pay_complate_num.setText(data.getBillCode());
                            pay_complate_timer.setText(Util.LongParseString(data.getCreateTime()));
                            Glide.with(Pay_Complate.this)
                                    .load(data.getUserImage())
                                    .bitmapTransform(new CropCircleTransformation(Pay_Complate.this))
                                    .crossFade(1000)
                                    .into(pay_complate_header);
                            type=data.getType();
                            if (type == 1) {
                                pay_complate_type.setText("开通企业VIP");
                            } else if (type == 2) {
                                pay_complate_type.setText("开通个人VIP");
                            } else if (type == 3) {
                                pay_lay.setVisibility(View.VISIBLE);
                                pay_complate_type.setText("解债人摘牌");
                                pay_id_num.setText(data.getDebtRelationCode());
                            } else if (type == 4) {
                                pay_complate_type.setText("充值余额");
                            } else if (type == 5) {
                                pay_complate_type.setText("债权转让");
                            }else if (data.getType()==6){
                                pay_complate_type.setText(data.getSearchName()+"大众版征信报告");
                                renewal_btn.setVisibility(View.GONE);
                            }else if(data.getType()==7){
                                pay_complate_type.setText(data.getSearchName()+"基础版征信报告");
                                renewal_btn.setVisibility(View.GONE);
                            }else if (data.getType()==8){
                                pay_complate_type.setText(data.getSearchName()+"深度版征信报告");
                                renewal_btn.setVisibility(View.GONE);
                            }else if (data.getType()==9){
                                pay_complate_type.setText(data.getSearchName()+"征信报告");
                                renewal_btn.setVisibility(View.GONE);
                            }
                            pay_complate_name.setText(data.getUserNickName());
                            pay_complate_phone.setText(data.getMobile());
                            pay_complate_amout.setText("-￥" + data.getPriceStr());
                            debtId=data.getDebtrelationId();

                        }

                    }
                });
    }

    private View.OnClickListener listener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v==pay_complate_back){
                finish();
            }else if(v==renewal_btn){
                if (type == 1 || type == 2) {//会员续费
                    startActivity(new Intent(Pay_Complate.this, Open_Member.class));
                } else if (type == 3) {//摘牌续费
                    if(debtId!=0){
                        startActivity(new Intent(Pay_Complate.this, Confirm_Delisting.class).putExtra("debtid", debtId));
                    }
                } else if (type == 4) {//备案次数续费
                    startActivity(new Intent(Pay_Complate.this, Recharge.class));

                } else if (type == 5) {
                }
            }else if(v==delete_complate_pay){//删除订单
//                delete_Order();
            }else if (v==pay_id_num){//跳入债事详情
                Intent intent =new Intent(Pay_Complate.this,Debt_Details.class);
                intent.putExtra("id",id);
                intent.putExtra("comePager",Constant.PAGER_MY_ZHAIPAI);
                startActivity(intent);

            }

        }
    };

    private void delete_Order() {//删除订单

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
                            UserInfoUtils.setUuid(Pay_Complate.this, baseResponse);
                            ToastUtil.showShort(Pay_Complate.this, "删除订单成功");
                            finish();
                        }
                    }
                });


    }
}
