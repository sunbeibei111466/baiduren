package com.yl.baiduren.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yl.baiduren.MainActivity;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.CooperationWin;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.NewsDetails;

import com.yl.baiduren.activity.credit_reporting_queries.ZhengXing_Query;
import com.yl.baiduren.activity.debtbunesshall.Debt_Buness_Hall1;
import com.yl.baiduren.activity.debtrecord.Exchange_Square_Activiyt;
import com.yl.baiduren.activity.mypager.My_Message;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.ZXingEventBus;
import com.yl.baiduren.entity.result.HomeDO;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.SharedUtil;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.feezu.liuli.timeselector.Utils.TextUtil;
import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import zhy.com.highlight.HighLight;
import zhy.com.highlight.interfaces.HighLightInterface;
import zhy.com.highlight.position.OnBottomPosCallback;
import zhy.com.highlight.shape.CircleLightShape;
import zhy.com.highlight.shape.RectLightShape;


public class OneFragment extends BaseFragment implements View.OnClickListener, OnBannerListener {


    private Banner banner;
    private ImageView iv_title_left;
    private LinearLayout debt_ting;
    private LinearLayout jiao_yi;
    private LinearLayout zheng_query;
    private LinearLayout he_zuo;
    private LineChart mLineChart;
    private PieChart mPieChart;
    private View view_top;
    public static HighLight highLight;
    public List<HomeDO.BannerDOsBean> bannerDOList;//banner 数据源
    private ArrayList<String> list_path;//放轮播图图片地址的集合
    private ArrayList<String> list_title;//放标题的集合
    private ImageView iv_title_right;
    private TextView tv_debt_preson_num, tv_debtNum;
    private TextView textViewYi;
    private TextView textViewWan;
    private TextView yiText;
    private TextView wanText;
    private TextView tv_geren;
    private TextView tv_qiye;

    @Override
    public int loadWindowLayout() {
        return R.layout.fragment_one;
    }


    @Override
    public void initViews(View rootView) {
        registerBoradcastReceiver();
        LinearLayout tv_debt_num = rootView.findViewById(R.id.tv_debt_num);
        textViewYi = new TextView(getActivity());
        yiText = new TextView(getActivity());
        textViewWan = new TextView(getActivity());
        wanText = new TextView(getActivity());
        if (textViewYi != null) {
            tv_debt_num.addView(textViewYi);
        }
        if (yiText != null) {
            tv_debt_num.addView(yiText);
        }
        if (textViewWan != null) {
            tv_debt_num.addView(textViewWan);
        }
        if (wanText != null) {
            tv_debt_num.addView(wanText);
        }


        LinearLayout ll_gen_one = rootView.findViewById(R.id.ll_gen_one);
        view_top = rootView.findViewById(R.id.view_top);
        TextView title_name = rootView.findViewById(R.id.title_name);//标题
        title_name.setText("首页");


        //二维码
        iv_title_left = rootView.findViewById(R.id.iv_title_left);
        iv_title_left.setImageResource(R.mipmap.scan);
        iv_title_left.setOnClickListener(this);

        iv_title_right = rootView.findViewById(R.id.iv_title_right);
        iv_title_right.setOnClickListener(this);

        //债事大厅
        debt_ting = rootView.findViewById(R.id.debt_bus_holl);
        debt_ting.setOnClickListener(this);
        //交易广场
        jiao_yi = rootView.findViewById(R.id.jiao_chang);
        jiao_yi.setOnClickListener(this);
        //征信查询
        zheng_query = rootView.findViewById(R.id.query_zh_xing);
        zheng_query.setOnClickListener(this);
        //合作共赢
        he_zuo = rootView.findViewById(R.id.win_cooperation);
        he_zuo.setOnClickListener(this);

        tv_debt_preson_num = rootView.findViewById(R.id.tv_debt_preson_num);//债事总人数
        tv_debtNum = rootView.findViewById(R.id.tv_debtNum);////债事总数
        mLineChart = rootView.findViewById(R.id.chart);//线状图
        mPieChart = rootView.findViewById(R.id.mPieChart);//饼状图

        tv_geren = rootView.findViewById(R.id.tv_geren);
        tv_qiye = rootView.findViewById(R.id.tv_qiye);

        initBanner(rootView);

        //显示新手指引
        if (Util.isFirstApp(getActivity(), 1)) {
            LUtils.e("--1111---TRUE---");
            initGuide();
        }
    }

    private void initGuide() {
        //默认为android.R.id.content
        highLight = new HighLight(getActivity());
        highLight.anchor(getActivity().getWindow().getDecorView())//如果是在Activity上增加引导层，不需要设置此方法
                .autoRemove(false)//设置背景点击高亮布局自动移除为false 默认为true
                .intercept(true)//拦截属性默认为true 使下方ClickCallback生效
                .enableNext()//开启next模式并通过show方法显示 然后通过调用next()方法切换到下一个提示布局，直到移除自身
                .setOnLayoutCallback(new HighLightInterface.OnLayoutCallback() {

                    @Override
                    public void onLayouted() {
                        highLight.addHighLight(view_top, R.layout.guide1, new OnBottomPosCallback(0), null);
                        highLight.addHighLight(debt_ting, R.layout.guide2, onPosCallback, new CircleLightShape(22, 22));
                        highLight.addHighLight(jiao_yi, R.layout.guide3, onPosCallback, new CircleLightShape(22, 22));
                        highLight.addHighLight(zheng_query, R.layout.guide4, onPosCallback, new CircleLightShape(22, 22));
                        highLight.addHighLight(he_zuo, R.layout.guide5, onPosCallback, new CircleLightShape(22, 22));
                        highLight.addHighLight(MainActivity.ll_parent_gx, R.layout.guide6, new HighLight.OnPosCallback() {
                            @Override
                            public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
                                marginInfo.bottomMargin = rectF.width();
                            }
                        }, new RectLightShape());
                        //然后显示高亮布局
                        highLight.show();
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();
        getHttpHomeData();
        judgmentNews();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Util.getInstance().setCJ(getActivity());
            getHttpHomeData();
            judgmentNews();
        }
    }


    /**
     * 位置回掉
     */
    private HighLight.OnPosCallback onPosCallback = new HighLight.OnPosCallback() {
        @Override
        public void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLight.MarginInfo marginInfo) {
            marginInfo.leftMargin = rectF.width() - (rectF.width() - 1);
        }
    };

    public void setResult(String res) {
        String result = res;
        if (result != null) {
            ToastUtil.showShort(getContext(), result);
        }
    }

    private void initBanner(View rootView) {
        bannerDOList = new ArrayList<>();
        list_path = new ArrayList<>();
        list_title = new ArrayList<>();
        //轮播图
        banner = rootView.findViewById(R.id.banner);

    }

    @Override
    public void onClick(View v) {
        if (v == iv_title_left) {
            if (UserInfoUtils.IsLogin(getActivity())) {
                EventBus.getDefault().post(new ZXingEventBus(true));//传递点击事件
            } else {
                ToastUtil.showShort(getActivity(), "请先登陆");
            }
        } else if (v == debt_ting) {
            //跳转到债事大厅
            startActivity(new Intent(getActivity(), Debt_Buness_Hall1.class));
        } else if (v == jiao_yi) {//交易大厅
            startActivity(new Intent(getActivity(), Exchange_Square_Activiyt.class));
        } else if (v == zheng_query) {//征信查xun
            if (UserInfoUtils.IsLogin(getActivity())) {
//                startActivity(new Intent(getActivity(), CreditTransition.class));
                startActivity(new Intent(getActivity(), ZhengXing_Query.class));
            } else {
                startActivity(new Intent(getActivity(), Login_Activity_Password.class));
            }

        } else if (v == he_zuo) {//合作共赢
            startActivity(new Intent(getActivity(), CooperationWin.class));
        } else if (v == iv_title_right) {//消息
            startActivity(new Intent(getActivity(), My_Message.class));
        }
    }


    //折线图
    private void lineImage(LineChart chart, HomeDO dataBean) {

        if (dataBean.getDates().size() != 0) {

            List<String> stringList = dataBean.getDates();
            LUtils.e("------大小--------" + stringList.size());
            List<Integer> list = dataBean.getNums();//y轴数据
            int minVul = 0;
            int maxVul = list.get(list.size() - 1);


            List<Entry> entries2 = new ArrayList<>();
            for (int i = 0; i < dataBean.getDrNums().size(); i++) {
                // i:x值，m：y值
                entries2.add(new Entry(i, dataBean.getDrNums().get(i)));
            }

            List<Entry> entries3 = new ArrayList<>();
            for (int i = 0; i < dataBean.getDebtNums().size(); i++) {
                // i:x值，m：y值
                entries3.add(new Entry(i, dataBean.getDebtNums().get(i)));
            }
            chart.setDrawBorders(false);//是否在折线图上添加边框   是否设置边框，默认false 
            Description description = new Description();
            description.setText(dataBean.getYearAndMonth());
            description.setTextAlign(Paint.Align.RIGHT);
            description.setTextSize(18);
            description.setTextColor(R.color.blue);
            chart.setDescription(description);// 数据描述
            chart.setNoDataText("当前没有数据");  // 没有数据时显示的文字
            chart.setNoDataTextColor(Color.GREEN); // 没有数据时显示的文字颜色
            chart.setBackgroundColor(Color.WHITE);// 设置图表背景色
            chart.setExtraOffsets(0, 0, 0, 10);//设置图表据外边距

            //折线图例 标签 设置
            Legend mLegend = chart.getLegend();
            mLegend.setEnabled(true);
            mLegend.setFormSize(10);                    //设置图例的大小
            mLegend.setFormToTextSpace(10f);            //设置每个图例实体中标签和形状之间的间距
            mLegend.setDrawInside(false);
            mLegend.setWordWrapEnabled(true);


            //线条设置  LineDataSet
            LineDataSet lineDataSet2 = new LineDataSet(entries2, "新增债事");
            lineDataSet2.setColor(getResources().getColor(R.color.blue)); // 设置线条颜色
            lineDataSet2.setLineWidth(1f);// 设置线条宽度
            lineDataSet2.setCircleColor(Color.GRAY);//设置圆的颜色
            lineDataSet2.setCircleRadius(2f);// 设置节点圆的半径
            lineDataSet2.setDrawCircleHole(true);// 是否绘制空心圆(默认true)  flase 实心
            lineDataSet2.setDrawFilled(true);//设置线下填充
            lineDataSet2.setFillColor(getResources().getColor(R.color.blue));//设置填充颜色
            lineDataSet2.setHighlightEnabled(false);//不显示定位线
            lineDataSet2.setValueTextSize(10);
            lineDataSet2.setValueTextColor(getResources().getColor(R.color.blue));

            //线条设置  LineDataSet
            LineDataSet lineDataSet1 = new LineDataSet(entries3, "新增债事人");
            lineDataSet1.setColor(getResources().getColor(R.color.orgin)); // 设置线条颜色
            lineDataSet1.setLineWidth(1f);// 设置线条宽度
            lineDataSet1.setCircleColor(Color.GRAY);//设置圆的颜色
            lineDataSet1.setCircleRadius(2f);// 设置节点圆的半径
            lineDataSet1.setDrawCircleHole(true);// 是否绘制空心圆(默认true)  flase 实心
            lineDataSet1.setDrawFilled(true);//设置线下填充
            lineDataSet1.setFillColor(getResources().getColor(R.color.orgin));//设置填充颜色
            lineDataSet1.setHighlightEnabled(false);//不显示定位线
            lineDataSet1.setValueTextSize(10);
            lineDataSet1.setValueTextColor(getResources().getColor(R.color.orgin));


            //线条  设置数据
            LineData data = new LineData(lineDataSet1, lineDataSet2);
            chart.setData(data);//设置数据
            chart.notifyDataSetChanged();

            LUtils.e("----大小2-----" + stringList.size());
            //x轴
            XAxis xAxis = chart.getXAxis();// 获取图表的X轴
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置 底部
            xAxis.setTextColor(Color.BLACK);// 设置x坐标轴字体颜色
            xAxis.setTextSize(14);// 设置x坐标轴字体大小
            xAxis.setDrawGridLines(false); // 否是绘制网格线(X轴代表的为竖线)，默认true
            xAxis.setLabelCount(stringList.size(), true);//设置X轴的刻度数量
//        xAxis.setValueFormatter(new IAxisValueFormatter() { //x轴设置字体
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                LUtils.e("----大小3-----" + value);
//                return stringList.get((int) value); //mList为存有月份的集合
//            }
//        });
            xAxis.setValueFormatter(new IndexAxisValueFormatter(stringList));

            //y轴
            YAxis leftYAxis = chart.getAxisLeft();//y轴左半轴
            YAxis rightYAxis = chart.getAxisRight();//y轴右半轴
            leftYAxis.setAxisMinimum(minVul);// Y左半轴最小值
            leftYAxis.setAxisMaximum(maxVul);//y左半轴最大值
            leftYAxis.setDrawGridLines(false);// 否是绘制网格线(Y轴代表的为横线)，默认true
            leftYAxis.setAxisLineColor(Color.BLACK); // Y坐标轴颜色
            leftYAxis.setTextColor(Color.BLACK);//设置y坐标轴字体颜色
            rightYAxis.setEnabled(false); //右侧Y轴不显示  不绘制右边Y值

        }
        {
            LUtils.e("-----x轴为空-----");
        }
    }

    //饼状图
    private void pieImage(PieChart mPieChart, HomeDO data) {
        mPieChart.setUsePercentValues(true);//设置显示成比例
        mPieChart.setNoDataText("当前没有数据");  // 没有数据时显示的文字
        mPieChart.getDescription().setEnabled(false);//设置pieChart图表的描述
        mPieChart.setExtraOffsets(0, 0, 20, 4);//圆环距离屏幕上下上下左右的距离
        mPieChart.setEntryLabelColor(Color.BLACK); //图表文本字体颜色
//        mPieChart.setDragDecelerationFrictionCoef(0.95f);//设置pieChart图表转动阻力摩擦系数[0,1]

        mPieChart.setDrawHoleEnabled(false);//显示类型 空心 还是实心
//        //设置圆环透明度及半径
//        mPieChart.setTransparentCircleColor(Color.WHITE);//设置PieChart内部透明圆与内部圆间距(31f-28f)填充颜色
//        mPieChart.setTransparentCircleAlpha(32);//设置PieChart内部透明圆与内部圆间距(31f-28f)透明度
//        mPieChart.setTransparentCircleRadius(50f);//设置半透明圆环的半径,看着就有一种立体的感觉
//        mPieChart.setHoleRadius(38f); //设置PieChart内部圆的半径(这里设置28.0f)
        mPieChart.setRotationAngle(20);// 初始旋转角度
        mPieChart.setRotationEnabled(true);  // 触摸旋转 可以手动旋转
        mPieChart.animateY(1000, Easing.EasingOption.EaseInOutQuad); //设置动画

        // 获取pieCahrt图列
        Legend l = mPieChart.getLegend();
        l.setEnabled(true);////是否启用图列（true：下面属性才有意义）
        l.setFormSize(10);                    //设置图例的大小
        l.setFormToTextSpace(2f);            //设置每个图例实体中标签和形状之间的间距
        l.setDrawInside(false);
        l.setWordWrapEnabled(true);           //设置图列换行(注意使用影响性能,仅适用legend位于图表下面)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        HomeDO.HomeRecode homeRecode = data.getHomeRecord();

        tv_geren.setText(homeRecode.getEnterpriseNum() + "");
        tv_qiye.setText(homeRecode.getDebtNum() + "");

        //模拟数据   PieEntry 饼状图实体  new PicEntiy(占比，内容)
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int j = 0; j < homeRecode.getList().size(); j++) {
            entries.add(new PieEntry(Float.valueOf(homeRecode.getList().get(j).getPercent()), homeRecode.getList().get(j).getName()));
        }

        //设置数据
        setData(entries);
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        //设置数据源 以及饼图标题
        PieDataSet dataSet = new PieDataSet(entries, "征信体系");
        dataSet.setSliceSpace(3f);//设置每一块之间的间隔
        dataSet.setSelectionShift(5f);

        //饼状图颜色
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);//设置饼状图颜色
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //设置项X值拿出去 设置指示线
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1Length(0.3f);//设置指示线长度

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setDrawValues(false);//设置Y值不显示
        mPieChart.setData(data);//给PieChart填充数据
        mPieChart.invalidate();
    }


    public void getHttpHomeData() {

        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setPlatform(2);
        String json = Util.toJson(baseRequest);//转成json
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---json---" + json);
        BaseObserver<HomeDO> baseObserver = new BaseObserver<HomeDO>(getActivity()) {
            @Override
            protected void onSuccees(String code, HomeDO data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {//成功
                    initServiceData(data);
                    initLine(data);
                }
            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(getActivity()).getServer().
                getHome(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<HomeDO>>bindToLifecycle()))
                .subscribe(baseObserver);
    }

    private void initLine(HomeDO data) {

        textViewYi.setTextColor(getResources().getColor(R.color.number));
        yiText.setText("亿");
        textViewWan.setTextColor(getResources().getColor(R.color.number));
        wanText.setText("万");
        if (!TextUtil.isEmpty(data.getDrAmountTotalStr())) {
            Long aLong = Long.valueOf(data.getDrAmountTotalStr());
            if (aLong > 100000000) {
                Long aLong1 = aLong / 100000000;
                String yi = new DecimalFormat("#").format(aLong1);
                textViewYi.setText(yi);
                Long wan = aLong - (Long.parseLong(yi) * 100000000);
                if (wan > 10000) {
                    Long wanYuan = wan / 10000;
                    textViewWan.setText(wanYuan + "");
                }
            } else if (aLong > 10000) {
                textViewYi.setText("0");
                Long aLong1 = aLong / 10000;
                textViewWan.setText(aLong1 + "");
            }
        }


        tv_debt_preson_num.setText(data.getDebtTotal() + "");//债事人总数
        tv_debtNum.setText(data.getDrTotal() + "");//债事总数
        lineImage(mLineChart, data);
        pieImage(mPieChart, data);
    }

    private void initServiceData(HomeDO data) {
        if (bannerDOList != null) {
            bannerDOList.clear();
            LUtils.e("------清空------");
            list_path.clear();
            list_title.clear();
        }
        bannerDOList.addAll(data.getBannerDOs());
        for (int i = 0; i < data.getBannerDOs().size(); i++) {
            //放轮播图图片地址的集合
            list_path.add(bannerDOList.get(i).getImgUrl());
            list_title.add(bannerDOList.get(i).getName());
        }
        //设置图片网址或地址的集合
        banner.setImages(list_path);
//    //设置轮播图的标题集合
//        banner.setBannerTitles(list_title);
//        //设置内置样式，共有六种可以点入方法内逐一体验使用。
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new MyLoader());
//        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
//        banner.setBannerAnimation(Transformer.Default);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true);
        banner.start();
//        //设置指示器的位置，小点点，左中右。
//        banner.setIndicatorGravity(BannerConfig.CENTER)
//                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
//                .setOnBannerListener(this)
//                //必须最后调用的方法，启动轮播图。
//                .start();

    }

    //轮播图监听
    @Override
    public void OnBannerClick(int position) {
        if (bannerDOList != null) {
            if (!TextUtil.isEmpty(bannerDOList.get(position).getJumpUrl())) {
                Intent intent = new Intent(getActivity(), NewsDetails.class);
                intent.putExtra("jumpUrl", bannerDOList.get(position).getJumpUrl());
                intent.putExtra("name", bannerDOList.get(position).getName());
                startActivity(intent);
            }
        }
    }

    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(context).load((String) path).placeholder(R.drawable.banner).error(R.drawable.banner).into(imageView);
        }
    }

    private void judgmentNews() {
        boolean b = SharedUtil.getSharedUtil().getBoolean(getActivity(), Constant.IS_INFO);
        if (b) {
            iv_title_right.setImageResource(R.mipmap.xinxi);
            LUtils.e("----------消息来了-------");
        } else {
            iv_title_right.setImageResource(R.mipmap.xingxi);
            LUtils.e("----------没消息-------");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
