package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtbunesshall.Break_Debt_Activity;
import com.yl.baiduren.activity.newdebt_preson.Add_DebtPerson_Activity;
import com.yl.baiduren.activity.pay_for.Recharge;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Debt1;
import com.yl.baiduren.entity.request_body.DebtRelationDO;
import com.yl.baiduren.entity.request_body.QueryDebtNameEntity;
import com.yl.baiduren.entity.result.Debt1Result;
import com.yl.baiduren.entity.result.QueryDebtNameResult;
import com.yl.baiduren.entity.result.Updata_Debt_Basic_Information;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.ServiceUrl;
import com.yl.baiduren.utils.ActivityCollector;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Timer_Select;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CustomDatePicker;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.math.BigDecimal;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.shape.OvalLightShape;
import zhy.com.highlight.shape.RectLightShape;


/**
 * 债事备案1 -- 基本信息
 */
public class Debt_One_BasicInformation extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private Button bt_debt1, bt_debt1_query1, bt_debt1_query2;
    private TextView timer_select, et_debt1_end_time, et_debt1_fs_area;
    private ImageView iv_debt1_back;
    private EditText et_debt1_zw_name, et_debt1_zwr_name, et_zqr_code, et_debt1_zqr_name, et_debt1_amout, et_debt1_jiezhai;
    private Integer formId, toId;
    private RadioGroup rg_debt1;
    private RadioButton rb_debt1_hb, rb_debt1_fhb;
    private Integer huibi;
    private Long zhi_id = 0l;
    private int fromId2;
    private int toId2;
    private String debtQuCode;
    private HighLight mHightLight;
    private int ischeck;
    private TextView user_instructions;
    private String amout;
    private String jiezhai;
    private String timeStrat;
    private String timeEnd;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt__one__basic_information;
    }

    @Override
    public void initViews() {
        zhi_id = getIntent().getLongExtra("id", 0);//修改Id
        LUtils.e("--备案111---TRUE---"+zhi_id);
        bt_debt1 = findViewById(R.id.bt_debt1);//保存
        bt_debt1.setOnClickListener(this);
        timer_select = findViewById(R.id.et_debt1_fs_time);// 债事发生时间
        timer_select.setOnClickListener(this);
        et_debt1_end_time = findViewById(R.id.et_debt1_end_time);//债事结束时间
        et_debt1_end_time.setOnClickListener(this);
        iv_debt1_back = findViewById(R.id.iv_debt1_back_finsh);
        iv_debt1_back.setOnClickListener(this);
        user_instructions = findViewById(R.id.user_instructions);//用户须知
        user_instructions.setOnClickListener(this);
        et_debt1_zw_name = findViewById(R.id.et_debt1_zw_name);//债务人证号
        bt_debt1_query1 = findViewById(R.id.bt_debt1_query1);
        bt_debt1_query1.setOnClickListener(this);//查询债务人
        et_debt1_zwr_name = findViewById(R.id.et_debt1_zwr_name);//债务人name
        et_zqr_code = findViewById(R.id.et_zqr_code);//债权人证号
        bt_debt1_query2 = findViewById(R.id.bt_debt1_query2);
        bt_debt1_query2.setOnClickListener(this);//查询债权人
        et_debt1_zqr_name = findViewById(R.id.et_debt1_zqr_name);//债权人姓名
        et_debt1_amout = findViewById(R.id.et_debt1_amout);//债事金额
        et_debt1_jiezhai = findViewById(R.id.et_debt1_jiezhai);//解债佣金
        rg_debt1 = findViewById(R.id.rg_debt1);
        rg_debt1.setOnCheckedChangeListener(this);
        rb_debt1_fhb = findViewById(R.id.rb_debt1_fhb);//非货币
        rb_debt1_hb = findViewById(R.id.rb_debt1_hb);//货币
        huibi = 1;
        rb_debt1_hb.setChecked(true);
        CheckBox checkBox = findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(checklistener);
        et_debt1_fs_area = findViewById(R.id.et_debt1_fs_area);//债事地址
        et_debt1_fs_area.setOnClickListener(this);
        if (zhi_id != 0) {
            ischeck=1;
            checkBox.setChecked(true);
            setData(zhi_id);
        }
        //显示新手指引
        if (Util.isFirstApp(this, 2)) {
            LUtils.e("--备案111---TRUE---");
            initGuidel();
        } else {
            LUtils.e("--备案111---false---");
        }

    }

    private void initGuidel() {
        mHightLight = new HighLight(Debt_One_BasicInformation.this);
        mHightLight.anchor(this.getWindow().getDecorView())//如果是在Activity上增加引导层，不需要设置此方法
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
                .intercept(true)//拦截属性默认为true 使下方ClickCallback生效
//                .enableNext()//开启next模式并通过show方法显示 然后通过调用next()方法切换到下一个提示布局，直到移除自身
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {

                    @Override
                    public void onLayouted() {
                        mHightLight.addHighLight(R.id.ll_debt1_one, R.layout.debt1_guidel, onPosCallback, new OvalLightShape(5, 5, 20));
                        mHightLight.addHighLight(R.id.ll_cx, R.layout.debt1_guidel2, onPosCallback2, new OvalLightShape(5, 5, 20));
                        mHightLight.addHighLight(R.id.view_line, R.layout.debt1_guidel3, onPosCallback3, new RectLightShape());

                        //然后显示高亮布局
                        mHightLight.show();
                    }
                })
                .setClickCallback(new HighLightInterface.OnClickCallback() {
                    @Override
                    public void onClick() {
                        mHightLight.remove();
                    }
                });

    }

    /**
     * 位置回掉
     */
    private HighLight.OnPosCallback onPosCallback = new HighLight.OnPosCallback() {
        @Override
        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
            marginInfo.topMargin = rectF.top * 2 - rectF.top / 3;
        }
    };


    /**
     * 位置回掉
     */
    private HighLight.OnPosCallback onPosCallback2 = new HighLight.OnPosCallback() {
        @Override
        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
            marginInfo.topMargin = rectF.top * 2 - rectF.top / 3;
            marginInfo.leftMargin = rectF.left - rectF.top / 2;
        }
    };

    /**
     * 位置回掉
     */
    private HighLight.OnPosCallback onPosCallback3 = new HighLight.OnPosCallback() {
        @Override
        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
            marginInfo.topMargin = rectF.top - (rectF.top - 1);
        }
    };

    private void setData(Long zhi_id) {
        com.yl.baiduren.entity.DebtRelationDO debtRelationDO = new com.yl.baiduren.entity.DebtRelationDO(DataWarehouse.getBaseRequest(this));
        debtRelationDO.setId(zhi_id);
        String json = Util.toJson(debtRelationDO);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .updataDebtDetail(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Updata_Debt_Basic_Information>>bindToLifecycle()))
                .subscribe(new BaseObserver<Updata_Debt_Basic_Information>(this) {
                    @Override
                    protected void onSuccees(String code, Updata_Debt_Basic_Information data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            et_debt1_zw_name.setText(data.getFromCardNumber());
                            et_debt1_zwr_name.setText(data.getFromName());
                            et_zqr_code.setText(data.getToCardNumber());
                            et_debt1_zqr_name.setText(data.getToName());
                            et_debt1_amout.setText(data.getAmoutStr());
                            timer_select.setText(Util.LongParseString(Long.valueOf(data.getRecordTime())));
                            if (!TextUtils.isEmpty(data.getEndTimeDr())){
                                et_debt1_end_time.setText(Util.LongParseString(Long.valueOf(data.getEndTimeDr())));
                            }
                            BigDecimal bigDecimal = new BigDecimal(data.getCommission());
                            et_debt1_jiezhai.setText(String.valueOf(bigDecimal.multiply(new BigDecimal("100")).doubleValue()));
                            fromId2 = data.getFromId();
                            toId2 = data.getToId();
                            et_debt1_fs_area.setText(data.getAreaStr());
                            Integer type = data.getType();//货币类型
                            if (type == 1) {//货币
                                rb_debt1_hb.setChecked(true);
                            } else if (type == 2) {
                                rb_debt1_fhb.setChecked(true);
                            }

                        }
                    }
                });


    }


    @Override
    public void onClick(View view) {
        if (view == bt_debt1) {
            isEdit();


        } else if (view == timer_select) {//债事发生时间
            Timer_Select.getTimer(this, Timer_Select.getTime(timer_select), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {
                    timer_select.setText(time.split(" ")[0]);
                    LUtils.e("--发生--*", time);
                }
            });

        } else if (view == et_debt1_fs_area) {//债事发生地
            PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_debt1_fs_area.setText(string);
                    debtQuCode = quId;
                }
            });
        } else if (view == et_debt1_end_time) {//债事结束时间
            Timer_Select.getTimer(this, Timer_Select.getTime(et_debt1_end_time), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {
                    et_debt1_end_time.setText(time.split(" ")[0]);
                    LUtils.e("--结束--*", time);
                }

            });
        } else if (view == iv_debt1_back) {//此时的返回返回到债事列表页
            finish();

        } else if (view == bt_debt1_query1) {//查询债务人
            String zwCode = et_debt1_zw_name.getText().toString().trim();
            if (!TextUtils.isEmpty(zwCode)) {
                queryZw(zwCode, 1);
            } else {
                ToastUtil.showShort(this, "请输入债务人证号");
                return;
            }
        } else if (view == bt_debt1_query2) {
            String zqCode = et_zqr_code.getText().toString().trim();
            if (!TextUtils.isEmpty(zqCode)) {
                queryZw(zqCode, 2);
            } else {
                ToastUtil.showShort(this, "请输入债权人证号");
                return;
            }
        } else if (view == user_instructions) {
            Intent intent = new Intent();
            intent.setClass(this, Break_Debt_Activity.class);
            intent.putExtra("url", ServiceUrl.H5_USER_INFORMATION);
            intent.putExtra("type", 2);
            startActivity(intent);
        }
    }

    private void isEdit() {
        amout = et_debt1_amout.getText().toString().trim();
        jiezhai = et_debt1_jiezhai.getText().toString().trim();
        timeStrat = timer_select.getText().toString().trim();
        timeEnd = et_debt1_end_time.getText().toString().trim();
        String area = et_debt1_fs_area.getText().toString().trim();
        if (TextUtils.isEmpty(amout)) {
            ToastUtil.showShort(this, "请输入债事金额");
            return;
        }
        if (TextUtils.isEmpty(jiezhai)) {
            ToastUtil.showShort(this, "请输入解债佣金百分比");
            return;
        }
        Double d = Double.valueOf(jiezhai);
        LUtils.e("浮点数的大小" + d);
        if (d < 0.1d) {
            ToastUtil.showShort(this, "解债佣金不能小于千分之一");
            return;
        }

        if (d > 100) {
            ToastUtil.showShort(this, "解债佣金不能大于100%");
            return;
        }

        if (TextUtils.isEmpty(timeStrat)) {
            ToastUtil.showShort(this, "请选择债事开始时间");
            return;
        }
        if (TextUtils.isEmpty(area)) {
            ToastUtil.showShort(this, "请选择债事发生地");
            return;
        }
        if (ischeck != 1) {
            ToastUtil.showShort(this, "请点击用户协议");
            return;
        }

        if (zhi_id!=0) {//编辑
            LUtils.e("出现弹窗——+————+——编辑+——+——+——+——"+zhi_id);
            save();
        } else {//新增
            LUtils.e("出现弹窗——+————+——+——+——+——+——");
            show_Dialogs();
        }
    }

    private void save() {


        Debt1 debt1 = new Debt1(DataWarehouse.getBaseRequest(this));
        DebtRelationDO debtRelationDO = new DebtRelationDO();
        if ( zhi_id != 0) {//有这个Id ，表示不为空
            debtRelationDO.setId(zhi_id);
        }
        if (formId != null) {
            debtRelationDO.setFromId(formId);
        } else {
            debtRelationDO.setFromId(fromId2);
        }
        if (toId != null) {
            debtRelationDO.setToId(toId);
        } else {
            debtRelationDO.setToId(toId2);
        }

        debtRelationDO.setType(huibi);
        debtRelationDO.setAmountStr(amout);//金额乘100   取小数点后二位
        BigDecimal big1 = new BigDecimal(jiezhai);
        BigDecimal big2 = new BigDecimal("100");  //不能输小于0.1
        debtRelationDO.setCommission(big1.divide(big2));//百分比处除100
        debtRelationDO.setRecordTime(Util.strParsedata(timeStrat));
        if (!TextUtil.isEmpty(timeEnd)) {
            debtRelationDO.setEndTimeDr(Util.strParsedata(timeEnd));
        }
        debtRelationDO.setArea(debtQuCode); //地址
        debt1.setDebtRelationDO(debtRelationDO);
        debt1.setProcess(1);
        debt1(debt1);
    }

    private void show_Dialogs() {
        String MESSAGE = "点击同意后，既为您保存备案信息，此次立案将消耗您备案次数:1";
        DialogUtils.showDialogZsr2(this, "同意", false, MESSAGE, new DialogUtils.OnButtonEventListener() {
            @Override
            public void onConfirm() {
                save();
            }

            @Override
            public void onCancel() {
            }
        });
    }


    private void debt1(Debt1 debt1) {

        String json = Util.toJson(debt1);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .debt(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Debt1Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Debt1Result>(this) {
                    @Override
                    protected void onSuccees(String code, Debt1Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
//
                            if (zhi_id == 0) {
                                LUtils.e("--备案成功--");
                                ActivityCollector.back(Debt_One_BasicInformation.this, data.getId());
                                ToastUtil.showShort(Debt_One_BasicInformation.this, "备案成功");
                            } else {
                                LUtils.e("--修改成功--");
                                ActivityCollector.back(Debt_One_BasicInformation.this, zhi_id);
                                ToastUtil.showShort(Debt_One_BasicInformation.this, "修改成功");
                            }
                            finish();
                        }
                    }

                    @Override
                    protected void onCodeError(String code, String mess) throws Exception {

                        if(mess.equals("剩余备案次数不足，请先去充值")){
                            DialogUtils.showDialogZsr(Debt_One_BasicInformation.this, false, mess, new DialogUtils.OnButtonEventListener() {
                                @Override
                                public void onConfirm() {
                                    //1 表示在债事添加成功后 返回该页
                                    startActivity(new Intent(Debt_One_BasicInformation.this, Recharge.class));
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    }
                });

    }

    /**
     * 查询债务人
     *
     * @param string
     * @param i
     */
    private void queryZw(String string, final int i) {

        LUtils.e("-----string----" + string);
        QueryDebtNameEntity queryDebtNameEntity = new QueryDebtNameEntity(DataWarehouse.getBaseRequest(this));
        queryDebtNameEntity.setCondition(string);
        String json = Util.toJson(queryDebtNameEntity);//转成json
        LUtils.e("-----json----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .queryZsr(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<QueryDebtNameResult>>bindToLifecycle()))
                .subscribe(new BaseObserver<QueryDebtNameResult>(Debt_One_BasicInformation.this) {
                    @Override
                    protected void onSuccees(String code, QueryDebtNameResult data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {

                            if (data.getList().size() == 1) { //查询结果为1条时
                                if (i == 1) {//债务人
                                    et_debt1_zwr_name.setText(data.getList().get(0).getName());
                                    formId = data.getList().get(0).getId();
                                } else if (i == 2) {//债权人
                                    et_debt1_zqr_name.setText(data.getList().get(0).getName());
                                    toId = data.getList().get(0).getId();
                                }
                                LUtils.e("--备案 one--" + data);
                                BaseRequest base = new BaseRequest();
                                base.setUuid(baseResponse.getUuid());
                                GreenDaoUtils.getInstance(Debt_One_BasicInformation.this).getBaseRequestDao().update(base);
                            } else {//查询结果为多条时
                                PopupWindeUtils.queryPopupWinde(Debt_One_BasicInformation.this, data.getList(), new PopupWindeUtils.OnClickListView() {
                                    @Override
                                    public void onStringClick(Integer id, String name) {
                                        if (i == 1) {
                                            et_debt1_zwr_name.setText(name);
                                            formId = id;
                                        } else if (i == 2) {
                                            et_debt1_zqr_name.setText(name);
                                            toId = id;
                                        }
                                    }
                                });
                            }
                        }

                    }

                    @Override
                    protected void onCodeError(String code, String mess) throws Exception {
                        if (mess.equals("查询不到该债事人，请添加该债事人")) {
                            DialogUtils.showDialogZsr(Debt_One_BasicInformation.this, false, mess, new DialogUtils.OnButtonEventListener() {
                                @Override
                                public void onConfirm() {
                                    //1 表示在债事添加成功后 返回该页
                                    startActivity(new Intent(Debt_One_BasicInformation.this, Add_DebtPerson_Activity.class));
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    }
                });
    }

    private CompoundButton.OnCheckedChangeListener checklistener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                ischeck = 1;
            } else {
                ischeck = 2;
            }
            LUtils.e("ischeck" + ischeck);

        }
    };

    @Override
    public void onBackPressed() {//此时的返回返回到债事列表页
        super.onBackPressed();
        finish();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == rg_debt1) {
            if (checkedId == R.id.rb_debt1_hb) {//货币
                huibi = 1;
            } else if (checkedId == R.id.rb_debt1_fhb) {
                huibi = 2;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        zhi_id = null;
    }



}
