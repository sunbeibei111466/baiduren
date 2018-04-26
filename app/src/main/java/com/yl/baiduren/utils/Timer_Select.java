package com.yl.baiduren.utils;

import android.app.Activity;
import android.widget.TextView;

import com.yl.baiduren.view.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by sunbeibei on 2017/12/4.
 */

public class Timer_Select {
    public  static void getTimer(Activity activity, String textView,CustomDatePicker.ResultHandler handler){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        CustomDatePicker customDatePicker1 = new CustomDatePicker(activity,handler, "1949-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动
        customDatePicker1.show(textView);



    }


    public static String getTime(TextView date) {//可根据需要自行截取数据显示

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        date.setText(now.split(" ")[0]);
        String s = date.getText().toString();
        return s;

    }

}
