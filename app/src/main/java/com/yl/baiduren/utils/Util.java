package com.yl.baiduren.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yl.baiduren.App;
import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;


/**
 * @author 王锋 on 2017/11/13.
 */

public class Util {

    private static Context context;
    private static Util util = null;


    private Util() {
        context = App.getContext();
    }

    public static Util getInstance() {
        if (util == null) {
            synchronized (Util.class) {
                if (util == null) {
                    util = new Util();
                }
            }
        }
        return util;
    }

    /**
     * 请求失败
     *
     * @param type
     */
    public static void getIsNetWork(String type) {
        Toast.makeText(context, type, Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求成功 code 错误
     *
     * @param t
     */
    public static void getToast(BaseEntity<?> t) {
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 请求成功 code 错误
     *
     * @param
     */
    public static void getToast(String mes) {
        Toast.makeText(context, mes, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        return outMetrics.heightPixels;
    }

    public static String toJson(Object t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }

    /**
     * @return 读取json数据
     */
    public static String InitData() {
        StringBuffer sb = new StringBuffer();
        AssetManager mAssetManager = App.getContext().getAssets();
        try {
            InputStream is = mAssetManager.open("address.json");
            byte[] data = new byte[is.available()];
            int len = -1;
            while ((len = is.read(data)) != -1) {
                sb.append(new String(data, 0, len, "utf-8"));
            }
            is.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * string 转 时间戳
     *
     * @param strTime
     * @return
     */
    public static Long strParsedata(String strTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            if (!TextUtils.isEmpty(strTime)) {
                date = formatter.parse(strTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String LongParseString(Long ll) {


        String re_StrTime = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        re_StrTime = sdf.format(new Date(ll));


        return re_StrTime;
    }

    /**
     * 乘100
     *
     * @return
     */
    public static int takeBai(String take) {
        return Integer.valueOf(take) * 100;
    }

    public static int besidesBai(String besidesBai) {
        return Integer.valueOf(besidesBai) / 100;
    }


    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    /**
     * list 转 String
     *
     * @param stringList
     * @return
     */
    public static String listToString(List<String> stringList) {

        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static HashMap<String, String> getUrlParams(String url) {
        int index = url.indexOf("?");//找到问号位置
        String argStr = url.substring(index + 1);
        String[] argAry = argStr.split("&");
        HashMap<String, String> argMap = new HashMap<>();
        for (String arg : argAry) {
            String[] argAryT = arg.split("=");
            argMap.put(argAry[0], argAryT[1]);
        }
        return argMap;
    }


    /**
     * 首页引导页是否显示
     *
     * @param context
     * @return
     */
    public static boolean isFirstApp(Context context, int index) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int currentVersion = info.versionCode;
            if (index == 1) { //首页是否显示
                int lastVersion1 = SharedUtil.getSharedUtil().getInt(context, Constant.VERSION_KEY);
                if (currentVersion > lastVersion1) {//是第一次
                    //如果当前版本大于上次版本，该版本属于第一次启动
                    //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
                    SharedUtil.getSharedUtil().putInt(context, Constant.VERSION_KEY, currentVersion);
                    LUtils.e("-------首页新手-------");
                    return true;
                }
            } else if (index == 2) {
                int lastVersion2 = SharedUtil.getSharedUtil().getInt(context, Constant.VERSION_1);
                if (currentVersion > lastVersion2) {//是第一次
                    //如果当前版本大于上次版本，该版本属于第一次启动
                    //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
                    SharedUtil.getSharedUtil().putInt(context, Constant.VERSION_1, currentVersion);
                    LUtils.e("-------备案--1-------");
                    return true;
                }
            } else if (index == 3) {
                int lastVersion2 = SharedUtil.getSharedUtil().getInt(context, Constant.VERSION_2);
                if (currentVersion > lastVersion2) {//是第一次
                    //如果当前版本大于上次版本，该版本属于第一次启动
                    //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
                    SharedUtil.getSharedUtil().putInt(context, Constant.VERSION_2, currentVersion);
                    LUtils.e("-------备案--2-------");
                    return true;
                }
            } else if (index == 4) {
                int lastVersion2 = SharedUtil.getSharedUtil().getInt(context, Constant.VERSION_3);
                if (currentVersion > lastVersion2) {//是第一次
                    //如果当前版本大于上次版本，该版本属于第一次启动
                    //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
                    SharedUtil.getSharedUtil().putInt(context, Constant.VERSION_3, currentVersion);
                    LUtils.e("-------备案--3-------");
                    return true;
                }
            } else if (index == 5) {
                int lastVersion2 = SharedUtil.getSharedUtil().getInt(context, Constant.VERSION_4);
                if (currentVersion > lastVersion2) {//是第一次
                    //如果当前版本大于上次版本，该版本属于第一次启动
                    //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
                    SharedUtil.getSharedUtil().putInt(context, Constant.VERSION_4, currentVersion);
                    LUtils.e("-------备案--4-------");
                    return true;
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 字符串替换
     *
     * @param from   要替换的字符
     * @param to     要替换成的目标字符
     * @param source 要替换的字符串
     * @return
     */
    public static String strReplace(String from, String to, String source) {
        StringBuffer bf = new StringBuffer("");
        StringTokenizer st = new StringTokenizer(source, from, true);
        while (st.hasMoreTokens()) {
            String tmp = st.nextToken();
            if (tmp.equals(from)) {
                bf.append(to);
            } else {
                bf.append(tmp);
            }
        }
        return bf.toString();
    }

    // 状态栏高度
    private static int statusBarHeight = 0;
    // 屏幕像素点
    private static final Point screenSize = new Point();

    // 获取屏幕像素点
    public static Point getScreenSize(Activity context) {

        if (context == null) {
            return screenSize;
        }
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
            Display diplay = wm.getDefaultDisplay();
            if (diplay != null) {
                diplay.getMetrics(mDisplayMetrics);
                int W = mDisplayMetrics.widthPixels;
                int H = mDisplayMetrics.heightPixels;
                if (W * H > 0 && (W > screenSize.x || H > screenSize.y)) {
                    screenSize.set(W, H);
                }
            }
        }
        return screenSize;
    }

    // 获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight <= 0) {
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
        }
        if (statusBarHeight <= 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                statusBarHeight = context.getResources().getDimensionPixelSize(x);

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    /**
     * 删除文件
     *
     * @param path
     */
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists()) {
            return;
        } else {
            if (path.isFile()) {
                path.delete();
                return;
            } else {
                File[] files = path.listFiles();
                if (files.length != 0) {
                    for (File file : files) {
                        deleteAllFilesOfDir(file);
                    }
                    path.delete();
                }
            }
        }
    }


    /**
     * 删除文件夹
     *
     * @param folderPath 文件夹完整绝对路径
     */
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        if (tempList.length != 0) {

            for (String aTempList : tempList) {
                if (path.endsWith(File.separator)) {
                    temp = new File(path + aTempList);
                } else {
                    temp = new File(path + File.separator + aTempList);
                }
                if (temp.isFile()) {
                    temp.delete();
                }
                if (temp.isDirectory()) {
                    delAllFile(path + "/" + aTempList);//先删除文件夹里面的文件
                    delFolder(path + "/" + aTempList);//再删除空文件夹
                    flag = true;
                }
            }
        } else {
            LUtils.e("----------空空空空------");
        }
        return flag;
    }

    public void setCJ(Activity context) {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = context.getWindow().getDecorView();
            int aa = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //界面默认情况下是全屏的，状态栏和导航栏都不会显示。而当我们需要用到状态栏或导航栏时，
            // 只需要在屏幕顶部向下拉，或者在屏幕右侧向左拉，状态栏和导航栏就会显示出来
            int cc = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(cc);
            context.getWindow().setStatusBarColor(Color.TRANSPARENT);
            context.getWindow().setNavigationBarColor(Color.TRANSPARENT);

        }
    }

}

