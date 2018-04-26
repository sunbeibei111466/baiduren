package com.yl.baiduren.activity.newdebt_preson;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.AddDebtPersonAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.fragment.add_debt_person.Add_New_EnterPrise;
import com.yl.baiduren.fragment.add_debt_person.Add_New_Natural_Person;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sunbeibei on 2017/12/1.
 * 添加债事人管理类
 */

public class Add_DebtPerson_Activity extends BaseActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private static final int REQUEST_IMAGE = 2;
    public static String isOver ="2";

    private String[]title={"债事自然人","债事机构"};

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
    private ImageView take_phone;
    public static ArrayList<String> path;

    @Override
    public int loadWindowLayout() {
        return R.layout.add_debtperson_activity;
    }

    @Override
    public void initViews() {
        ImageView iv_title_left = findViewById(R.id.iv_title_left);
        iv_title_left.setOnClickListener(this);
        idiocies();
    }

    private void idiocies() {

        initStyle();
        initView_s();
        // 准备碎片
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new Add_New_Natural_Person());//债事个人
        fragments.add(new Add_New_EnterPrise());//债事企业
        // 实例化适配器
        AddDebtPersonAdapter debtFragmentAdapter = new AddDebtPersonAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(debtFragmentAdapter);// 关联适配器
        initListener();
        viewPager.setCurrentItem(0);
        setColor(0);



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
        viewPager.addOnPageChangeListener(this);
    }

    private void initView_s() {
        // 初始化下划线(逐帧动画)
        String packageName = getApplicationContext().getPackageName();//获取当前包名
        for (int i = 0; i < 2; i++) {
            //从图片名称反射资源ID  R.id.line1
            int id = this.getResources().getIdentifier("line_Wf" + (i + 1), "id", packageName);
            arrLine[i] =  findViewById(id);
            int id2 = this.getResources().getIdentifier("btn_Wf" + (i + 1), "id", packageName);
            arrBtn[i] =  findViewById(id2);
            int id3 = this.getResources().getIdentifier("txt_Wf" + (i + 1), "id", packageName);
            arrTxt[i] = findViewById(id3);
            // 设置标签名
            arrTxt[i].setText(title[i]);
        }
        // 获取ViewPager对象
        viewPager =  findViewById(R.id.vp_tjzrr);
    }

    /**
     * 1.将所有的背景统一颜色
     * 2.将当前选中的背景设置特殊颜色
     * @param index
     */
    public void setColor(int index){
        // "所有人"都回复最初的状态
        for (int i = 0; i<arrBtn.length; i++){
            arrLine[i].setBackgroundColor(getResources().getColor(R.color.white));
            arrTxt[i].setTextColor(getResources().getColor(color_unselected));
        }
        arrLine[index].setBackgroundColor(getResources().getColor(color_selected));// 特殊
        arrTxt[index].setTextColor(getResources().getColor(color_selected));

    }

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
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOver =null;
    }

}
