package com.yl.baiduren.activity.debtbunesshall;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Debt_Type_Adapter;
import com.yl.baiduren.adapter.My_Sussful_Adapter;
import com.yl.baiduren.adapter.Sussful_DrawlayoutAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.BunessListener;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.MyDeliing_Body;
import com.yl.baiduren.entity.request_body.Sussful_Exmple_Body;
import com.yl.baiduren.entity.result.Debt_Type_Result;
import com.yl.baiduren.entity.result.My_Record_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Timer_Select;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CustomDatePicker;
import com.yl.baiduren.view.MyListView;
import com.yl.baiduren.view.MyRecyclerView;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bakerj.backgrounddarkpopupwindow.BackgroundDarkPopupWindow;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/18.
 * 成功案例
 */

public class Sussful_Exmple extends BaseActivity implements View.OnClickListener, BunessListener {
    private RadioGroup group;
    private ArrayList<String> list = null;
    private DrawerLayout drawerLayout;
    private RelativeLayout layout;
    private TextView timer_select;
    private TextView tv_buness_vules;
    private TextView area_select;
    private TextView shuai_select;
    private TextView timer_start;
    private TextView timer_end;
    private TextView area;
    private TextView tv_area_select;
    private TextView timer_begin;
    private TextView timer_over;
    private EditText et_jine;
    private TextView et_yongjin;
    private TextView et_need;
    private TextView et_assert;
    private RecyclerView recyclerView;
    private MaterialRefreshLayout refresh;
    public static int pn = 1;
    public static int ps = 8;
    private Integer assetCategoryId = null, demandCategoryId = null;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 0;
    private My_Sussful_Adapter adapter;
    private List<My_Record_Result.DataListBean> dataList;
    private Handler handler = new Handler();
    private EditText jine_start;
    private EditText jine_end;
    private String area_code;
    private MyListView lv_buness_drawlayout;
    private Sussful_DrawlayoutAdapter bunessDrawlayoutAdapter;
    private BigDecimal bigDecimal2;
    private LinearLayout line_group;
    private Debt_Type_Adapter debt_type_adapter;


    @Override
    public int loadWindowLayout() {
        return R.layout.sussful_exmple;
    }

    @Override
    public void initViews() {
        refresh = findViewById(R.id.sufful_refresh);
        refresh.setMaterialRefreshListener(refreshListener);
        timer_select = findViewById(R.id.timer_select);//时间选择按钮
        timer_select.setOnClickListener(this);
        tv_buness_vules = findViewById(R.id.tv_buness_vules);//类型
        tv_buness_vules.setOnClickListener(this);
        area_select = findViewById(R.id.area_select);//地区选择
        area_select.setOnClickListener(this);
        shuai_select = findViewById(R.id.shuai_select);//筛选
        shuai_select.setOnClickListener(this);
        TextView qing_que = findViewById(R.id.qing_que);
        qing_que.setOnClickListener(this);
        line_group = findViewById(R.id.line_group);//筛选容器
        drawerLayout = findViewById(R.id.drawlayout);
        layout = findViewById(R.id.right_layout);
        tv_area_select = findViewById(R.id.tv_area_select);//侧拉地区选择
        tv_area_select.setOnClickListener(this);
        timer_begin = findViewById(R.id.timer_start2);//侧拉开始时间选择
        timer_begin.setOnClickListener(this);
        timer_over = findViewById(R.id.timer_end2);//侧拉结束时间选择
        timer_over.setOnClickListener(this);
        Button sussful_chongzhi = findViewById(R.id.sussful_chongzhi);
        sussful_chongzhi.setOnClickListener(this);
        TextView chanye_select = findViewById(R.id.chanye_select);
        chanye_select.setOnClickListener(this);
        et_jine = findViewById(R.id.et_jine);//侧拉债事金

        et_yongjin = findViewById(R.id.et_yongjin);//侧拉佣金选择
        et_yongjin.setOnClickListener(this);
        et_need = findViewById(R.id.et_need);//侧拉债权人需求选择
        et_need.setOnClickListener(this);
        et_assert = findViewById(R.id.et_assert);//侧拉债务人资产选择
        et_assert.setOnClickListener(this);
        Button cl_query = findViewById(R.id.cl_query);
        cl_query.setOnClickListener(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(drawerListener);
        //资产，需求选择列表
        lv_buness_drawlayout = findViewById(R.id.lv_buness_drawlayout2);
        lv_buness_drawlayout.setVisibility(View.GONE);
        ImageView iv_buness_hall_back = findViewById(R.id.iv_buness_hall_back);
        iv_buness_hall_back.setOnClickListener(this);
        recyclerView = findViewById(R.id.my_exmple);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new My_Sussful_Adapter(Sussful_Exmple.this);
        bunessDrawlayoutAdapter = new Sussful_DrawlayoutAdapter(Sussful_Exmple.this);
        status=NORMAL;
        debt_type_adapter = new Debt_Type_Adapter(this);
        getTextType();
        requestWork();
    }





    private void requestWork() {
        MyDeliing_Body entity = new MyDeliing_Body(DataWarehouse.getBaseRequest(this));
        if(UserInfoUtils.IsLogin(this)){
        entity.setPageNo(pn);
        entity.setPageSize(ps);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<My_Record_Result> observer = new BaseObserver<My_Record_Result>(this) {
            @Override
            protected void onSuccees(String code, My_Record_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {

                    UserInfoUtils.setUuid(Sussful_Exmple.this,baseResponse);

                    if (data.getDataList().size() != 0) {
                        displayData(data);
                        hideImageView();
                    } else {
                        if (adapter.getList() != null && adapter.getList().size() != 0) {
                            hideImageView();
                        }else {
                            showImageView();
                        }
                    }
                    refresh.finishRefresh();
                    refresh.finishRefreshLoadMore();

                }else {
                    refresh.finishRefresh();
                    refresh.finishRefreshLoadMore();
                }
            }
        };
        observer.setStop(false);
        RetrofitHelper.getInstance(this).getServer()
                .debt_Sussful(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<My_Record_Result>>bindToLifecycle()))
                .subscribe(observer);

        }else {
            finish();
        }
    }

    private void requestWorkQurey(Sussful_Exmple_Body entity) {//条件搜索
        entity.setPageNo(pn);
        entity.setPageSize(100);

        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<My_Record_Result> baseObserver = new BaseObserver<My_Record_Result>(this) {
            @Override
            protected void onSuccees(String code, My_Record_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    if (dataList!=null){
                        dataList.clear();
                        adapter.notifyDataSetChanged();
                    }
                    UserInfoUtils.setUuid(Sussful_Exmple.this, baseResponse);
                    if (data.getDataList().size() != 0) {
                        displayData(data);
                        hideImageView();
                    } else {
                        if (adapter.getList() != null && adapter.getList().size() != 0) {
                            hideImageView();
                        }else {
                            showImageView();
                        }
                    }
                    refresh.finishRefresh();
                    refresh.finishRefreshLoadMore();

                }
            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .debt_Sussful(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<My_Record_Result>>bindToLifecycle()))
                .subscribe(baseObserver);
    }

    //抽取的展示数据的方法
    private void displayData(final My_Record_Result data) {
        if (status==NORMAL){
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            recyclerView.setAdapter(adapter);
        }

        if (status == REFRESH) {
           if (dataList!=null){
               dataList.clear();
           }
            dataList = data.getDataList();
            adapter.setDataList(dataList);
            recyclerView.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = dataList.size();
            List<My_Record_Result.DataListBean> dataList2 = data.getDataList();
            dataList.addAll(dataList2);
            adapter.setDataList(this.dataList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timer_start:
                //时间开始
                getTimer(timer_start);
                break;
            case R.id.timer_end:
                //时间结束
                getTimer(timer_end);
                break;
            case R.id.area:
                //弹框地区选择
                getArea(area);

                break;
            case R.id.tv_area_select:
                //侧拉地区选择
                getArea(tv_area_select);
                break;
            case R.id.timer_start2://侧拉开始时间

                getTimer(timer_begin);
                break;
            case R.id.timer_end2://侧拉结束时间

                getTimer(timer_over);
                break;
            case R.id.chanye_select:
                //侧拉产业选择
                break;
            case R.id.sussful_chongzhi://侧拉重置
                setEmptey();
                break;
            case R.id.cl_query://侧拉完成按钮
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
                colserTextColor();
                setContent_Drawlay();
                drawerLayout.closeDrawer(layout);

                break;
            case R.id.et_yongjin:
                //侧拉佣金选择

                PopupWindeUtils.yongJinPopupWinde(this, et_yongjin, new PopupWindeUtils.onClivkListViewItem() {
                    @Override
                    public void onClickListViewItemString(String yongjin) {

                        et_yongjin.setText(yongjin);
                        bigDecimal2 = setBig(yongjin);
                        LUtils.e("---佣金---" + bigDecimal2);
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
                finish();

                break;
            case R.id.timer_select://时间选择
                setTextColor(timer_select);
                View view = LayoutInflater.from(this).inflate(R.layout.timer_select, null);
                showpouple(view, 1);
                break;
            case R.id.area_select://地区选择
                setTextColor(area_select);
                View view1 = LayoutInflater.from(this).inflate(R.layout.area_select, null);
                showpouple(view1, 2);
                break;
            case R.id.tv_buness_vules://金额选择
                setTextColor(tv_buness_vules);
                View view2 = LayoutInflater.from(this).inflate(R.layout.jine_select, null);
                showpouple(view2, 3);
                break;
            case R.id.shuai_select://筛选
                if (!drawerLayout.isDrawerOpen(layout)) {
                    drawerLayout.openDrawer(layout);
                }
                break;
            case R.id.qing_que://精确
                startActivity(new Intent(this, Sussful_Exple_Query_Serch.class));
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
        timer_select.setTextColor(getResources().getColor(R.color.light_hei));////时间选择按钮
        timer_select.setCompoundDrawables(null, null, drawable, null);
        tv_buness_vules.setTextColor(getResources().getColor(R.color.light_hei));//金额选择
        tv_buness_vules.setCompoundDrawables(null, null, drawable, null);
        area_select.setTextColor(getResources().getColor(R.color.light_hei));//地区选择
        area_select.setCompoundDrawables(null, null, drawable, null);
        shuai_select.setTextColor(getResources().getColor(R.color.light_hei));//筛选
        shuai_select.setCompoundDrawables(null, null, drawable, null);
    }

    private void setEmptey() {
        tv_area_select.setText("");
        timer_begin.setText("");
        timer_over.setText("");
        et_jine.setText("");
        et_yongjin.setText("");
        et_need.setText("");
        et_assert.setText("");
    }

    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            pn = 1;
            status = REFRESH;
            requestWork();


        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            pn = pn + 1;
            status = LOADMORE;
            requestWork();

        }
    };

    private void showpouple(final View view, final int type) {

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
        mPopupWindow.darkBelow(line_group);
        //显示位置
        mPopupWindow.showAsDropDown(line_group);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                colserTextColor();
                if (type == 1) {//时间选择
                    setContent_Time();
                } else if (type == 2) {//地区选择
                    setContent_Area();
                } else if (type == 3) {//类型
                    setType();
                }

            }
        });
        if (type == 1) {
            //时间弹窗的选择
            timer_start = view.findViewById(R.id.timer_start);//起始时间
            timer_start.setOnClickListener(this);
            timer_end = view.findViewById(R.id.timer_end);//结束时间
            timer_end.setOnClickListener(this);
        }
        if (type == 2) {
            //地区选择
            area = view.findViewById(R.id.area);
            area.setOnClickListener(this);

        }
        if (type == 3) {//类型
            MyRecyclerView typeRecyclerView = view.findViewById(R.id.texttype);
            typeRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
            if (debt_type_adapter != null && debt_type_adapter.getList() != null) {
                typeRecyclerView.setAdapter(debt_type_adapter);
            }
        }


    }

    private void getTimer(final TextView timer) {
        Timer_Select.getTimer(this, Timer_Select.getTime(timer), new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                timer.setText(time.split(" ")[0]);
            }
        });
    }


    private void getArea(final TextView textView) {
        PopupWindeUtils.showPopupWinde(this, new PopupWindeUtils.OnAreaDataIdListener() {
            @Override
            public void areaId(String shengId, String shiId, String quId, String string) {
                //显示地区
                textView.setText(string);
                area_code = quId;
            }
        });
    }


    private void setContent_Time() {//时间筛选
        String start = timer_start.getText().toString().trim();
        String end = timer_end.getText().toString().trim();

        if (!TextUtils.isEmpty(start) && !TextUtils.isEmpty(end)) {
            Long startL = Util.strParsedata(start);
            Long endL = Util.strParsedata(end);
            Sussful_Exmple_Body entity = new Sussful_Exmple_Body(DataWarehouse.getBaseRequest(this));
            entity.setMinCreateTime(startL);
            entity.setMaxCreateTime(endL);
            status=NORMAL;
            requestWorkQurey(entity);

        }
    }

    /**
     * 类型回掉
     */
    private void setType() {
        if (debt_type_adapter != null && debt_type_adapter.getList() != null) {
            Sussful_Exmple_Body amout = new Sussful_Exmple_Body(DataWarehouse.getBaseRequest(this));
            if(debt_type_adapter.getSelectedItem().size()!=0){
                amout.settIds(debt_type_adapter.getSelectedItem());
                status=NORMAL;
                requestWorkQurey(amout);
            }
        }
    }


    private void setContent_Area() {
        if (area_code != null) {
            Sussful_Exmple_Body area = new Sussful_Exmple_Body(DataWarehouse.getBaseRequest(this));
            area.setAreaCode(area_code);
            status=NORMAL;
            requestWorkQurey(area);

        }
    }

    private void setContent_Drawlay() {
        String cl_timerst = timer_begin.getText().toString().trim();//开始时间
        String cl_tiend = timer_over.getText().toString().trim();//结束时间
        String amout = et_jine.getText().toString();
        if (!TextUtils.isEmpty(cl_timerst) && !TextUtils.isEmpty(cl_tiend) && !TextUtils.isEmpty(amout)) {
            Long aLong = Long.valueOf(amout);
            Sussful_Exmple_Body drawlay = new Sussful_Exmple_Body(DataWarehouse.getBaseRequest(this));
            drawlay.setMinRecordTime(Util.strParsedata(cl_timerst));
            drawlay.setMaxRecordTime(Util.strParsedata(cl_tiend));
            drawlay.setAreaCode(area_code);
            drawlay.setAmount(aLong);
            drawlay.setCommission(bigDecimal2);
            drawlay.setDemandCategoryId(demandCategoryId);
            drawlay.setAssetCategoryId(assetCategoryId);
            status=NORMAL;
            requestWorkQurey(drawlay);

        }


    }

    private BigDecimal setBig(String yongjin) {

        BigDecimal bigDecimal = null;
        Double d = null;
        if (yongjin != null) {
            switch (yongjin) {
                case "10%以下":
                    bigDecimal = new BigDecimal("0.1");
                    break;
                case "20%以上":
                    bigDecimal = new BigDecimal("0.2");
                    break;
                case "30%以上":
                    bigDecimal = new BigDecimal("0.3");
                    break;
                case "40%以上":
                    bigDecimal = new BigDecimal("0.4");
                    break;
                case "50%以上":
                    bigDecimal = new BigDecimal("0.5");
                    break;
                case "60%以上":
                    bigDecimal = new BigDecimal("0.6");
                    break;
                case "70%以上":
                    bigDecimal = new BigDecimal("0.7");
                    break;
                case "80%以上":
                    bigDecimal = new BigDecimal("0.8");
                    break;
                case "90%以上":
                    bigDecimal = new BigDecimal("0.9");
                    break;
            }
        }

        return bigDecimal;
    }

    private DrawerLayout.DrawerListener drawerListener = new DrawerLayout.DrawerListener() {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(View drawerView) {
            // 打开手势滑动
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            // 关闭手势滑动
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };


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

    /**
     * 获取搜索类型
     */
    public void getTextType() {//请求供应数据类型
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        LUtils.e("----获取第三页数据----");
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setPlatform(2);
        baseRequest.setUuid(baseRequests.get(0).getUuid());
        baseRequest.setUid(baseRequests.get(0).getUid());
        baseRequest.setLoginUsername(baseRequests.get(0).getLoginUsername());
        baseRequest.setAccessToken(baseRequests.get(0).getAccessToken());

        String json = Util.toJson(baseRequest);//转成json


        BaseObserver<Debt_Type_Result> baseObserver=new BaseObserver<Debt_Type_Result>(this) {
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
        };
        baseObserver.setStop(true);

        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getAlltype(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Debt_Type_Result>>bindToLifecycle()))
                .subscribe(baseObserver);

    }

    private void initViewData(List<Debt_Type_Result.CategoryDO> list) {
        bunessDrawlayoutAdapter.setList(list);
        lv_buness_drawlayout.setAdapter(bunessDrawlayoutAdapter);
    }

}
