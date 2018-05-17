package com.yl.baiduren.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.mypager.About_Us_Activity;
import com.yl.baiduren.activity.mypager.Apply_Claimant_People;
import com.yl.baiduren.activity.mypager.FeedBack;
import com.yl.baiduren.activity.mypager.MyReport;
import com.yl.baiduren.activity.mypager.My_Bill;
import com.yl.baiduren.activity.mypager.My_Child_Account;
import com.yl.baiduren.activity.mypager.My_Delisting;
import com.yl.baiduren.activity.mypager.My_Message;
import com.yl.baiduren.activity.mypager.PersonalInformation;
import com.yl.baiduren.activity.pay_for.Open_Member;
import com.yl.baiduren.activity.pay_for.Recharge;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.data.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.greenentity.LoginSuccess;
import com.yl.baiduren.entity.result.MyPager;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.SharedUtil;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ForeFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_information, tv_order, tv_bill, tv_account_number,
            tv_opinion, tv_about, tv_telephone, tv_vip, tv_userName,
            mypager_record_num, tv_my_report;
    private LinearLayout ll_my_chile, ll_no_login, ll_yer;
    private Button recharge;
    private ImageView iv_userImage, mypager_vip, mypager_break_debt;
    private TextView tv_apply_debt;
    private LinearLayout apply_debt_person, tv_login_zhuc, tv_xiaoxi;
    private ImageView iv_info;
    private boolean isVip;

    @Override
    public int loadWindowLayout() {
        return R.layout.fragment_fore;
    }


    @Override
    public void initViews(View rootView) {
        registerBoradcastReceiver();
        iv_info = rootView.findViewById(R.id.iv_info);//来消息时
        recharge = rootView.findViewById(R.id.mypager_recharge);
        recharge.setOnClickListener(this);
        tv_xiaoxi = rootView.findViewById(R.id.tv_xiaoxi);//我的消息
        tv_xiaoxi.setOnClickListener(this);
        tv_information = rootView.findViewById(R.id.information);//个人资料
        tv_information.setOnClickListener(this);
        tv_order = rootView.findViewById(R.id.tv_order);//我的摘牌
        tv_order.setOnClickListener(this);
        tv_my_report = rootView.findViewById(R.id.tv_my_report);//我的报告
        tv_my_report.setOnClickListener(this);
        tv_bill = rootView.findViewById(R.id.tv_bill);//我的账单
        tv_bill.setOnClickListener(this);
        tv_vip = rootView.findViewById(R.id.tv_vip);//成为会员
        tv_vip.setOnClickListener(this);
        ll_my_chile = rootView.findViewById(R.id.ll_my_chile);//我们的子账号父布局
        tv_account_number = rootView.findViewById(R.id.tv_account_number);//我的子账号
        tv_account_number.setOnClickListener(this);
        tv_about = rootView.findViewById(R.id.tv_about);//关于我们
        tv_about.setOnClickListener(this);
        tv_opinion = rootView.findViewById(R.id.tv_opinion);//意见反馈
        tv_opinion.setOnClickListener(this);
        tv_telephone = rootView.findViewById(R.id.tv_telephone);//客服热线
        tv_telephone.setOnClickListener(this);
        tv_login_zhuc = rootView.findViewById(R.id.tv_login_zhuc);//登陆注册
        tv_login_zhuc.setOnClickListener(this);
        ll_no_login = rootView.findViewById(R.id.ll_no_login);//未登录界面
        ll_yer = rootView.findViewById(R.id.ll_yer);//登录界面
        iv_userImage = rootView.findViewById(R.id.iv_userImage);//用户头像
        iv_userImage.setOnClickListener(this);
        tv_userName = rootView.findViewById(R.id.tv_userName);//昵称
        mypager_record_num = rootView.findViewById(R.id.mypager_record_num);//备案次数
        Button mypager_recharge = rootView.findViewById(R.id.mypager_recharge);
        mypager_vip = rootView.findViewById(R.id.mypager_vip);//vip 图标
        mypager_break_debt = rootView.findViewById(R.id.mypager_break_debt);//解债人 图标
        tv_apply_debt = rootView.findViewById(R.id.tv_apply_debt); //申请解债人
        tv_apply_debt.setOnClickListener(this);
        apply_debt_person = rootView.findViewById(R.id.apply_debt_person);//申请解债人的一行
    }


    @Override
    public void onClick(View v) {

        if (v == tv_xiaoxi) {//我的消息
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), My_Message.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登陆");
            }

        } else if (v == tv_information || v == iv_userImage) {//个人资料
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), PersonalInformation.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登陆");
            }
        } else if (v == tv_order) {//我的摘牌
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), My_Delisting.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登录");
            }

        } else if (v == tv_my_report) {//我的报告
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), MyReport.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登录");
            }
        } else if (v == tv_bill) {//我的账单
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), My_Bill.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登录");
            }

        } else if (v == tv_vip) {//开通Vip

            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), Open_Member.class));
            } else {
                startActivity(new Intent(getActivity(), Login_Activity_Password.class));
            }
        } else if (v == tv_account_number) {//我的子帐号
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), My_Child_Account.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登陆");
            }

        } else if (v == tv_apply_debt) {//申请解债人
            if (UserInfoUtils.IsLogin(getActivity())) {
                if (isVip) {
                    startActivity(new Intent(getActivity(), Apply_Claimant_People.class));
                } else {
                    ToastUtil.showShort(getActivity(), "您不是VIP，请先开通VIP");
                }

            } else {
                ToastUtil.showShort(getActivity(), "请先登陆");
            }

        } else if (v == tv_about) {//关于我们
            startActivity(new Intent(getActivity(), About_Us_Activity.class));

        } else if (v == tv_opinion) {//意见反馈
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), FeedBack.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登陆");
            }


        } else if (v == tv_telephone) {//客服热线
            DialogUtils.showPhone(getActivity());

        } else if (v == recharge) {//充值
            if (UserInfoUtils.IsLogin(getActivity())) {
                startActivity(new Intent(getActivity(), Recharge.class));
            } else {
                ToastUtil.showShort(getActivity(), "请先登陆");
            }

        } else if (v == tv_login_zhuc) {//登陆注册
            startActivity(new Intent(getActivity(), Login_Activity_Password.class));
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {//显示时所作的事情
            getUsetInfo();
            judgmentNews();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getUsetInfo();
        judgmentNews();
    }

    private void judgmentNews() {
        boolean b = SharedUtil.getSharedUtil().getBoolean(getActivity(), Constant.IS_INFO);
        if (b) {
            iv_info.setVisibility(View.VISIBLE);
        } else {
            iv_info.setVisibility(View.GONE);
        }
    }

    /**
     * 获取用户数据
     */
    private void getUsetInfo() {
        if (UserInfoUtils.IsLogin(getActivity())) {
            BaseRequest baseRequest = DataWarehouse.getApplication(getActivity()).baseRequest;
            String json = Util.toJson(baseRequest);//转成json
            LUtils.e("-我的页面--json----", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver baseObserver = new BaseObserver<MyPager>(getActivity()) {
                @Override
                protected void onSuccees(String code, MyPager data, com.yl.baiduren.base.BaseRequest baseResponse) throws Exception {
                    UserInfoUtils.setUuid(getActivity(), baseResponse);
                    /*
                      保存，修改我的页数据
                     */
                    List<MyPager> myPagerList = GreenDaoUtils.getInstance(getActivity()).getMyPagerDao().loadAll();
                    if (myPagerList.size() == 0) { //添加我的页数据
                        LUtils.e("---保存我的页数据---");
                        GreenDaoUtils.getInstance(getActivity()).getMyPagerDao().insert(data);
                    } else { //新增
                        LUtils.e("---更新数据---");
                        MyPager myPager = myPagerList.get(0);
                        myPager.setRealName(data.getRealName());
                        myPager.setNickName(data.getNickName());
                        myPager.setAreaStr(data.getAreaStr());
                        myPager.setIdCard(data.getIdCard());
                        myPager.setAddress(data.getAddress());
                        myPager.setBurse(data.getBurse());
                        myPager.setImage(data.getImage());
                        myPager.setMobile(data.getMobile());
                        myPager.setIsVip(data.getIsVip());
                        myPager.setUserType(data.getUserType());
                        myPager.setRecordNumber(data.getRecordNumber());
                        myPager.setStandbyMobile(data.getStandbyMobile());
                        myPager.setRoleType(data.getRoleType());
                        GreenDaoUtils.getInstance(getActivity()).getMyPagerDao().update(myPager);
                    }
                    List<LoginSuccess> loginSuccessList = GreenDaoUtils.getInstance(getActivity()).getLoginSuccessDao().loadAll();
                    if (loginSuccessList.size() != 0) {//更新登录状态
                        LUtils.e("-------" + loginSuccessList.size());
                        LoginSuccess loginSuccess = loginSuccessList.get(0);
                        loginSuccess.setIsVip(data.getIsVip());
                        loginSuccess.setMobile(data.getMobile());
                        loginSuccess.setUserType(data.getUserType());
                        loginSuccess.setRoleType(data.getRoleType());
                        GreenDaoUtils.getInstance(getActivity()).getLoginSuccessDao().update(loginSuccess);
                    }
                    isLogin();
                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(getActivity()).getServer().
                    getUserInfo(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<MyPager>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            LUtils.e("-请求--未登录----");
            isLogin();
        }
    }

    private void isLogin() {
        if (UserInfoUtils.IsLogin(getActivity())) {
            LUtils.e("--已-登录----");
            ll_no_login.setVisibility(View.GONE);
            ll_yer.setVisibility(View.VISIBLE);
            List<MyPager> myPagerList = GreenDaoUtils.getInstance(getActivity()).getMyPagerDao().loadAll();
            if (myPagerList.size() != 0) {
                MyPager myPager = myPagerList.get(0);
                if (!TextUtils.isEmpty(myPager.getNickName())) {//昵称
                    tv_userName.setText(myPager.getNickName());//昵称
                }
                if (!TextUtils.isEmpty(myPager.getRecordNumber())) {//剩余备案数
                    mypager_record_num.setText(myPager.getRecordNumber());
                } else {
                    mypager_record_num.setText("0");
                }
                //设置Vip 图标
                isVip = myPager.getIsVip();
                if (isVip) {
                    Glide.with(getActivity()).load(R.drawable.vip).asGif().into(mypager_vip);
                    apply_debt_person.setVisibility(View.VISIBLE);
                    if (myPager.getUserType() == 1) { //1解债人
                        Glide.with(getActivity()).load(R.drawable.debt).asGif().into(mypager_break_debt);
                        apply_debt_person.setVisibility(View.GONE);
                    } else {
                        mypager_break_debt.setImageResource(R.drawable.debt_no);
                    }
                } else {
                    mypager_vip.setImageResource(R.drawable.vip_no);
                    apply_debt_person.setVisibility(View.VISIBLE);
                    mypager_break_debt.setImageResource(R.drawable.debt_no);
                    apply_debt_person.setVisibility(View.VISIBLE);
                }
//

                if (myPager.getRoleType().equals(Constant.ROLE_MAIN_ENTERPRISE)) {//企业主账号
                    ll_my_chile.setVisibility(View.VISIBLE);
                }

                //设置用户头像
                if (!TextUtils.isEmpty(myPager.getImage())) {
                    Glide.with(getActivity()).
                            load(myPager.getImage())
                            .bitmapTransform(new CropCircleTransformation(getActivity()))
                            .placeholder(R.mipmap.my_header)
                            .error(R.mipmap.my_header)
                            .crossFade(1000)
                            .into(iv_userImage);
                }
            }
        } else {
            LUtils.e("--未-登录----");
            ll_no_login.setVisibility(View.VISIBLE);
            ll_yer.setVisibility(View.GONE);
            ll_my_chile.setVisibility(View.GONE);
            tv_userName.setText("");
            mypager_record_num.setText("");
            mypager_vip.setImageResource(R.drawable.vip_no);
            mypager_break_debt.setImageResource(R.drawable.debt_no);
        }
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_NAME)) {
                judgmentNews();
            }
        }
    };

    /**
     * 动态注册广播
     */
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constant.ACTION_NAME);
        //注册广播
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }


}