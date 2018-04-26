package com.yl.baiduren.activity.debtbunesshall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Debt_One_BasicInformation;
import com.yl.baiduren.adapter.AddDebtPersonAdapter;
import com.yl.baiduren.adapter.Buness_DrawlayoutAdapter;
import com.yl.baiduren.adapter.Debt_Type_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.BunessListener;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.result.Debt_Type_Result;
import com.yl.baiduren.fragment.debt_buness_hall.Debt_Claimed;
import com.yl.baiduren.fragment.debt_buness_hall.Debt_Yet_laimed;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Timer_Select;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CustomDatePicker;
import com.yl.baiduren.view.MyRecyclerView;


import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bakerj.backgrounddarkpopupwindow.BackgroundDarkPopupWindow;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/4.
 * 债事列表
 */

public class Debt_Buness_Hall extends BaseActivity implements View.OnClickListener, BunessListener {
    public static String isOwer = "2";

    private String[] title = {"未摘牌", "已摘牌"};

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
    // 未选中的标签颜色
    int color_unselected = R.color.light_black;
    private RadioGroup group;
    private DrawerLayout drawerLayout;
    private LinearLayout layout;
    private TextView timer_start;
    private TextView timer_end;
    private TextView area;
    private TextView tv_area_select;
    private TextView timer_begin;
    private TextView timer_over;
    private TextView et_yongjin;
    private TextView et_need;
    private TextView et_assert;
    private TextView tv_buness_time;
    private TextView tv_buness_vules;
    private TextView tv_buness_address;
    private TextView tv_buness_saixuan;
    private LinearLayout ll_patent_buness;
    private EditText et_jine;
    private int intentIndex = 0;//页面跳转返回值 从备案跳过来的
    private Debt_Yet_laimed debtYetLaimed;
    private Debt_Claimed debtClaimed;
    private String areaCode = null, clQuCode = null;
    private com.yl.baiduren.view.MyListView lv_buness_drawlayout;
    private Buness_DrawlayoutAdapter bunessDrawlayoutAdapter;//资产/需求适配器
    private Integer assetCategoryId = null, demandCategoryId = null;
    private Double commissionDob = null;
    private String wVCode;//搜索Code
    private Debt_Type_Adapter debt_type_adapter;


    @Override
    public int loadWindowLayout() {
        return R.layout.debt_buness_hall;
    }

    @Override
    public void initViews() {
        wVCode = getIntent().getStringExtra("code");
        String province = getIntent().getStringExtra("province");
        intentIndex = getIntent().getIntExtra("back", 0);
        ll_patent_buness = findViewById(R.id.ll_patent_buness);
        tv_buness_time = findViewById(R.id.tv_buness_time);//时间选择按钮
        tv_buness_time.setOnClickListener(this);
        tv_buness_vules = findViewById(R.id.tv_buness_vules);//类型
        tv_buness_vules.setOnClickListener(this);
        tv_buness_address = findViewById(R.id.tv_buness_address);//地区选择
        tv_buness_address.setOnClickListener(this);
        tv_buness_saixuan = findViewById(R.id.tv_buness_saixuan);//筛选
        tv_buness_saixuan.setOnClickListener(this);
        TextView tv_buness_jinque = findViewById(R.id.tv_buness_jinque);
        tv_buness_jinque.setOnClickListener(this);
        drawerLayout = findViewById(R.id.drawlayout);
        layout = findViewById(R.id.rl_right_layout);
        Button bt_drawlayout_wanc = findViewById(R.id.bt_drawlayout_wanc);
        bt_drawlayout_wanc.setOnClickListener(this);
        timer_begin = findViewById(R.id.tv_timer_start2);//侧拉开始时间选择
        timer_begin.setOnClickListener(this);
        timer_over = findViewById(R.id.tv_timer_end2);//侧拉结束时间选择
        timer_over.setOnClickListener(this);
        tv_area_select = findViewById(R.id.tv_area_select_buness);//侧拉地区选择
        tv_area_select.setOnClickListener(this);
        et_jine = findViewById(R.id.et_jine_drawlayout);//侧拉债事金额选择
        et_yongjin = findViewById(R.id.et_yongjin);//侧拉佣金选择
        et_yongjin.setOnClickListener(this);
        et_need = findViewById(R.id.et_need);//侧拉债权人需求选择
        et_need.setOnClickListener(this);
        et_assert = findViewById(R.id.et_assert);//侧拉债务人资产选择
        et_assert.setOnClickListener(this);
        lv_buness_drawlayout = findViewById(R.id.lv_buness_drawlayout);//资产，需求选择列表
        lv_buness_drawlayout.setVisibility(View.GONE);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(drawerListener);
        ImageView iv_buness_hall_back = findViewById(R.id.iv_buness_hall_back);
        iv_buness_hall_back.setOnClickListener(this);
        Button bt_new_add_hall = findViewById(R.id.bt_new_add_hall);
        bt_new_add_hall.setOnClickListener(this);
        initview();
        bunessDrawlayoutAdapter = new Buness_DrawlayoutAdapter(Debt_Buness_Hall.this);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentArea();
            }
        }, 50);

        debt_type_adapter = new Debt_Type_Adapter(this);
        getTextType();
    }

    /**
     * 获取搜索类型
     */
    public void getTextType() {//请求供应数据类型
        if(UserInfoUtils.IsLogin(this))
        LUtils.e("----获取第三页数据----");
        com.yl.baiduren.data.BaseRequest baseRequest = DataWarehouse.getBaseRequest(this);
        String json = Util.toJson(baseRequest);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getAlltype(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Debt_Type_Result>>bindToLifecycle()))
                .subscribe(new BaseObserver<Debt_Type_Result>(this) {
                    @Override
                    protected void onSuccees(String code, Debt_Type_Result data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            List<Debt_Type_Result.CategoryDO> ass_list = data.getClist();//资产类别
                            List<Debt_Type_Result.TypesDO> tlist = data.getTlist();//债事类别
                            if (ass_list.size() != 0 && tlist.size() != 0) {
                                debt_type_adapter.setList(tlist);
                                initViewData(ass_list);
                            }
                        }
                    }
                });

    }

    private void initview() {

        initStyle();
        initView_s();
        // 准备碎片
        List<Fragment> fragments = new ArrayList<>();
        debtYetLaimed = new Debt_Yet_laimed();
        debtClaimed = new Debt_Claimed();
        fragments.add(debtYetLaimed);//未摘牌
        fragments.add(debtClaimed);//已摘牌
        // 实例化适配器
        AddDebtPersonAdapter debtFragmentAdapter = new AddDebtPersonAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(debtFragmentAdapter);// 关联适配器
        initListener();
        viewPager.setCurrentItem(0);
        setColor(0);
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
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.timer_start:
                //弹框开始时间选择
                getTimer(timer_start);
                break;
            case R.id.timer_end:
                //弹框结束时间选择
                getTimer(timer_end);
                break;
            case R.id.area:
                PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                    @Override
                    public void areaId(String shengId, String shiId, String quId, String string) {
                        //显示地区
                        area.setText(string);
                        areaCode = quId;
                    }
                });
                break;
            case R.id.bt_drawlayout_wanc://侧拉地区完成按钮
                if (TextUtil.isEmpty(timer_begin.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请选择债事发布开始时间");
                    return;
                }
                if (TextUtil.isEmpty(timer_over.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请选择债事发布结束时间");
                    return;
                }
                if (TextUtil.isEmpty(tv_area_select.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请选择所属地区");
                    return;
                }
                if (TextUtil.isEmpty(et_jine.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请输入债事金额");
                    return;
                }
                if (TextUtil.isEmpty(et_yongjin.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请选择所得佣金");
                    return;
                }
                if (TextUtil.isEmpty(et_need.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请选择债权人需求");
                    return;
                }
                if (TextUtil.isEmpty(et_assert.getText().toString().trim())) {
                    ToastUtil.showShort(this, "请选择债务人资产");
                    return;
                }

                if (drawerLayout.isDrawerOpen(layout)) {
                    drawerLayout.closeDrawer(layout);
                    setDarwLayoutData();
                }
                break;
            case R.id.tv_area_select_buness: //侧拉地区选择
                PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
                    @Override
                    public void areaId(String shengId, String shiId, String quId, String string) {
                        //显示地区
                        tv_area_select.setText(string);
                        clQuCode = quId;
                    }
                });
                break;
            case R.id.tv_timer_start2://侧拉开始时间选择
                getTimer(timer_begin);
                break;
            case R.id.tv_timer_end2://侧拉结束时间选择
                getTimer(timer_over);
                break;
            case R.id.et_yongjin:  //侧拉佣金选择
                PopupWindeUtils.yongJinPopupWinde(this, et_yongjin, new PopupWindeUtils.onClivkListViewItem() {
                    @Override
                    public void onClickListViewItemString(String yongjin) {
                        LUtils.e("---佣金---" + yongjin);
                        et_yongjin.setText(yongjin);
                        commissionDob = setBig(yongjin);
                    }
                });
                break;
            case R.id.et_need:
                //侧拉债权人需求选择
                lv_buness_drawlayout.setVisibility(View.VISIBLE);
                bunessDrawlayoutAdapter.setIndex(1);

                break;
            case R.id.et_assert:
                //侧拉债事人资产选择
                lv_buness_drawlayout.setVisibility(View.VISIBLE);
                bunessDrawlayoutAdapter.setIndex(2);
                break;
            case R.id.iv_buness_hall_back://返回
                if (intentIndex == 1) {
                    startActivity(new Intent(this, Debt_Buness_Hall1.class));
                } else {
                    finish();
                }
                break;
            case R.id.tv_buness_time://时间选择按钮
                setTextColor(tv_buness_time);
                showpouple(1);
                break;
            case R.id.tv_buness_vules://类型
                setTextColor(tv_buness_vules);
                showpouple(2);
                break;
            case R.id.tv_buness_address://地区选择按钮
                setTextColor(tv_buness_address);
                showpouple(3);
                break;
            case R.id.tv_buness_saixuan://筛选
                setTextColor(tv_buness_saixuan);
                if (!drawerLayout.isDrawerOpen(layout)) {
                    drawerLayout.openDrawer(layout);
                } else {
                    drawerLayout.closeDrawer(layout);
                }
                break;
            case R.id.tv_buness_jinque://精确查询
                startActivity(new Intent(this, DebtQuerry_BunessHall.class).putExtra("item", viewPager.getCurrentItem()));
                break;
            case R.id.bt_new_add_hall://新增债事
                startActivity(new Intent(this, Debt_One_BasicInformation.class));
                break;
            default:
                break;
        }
    }


    /**
     * 设置字体颜色 背景
     *
     * @param textView
     */
    private void setTextColor(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.blue));
        Drawable drawable = getResources().getDrawable(R.drawable.xia_la_blue);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 清空字体颜色，背景
     */
    private void colserTextColor() {
        Drawable drawable = getResources().getDrawable(R.drawable.xia_la_hei);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_buness_time.setTextColor(getResources().getColor(R.color.light_hei));////时间选择按钮
        tv_buness_time.setCompoundDrawables(null, null, drawable, null);
        tv_buness_vules.setTextColor(getResources().getColor(R.color.light_hei));//金额选择
        tv_buness_vules.setCompoundDrawables(null, null, drawable, null);
        tv_buness_address.setTextColor(getResources().getColor(R.color.light_hei));//地区选择
        tv_buness_address.setCompoundDrawables(null, null, drawable, null);
        tv_buness_saixuan.setTextColor(getResources().getColor(R.color.light_hei));//筛选
        tv_buness_saixuan.setCompoundDrawables(null, null, drawable, null);
    }


    /**
     * 侧拉佣金选择
     *
     * @param yongjin
     * @return
     */
    private Double setBig(String yongjin) {
        Double d = null;
        BigDecimal bigDecimal = null;
        if (yongjin != null) {
            switch (yongjin) {
                case "10%以下":
                    bigDecimal = new BigDecimal("10");
                    break;
                case "20%以上":
                    bigDecimal = new BigDecimal("20");
                    break;
                case "30%以上":
                    bigDecimal = new BigDecimal("30");
                    break;
                case "40%以上":
                    bigDecimal = new BigDecimal("40");
                    break;
                case "50%以上":
                    bigDecimal = new BigDecimal("50");
                    break;
                case "60%以上":
                    bigDecimal = new BigDecimal("60");
                    break;
                case "70%以上":
                    bigDecimal = new BigDecimal("70");
                    break;
                case "80%以上":
                    bigDecimal = new BigDecimal("80");
                    break;
                case "90%以上":
                    bigDecimal = new BigDecimal("90");
                    break;
            }
            d = bigDecimal.divide(new BigDecimal("100")).doubleValue();
        }
        return d;
    }

    /**
     * 设置弹出popup
     *
     * @param type
     */
    private void showpouple(final int type) {
        View view = null;
        if (type == 1) {
            //时间弹窗的选择
            view = LayoutInflater.from(this).inflate(R.layout.timer_select, null);
            timer_start = view.findViewById(R.id.timer_start);//起始时间
            timer_start.setOnClickListener(this);
            timer_end = view.findViewById(R.id.timer_end);//结束时间
            timer_end.setOnClickListener(this);
        }
        if (type == 2) {
            //类型选择
            view = LayoutInflater.from(this).inflate(R.layout.jine_select, null);
            MyRecyclerView typeRecyclerView = view.findViewById(R.id.texttype);
            typeRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            if (debt_type_adapter != null && debt_type_adapter.getList() != null) {
                typeRecyclerView.setAdapter(debt_type_adapter);
            }

        }
        if (type == 3) {
            //地区选择
            view = LayoutInflater.from(this).inflate(R.layout.area_select, null);
            area = view.findViewById(R.id.area);
            area.setOnClickListener(this);
        }

     /*弹出窗口*/
        BackgroundDarkPopupWindow mPopupWindow = new BackgroundDarkPopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
    /*外部可点击*/
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
     /*弹出方式*/
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mPopupWindow.setDarkStyle(-1);
     /*设置阴影的颜色*/
        mPopupWindow.setDarkColor(Color.parseColor("#a0000000"));

        mPopupWindow.resetDarkPosition();
        //阴影在哪个控件之下
        mPopupWindow.darkBelow(ll_patent_buness);
        //显示位置
        mPopupWindow.showAsDropDown(ll_patent_buness);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                colserTextColor();
                if (type == 1) {
                    setContentTime();
                } else if (type == 2) {
                    setType();
                } else if (type == 3) {
                    setContentArea();
                }
            }
        });

    }

    /**
     * 筛选回掉
     */
    private void setDarwLayoutData() {

        //时间
        String stratTime = timer_begin.getText().toString().trim();
        String endTime = timer_over.getText().toString().trim();
        Long createTimeMin = null, createTimeMax = null;
        if (!TextUtils.isEmpty(stratTime) && !TextUtils.isEmpty(endTime)) {
            createTimeMin = Util.strParsedata(stratTime);
            createTimeMax = Util.strParsedata(endTime);
        }
        //金额
        Long jine = null;
        if (!TextUtils.isEmpty(et_jine.getText().toString().trim())) {
            jine = Long.valueOf(et_jine.getText().toString().trim());
        }
        //解债佣金

        if (viewPager.getCurrentItem() == 0) {
            debtYetLaimed.onSaiXuanListener(createTimeMin, createTimeMax, clQuCode, jine, commissionDob, assetCategoryId, demandCategoryId, false);
        } else {
            debtYetLaimed.onSaiXuanListener(createTimeMin, createTimeMax, clQuCode, jine, commissionDob, assetCategoryId, demandCategoryId, true);
        }
    }


    /**
     * 类型回掉
     */
    private void setType() {
        if (debt_type_adapter != null && debt_type_adapter.getList() != null) {
            if (debt_type_adapter.getSelectedItem().size() != 0) {
                if (viewPager.getCurrentItem() == 0) {//未摘牌
                    debtYetLaimed.onTypeListener(debt_type_adapter.getSelectedItem(), false);
                } else {
                    debtClaimed.onTypeListener(debt_type_adapter.getSelectedItem(), true);
                }
            }
        }
    }

    /**
     * 地址回掉
     */
    private void setContentArea() {
        String code = null;
        code = areaCode;

        if (!TextUtils.isEmpty(code)) {
            LUtils.e("--地址---dddd---");
            if (viewPager.getCurrentItem() == 0) {//未摘牌
                debtYetLaimed.onAreaListener(code, false);
            } else {
                debtClaimed.onAreaListener(code, true);
            }
        }
        if (!TextUtils.isEmpty(wVCode)) {//网页传回的code
            if (viewPager.getCurrentItem() == 0) {//未摘牌
                LUtils.e("---111---", "DFDFD");
                debtYetLaimed.onAreaListener(wVCode, false);
            } else {
                LUtils.e("---222---", "DFDFD");
                debtClaimed.onAreaListener(wVCode, true);
            }
        }
    }

    /**
     * 获取选择时间
     */
    private void setContentTime() {
        String start = timer_start.getText().toString().trim();
        String end = timer_end.getText().toString().trim();

        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            Long startL = Util.strParsedata(start);
            Long endL = Util.strParsedata(end);
            if (startL != 0 && endL != 0) {
                //  通过回掉拿到数据
                if (viewPager.getCurrentItem() == 0) {//未摘牌
                    LUtils.e("----为摘牌----");
                    debtYetLaimed.onTimeListener(startL, endL, false);
                } else { //已摘牌
                    LUtils.e("----已摘牌----");
                    debtClaimed.onTimeListener(startL, endL, true);
                }
            }
        }
    }

    /**
     * 筛选滑动监听
     */
    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            // 打开手势滑动
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            colserTextColor();
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            // 关闭手势滑动
            colserTextColor();
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    /**
     * 设置时间
     *
     * @param timer
     */
    private void getTimer(final TextView timer) {
        Timer_Select.getTimer(this, Timer_Select.getTime(timer), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                timer.setText(time.split(" ")[0]);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (intentIndex == 1) {
            startActivity(new Intent(this, Debt_Buness_Hall1.class));
        }
    }

    /**
     * 选择资产，需求Id
     *
     * @param id index==1 需求，2 资产
     */
    @Override
    public void onClickItem(String id, String type, int index) {
        if (index == 1) {
            demandCategoryId = Integer.valueOf(id);
            et_need.setText(type);
        } else {
            assetCategoryId = Integer.valueOf(id);
            et_assert.setText(type);
        }
        LUtils.e("---id---" + id);
        lv_buness_drawlayout.setVisibility(View.GONE);
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
        viewPager.addOnPageChangeListener(onPageChangeListener);
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
        viewPager = findViewById(R.id.debt_hall_viewpager);
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
        arrLine[index].setBackgroundColor(getResources().getColor(color_selected));// 特殊
        arrTxt[index].setTextColor(getResources().getColor(color_selected));

    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 滑动过程中...(写动画)
        }

        @Override
        public void onPageSelected(int position) {
            // 页面的选中(当前的页面已经显示了90%)
            setColor(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // 滑动的状态改变
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOwer = null;
    }


    private void initViewData(List<Debt_Type_Result.CategoryDO> list) {
        bunessDrawlayoutAdapter.setList(list);
        lv_buness_drawlayout.setAdapter(bunessDrawlayoutAdapter);
    }


}
