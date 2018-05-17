package com.yl.baiduren.activity.debtbunesshall;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Debt_One_BasicInformation;
import com.yl.baiduren.activity.debtrecord.Debt_Three_DebtorAssets;
import com.yl.baiduren.activity.debtrecord.Debt_Too_CreditorsDemand;
import com.yl.baiduren.activity.debtrecord.Debt_fore_Actiity2;
import com.yl.baiduren.activity.pay_for.Confirm_Delisting;
import com.yl.baiduren.activity.pay_for.Open_Member;
import com.yl.baiduren.adapter.DebtDetailsProofAdapter;
import com.yl.baiduren.adapter.Debt_Details_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.IDeleteCall;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Debt_Details_Entity;
import com.yl.baiduren.entity.request_body.Delete_Demend_Assert_Body;
import com.yl.baiduren.entity.result.Debt_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import org.feezu.liuli.timeselector.Utils.TextUtil;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.Observable;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/*
  Created by sunbeibei on 2017/12/11.
 */

/**
 * 债事详情
 */
public class Debt_Details extends BaseActivity implements View.OnClickListener, IDeleteCall {

    private MyRecyclerView recyclerView1, recyclerView2, debt_recyclerview_proof;
    private ImageView finish;
    private TextView debt_wu_code, debt_wu_name, debt_crd_code, debt_crd_name, debt_type,
            debt_money, debt_time, debt_stast, debt_details_time, debt_pipei, debt_jieguo,
            modification, debt_yongjin, debt_endTime;
    private Button btn_all;

    private Debt_Details_Result.DebtRelationResponse1Bean debtRelationResponse2;
    private TextView add_demend;
    private TextView add_assic;
    private TextView add_pingzheng;
    private TextView infromation_type;
    private LinearLayout line_text;
    private int back;
    private LinearLayout renline_isshow;
    private ImageView cilmed_header;
    private TextView debt_details_rel;
    private TextView debt_details_phone;
    private TextView debt_person_phone;
    private ImageView tv_debt_details_1, tv_debt_details_2, tv_debt_details_3, tv_debt_details_4;
    private View view_pro;
    private LinearLayout linearLayout;
    private LinearLayout layou_details;
    private TextView detail_num;

    private Integer userType;
    private boolean isVip;
    private Long id;//债事id
    private Long userId = null;//债事对应的用户Id
    private Long childUserId;//子账号id
    private String comePager = null;//表示是从哪个页面进入的
    private TextView tv_zqr_name_debt, tv_zqr_code_debt, tv_zwr_code_debt, tv_zwr_name_debt;


    @Override
    public int loadWindowLayout() {
        return R.layout.debt_details_activity;
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();

        id = intent.getLongExtra("id", 0);//债事Id
        childUserId = getIntent().getLongExtra("childUserId", 0); //值不为空时，表示从子账号进入
        comePager = intent.getStringExtra("comePager");
        back = intent.getIntExtra("back", 0);//返回
        linearLayout = findViewById(R.id.line_layout);//跟布局
        layou_details = findViewById(R.id.layout_detail);
        modification = findViewById(R.id.modification);//修改基本信息
        modification.setOnClickListener(this);
        debt_stast = findViewById(R.id.debt_stast);//债事状态
        debt_details_time = findViewById(R.id.debt_details_time);//备案时长
        debt_pipei = findViewById(R.id.debt_pipei);//匹配时长
        debt_jieguo = findViewById(R.id.debt_jieguo);//匹配结果
        finish = findViewById(R.id.details_back);
        detail_num = findViewById(R.id.detail_num);//债事编号
        finish.setOnClickListener(this);
        infromation_type = findViewById(R.id.infromation_type);//凭证状态

        //债务人证号
        debt_wu_code = findViewById(R.id.debt_wu_code);
        tv_zwr_code_debt = findViewById(R.id.tv_zwr_code_debt);
        //债务人姓名
        debt_wu_name = findViewById(R.id.debt_wu_name);
        tv_zwr_name_debt = findViewById(R.id.tv_zwr_name_debt);
        //债权人证号
        debt_crd_code = findViewById(R.id.debt_crd_code);
        tv_zqr_code_debt = findViewById(R.id.tv_zqr_code_debt);
        //债权人姓名
        debt_crd_name = findViewById(R.id.debt_crd_name);
        tv_zqr_name_debt = findViewById(R.id.tv_zqr_name_debt);
        //债事类型
        debt_type = findViewById(R.id.debt_type);
        //解债佣金
        debt_yongjin = findViewById(R.id.debt_yongjin);
        //债事金额
        debt_money = findViewById(R.id.debt_money);
        //债事时间
        debt_time = findViewById(R.id.debt_time);
        //债事结束时间
        debt_endTime = findViewById(R.id.debt_endTime);
        //摘牌
        btn_all = findViewById(R.id.btn_all);
        btn_all.setOnClickListener(this);
        //新增需求
        add_demend = findViewById(R.id.add_demend);
        add_demend.setOnClickListener(this);
        //新增资产
        add_assic = findViewById(R.id.add_assic);
        add_assic.setOnClickListener(this);
        //新增凭证
        add_pingzheng = findViewById(R.id.add_pingzheng);
        add_pingzheng.setOnClickListener(this);

        renline_isshow = findViewById(R.id.renline_isshow);//认领人是否显示
        cilmed_header = findViewById(R.id.cilmed_header);//认领人头像
        debt_details_rel = findViewById(R.id.debt_details_rel);//债事认领人姓名
        debt_details_phone = findViewById(R.id.debt_details_phone);//债事认领人联系电话
        debt_person_phone = findViewById(R.id.debt_person_phone);//备案人手机号
        tv_debt_details_1 = findViewById(R.id.tv_debt_details_1);//基本信息
        tv_debt_details_2 = findViewById(R.id.tv_debt_details_2);//债权人需求
        tv_debt_details_3 = findViewById(R.id.tv_debt_details_3);//债务人资产
        tv_debt_details_4 = findViewById(R.id.tv_debt_details_4);//上传凭证
        view_pro = findViewById(R.id.view_pro);//进度线


        //债权人需求
        recyclerView1 = findViewById(R.id.debt_recyclerview);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        //债务人资产
        recyclerView2 = findViewById(R.id.debt2_recyclerview);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        line_text = findViewById(R.id.line_text);//上传凭证下状态字
        //上传凭证
        debt_recyclerview_proof = findViewById(R.id.debt_recyclerview_proof);
        debt_recyclerview_proof.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        childUserId = getIntent().getLongExtra("childUserId", 0);
        id = intent.getLongExtra("id", 0);//债事Id
        back = intent.getIntExtra("back", 0);//返回
        LUtils.e("-------onNewIntent----------" + id);
    }


    @Override
    protected void onResume() {
        super.onResume();
        requestWord();
    }

    private void requestWord() {//详情接口

        if (UserInfoUtils.IsLogin(this)) {
            Debt_Details_Entity entity = new Debt_Details_Entity(DataWarehouse.getBaseRequest(this));
            entity.setId(id);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitHelper.getInstance(this).getServer()
                    .getDetailsResult(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<Debt_Details_Result>>bindToLifecycle()))
                    .subscribe(new BaseObserver<Debt_Details_Result>(this) {

                        @Override
                        protected void onSuccees(String code, Debt_Details_Result data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                Debt_Details_Result.DebtRelationResponse1Bean debtRelationResponse1 = data.getDebtRelationResponse1();
                                debtRelationResponse2 = debtRelationResponse1;

                                showStatus(data);//债事展出状态
                                status(data.getDebtRelationElseResponse().getProgress());//进度状态
                                judgment(data.getDebtRelationResponse1().getFromType(), data.getDebtRelationResponse1().getToType());
                                showInfo(data);//基本信息可显示部分
                                isProofGone(data.getFlag());//判断资料是否填写完整
                                LUtils.e("-----是否显示认领人----", data.getDebtRelationElseResponse().getIsHasSettle() + "");
                                boolean isHasSettle = isHasSettle(data.getDebtRelationElseResponse().getIsHasSettle());//是否显示认领人
                                isVip = data.isVip();
                                userType = data.getUserType();
                                userId = data.getUserId();
                                isShow(isHasSettle, data);

                                boolean isMyDebt = isMyDebt(userId);//判断备案人
                                if (isMyDebt) {//自己备的案
                                    if (comePager.equals(Constant.PAGER_MYDEBT_YZP)) {//我的备案 已摘牌
                                        LUtils.e("----------我的备案 已摘牌--------");
                                        statusE(data);
                                        hideButton(data);
                                        isGoneZaiPaiButton();
                                    } else if (comePager.equals(Constant.PAGER_MYDEBT_WZP)) {//我的备案 未摘牌
                                        LUtils.e("----------我的备案 未摘牌--------");
                                        isGoneZaiPaiButton();
                                    } else if (comePager.equals(Constant.PAGER_DEBTHALL_WZP)) {//债事大厅 为摘牌
                                        statusE(data);
                                    } else if (comePager.equals(Constant.PAGER_MY_ZHAIPAI)) {//我的摘牌
                                        statusE(data);
                                        isGoneZaiPaiButton();
                                    }
                                    strInfoData();
                                } else {
                                    if (comePager.equals(Constant.PAGER_SUSSFUL)) {//成功案例
                                        isGoneUpdataButton();
                                        strReplace();
                                        isGoneZaiPaiButton();
                                    } else if (comePager.equals(Constant.PAGER_MYDEBT_WZP)) {//我的备案 未摘牌
                                        isGoneUpdataButton();
                                        strReplace();
                                    } else if (comePager.equals(Constant.PAGER_MYDEBT_YZP)) {//我的备案 已摘牌
                                        LUtils.e("-----false-----我的备案 已摘牌--------");
                                        isGoneUpdataButton();
                                        strReplace();
                                        isGoneZaiPaiButton();
                                    } else if (comePager.equals(Constant.PAGER_DEBTHALL_WZP)) {//债事大厅 未摘牌
                                        isUserPermissions(isVip, userType);
                                        strReplace();
                                        isGoneUpdataButton();
                                    } else if (comePager.equals(Constant.PAGER_DEBTHALL_YZP)) {//债事大厅 已摘牌
                                        isUserPermissions(isVip, userType);
                                        strReplace();
                                        isGoneUpdataButton();
                                        isGoneZaiPaiButton();
                                    } else if (comePager.equals(Constant.PAGER_MY_CHILDDEBT_WZP)) {//我的子账号备案 未摘牌
                                        isUserPermissions(isVip, userType);
                                        strReplace();
                                        isGoneUpdataButton();
                                    } else if (comePager.equals(Constant.PAGER_MY_CHILDDEBT_YZP)) {//我的子账号备案 已摘牌
                                        isUserPermissions(isVip, userType);
                                        strReplace();
                                        isGoneUpdataButton();
                                        isGoneZaiPaiButton();
                                    } else if (comePager.equals(Constant.PAGER_MY_CHILD_ZHAIPAI)) {//子账号我的摘牌
                                        isUserPermissions(isVip, userType);
                                        strReplace();
                                        isGoneUpdataButton();
                                        isGoneZaiPaiButton();
                                    } else if (comePager.equals(Constant.PAGER_MY_ZHAIPAI)) {//我的摘牌
                                        statusE(data);
                                        strInfoData();
                                        isGoneUpdataButton();
                                        isGoneZaiPaiButton();
                                    }

                                }
                                setAdapter(data);//设置适配器
                            }
                        }
                    });

        } else {
            finish();

        }
    }

    /**
     * @param debtFromType 债权人
     * @param debtToType   债务人
     */
    private void judgment(String debtFromType, String debtToType) {
        if (debtFromType.equals("1")) {//机构
            tv_zwr_code_debt.setText("机 构 代 码:");
            tv_zwr_name_debt.setText("债 务 机 构:");
        } else {//个人
            tv_zwr_code_debt.setText("债务人证号 :");
            tv_zwr_name_debt.setText("债务人姓名 :");
        }
        if (debtToType.equals("1")) {//机构
            tv_zqr_code_debt.setText("机 构 代 码 :");
            tv_zqr_name_debt.setText("债 权 机 构 :");

        } else {//个人
            tv_zqr_code_debt.setText("债权人证号:");
            tv_zqr_name_debt.setText("债权人姓名:");

        }
    }

    /**
     * 根据vip  userType 判断用户权限
     *
     * @param vip      true vip
     * @param userType 0 普通用户 1解债人
     */
    private void isUserPermissions(boolean vip, int userType) {
        if (!vip && userType == 0) {
            isMoHu();
            goOpenVip();
        }
    }

    /**
     * 普通用户呈现模糊效果
     */
    private void isMoHu() {
        layou_details.setVisibility(View.GONE);
        Glide.with(this).load(R.drawable.menglong)
                .bitmapTransform(new BlurTransformation(this, 30)).into(new ViewTarget<View, GlideDrawable>(linearLayout) {
            //括号里为需要加载的控件
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResourceReady(GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                this.view.setBackground(resource.getCurrent());
            }
        });
    }

    /**
     * 隐藏新增/修改按钮
     */
    private void isGoneUpdataButton() {
        modification.setVisibility(View.GONE);
        add_demend.setVisibility(View.GONE);
        add_assic.setVisibility(View.GONE);
        add_pingzheng.setVisibility(View.GONE);
    }

    /**
     * 隐藏摘牌按钮
     */
    private void isGoneZaiPaiButton() {
        btn_all.setVisibility(View.GONE);
    }

    /**
     * 用户非解债人时 弹窗  去摘牌说明
     */
    private void show_Dialogs() {
        DialogUtils.showDialogZsr(this, false, Constant.MESSAGE, new DialogUtils.OnButtonEventListener() {
            @Override
            public void onConfirm() {
                startActivity(new Intent(Debt_Details.this, Web_Greate_Debt_Preson.class));
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * 用户非vip时 弹窗  去开通vip
     */
    private void goOpenVip() {
        DialogUtils.showDialogZsr(this, false, Constant.MESSAGE_Vip, new DialogUtils.OnButtonEventListener() {
            @Override
            public void onConfirm() {
                startActivity(new Intent(Debt_Details.this, Open_Member.class));
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * 展示认领人
     *
     * @param isHasSettle
     * @param data
     */
    private void isShow(boolean isHasSettle, Debt_Details_Result data) {
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (isHasSettle) {
            if (userId == baseRequestList.get(0).getUid()) {// 备案人id 与 8
                statusE(data);
            } else {
                if (!TextUtils.isEmpty(data.getDebtRelationElseResponse().getSettleName())) {
                    String settle_na = new String(data.getDebtRelationElseResponse().getSettleName());
                    debt_details_rel.setText(settle_na.replace(settle_na.substring(1, data.getDebtRelationElseResponse().getSettleName().length()), "*"));
                }
                if (!TextUtils.isEmpty(data.getDebtRelationElseResponse().getSettleMobile())) {
                    String settle_ph = new String(data.getDebtRelationElseResponse().getSettleMobile());
                    debt_details_phone.setText(settle_ph.replace(settle_ph.substring(3, data.getDebtRelationElseResponse().getSettleMobile().length()), "********"));
                }
            }
            if (!TextUtils.isEmpty(data.getDebtRelationElseResponse().getSettleImage())) {
                Glide.with(Debt_Details.this)
                        .load(data.getDebtRelationElseResponse().getSettleImage())
                        .bitmapTransform(new CropCircleTransformation(this))
                        .crossFade(1000)
                        .into(cilmed_header);
            }
        }
    }

    private void statusE(Debt_Details_Result data) {
        if (!TextUtils.isEmpty(data.getDebtRelationElseResponse().getSettleName())) {
            debt_details_rel.setText(data.getDebtRelationElseResponse().getSettleName());
        }
        if (!TextUtils.isEmpty(data.getDebtRelationElseResponse().getSettleMobile())) {
            debt_details_phone.setText(data.getDebtRelationElseResponse().getSettleMobile());
        }
    }

    /**
     * 判断该债事是否是自己备的案  return true 是自己备的案
     *
     * @param userId 该条债事备案人的id
     */
    private boolean isMyDebt(Long userId) {

        if (UserInfoUtils.IsLogin(this)) {
            //登陆用户 与 债事备案人  是同一个人
            return userId.equals(DataWarehouse.getBaseRequest(this).uid);
        } else {
            return false;
        }
    }

    /**
     * 是否有人摘牌
     *
     * @param isHasSettle 0没有认领人
     */
    private boolean isHasSettle(int isHasSettle) {
        if (isHasSettle == 1) {
            LUtils.e("-----有摘牌--------");
            renline_isshow.setVisibility(View.VISIBLE);
            return true;
        } else if (isHasSettle == 0) {
            LUtils.e("----没-有摘牌--------");
            renline_isshow.setVisibility(View.GONE);
            return false;
        }
        return false;
    }

    /**
     * 进度状态
     *
     * @param pro
     */
    private void status(String pro) {

        char one = pro.charAt(0);
        char too = pro.charAt(1);
        char three = pro.charAt(2);
        char fore = pro.charAt(3);
        if (one == '1') {
            tv_debt_details_1.setImageResource(R.mipmap.debt_details_wc);
        }
        if (too == '1') {
            tv_debt_details_2.setImageResource(R.mipmap.debt_details_wc);
        }
        if (three == '1') {
            tv_debt_details_3.setImageResource(R.mipmap.debt_details_wc);
        }
        if (fore == '1') {
            tv_debt_details_4.setImageResource(R.mipmap.debt_details_wc);
        }
        if (one == '1' && too == '1' && three == '1' && fore == '1') {
            view_pro.setBackgroundColor(getResources().getColor(R.color.orgin));
        }
    }


    /**
     * 替换字符串 为*
     */
    private void strReplace() {
        debt_wu_code.setText(Constant.codeN);//债务人证号
        debt_crd_code.setText(Constant.codeN);//债权人证号
        //备案人手机号
        String phoneNumbe = new String(debtRelationResponse2.getPhoneNumber());
        debt_person_phone.setText(phoneNumbe.replace(phoneNumbe.substring(3, phoneNumbe.length()), "********"));
        //债务人姓名
        String fromName = new String(debtRelationResponse2.getFromName());
        debt_wu_name.setText(fromName.replace(fromName.substring(1, fromName.length()), "*"));
        //债权人姓名
        String toName = new String(debtRelationResponse2.getToName());
        debt_crd_name.setText(toName.replace(toName.substring(1, debtRelationResponse2.getToName().length()), "*"));
    }

    /**
     * 自己看自己的基础信息时
     */
    private void strInfoData() {
        debt_wu_code.setText(debtRelationResponse2.getFromCardNumber());
        debt_wu_name.setText(debtRelationResponse2.getFromName());
        debt_crd_code.setText(debtRelationResponse2.getToCardNumber());
        debt_crd_name.setText(debtRelationResponse2.getToName());
        debt_person_phone.setText(debtRelationResponse2.getPhoneNumber());//备案人手机号
    }

    /**
     * 判断资料是否填写完整
     *
     * @param flag
     */
    private void isProofGone(int flag) {
        if (flag == 1) {//资料未填写
            debt_recyclerview_proof.setVisibility(View.GONE);
            line_text.setVisibility(View.VISIBLE);
            infromation_type.setText("资料未填写");
        } else if (flag == 2) {//没有第四部资料
            debt_recyclerview_proof.setVisibility(View.GONE);
            line_text.setVisibility(View.VISIBLE);
            infromation_type.setText("无资料");
        } else {
            debt_recyclerview_proof.setVisibility(View.VISIBLE);
            line_text.setVisibility(View.GONE);
        }
    }


    /**
     * 债事展出状态
     *
     * @param data
     */
    private void showStatus(Debt_Details_Result data) {
        debt_stast.setText(data.getDebtRelationElseResponse().getStatus());
        debt_details_time.setText(data.getDebtRelationElseResponse().getCreateDays() + "天");
        debt_pipei.setText(data.getDebtRelationElseResponse().getMatchDays() + "天");
        debt_jieguo.setText(data.getDebtRelationElseResponse().getMatchResult());
        detail_num.setText("债事编号:" + data.getDebtRelationElseResponse().getCode());

    }

    /**
     * 当从我的备案已摘牌进来时，
     *
     * @param data
     */
    private void hideButton(Debt_Details_Result data) {
        if ("已解债".equals(data.getDebtRelationElseResponse().getStatus())) {
            isGoneUpdataButton();
        }
    }


    /**
     * 显示基本信息 债事类型，金额，时间
     *
     * @param data
     */
    private void showInfo(Debt_Details_Result data) {
        debt_type.setText(data.getDebtRelationResponse1().getTypeName());
        debt_money.setText(data.getDebtRelationResponse1().getAmoutStr());
        if (!TextUtil.isEmpty(data.getDebtRelationResponse1().getCommission())) {
            debt_yongjin.setText(data.getDebtRelationResponse1().getCommission() + "%");
        }
        debt_time.setText(data.getDebtRelationResponse1().getRecordTime());
        if (!TextUtil.isEmpty(data.getDebtRelationResponse1().getEndTime())) {
            debt_endTime.setText(data.getDebtRelationResponse1().getEndTime());
        }
    }

    /**
     * 设置数据源，适配器
     */
    private void setAdapter(Debt_Details_Result data) {
        //债权人需求
        List<Debt_Details_Result.DemandResponsesBean> demandResponses = data.getDemandResponses();
        List<Debt_Details_Result.AssetResponsesBean> assetResponses = data.getAssetResponses();
        String status = data.getDebtRelationElseResponse().getStatus();
        Debt_Details_Adapter adapter = new Debt_Details_Adapter(1, Debt_Details.this, demandResponses, assetResponses, userId, childUserId, comePager, id, status);
        recyclerView1.setAdapter(adapter);
        //债务人资产
        Debt_Details_Adapter adapter2 = new Debt_Details_Adapter(2, Debt_Details.this, demandResponses, assetResponses, userId, childUserId, comePager, id, status);
        recyclerView2.setAdapter(adapter2);
        //上传凭证
        List<Debt_Details_Result.DebtRelation4ResponsesBean> debtRelation4Responses = data.getDebtRelation4Responses();
        DebtDetailsProofAdapter debtDetailsProofAdapter = new DebtDetailsProofAdapter(Debt_Details.this, comePager, status);
        debtDetailsProofAdapter.setUserId(userId);
        debtDetailsProofAdapter.setChildUserId(childUserId);
        debtDetailsProofAdapter.setTest2List(debtRelation4Responses);
        debt_recyclerview_proof.setAdapter(debtDetailsProofAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == finish) {
            if (back == 1) {
                startActivity(new Intent(this, Debt_Buness_Hall.class).putExtra("back", 1));
                finish();
            } else {
                finish();
            }

        } else if (v == btn_all) {//进入摘牌
            if (userType == 1) {//解债人
                startActivity(new Intent(this, Confirm_Delisting.class).putExtra("debtid", id));
                finish();
            } else {
                show_Dialogs();
            }
        } else if (v == modification) {
            Intent intent = new Intent(this, Debt_One_BasicInformation.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else if (v == add_demend) {//新增需求
            startActivity(new Intent(this, Debt_Too_CreditorsDemand.class).putExtra("id", id));
        } else if (v == add_assic) {//新增资产
            startActivity(new Intent(this, Debt_Three_DebtorAssets.class).putExtra("id", id));
        } else if (v == add_pingzheng) {//新增凭证
            startActivity(new Intent(this, Debt_fore_Actiity2.class).putExtra("id", id));
        }
    }


    /**
     * 删除回掉
     *
     * @param id
     * @param type 类别
     */
    @Override
    public void onDelete(Long id, int type) {
        deleteItem(id, type);
    }

    /**
     * 删除item
     *
     * @param demend_Id
     * @param type
     */
    private void deleteItem(Long demend_Id, int type) {
        Delete_Demend_Assert_Body entity = new Delete_Demend_Assert_Body(DataWarehouse.getBaseRequest(this));
        entity.setId(demend_Id);
        entity.setDebtRelationId(id);

        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密

        RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
        Observable<BaseEntity<String>> observable = null;
        if (type == 1) {//删除需求
            observable = retrofitService.delete_Demand(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 2) {//删除资产
            observable = retrofitService.delete_Assert(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 3) {//删除货币借贷
            observable = retrofitService.delete_MoneyLoan(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 4) {//删除实物借贷
            observable = retrofitService.delete_GoodLoan(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 5) {//删除产权借贷
            observable = retrofitService.delete_PropertyLoan(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 6) {//删除担保人
            observable = retrofitService.delete_SponsorLoan(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        } else if (type == 7) {//删除资产抵押
            observable = retrofitService.delete_MortgageLoan(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
        }

        observable.compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {


                        }

                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (back == 1) {
            startActivity(new Intent(this, My_Record_Activity.class).putExtra("back", 1));
            finish();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        userId = null;
    }
}
