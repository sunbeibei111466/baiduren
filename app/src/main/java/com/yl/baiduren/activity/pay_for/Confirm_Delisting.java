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

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Debt_Details_Entity;
import com.yl.baiduren.entity.request_body.Greate_Order_Body;
import com.yl.baiduren.entity.result.Create_Order_Result;
import com.yl.baiduren.entity.result.Delist_Information_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/18.
 * 确认摘牌
 */

public class Confirm_Delisting extends BaseActivity {

    public ImageView delisting_back, delisting_header;
    public TextView delisting_adress, delisting_timer, delisting_appen_timer,
            delisting_break_aoumt, delisting_price, delisting_amout,infoumation_content;
    private EditText delisting_num;
    private Button pay_delisting;
    private Long debtid;
    private int days;
    private String price;
    private TextView sing_price_yuanjia, sing_price_xianjia;
    private String priceStr = null;

    @Override
    public int loadWindowLayout() {
        return R.layout.confirm_delisting;
    }

    @Override
    public void initViews() {
        debtid = getIntent().getLongExtra("debtid", 0);//债事id
        delisting_back = findViewById(R.id.delisting_back);
        delisting_back.setOnClickListener(listener);
        //头像
        delisting_header = findViewById(R.id.delisting_header);
        //债事地址
        delisting_adress = findViewById(R.id.delisting_adress);
        //债事登记时间
        delisting_timer = findViewById(R.id.delisting_timer);
        //债事金额
        delisting_amout = findViewById(R.id.delisting_amout);
        //债事发生时间
        delisting_appen_timer = findViewById(R.id.delisting_appen_timer);
        infoumation_content=findViewById(R.id.infoumation_content);//说明内容
        //解债佣金
        delisting_break_aoumt = findViewById(R.id.delisting_break_aoumt);
        //摘牌天数
        delisting_num = findViewById(R.id.delisting_num);
        delisting_num.addTextChangedListener(textWatcher);
        //总金额
        delisting_price = findViewById(R.id.delisting_price);
        //支付
        pay_delisting = findViewById(R.id.pay_delisting);
        pay_delisting.setOnClickListener(listener);
        sing_price_yuanjia = findViewById(R.id.sing_price_yuanjia);//摘牌原价
        sing_price_xianjia = findViewById(R.id.sing_price_xianjia);//活动价格


        if (debtid != 0) {
            requestWork();
        }
    }


    private void requestWork() {
        Debt_Details_Entity entity = new Debt_Details_Entity(DataWarehouse.getBaseRequest(this));
        entity.setId(debtid);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
       String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getInfromation(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Delist_Information_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Delist_Information_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Delist_Information_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(Confirm_Delisting.this, baseResponse);
                            Glide.with(Confirm_Delisting.this)
                                    .load(data.getUserImage())
                                    .bitmapTransform(new CropCircleTransformation(Confirm_Delisting.this))
                                    .crossFade(1000)
                                    .into(delisting_header);//摘牌人头像
                            delisting_adress.setText(data.getAreaName());
                            delisting_timer.setText("债事登记时间:" + Util.LongParseString(data.getCreateTime()));
                            delisting_amout.setText(data.getAmountStr());
                            delisting_appen_timer.setText(Util.LongParseString(data.getRecordTime()));
                            delisting_break_aoumt.setText(data.getCommission() * 100 + "%");
                            infoumation_content.setText(data.getDetail());

                            priceStr = data.getPriceStr();
                            if (!data.getPriceStr().equals(data.getOriginalPriceStr())) {
                                sing_price_xianjia.setText("现价:￥" + data.getPriceStr() + "/天");
                                sing_price_yuanjia.setText("原价:￥" + data.getOriginalPriceStr() + "/天");
                                sing_price_yuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
                            } else {
                                sing_price_xianjia.setText("现价:￥" + data.getOriginalPriceStr() + "/天");
                            }
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String mess) throws Exception {
                        super.onCodeError(code, mess);
                        ToastUtil.showShort(Confirm_Delisting.this,mess);
                        LUtils.e(mess+"___________________________________");
                    }
                });
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == delisting_back) {
                finish();
            } else if (v == pay_delisting) {
                if (TextUtil.isEmpty(delisting_num.getText().toString().trim())) {
                    ToastUtil.showShort(Confirm_Delisting.this, "请输入摘牌天数");
                    return;
                }
                String num = delisting_num.getText().toString().trim();
                days = Integer.valueOf(num);
                if (days < 20) {
                    ToastUtil.showShort(Confirm_Delisting.this, "摘牌天数最少20天");
                    return;
                }
                create_Order();

            }

        }
    };


    private void create_Order() {//生成订单
        Greate_Order_Body entity = new Greate_Order_Body(DataWarehouse.getBaseRequest(this));
        entity.setDuration(days);
        entity.setType(3);//摘牌支付类型
        entity.setDebtRelationId(debtid);
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
                            UserInfoUtils.setUuid(Confirm_Delisting.this, baseResponse);
                            Intent intent = new Intent();
                            intent.putExtra("orderId", data.getId());
                            intent.setClass(Confirm_Delisting.this, Pay.class);
                            startActivity(intent);
                        }

                    }

                });
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String s1 = s.toString();
            if (!TextUtils.isEmpty(s1)) {
                int days = Integer.parseInt(s1);
                delisting_num.setSelection(delisting_num.getText().toString().length());//文本的光标位置为文本末端
                if (!TextUtil.isEmpty(priceStr)) {
                    int i1 = Integer.valueOf(priceStr) * days;
//                price = Long.valueOf(i1)+"";
                    delisting_price.setText("￥" + i1);
                }
            }
        }
    };

}
