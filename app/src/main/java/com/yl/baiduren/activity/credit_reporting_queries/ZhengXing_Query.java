package com.yl.baiduren.activity.credit_reporting_queries;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.pay_for.Pay;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Greate_Order_Body;
import com.yl.baiduren.entity.request_body.RegisierEntity;
import com.yl.baiduren.entity.request_body.Report_Query_Body;
import com.yl.baiduren.entity.request_body.ValidCode;
import com.yl.baiduren.entity.request_body.Yan_Zheng_Code_Body;
import com.yl.baiduren.entity.result.Create_Order_Result;
import com.yl.baiduren.entity.result.ZhengXin_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2018/4/10.
 */

public class ZhengXing_Query extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText enterprise_full_name;
    private TextView proofreading;
    private CheckBox checkBox;
    private TextView tv_amount;
    private Button bt_topay;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private List<ZhengXin_Result.Category> category;
    private TextView suggests;
    private ImageView back_finish;
    private int price = 0;//价格
    private boolean isCheckBox8 = false;
    private boolean isCheckBox9 = false;
    private boolean whetherCeck = false;//是否点击校对
    private String strOld = null;//文字改变之前
    private String strNew = null;//文字改变之后
    private int num;//
    private List<Integer> list = new ArrayList<>();
    private Map<Integer, Integer> map = new HashMap<>();
    private Long assid;
    private boolean ischeck = true;

    @Override
    public int loadWindowLayout() {
        return R.layout.zhengxing_query;
    }

    @Override
    public void initViews() {
        assid = getIntent().getLongExtra("assid", 0);
        String conpany = getIntent().getStringExtra("conpany");
        back_finish = findViewById(R.id.credit_basic_finish);
        back_finish.setOnClickListener(this);
        suggests = findViewById(R.id.suggests);//征信说明
        enterprise_full_name = findViewById(R.id.enterprise_full_name);//企业全称
        enterprise_full_name.addTextChangedListener(textWatcher);
        enterprise_full_name.setText(conpany);
        proofreading = findViewById(R.id.proofreading);//校对
        proofreading.setOnClickListener(this);
        checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(this);
        tv_amount = findViewById(R.id.tv_amount);//金额
        bt_topay = findViewById(R.id.bt_topay);//支付
        bt_topay.setOnClickListener(this);
        checkBox1 = findViewById(R.id.checkbox1);//企业工商完整信息
        checkBox1.setOnCheckedChangeListener(this);
        checkBox2 = findViewById(R.id.checkbox2);//失信被执行人信息
        checkBox2.setOnCheckedChangeListener(this);
        checkBox3 = findViewById(R.id.checkbox3);//被执行人信息
        checkBox3.setOnCheckedChangeListener(this);
        checkBox4 = findViewById(R.id.checkbox4);//法院数据综合信息
        checkBox4.setOnCheckedChangeListener(this);
        checkBox5 = findViewById(R.id.checkbox5);//税务负面信息
        checkBox5.setOnCheckedChangeListener(this);
        checkBox6 = findViewById(R.id.checkbox6);//企业经营异常名录
        checkBox6.setOnCheckedChangeListener(this);
        checkBox7 = findViewById(R.id.checkbox7);//企业负面信息
        checkBox7.setOnCheckedChangeListener(this);
        CheckBox checkBox8 = findViewById(R.id.checkbox8);
        checkBox8.setOnCheckedChangeListener(this);
        CheckBox checkBox9 = findViewById(R.id.checkbox9);
        checkBox9.setOnCheckedChangeListener(this);
        EditText merchant_code = findViewById(R.id.merchant_code);
        EditText bank_ic_code = findViewById(R.id.bank_ic_code);
        EditText consumption_amount = findViewById(R.id.consumption_amount);
        requestWord();
    }

    private void requestWord() {
        if (UserInfoUtils.IsLogin(this)) {
            Report_Query_Body entity = new Report_Query_Body(DataWarehouse.getBaseRequest(this));
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

            BaseObserver baseObserver = new BaseObserver<ZhengXin_Result>(this) {


                @Override
                protected void onSuccees(String code, ZhengXin_Result data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        if (data != null) {
                            suggests.setText(data.getCreditReport());
                            category = data.getCategory();

                        }

                    }
                }
            };
            RetrofitHelper.getInstance(this).getServer()
                    .zheng_Xing(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<ZhengXin_Result>>bindToLifecycle()))
                    .subscribe(baseObserver);
        } else {
            finish();
        }
    }

    private void create_Order() {//生成订单
        Greate_Order_Body entity = new Greate_Order_Body(DataWarehouse.getBaseRequest(this));
        if (list.size() != 0) {
            entity.setBillPriceIds(list);
            entity.setType(9);
            entity.setSearchName(strNew);
            entity.setGenre(2);
            if (assid != 0) {
                entity.setSupplyId(assid);
            }

        }
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
                            UserInfoUtils.setUuid(ZhengXing_Query.this, baseResponse);
                            Long orderId = data.getId();
                            Intent intent = new Intent();//跳转支付界面
                            intent.setClass( ZhengXing_Query.this, Pay.class);
                            intent.putExtra("orderId", orderId);
                            startActivity(intent);
                        }
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            strOld = s.toString().trim();
            LUtils.e("------旧的字符串-----", strOld);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            LUtils.e("------正在改变字符串-----", s.toString().trim());
        }

        /**
         * 改变之后
         *
         * @param s
         */
        @Override
        public void afterTextChanged(Editable s) {
            strNew = s.toString().trim();
            LUtils.e("------新的字符串-----", strNew);
            if (num == 0) {
                num++;
                strOld = strNew;

            }
        }
    };


    @Override
    public void onClick(View v) {
        if (v == bt_topay) {//支付
            String creadeName = enterprise_full_name.getText().toString().trim();
            if (TextUtils.isEmpty(creadeName)) {
                ToastUtil.showShort(this, "请输入企业全称");
                return;
            }

            if (!whetherCeck) {
                ToastUtil.showShort(this, "请先校对");
                return;
            }

            if (!strOld.equals(strNew)) {
                whetherCeck = false;
                ToastUtil.showShort(this, "请重新校对");
                return;
            }
            if (!ischeck) {
                ToastUtil.showShort(this, "请同意委托方承诺书");
                return;

            }
            for (Integer key : map.keySet()) {
                list.add(map.get(key));
            }
            if (list.size() == 0) {
                ToastUtil.showShort(this, "请选择您所需要的信息");
                return;
            }
            create_Order();
        } else if (v == back_finish) {
            finish();


        } else if (v == proofreading) {//校对
            final String creadeName = enterprise_full_name.getText().toString().trim();
            if (TextUtils.isEmpty(creadeName)) {
                ToastUtil.showShort(this, "请输入企业全称");
                return;
            }
            PopupWindeUtils.showPopupWindeCredit(this, new PopupWindeUtils.OnButtonEventListener() {
                @Override
                public void onClickSend(String mobile) {
                    getValidCode(mobile);
                }

                @Override
                public void onConfirm(PopupWindow popupWindow, String code) {
                    requestNetWork(code, popupWindow,creadeName);
                }
            });
        }

    }

    private void requestNetWork(String code, final PopupWindow popupWindow, final String creadeName) {

        Yan_Zheng_Code_Body regisierEntity = new Yan_Zheng_Code_Body(DataWarehouse.getBaseRequest(this));
        regisierEntity.setValidCode(code);
        regisierEntity.setValidCodeType(8);
        String json = Util.toJson(regisierEntity);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);
        RetrofitHelper.getInstance(this).getServer().
                yan_Zheng_Code(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            UserInfoUtils.setUuid(ZhengXing_Query.this, baseResponse);
                            popupWindow.dismiss();
                            Util.backgroundAlpha(ZhengXing_Query.this, 1f);
                            Intent intent = new Intent();
                            intent.setClass(ZhengXing_Query.this, Credit_Reporting_Qurey_Result.class);
                            intent.putExtra("ComeOn", 1);
                            intent.putExtra("companyname", creadeName);
                            startActivityForResult(intent, 1);
                        }
                    }
                });

    }

    private void getValidCode(String p) {

        ValidCode validCode = new ValidCode();
        validCode.setMobile(p);
        validCode.setValidCodeType(8);
        validCode.setPlatform(2);

        String json = Util.toJson(validCode);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer().
                getValidCode(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            ToastUtil.showShort(ZhengXing_Query.this, "验证码发送成功");
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            String text = data.getExtras().getString("text");
            whetherCeck = true;//返回成功表示校对过了
            enterprise_full_name.setText(text);//第一次设置值 在editText的监听中执行的是改变后的方法 及strNew的值
            enterprise_full_name.setText(text);//这个时候设置值时 edittext会先执行改变前的方法，及strOld=text ,然后在执行 strNew=text
        }
    }

    //获取价格
    private Long getObject(int i) {
        if (category != null) {
            return Long.parseLong(category.get(i).getPriceStr());
        }
        return null;
    }

    //获取id
    private Integer getId(int a) {
        if (category != null) {
            return category.get(a).getId();
        }
        return null;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView == checkBox1) {
            if (isChecked) {
                price += getObject(0);
                map.put(getId(0), getId(0));
            } else {
                price -= getObject(0);
                map.remove(getId(0));
            }
        } else if (buttonView == checkBox2) {
            if (isChecked) {
                price += getObject(1);
                map.put(getId(1), getId(1));
            } else {
                price -= getObject(1);
                map.remove(getId(1));
            }
        } else if (buttonView == checkBox3) {
            if (isChecked) {
                price += getObject(2);
                map.put(getId(2), getId(2));
            } else {
                price -= getObject(2);
                map.remove(getId(2));
            }
        } else if (buttonView == checkBox4) {
            if (isChecked) {
                price += getObject(3);
                map.put(getId(3), getId(3));
            } else {
                price -= getObject(3);
                map.remove(getId(3));
            }
        } else if (buttonView == checkBox5) {
            if (isChecked) {
                price += getObject(4);
                map.put(getId(4), getId(4));
            } else {
                price -= getObject(4);
                map.remove(getId(4));
            }
        } else if (buttonView == checkBox6) {
            if (isChecked) {
                price += getObject(5);
                map.put(getId(5), getId(5));
            } else {
                price -= getObject(5);
                map.remove(getId(5));
            }
        } else if (buttonView == checkBox7) {
            if (isChecked) {
                price += getObject(6);
                map.put(getId(6), getId(6));
            } else {
                price -= getObject(6);
                map.remove(getId(6));
            }
        } else if (buttonView == checkBox) {
            ischeck = isChecked;
        }
        if (price == 0) {
            tv_amount.setText("金额:");
        } else {
            tv_amount.setText("金额:" + price + " 元");
        }

    }
}
