package com.yl.baiduren.activity.pay_for;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.mypager.PersonalInformation;
import com.yl.baiduren.adapter.AddDebtPersonAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.Reture_Price;
import com.yl.baiduren.entity.request_body.Greate_Order_Body;
import com.yl.baiduren.entity.request_body.My_Record_Entity;
import com.yl.baiduren.entity.result.Create_Order_Result;
import com.yl.baiduren.entity.result.Open_Member_Result;
import com.yl.baiduren.fragment.open_member.Open_Enterprise_Member;
import com.yl.baiduren.fragment.open_member.Open_Preson_Member;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/19.
 * 开通会员
 */

public class Open_Member extends BaseActivity implements View.OnClickListener {
    public static String isOwer = "2";

    private String[] title = {"个人VIP", "企业VIP"};

    // 按钮数组
    private RelativeLayout[] arrBtn = new RelativeLayout[2];
    // 标签文字数组
    private TextView[] arrTxt = new TextView[2];
    // 标签下划线(Indicator)
    private RelativeLayout[] arrLine = new RelativeLayout[2];
    // 滑动页容器
    private ViewPager viewPager;
    // 选中的标签颜色
    int color_selected = R.color.blue;
    private int[] color = {R.color.blue, R.color.orgin};
    // 未选中的标签颜色
    int color_unselected = R.color.light_hei;
    private ImageView open_header;
    private TextView open_name;
    private TextView open_break_timer;
    private ImageView vip;
    private ImageView open_break_preson;
    public static List<Open_Member_Result.PersonVipListBean> personMonthsAndPriceList1 = null;
    public static Open_Member_Result openMemberResults = null;
    private TextView open_amout;
    private Long id1;
    private Integer type1;
    private Integer mouths2;
    public static String personexpand = null;
    public static String mechanexpand = null;
    public static Long toVipTimeAfterPay = null;
    public static Long orderId;

    @Override
    public int loadWindowLayout() {
        return R.layout.open_member;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        ImageView open_memeber_back = findViewById(R.id.open_memeber_back);
        open_memeber_back.setOnClickListener(this);
        ImageView imageView = open_header = findViewById(R.id.open_header);
        open_name = findViewById(R.id.open_type); //用户姓名
        open_break_timer = findViewById(R.id.open_break_timer);//会员截止时期
        vip = findViewById(R.id.vip);//vip图像
        open_break_preson = findViewById(R.id.open_break_preson);//解债人图像
        //价格
        open_amout = findViewById(R.id.open_amout);
        //支付
        Button open_pay = findViewById(R.id.open_pay);
        open_pay.setOnClickListener(this);

        requestWord();

    }

    private void initview() {

        initStyle();
        initView_s();
        // 准备碎片
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new Open_Preson_Member());//债事个人列表
        fragments.add(new Open_Enterprise_Member());//债事企业列表
        // 实例化适配器
        AddDebtPersonAdapter debtFragmentAdapter = new AddDebtPersonAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(debtFragmentAdapter);// 关联适配器
        initListener();
        viewPager.setCurrentItem(0);
        setColor(0);

    }

    private void requestWord() {
        My_Record_Entity entity = new My_Record_Entity(DataWarehouse.getBaseRequest(this));
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getUserToVip(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Open_Member_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Open_Member_Result>(this) {

                    protected void onSuccees(String code, Open_Member_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            Boolean full = data.isIsFull();
                            if (!full) {//判断个人资料是否齐全
                                ShowDilog();
                            }
                            List<Open_Member_Result.PersonVipListBean> personMonthsAndPriceList = data.getPersonVipList();//个人套餐价格
                            List<Open_Member_Result.MechanisSixtListBean> mechanisMonthsAndPriceList = data.getMechanisSixtList();//企业套餐价格
                            personMonthsAndPriceList1 = personMonthsAndPriceList;
                            openMemberResults = data;

                            String personVipExplain = data.getPersonVipExplain();//个人特权说明
                            personexpand = personVipExplain;
                            String mechanisVipExplain = data.getMechanisVipExplain();//企业特权说明
                            mechanexpand = mechanisVipExplain;
                            toVipTimeAfterPay = data.getToVipTimeAfterPay();


                            initview();

                            Glide.with(Open_Member.this)
                                    .load(data.getUserImage())
                                    .bitmapTransform(new CropCircleTransformation(Open_Member.this))
                                    .crossFade(1000).into(open_header);
                            open_name.setText(data.getNickName());

                            if (data.isIsVip()) {
                                LUtils.e(data.isIsVip() + "是否是vip");
                                Glide.with(Open_Member.this).load(R.drawable.vip).asGif().into(vip);
                                if (data.isIsSolveMan()) {
                                    Glide.with(Open_Member.this).load(R.drawable.debt).asGif().into(open_break_preson);

                                }else{
                                    open_break_preson.setImageResource(R.drawable.debt_no);
                                }
                            } else {
                                vip.setImageResource(R.drawable.vip_no);
                                open_break_preson.setImageResource(R.drawable.debt_no);
                            }

                            open_break_timer.setText(Util.LongParseString(data.getVipTime()));



                        }
                    }
                });
    }


    private void ShowDilog() {
        String mesage = "您的资料不全,请补全资料";
        DialogUtils.showDialogZsr(this, false, mesage, new DialogUtils.OnButtonEventListener() {
            @Override
            public void onConfirm() {
                startActivity(new Intent(Open_Member.this, PersonalInformation.class));
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }

    // 改变颜色
    private void initStyle() {
        color_selected = R.color.blue;
    }

    private void initListener() {
        // 添加按钮的监听
        for (RelativeLayout anArrBtn : arrBtn) {
            anArrBtn.setOnClickListener(this);
        }
        // 添加滑动页的监听
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    private void initView_s() {
        // 初始化下划线(逐帧动画)
        String packageName = getApplicationContext().getPackageName();//获取当前包名
        for (int i = 0; i < 2; i++) {
            //从图片名称反射资源ID  R.id.line1
            int id = this.getResources().getIdentifier("line_Wf" + (i + 1), "id", packageName);
            arrLine[i] = findViewById(id);
            int id2 = this.getResources().getIdentifier("btn_Wf" + (i + 1), "id", packageName);
            arrBtn[i] = findViewById(id2);
            int id3 = this.getResources().getIdentifier("txt_Wf" + (i + 1), "id", packageName);
            arrTxt[i] = findViewById(id3);
            // 设置标签名
            arrTxt[i].setText(title[i]);
        }
        // 获取ViewPager对象
        viewPager = findViewById(R.id.open_person_viewpager);
    }

    /**
     * 1.将所有的背景统一颜色
     * 2.将当前选中的背景设置特殊颜色
     *
     * @param index
     */
    public void setColor(int index) {
        // "所有人"都回复最初的状态
        for (int i = 0; i < arrBtn.length; i++) {
            arrLine[i].setBackgroundColor(getResources().getColor(R.color.white));
            arrTxt[i].setTextColor(getResources().getColor(color_unselected));
        }
        arrLine[index].setBackgroundColor(getResources().getColor(color[index]));// 特殊
        arrTxt[index].setTextColor(getResources().getColor(color[index]));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Wf1:
                viewPager.setCurrentItem(0);// 第一页
                break;
            case R.id.btn_Wf2:
                viewPager.setCurrentItem(1);// 第二页
                break;
            case R.id.open_memeber_back:
                finish();
                break;
            case R.id.open_pay:
                if (type1 != null && id1 != null && mouths2 != 0) {
                    create_Order();
                } else {
                    ToastUtil.showShort(this, "请选择套餐");
                }
                break;
        }
    }

    private void create_Order() {//生成订单
        Greate_Order_Body entity = new Greate_Order_Body(DataWarehouse.getBaseRequest(this));
        entity.setVipMonthAndPriceId(id1);
        entity.setType(type1);
        entity.setVipMonths(mouths2);
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
                            UserInfoUtils.setUuid(Open_Member.this, baseResponse);
                            orderId = data.getId();
                            Intent intent = new Intent();//跳转支付界面
                            intent.setClass(Open_Member.this, Pay.class);
                            intent.putExtra("orderId", orderId);
                            startActivity(intent);
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getPrice(Reture_Price price) {
        if (price != null) {
            id1 = price.getVipMonthAndPriceId();
            type1 = price.getType();//type=1企业vip,type=2时个人vip
            mouths2 = price.getMouths();
            open_amout.setText("￥" + price.getPriceStr());
        }

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 页面的选中(当前的页面已经显示了90%)
            setColor(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOwer = null;


    }
}
