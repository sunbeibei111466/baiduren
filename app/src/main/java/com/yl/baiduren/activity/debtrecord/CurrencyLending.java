package com.yl.baiduren.activity.debtrecord;


import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.adapter.debtrecord_adp.CurrencyLendingAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.greenentity.MoneyLoanDO;
import com.yl.baiduren.entity.greenentity.PayRecord;
import com.yl.baiduren.entity.request_body.MoneyLoanUpdataEntity;
import com.yl.baiduren.entity.result.CurrencyLendingDetails_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Timer_Select;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CustomDatePicker;
import com.yl.baiduren.view.MyGridView;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CurrencyLending extends BaseActivity implements RadioGroup.OnCheckedChangeListener
        , View.OnClickListener {

    private RadioGroup rg_currency;
    private RecyclerView recyclerView_currency;
    private ConstraintLayout cl_currency_add;
    private LinearLayout ll_title;
    private Button bt_currency_seva, bt_currency_baoc;
    private ImageView iv_debt4_currency_back;
    private List<PayRecord> payRecordList;
    private TextView et_currency_js_time, et_currency_js_jine;
    private CurrencyLendingAdapter currencyLendingAdapter;
    private EditText et_currency_bizhong, et_currency_benjin, et_currency_lixi, et_currency_yihuan, et_currency_weihuan;
    private TextView et_currency_fang_k, tv_currency_jiezhi_time;
    private Long updataId;
    private RadioButton rb_currency_yer, rb_currency_no;
    private CurrencyLendingDetails_Result currencyLendingDetailsResult;
    private MyGridView gv_currency_photo;
    private UphotoAdapter adapter;
    private List<String> mPath;
    private boolean pay;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_currency_lending;
    }

    @Override
    public void initViews() {
        mPath = new ArrayList<>();
        currencyLendingDetailsResult = (CurrencyLendingDetails_Result) getIntent().getSerializableExtra("currencyLendingDetailsResult");
        updataId = getIntent().getLongExtra("updataId", 0);
        payRecordList = new ArrayList<>();
        iv_debt4_currency_back = findViewById(R.id.iv_debt4_currency_back);
        iv_debt4_currency_back.setOnClickListener(this);
        bt_currency_baoc = findViewById(R.id.bt_currency_baoc);//页面底部保存按钮
        bt_currency_baoc.setOnClickListener(this);
        rg_currency = findViewById(R.id.rg_currency);//GrdioGroup
        rg_currency.setOnCheckedChangeListener(this);
        cl_currency_add = findViewById(R.id.cl_currency_add);//添加结算日期
        recyclerView_currency = findViewById(R.id.recyclerView_currency);//recyclerView
        bt_currency_seva = findViewById(R.id.bt_currency_seva);
        bt_currency_seva.setOnClickListener(this);
        et_currency_js_time = findViewById(R.id.et_currency_js_time);//结算日期
        et_currency_js_time.setOnClickListener(this);
        et_currency_js_jine = findViewById(R.id.et_currency_js_jine);//结算金额
        ll_title = findViewById(R.id.ll_title);//标题
        recyclerView_currency.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        currencyLendingAdapter = new CurrencyLendingAdapter(this);
        rb_currency_no = findViewById(R.id.rb_currency_no);
        rb_currency_yer = findViewById(R.id.rb_currency_yer);
        et_currency_bizhong = findViewById(R.id.et_currency_bizhong);//币种
        et_currency_benjin = findViewById(R.id.et_currency_benjin);//本金
        et_currency_lixi = findViewById(R.id.et_currency_lixi);//利息
        et_currency_fang_k = findViewById(R.id.et_currency_fang_k);//放款日期
        et_currency_fang_k.setOnClickListener(this);
        tv_currency_jiezhi_time = findViewById(R.id.tv_currency_jiezhi_time);//还款截至日期
        tv_currency_jiezhi_time.setOnClickListener(this);
        et_currency_yihuan = findViewById(R.id.et_currency_yihuan);//已还合计
        et_currency_weihuan = findViewById(R.id.et_currency_weihuan);//为还合计
        adapter = new UphotoAdapter(this);
        gv_currency_photo = findViewById(R.id.gv_currency_photo);
        gv_currency_photo.setOnItemClickListener(Oitemlistener);
        gv_currency_photo.setAdapter(adapter);

        updataService();
        updata();
    }

    private void updataService() {
        if (currencyLendingDetailsResult != null) {
            et_currency_bizhong.setText(currencyLendingDetailsResult.getType());//币种
            et_currency_benjin.setText(currencyLendingDetailsResult.getPrincipalStr());//本金
            String str = String.valueOf(currencyLendingDetailsResult.getInterest());
            BigDecimal bigDecimal = new BigDecimal("100");
            BigDecimal bigDecima2 = new BigDecimal(str);
            et_currency_lixi.setText(bigDecima2.multiply(bigDecimal) + "");//利息
            et_currency_fang_k.setText(Util.LongParseString(currencyLendingDetailsResult.getLoanTime()));//放款日期
            tv_currency_jiezhi_time.setText(Util.LongParseString(currencyLendingDetailsResult.getEndTime()));//还款截至日期
            et_currency_yihuan.setText(currencyLendingDetailsResult.getPayMoneyStr());//已还合计
            et_currency_weihuan.setText(currencyLendingDetailsResult.getBalanceStr());
            List<CurrencyLendingDetails_Result.PayRecordListBean> payRecordListBeans = currencyLendingDetailsResult.getPayRecordList();
            if (payRecordListBeans.size() != 0) {//子表数据不为空时

                for (int i = 0; i < payRecordListBeans.size(); i++) {
                    PayRecord payRecord = new PayRecord();
                    payRecord.setPayTime(payRecordListBeans.get(i).getPayTime());
                    payRecord.setPayMoneyStr(payRecordListBeans.get(i).getPayMoneyStr());
                    payRecordList.add(payRecord);
                }
                currencyLendingAdapter.setRecordList(payRecordList);//为适配器设置数据源
                recyclerView_currency.setAdapter(currencyLendingAdapter);
                currencyLendingAdapter.notifyDataSetChanged();

                ll_title.setVisibility(View.VISIBLE);//数据源不为0时，展开头布局
                cl_currency_add.setVisibility(View.VISIBLE);//子列表展开
                rb_currency_yer.setChecked(true);
            } else {
                rb_currency_no.setChecked(true);
            }

            if (!TextUtils.isEmpty(currencyLendingDetailsResult.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = currencyLendingDetailsResult.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_currency_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void updata() {
        if (updataId != 0) {
            MoneyLoanDO moneyLoanDO = GreenDaoUtils.getInstance(this).getMoneyLoanDODao().load(updataId);
            et_currency_bizhong.setText(moneyLoanDO.getType());//币种
            et_currency_benjin.setText(moneyLoanDO.getPrincipalStr());//本金
            String str = String.valueOf(moneyLoanDO.getInterest());
            BigDecimal bigDecimal = new BigDecimal("100");
            BigDecimal bigDecima2 = new BigDecimal(str);
            et_currency_lixi.setText(bigDecima2.multiply(bigDecimal) + "");//利息
            et_currency_fang_k.setText(Util.LongParseString(moneyLoanDO.getLoanTime()));//放款日期
            tv_currency_jiezhi_time.setText(Util.LongParseString(moneyLoanDO.getEndTime()));//还款截至日期
            et_currency_yihuan.setText(moneyLoanDO.getPayMoneyStr());//已还合计
            et_currency_weihuan.setText(moneyLoanDO.getBalanceStr());

            List<PayRecord> payRecordList1 = GreenDaoUtils.getInstance(this).getPayRecordDao()._queryMoneyLoanDO_PayRecordList(updataId);
            if (payRecordList1.size() != 0) { //子表

                for (int i = 0; i < payRecordList1.size(); i++) {
                    PayRecord payRecord = new PayRecord();
                    payRecord.setPayTime(payRecordList1.get(i).getPayTime());
                    payRecord.setPayMoneyStr(payRecordList1.get(i).getPayMoneyStr());
                    payRecordList.add(payRecord);
                }

                currencyLendingAdapter.setRecordList(payRecordList);//为适配器设置数据源
                recyclerView_currency.setAdapter(currencyLendingAdapter);
                currencyLendingAdapter.notifyDataSetChanged();

                ll_title.setVisibility(View.VISIBLE);//数据源不为0时，展开头布局
                cl_currency_add.setVisibility(View.VISIBLE);//子列表展开
                rb_currency_yer.setChecked(true);
            }

            //图片
            if (!TextUtils.isEmpty(moneyLoanDO.getImage())) {
                //将String 转成数组，在转成集合
                String[] strings = moneyLoanDO.getImage().split(",");
                mPath.addAll(new ArrayList<>(Arrays.asList(strings)));
                adapter.setPath(mPath);
                gv_currency_photo.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (bt_currency_seva == v) {//结算保存按钮
            presrve();
        } else if (bt_currency_baoc == v) {//整体保存按钮
            presrveAll();
        } else if (v == et_currency_fang_k) {//放款日期
            Timer_Select.getTimer(this, Timer_Select.getTime(et_currency_fang_k), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {
                    et_currency_fang_k.setText(time.split(" ")[0]);
                }
            });
        } else if (v == tv_currency_jiezhi_time) { //还款截至日期
            Timer_Select.getTimer(this, Timer_Select.getTime(tv_currency_jiezhi_time), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {
                    tv_currency_jiezhi_time.setText(time.split(" ")[0]);
                }
            });
        } else if (v == et_currency_js_time) {//结算日期
            Timer_Select.getTimer(this, Timer_Select.getTime(et_currency_js_time), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {
                    et_currency_js_time.setText(time.split(" ")[0]);
                }
            });
        } else if (iv_debt4_currency_back == v) {
            finish();
        }
    }

    /**
     * 保存时先添加主表，再添加字表
     */
    private void presrveAll() {

        String bz = et_currency_bizhong.getText().toString().trim();//币种
        String benjin = et_currency_benjin.getText().toString().trim();//本金
        String lixi = et_currency_lixi.getText().toString().trim();//利息
        String fk = et_currency_fang_k.getText().toString().trim();//放款日期
        String hk_jz_time = tv_currency_jiezhi_time.getText().toString().trim();//还款截至日期
        String yh = et_currency_yihuan.getText().toString().trim();//已还合计
        String weihuan = et_currency_weihuan.getText().toString().trim();//为还合计
        if (TextUtils.isEmpty(bz)) {
            ToastUtil.showShort(this, "请输入币种");
            return;
        }
        if (TextUtils.isEmpty(benjin)) {
            ToastUtil.showShort(this, "请输入本金");
            return;
        }
        if (TextUtils.isEmpty(lixi)) {
            ToastUtil.showShort(this, "请输入借贷利息");
            return;
        }
        Double d=Double.valueOf(lixi);
        if (d==0||d>100){
            ToastUtil.showShort(this,"利息不能小于0%或者大于100%");
            return;
        }
        if (TextUtils.isEmpty(fk)) {
            ToastUtil.showShort(this, "请选择放款日期");
            return;
        }
        if (TextUtils.isEmpty(hk_jz_time)) {
            ToastUtil.showShort(this, "请选择还款截止日期");
            return;
        }
        if (TextUtils.isEmpty(yh)) {
            ToastUtil.showShort(this, "请输入已还金额");
            return;
        }
        if (TextUtils.isEmpty(weihuan)) {
            ToastUtil.showShort(this, "请输入未还合计金额");
            return;
        }

        if (updataId != 0) { //编辑本地数据
            MoneyLoanDO moneyLoanDO = GreenDaoUtils.getInstance(this).getMoneyLoanDODao().load(updataId);
            moneyLoanDO.setType(bz);
            moneyLoanDO.setPrincipalStr(benjin);
            BigDecimal bigDecimal = new BigDecimal(lixi);
            BigDecimal bigDecimal2 = bigDecimal.divide(new BigDecimal("100"));
            moneyLoanDO.setInterest(bigDecimal2.doubleValue());//利息
            moneyLoanDO.setLoanTime(Util.strParsedata(fk));
            moneyLoanDO.setEndTime(Util.strParsedata(hk_jz_time));
            moneyLoanDO.setPayMoneyStr(yh);
            moneyLoanDO.setBalanceStr(weihuan);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                moneyLoanDO.setImage(imageUrl);
            } else { //没有传空
                moneyLoanDO.setImage("");
            }

            GreenDaoUtils.getInstance(this).getMoneyLoanDODao().update(moneyLoanDO);

            /*
              编辑前通过 编辑 父Id ,查找到子表数据先 全部删除
             */
            List<PayRecord> payRecords = GreenDaoUtils.getInstance(this).getPayRecordDao()._queryMoneyLoanDO_PayRecordList(updataId);
            if (payRecords.size() != 0) {//点击编辑时，拿到之前数据
                for (int i = 0; i < payRecords.size(); i++) {
                    GreenDaoUtils.getInstance(this).getPayRecordDao().delete(payRecords.get(i));
                }
            }

            /* 然后添加适配器数据 进当前子表

             */
            if (currencyLendingAdapter.getRecordList() != null) {
                int size = currencyLendingAdapter.getRecordList().size();
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        PayRecord payRecord = new PayRecord();
                        payRecord.setPId(moneyLoanDO.getId());
                        payRecord.setPayTime(currencyLendingAdapter.getRecordList().get(i).getPayTime());
                        payRecord.setPayMoneyStr(currencyLendingAdapter.getRecordList().get(i).getPayMoneyStr());
                        GreenDaoUtils.getInstance(this).getPayRecordDao().insert(payRecord);
                    }
                } else {
                    GreenDaoUtils.getInstance(this).getPayRecordDao().insertInTx(new ArrayList<PayRecord>());
                }
            }
        } else if (currencyLendingDetailsResult != null) {
            MoneyLoanUpdataEntity moneyLoan = new MoneyLoanUpdataEntity();
            moneyLoan.setId(currencyLendingDetailsResult.getId());
            moneyLoan.setDebtRelationId(currencyLendingDetailsResult.getDebtRelationId());
            moneyLoan.setPay(pay);
            moneyLoan.setType(bz);
            moneyLoan.setPrincipalStr(benjin);
            BigDecimal bigDecimal = new BigDecimal(lixi);
            BigDecimal bigDecimal2 = bigDecimal.divide(new BigDecimal("100"));
            moneyLoan.setInterest(bigDecimal2.doubleValue());//利息
            moneyLoan.setLoanTime(Util.strParsedata(fk));
            moneyLoan.setEndTime(Util.strParsedata(hk_jz_time));
            moneyLoan.setPayMoneyStr(yh);
            moneyLoan.setBalanceStr(weihuan);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                moneyLoan.setImage(imageUrl);
            } else { //没有传空
                moneyLoan.setImage("");
            }
            List<PayRecord> payRecordList = new ArrayList<>();
            if (currencyLendingAdapter.getRecordList() != null) {
                int size = currencyLendingAdapter.getRecordList().size();
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        PayRecord payRecord = new PayRecord();
                        payRecord.setPayTime(currencyLendingAdapter.getRecordList().get(i).getPayTime());
                        payRecord.setPayMoneyStr(currencyLendingAdapter.getRecordList().get(i).getPayMoneyStr());
                        payRecordList.add(payRecord);
                    }
                }
            }
            moneyLoan.setPayRecordList(payRecordList);

            getHttpUpdata(moneyLoan);

        } else { //新增
            MoneyLoanDO moneyLoanDO = new MoneyLoanDO();
            moneyLoanDO.setType(bz);
            moneyLoanDO.setPrincipalStr(benjin);
            BigDecimal bigDecimal = new BigDecimal(lixi);
            BigDecimal bigDecimal2 = bigDecimal.divide(new BigDecimal("100"));
            moneyLoanDO.setInterest(bigDecimal2.doubleValue());//利息
            moneyLoanDO.setLoanTime(Util.strParsedata(fk));
            moneyLoanDO.setEndTime(Util.strParsedata(hk_jz_time));
            moneyLoanDO.setPayMoneyStr(yh);
            moneyLoanDO.setBalanceStr(weihuan);
            if (adapter.getPath() != null && adapter.getPath().size() != 0) {//设置图片
                String imageUrl = Util.listToString(adapter.getPath());
                moneyLoanDO.setImage(imageUrl);
            } else { //没有传空
                moneyLoanDO.setImage("");
            }

            GreenDaoUtils.getInstance(this).getMoneyLoanDODao().insert(moneyLoanDO);

            if (currencyLendingAdapter.getRecordList() != null) { //子列表有数据时 ，进行数据库 添加
                int size = currencyLendingAdapter.getRecordList().size();
                if (size != 0) {
                    for (int i = 0; i < size; i++) {
                        PayRecord payRecord = new PayRecord();
                        payRecord.setPId(moneyLoanDO.getId());
                        payRecord.setPayTime(currencyLendingAdapter.getRecordList().get(i).getPayTime());
                        payRecord.setPayMoneyStr(currencyLendingAdapter.getRecordList().get(i).getPayMoneyStr());
                        GreenDaoUtils.getInstance(this).getPayRecordDao().insert(payRecord);
                    }
//                    List<PayRecord> payRecords1 = GreenDaoUtils.getInstance(this).getPayRecordDao().loadAll();
//                    if (payRecords1.size() != 0) {
//                        for (int i = 0; i < payRecords1.size(); i++) {
//                            LUtils.e("--新增--子--pId--", payRecords1.get(i).getPId() + "");
//                            LUtils.e("--新增-子--钱--", payRecords1.get(i).getPayMoneyStr() + "");
//                        }
//                    }
                } else {
                    GreenDaoUtils.getInstance(this).getPayRecordDao().insertInTx(new ArrayList<PayRecord>());
                }
            }
        }
        finish();
    }

    private void getHttpUpdata(MoneyLoanUpdataEntity moneyLoan) {

        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            moneyLoan.setAccessToken(baseRequests.get(0).getAccessToken());
            moneyLoan.setPlatform(2);
            moneyLoan.setUuid(baseRequests.get(0).getUuid());
            moneyLoan.setUid(baseRequests.get(0).getUid());
            moneyLoan.setLoginUsername(baseRequests.get(0).getLoginUsername());
            String json = Util.toJson(moneyLoan);//转成json
            LUtils.e("----事物借贷--json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getMoneyLoanUpdata(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                UserInfoUtils.setUuid(CurrencyLending.this, baseResponse);
                                finish();
                            }
                        }
                    });
        }
    }


    /**
     * 结算日期保存按钮操作
     */
    private void presrve() {

        String riqi = et_currency_js_time.getText().toString().trim();//日期
        String jine = et_currency_js_jine.getText().toString().trim();//金额
        if (TextUtils.isEmpty(riqi)) {
            ToastUtil.showShort(this, "请选择结算日期");
            return;
        }
        if (TextUtils.isEmpty(jine)) {
            ToastUtil.showShort(this, "请输入结算金额");
            return;
        }

        PayRecord payRecord = new PayRecord();
        payRecord.setPayMoneyStr(jine);
        payRecord.setPayTime(Util.strParsedata(riqi));
        payRecordList.add(payRecord);
        if (payRecordList.size() > 0) {
            ll_title.setVisibility(View.VISIBLE);
        }
        currencyLendingAdapter.setRecordList(payRecordList);//为适配器设置数据源
        recyclerView_currency.setAdapter(currencyLendingAdapter);
        currencyLendingAdapter.notifyDataSetChanged();
        et_currency_js_time.setText("");
        et_currency_js_jine.setText("");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == rg_currency) {//是否结算过
            if (checkedId == R.id.rb_currency_yer) {//是
                pay=true;
                cl_currency_add.setVisibility(View.VISIBLE);


                List<PayRecord> payRecordList = GreenDaoUtils.getInstance(this).getPayRecordDao().loadAll();
                if (payRecordList.size() == 0) {//数据源为0时，隐藏头布局
                    ll_title.setVisibility(View.GONE);
                }
            } else if (checkedId == R.id.rb_currency_no) {//否
                pay=false;
                cl_currency_add.setVisibility(View.GONE);
            }
        }
    }

    private void getHttpImage(List<File> resultList) {
        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(CurrencyLending.this).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(CurrencyLending.this).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(CurrencyLending.this) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(CurrencyLending.this, baseResponse);
                            mPath.addAll(data);
                            adapter.setPath(mPath);
                            gv_currency_photo.setAdapter(adapter);
                        }
                    }
                });
    }

    /**
     * 获取本地图片回掉
     */
    private GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList.size() != 0) {
                LUtils.e("--------大小---",+resultList.size()+"");
                getHttpImage(ImageUtils.comperssImage(resultList));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
        }

    };


    private AdapterView.OnItemClickListener Oitemlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == parent.getCount() - 1) {
                Photo_Select.open_Photo(CurrencyLending.this, parent.getCount() - 1, callback);
                adapter.notifyDataSetChanged();
            }
        }
    };

}
