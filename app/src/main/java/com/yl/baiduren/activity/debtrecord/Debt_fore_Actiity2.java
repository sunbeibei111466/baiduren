package com.yl.baiduren.activity.debtrecord;

import android.content.Intent;
import android.graphics.RectF;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Debt4_1Adapter;
import com.yl.baiduren.adapter.Debt4_2Adapter;
import com.yl.baiduren.adapter.Debt4_3Adapter;
import com.yl.baiduren.adapter.Debt4_4Adapter;
import com.yl.baiduren.adapter.Debt4_5Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.GoodLoanDO;
import com.yl.baiduren.entity.greenentity.MortgageDO;
import com.yl.baiduren.entity.greenentity.PayRecord;
import com.yl.baiduren.entity.greenentity.PropertyLoanDO;
import com.yl.baiduren.entity.greenentity.SponsorDO;
import com.yl.baiduren.entity.request_body.Debt4;
import com.yl.baiduren.entity.request_body.DebtRelationVo4;
import com.yl.baiduren.entity.greenentity.MoneyLoanDO;
import com.yl.baiduren.entity.request_body.MoneyLoan;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.ActivityCollector;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.shape.RectLightShape;

public class Debt_fore_Actiity2 extends BaseActivity implements View.OnClickListener {


    private TextView tv_debt4_huobi, tv_debt4_shiwu, tv_debt4_changquan, tv_debt4_dbr, tv_debt4_dy;
    private ImageView iv_debt4_back;
    private Button bt_debt4;
    private MyRecyclerView rv_debt4_1, rv_debt4_2, rv_debt4_3, rv_debt4_4, rv_debt4_5;
    private Debt4_1Adapter debt4_1Adapter;
    private Debt4_2Adapter debt4_2Adapter;
    private Debt4_3Adapter debt4_3Adapter;
    private Debt4_4Adapter debt4_4Adapter;
    private Debt4_5Adapter debt4_5Adapter;
    private List<MoneyLoan> moneyLoans;
    private Long addDebtId;
    private HighLight mHightLight;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt_fore__actiity2;
    }

    @Override
    public void initViews() {
        addDebtId = getIntent().getLongExtra("id", 0);// 债事id
        moneyLoans = new ArrayList<>();
        tv_debt4_huobi = findViewById(R.id.tv_debt4_huobi);//货币借贷
        tv_debt4_huobi.setOnClickListener(this);
        tv_debt4_shiwu = findViewById(R.id.tv_debt4_shiwu);//事物借贷
        tv_debt4_shiwu.setOnClickListener(this);
        tv_debt4_changquan = findViewById(R.id.tv_debt4_changquan);//产权凭证
        tv_debt4_changquan.setOnClickListener(this);
        tv_debt4_dbr = findViewById(R.id.tv_debt4_dbr);//担保人
        tv_debt4_dbr.setOnClickListener(this);
        tv_debt4_dy = findViewById(R.id.tv_debt4_dy);//资产抵押
        tv_debt4_dy.setOnClickListener(this);
        iv_debt4_back = findViewById(R.id.iv_debt4_back);
        iv_debt4_back.setOnClickListener(this);
        bt_debt4 = findViewById(R.id.bt_debt4);//按钮
        bt_debt4.setOnClickListener(this);
        rv_debt4_1 = findViewById(R.id.rv_debt4_1);
        rv_debt4_1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        debt4_1Adapter = new Debt4_1Adapter(this);
        rv_debt4_2 = findViewById(R.id.rv_debt4_2);
        rv_debt4_2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        debt4_2Adapter = new Debt4_2Adapter(this);
        rv_debt4_3 = findViewById(R.id.rv_debt4_3);
        rv_debt4_3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        debt4_3Adapter = new Debt4_3Adapter(this);
        rv_debt4_4 = findViewById(R.id.rv_debt4_4);
        rv_debt4_4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        debt4_4Adapter = new Debt4_4Adapter(this);
        rv_debt4_5 = findViewById(R.id.rv_debt4_5);
        rv_debt4_5.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        debt4_5Adapter = new Debt4_5Adapter(this);
        if (addDebtId != 0) {
            bt_debt4.setText("保存");
        }
        //显示新手指引
        if (Util.isFirstApp(this,5)) {
            initGuidel();
        }
    }

    private void initGuidel() {
        mHightLight = new HighLight(Debt_fore_Actiity2.this);
        mHightLight.anchor(this.getWindow().getDecorView())//如果是在Activity上增加引导层，不需要设置此方法
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
                .intercept(true)//拦截属性默认为true 使下方ClickCallback生效
//                .enableNext()//开启next模式并通过show方法显示 然后通过调用next()方法切换到下一个提示布局，直到移除自身
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {

                    @Override
                    public void onLayouted() {
                        mHightLight.addHighLight(R.id.tv_debt4_huobi, R.layout.debt_guidel_fore,onPosCallback, new RectLightShape());
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
            marginInfo.bottomMargin = rectF.top-(rectF.top-1);
        }
    };

    @Override
    public void onClick(View v) {
        if (v == tv_debt4_huobi) {//货币借贷
            startActivity(new Intent(Debt_fore_Actiity2.this, CurrencyLending.class));
        } else if (v == tv_debt4_shiwu) {//事物借贷
            startActivity(new Intent(Debt_fore_Actiity2.this, PhysicalBorrowing.class));
        } else if (v == tv_debt4_changquan) {//产权借贷
            startActivity(new Intent(Debt_fore_Actiity2.this, PropertyRightsProof.class));
        } else if (v == tv_debt4_dbr) {//担保人
            startActivity(new Intent(Debt_fore_Actiity2.this, Sponsor.class));
        } else if (v == tv_debt4_dy) {//资产抵押
            startActivity(new Intent(Debt_fore_Actiity2.this, AssetrMortgage.class));
        } else if (v == iv_debt4_back) {
            ActivityCollector.back(Debt_fore_Actiity2.this, addDebtId);
        } else if (v == bt_debt4) {//保存
            baocun();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        List<MoneyLoanDO> moneyLoanDOList = GreenDaoUtils.getInstance(this).getMoneyLoanDODao().loadAll();
        if (moneyLoanDOList != null && moneyLoanDOList.size() != 0) {
            moneyLoans.clear();
            for (int i = 0; i < moneyLoanDOList.size(); i++) {
                MoneyLoan moneyLoan = new MoneyLoan();
                moneyLoan.setId(moneyLoanDOList.get(i).getId());
                moneyLoan.setType(moneyLoanDOList.get(i).getType());
                moneyLoan.setPayMoneyStr(moneyLoanDOList.get(i).getPayMoneyStr());
                moneyLoan.setInterest(moneyLoanDOList.get(i).getInterest());//利息
                moneyLoan.setLoanTime(moneyLoanDOList.get(i).getLoanTime());
                moneyLoan.setEndTime(moneyLoanDOList.get(i).getEndTime());
                moneyLoan.setPayMoneyStr(moneyLoanDOList.get(i).getPayMoneyStr());
                moneyLoan.setBalanceStr(moneyLoanDOList.get(i).getBalanceStr());
                moneyLoan.setImage(moneyLoanDOList.get(i).getImage());
                moneyLoan.setPrincipalStr(moneyLoanDOList.get(i).getPrincipalStr());
                List<PayRecord> payRecords = GreenDaoUtils.getInstance(this).getPayRecordDao()._queryMoneyLoanDO_PayRecordList(moneyLoanDOList.get(i).getId());
                if (payRecords.size() != 0) {
                    moneyLoan.setPayRecordList(payRecords);
                }
                moneyLoans.add(moneyLoan);
            }
            debt4_1Adapter.setMoneyLoanList(moneyLoans);
            rv_debt4_1.setAdapter(debt4_1Adapter);
            debt4_1Adapter.notifyDataSetChanged();
        }


        List<GoodLoanDO> goodLoanDOList = GreenDaoUtils.getInstance(this).getGoodLoanDODao().loadAll();//实物借贷
        if (goodLoanDOList != null && goodLoanDOList.size() != 0) {
            debt4_2Adapter.setGoodLoanDOList(goodLoanDOList);
            rv_debt4_2.setAdapter(debt4_2Adapter);
            debt4_2Adapter.notifyDataSetChanged();
        }
        List<PropertyLoanDO> propertyLoanDOList = GreenDaoUtils.getInstance(this).getPropertyLoanDODao().loadAll();//产权借贷
        if (propertyLoanDOList != null && propertyLoanDOList.size() != 0) {
            debt4_3Adapter.setPropertyLoanDOList(propertyLoanDOList);
            rv_debt4_3.setAdapter(debt4_3Adapter);
            debt4_3Adapter.notifyDataSetChanged();
        }

        List<SponsorDO> sponsorDOList = GreenDaoUtils.getInstance(this).getSponsorDODao().loadAll();//担保人
        if (sponsorDOList != null && sponsorDOList.size() != 0) {
            debt4_4Adapter.setSponsorDOList(sponsorDOList);
            rv_debt4_4.setAdapter(debt4_4Adapter);
            debt4_4Adapter.notifyDataSetChanged();
        }

        List<MortgageDO> mortgageDOList = GreenDaoUtils.getInstance(this).getMortgageDODao().loadAll();//资产抵押
        if (mortgageDOList != null && mortgageDOList.size() != 0) {
            debt4_5Adapter.setMortgageDOList(mortgageDOList);
            rv_debt4_5.setAdapter(debt4_5Adapter);
            debt4_5Adapter.notifyDataSetChanged();
        }

    }

    private void baocun() {

        List<GoodLoanDO> goodLoanDOList = GreenDaoUtils.getInstance(this).getGoodLoanDODao().loadAll();//实物借贷
        List<PropertyLoanDO> propertyLoanDOList = GreenDaoUtils.getInstance(this).getPropertyLoanDODao().loadAll();////产权借贷
        List<MortgageDO> mortgageDOList = GreenDaoUtils.getInstance(this).getMortgageDODao().loadAll();//资产抵押
        List<SponsorDO> sponsorDOList = GreenDaoUtils.getInstance(this).getSponsorDODao().loadAll();//担保人

        Debt4 debt4 = new Debt4();
        debt4.setProcess(4);
        DebtRelationVo4 debtRelationVo4 = new DebtRelationVo4();
        debtRelationVo4.setDebtRelationId(addDebtId);
        debtRelationVo4.setMoneyLoans(moneyLoans);//货币借贷
        if (goodLoanDOList.size() != 0) {//实物借贷
            debtRelationVo4.setGoodLoans(goodLoanDOList);
        } else {
            debtRelationVo4.setGoodLoans(new ArrayList<GoodLoanDO>());
        }

        if (propertyLoanDOList.size() != 0) {//产权借贷
            debtRelationVo4.setPropertyLoans(propertyLoanDOList);
        } else {
            debtRelationVo4.setPropertyLoans(new ArrayList<PropertyLoanDO>());
        }

        if (sponsorDOList.size() != 0) {
            debtRelationVo4.setSponsors(sponsorDOList);//担保人
        } else {
            debtRelationVo4.setSponsors(new ArrayList<SponsorDO>());//担保人
        }

        if (mortgageDOList.size() != 0) {
            debtRelationVo4.setMortgages(mortgageDOList);//资产抵押
        } else {
            debtRelationVo4.setMortgages(new ArrayList<MortgageDO>());//资产抵押
        }

        debt4.setDebtRelationVo4(debtRelationVo4);
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        debt4.setPlatform(2);
        debt4.setAccessToken(baseRequestList.get(0).getAccessToken());
        debt4.setLoginUsername(baseRequestList.get(0).getLoginUsername());
        debt4.setUuid(baseRequestList.get(0).getUuid());
        debt4.setUid(baseRequestList.get(0).getUid());


        String json = Util.toJson(debt4);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(this).getServer()
                .debt2(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            LUtils.e("---第4步数据返回不为空--");

                            UserInfoUtils.setUuid(Debt_fore_Actiity2.this, baseResponse);

                            GreenDaoUtils.getInstance(Debt_fore_Actiity2.this).getMoneyLoanDODao().deleteAll();
                            GreenDaoUtils.getInstance(Debt_fore_Actiity2.this).getPayRecordDao().deleteAll();
                            GreenDaoUtils.getInstance(Debt_fore_Actiity2.this).getSponsorDODao().deleteAll();
                            GreenDaoUtils.getInstance(Debt_fore_Actiity2.this).getPropertyLoanDODao().deleteAll();
                            GreenDaoUtils.getInstance(Debt_fore_Actiity2.this).getGoodLoanDODao().deleteAll();
                            GreenDaoUtils.getInstance(Debt_fore_Actiity2.this).getMortgageDODao().deleteAll();

                            ActivityCollector.back(Debt_fore_Actiity2.this, addDebtId);
                            finish();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {//此时的返回返回到债事列表页
        super.onBackPressed();
        ActivityCollector.back(Debt_fore_Actiity2.this, addDebtId);
    }


}
