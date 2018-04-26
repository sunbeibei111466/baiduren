package com.yl.baiduren.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.CommissionAdapter;
import com.yl.baiduren.entity.Area;
import com.yl.baiduren.entity.Area2;
import com.yl.baiduren.entity.result.QueryDebtNameResult;
import com.yl.baiduren.entity.result.ReportResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Android_apple on 2017/12/12.
 */

public class PopupWindeUtils {

    private static Spinner mProvinceSpinner;//省
    private static Spinner mCitySpinner;//市
    private static Spinner mAreaSpinner;//区
    // 列表选择的省市区
    public static String selectedPro = "";//省
    public static String selectedCity = "";//市
    public static String selectedArea = "";//区
    // 省份
    public static String[] mProvinceDatas;
    // 城市
    public static String[] mCitiesDatas;
    // 地区
    public static String[] mAreaDatas;

    public static ArrayAdapter<String> mProvinceAdapter;
    public static ArrayAdapter<String> mCityAdapter;
    public static ArrayAdapter<String> mAreaAdapter;
    // 存储省对应的所有市 name
    private static Map<String, String[]> mCitiesDataMap = new HashMap<>();
    // 存储市对应的所有区 name
    private static Map<String, String[]> mAreaDataMap = new HashMap<>();
    //省市區Id
    private static String shengId = null, shiId = null, quId = null;
    private static Area area;//数据源实体
    private static Area2 area2;//数据源实体
    private static Activity mContext;

    public interface OnAreaDataIdListener {
        void areaId(String shengId, String shiId, String quId, String string);
    }

    public interface OnClickListView {
        void onStringClick(Integer id, String name);
    }


    @SuppressLint("WrongViewCast")
    public static void showPopupWinde(final Activity context, final OnAreaDataIdListener onAreaDataIdListener) {
        mContext = context;
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.spinner2, null);

        Util.backgroundAlpha(context, 0.6f);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.window_style);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.LEFT | Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1f);
            }
        });

        mProvinceSpinner = popupView.findViewById(R.id.spinner_pro);//省
        mCitySpinner = popupView.findViewById(R.id.spinner_city);//市
        mAreaSpinner = popupView.findViewById(R.id.spinner_area);//区
        TextView quxiao = popupView.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView queren = popupView.findViewById(R.id.queren);
        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAreaDataIdListener.areaId(shengId, shiId, quId, selectedPro + selectedCity + selectedArea);//拿到省市区Id
                popupWindow.dismiss();
            }
        });

        jsonCitisData();
        initSpinner();
    }

    private static void jsonCitisData() {

        Gson gson = new Gson();
        area2 = gson.fromJson(Util.InitData(), Area2.class);
        int shengSize = area2.getDataResult().size();//省个数
        mProvinceDatas = new String[shengSize];//省数组

        String mProvinceStr;
        for (int i = 0; i < shengSize; i++) {

            mProvinceStr = area2.getDataResult().get(i).getName();
            mProvinceDatas[i] = mProvinceStr;
            int shiSize = area2.getDataResult().get(i).getChild().size();//市个数
            mCitiesDatas = new String[shiSize];

            String mCityStr;
            for (int j = 0; j < shiSize; j++) {
                mCityStr = area2.getDataResult().get(i).getChild().get(j).getName();
                mCitiesDatas[j] = mCityStr;
                int quSize = area2.getDataResult().get(i).getChild().get(j).getChild().size();
                mAreaDatas = new String[quSize];
                for (int k = 0; k < quSize; k++) {
                    mAreaDatas[k] = area2.getDataResult().get(i).getChild().get(j).getChild().get(k).getName();
                }
                // 存储市对应的所有第三级区域
                mAreaDataMap.put(mCityStr, mAreaDatas);
            }
            // 存储省份对应的所有市
            mCitiesDataMap.put(mProvinceStr, mCitiesDatas);
        }
    }

    private static void initSpinner() {

        //设置未下拉时的布局
        mProvinceAdapter = new ArrayAdapter<>(mContext, R.layout.my_spinner_item, mProvinceDatas);
        //设置下拉时的布局
        mProvinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProvinceSpinner.setAdapter(mProvinceAdapter);

        // 省份选择
        mProvinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPro = "";
                selectedPro = (String) parent.getSelectedItem();
                // 根据省份更新城市区域信息
                shengId = updateCity(selectedPro);
                Log.d("--H_OpenDebt-", "mProvinceSpinner has excuted !" + "selectedPro is " + selectedPro);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 市选择
        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = "";


                selectedCity = (String) parent.getSelectedItem();
                shiId = updateArea(selectedPro, selectedCity);
                Log.d("----H_OpenDebt--", "mCitySpinner has excuted !" + "selectedCity is " + selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 区选择
        mAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = "";
                selectedArea = (String) parent.getSelectedItem();
                quId = getQuId(selectedPro, selectedCity, selectedArea);
                Log.e("--区--id--", selectedArea + "------" + quId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    /**
     * 根据省份更新城市数据    返回省id
     */
    private static String updateCity(String pro) {
        String shengId = "";//省id
        String[] cities = null;
        List<Area2.DataResultBean.ChildBeanX> shiNameList;
        for (int i = 0; i < area2.getDataResult().size(); i++) {
            if (pro.equals(area2.getDataResult().get(i).getName())) {
                shiNameList = area2.getDataResult().get(i).getChild();
                shengId = area2.getDataResult().get(i).getCode() + "";
                Log.e("-----", "---选择---省 Id---- " + shengId);
                cities = new String[shiNameList.size()];
                for (int j = 0; j < shiNameList.size(); j++) {
                    cities[j] = shiNameList.get(j).getName();
                }
            }
        }

        // 存在区
        mCityAdapter = new ArrayAdapter<>(mContext, R.layout.my_spinner_item, cities);
        mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCitySpinner.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();
        mCitySpinner.setSelection(0);
        return shengId;
    }

    /**
     * 根据市 选择对应的区  返回市Id
     */
    private static String updateArea(String pro, String city) {

        String[] areas = null;
        String shiId = "";

        //市 集合
        List<Area2.DataResultBean.ChildBeanX> shiNameList = null;
        //区 集合
        List<Area2.DataResultBean.ChildBeanX.ChildBean> quNameList = null;

        for (int i = 0; i < area2.getDataResult().size(); i++) {//循环省

            //找到指定省
            if (pro.equals(area2.getDataResult().get(i).getName())) {
                //拿到指定省下的市
                shiNameList = area2.getDataResult().get(i).getChild();
                //循环市
                for (int j = 0; j < shiNameList.size(); j++) {

                    //找到指定市
                    if (city.equals(shiNameList.get(j).getName())) {
                        shiId = shiNameList.get(j).getCode() + "";
                        Log.e("---", "---选择---市--id-- " + shiId);
                        quNameList = shiNameList.get(j).getChild();
                        if (quNameList != null && quNameList.size() != 0) {
                            areas = new String[quNameList.size()];
                            for (int k = 0; k < quNameList.size(); k++) {
                                areas[k] = quNameList.get(k).getName();
                            }
                        } else {
                            areas = null;
                        }
                    }
                }
            }
        }
        // 存在区
        if (areas != null) {
            // 存在区
            mAreaSpinner.setVisibility(View.VISIBLE);
            mAreaAdapter = new ArrayAdapter<>(mContext, R.layout.my_spinner_item, areas);
            mAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mAreaSpinner.setAdapter(mAreaAdapter);
            mAreaAdapter.notifyDataSetChanged();
            mAreaSpinner.setSelection(0);
        } else {
            Log.e("---", "-------隱藏-------");
            mAreaSpinner.setVisibility(View.GONE);
            quId = "";
        }
        return shiId;
    }

    /**
     * 获取区Id
     *
     * @param pro
     * @param city
     * @param areaString
     * @return
     */
    private static String getQuId(String pro, String city, String areaString) {

        String shiId = null;
        String quId = "";
        //市 集合
        List<Area2.DataResultBean.ChildBeanX> shiNameList = null;
        //区 集合
        List<Area2.DataResultBean.ChildBeanX.ChildBean> quNameList = null;
        for (int i = 0; i < area2.getDataResult().size(); i++) {//循环省

            //找到指定省
            if (pro.equals(area2.getDataResult().get(i).getName())) {
                //拿到指定省下的市
                shiNameList = area2.getDataResult().get(i).getChild();
                //循环市
                for (int j = 0; j < shiNameList.size(); j++) {
                    //找到指定市
                    if (city.equals(shiNameList.get(j).getName())) {

                        shiId = shiNameList.get(j).getCode() + "";
                        quNameList = shiNameList.get(j).getChild();
                        for (int k = 0; k < quNameList.size(); k++) {
                            if (areaString.equals(quNameList.get(k).getName())) {
                                quId = quNameList.get(k).getCode() + "";
                                Log.e("---", "---选择---区--Id-- " + quId);
                            }
                        }
                    }
                }
            }
        }
        return quId;
    }

    public static void queryPopupWinde2(final Activity context, List<ReportResult.Report> dataList, OnClickListView onClickListView) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popuwinde_list, null);
        TextView title = popupView.findViewById(R.id.title);
        ListView listView = popupView.findViewById(R.id.popupListView);
        title.setText("请选择您购买的报告");
        Util.backgroundAlpha(context, 0.6f);
        final PopupWindow popupWindow = new PopupWindow(popupView, ScreenUtil.getScreenWidth(context) - 100, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.window_style);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1f);
            }
        });

        setListView2(popupWindow, context, listView, dataList, onClickListView);
    }

    private static void setListView2(final PopupWindow popupWindow, final Activity context, ListView listView, final List<ReportResult.Report> dataList, final OnClickListView onClickListView) {

        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public Object getItem(int position) {
                return dataList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(context);
                textView.setText(dataList.get(position).getSearchName());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(18);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListView.onStringClick(dataList.get(position).getId(), dataList.get(position).getSearchName());
                        popupWindow.dismiss();
                    }
                });
                return textView;
            }
        };
        listView.setAdapter(baseAdapter);
    }


    public static void queryPopupWinde(final Activity context, List<QueryDebtNameResult.DebtNameResponse> list, OnClickListView onClickListView) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popuwinde_list, null);

        TextView title = popupView.findViewById(R.id.title);
        ListView listView = popupView.findViewById(R.id.popupListView);
        title.setText("选择您要备案的债事人");
        Util.backgroundAlpha(context, 0.6f);
        final PopupWindow popupWindow = new PopupWindow(popupView, ScreenUtil.getScreenWidth(context) - 100, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.window_style);
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1f);
            }
        });

        setListView(popupWindow, context, listView, list, onClickListView);
    }

    private static void setListView(final PopupWindow popupWindow, final Activity context, ListView listView, final List<QueryDebtNameResult.DebtNameResponse> list, final OnClickListView onClickListView) {

        BaseAdapter baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                TextView textView = new TextView(context);
                textView.setText(list.get(position).getName());
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(18);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListView.onStringClick(list.get(position).getId(), list.get(position).getName());
                        popupWindow.dismiss();
                    }
                });
                return textView;
            }
        };
        listView.setAdapter(baseAdapter);
    }


    public static void yongJinPopupWinde(final Activity context, View view, final onClivkListViewItem onClivkListViewItem) {
        final List<String> stringList = new ArrayList<>();
        stringList.add("10%以下");
        stringList.add("20%以上");
        stringList.add("30%以上");
        stringList.add("40%以上");
        stringList.add("50%以上");
        stringList.add("60%以上");
        stringList.add("70%以上");
        stringList.add("80%以上");
        stringList.add("90%以上");

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.buness_drawlayout, null);

        ListView listView = popupView.findViewById(R.id.lv_popup_buness);
        CommissionAdapter commissionAdapter = new CommissionAdapter(context, stringList);
        listView.setAdapter(commissionAdapter);
        final PopupWindow popupWindow = new PopupWindow(popupView, ScreenUtil.getScreenWidth(context) / 3, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.light_hei)));
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.window_style);
//        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER,0,0);
        popupWindow.showAsDropDown(view, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String string = stringList.get(position);
                onClivkListViewItem.onClickListViewItemString(string);
                popupWindow.dismiss();
            }
        });

    }

    public interface onClivkListViewItem {
        void onClickListViewItemString(String yongjin);
    }


    @SuppressLint("WrongViewCast")
    public static void showPopupWindeCredit(final Activity context, final OnButtonEventListener onButtonEventListener) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.credit_popup, null);
        TextView tv_phone = popupView.findViewById(R.id.tv_phone);//手机号
        final EditText et_mess = popupView.findViewById(R.id.et_mess);//验证码
        Button credit_send = popupView.findViewById(R.id.credit_send);//发送
        TextView qr = popupView.findViewById(R.id.tv_qr);//确认
        TextView qx = popupView.findViewById(R.id.tv_qx);//取消
        et_mess.setOnTouchListener(new View.OnTouchListener() {


            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
                    //activity要换成自己的activity名字
                    imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
                }
                return true;
            }
        });

        final String mobile = UserInfoUtils.getMobile(context);
        tv_phone.setText(mobile);

        Util.backgroundAlpha(context, 0.3f);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.window_style);
        int[] location = new int[2];
        //LayoutInflater.from(context).inflate(R.layout.add_new_natural_person, null).getLocationOnScreen(location);
        //        LayoutInflater.from(mContext).inflate(R.layout.add_new_natural_person,
        popupWindow.showAtLocation(context.getWindow().getDecorView(), Gravity.CENTER, 0, -location[1]);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Util.backgroundAlpha(context, 1f);
            }
        });


        credit_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonEventListener != null) {
                    onButtonEventListener.onClickSend(mobile);
                }
            }
        });
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonEventListener != null) {
                    String code = et_mess.getText().toString().trim();
                    if (!TextUtils.isEmpty(code)) {
                        onButtonEventListener.onConfirm(popupWindow, code);
                    } else {
                        ToastUtil.showShort(context, "请输入验证码");
                    }
                }
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonEventListener != null) {
                    popupWindow.dismiss();
                    Util.backgroundAlpha(context, 1f);
                }
            }
        });
    }


    public interface OnButtonEventListener {

        void onClickSend(String mobile);

        void onConfirm(PopupWindow popupWindow, String code);
    }

}