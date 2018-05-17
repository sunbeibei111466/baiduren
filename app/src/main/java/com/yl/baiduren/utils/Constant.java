package com.yl.baiduren.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Android_apple on 2017/12/11.
 */

public class Constant {

    public static String JSON="application/json; charset=utf-8";
    public static String  VERSION_KEY="FIRST_INSTALLATION";//记录首页引导是第几次进入
    public static String  VERSION_1="FIRST_ONE";//备案第一页
    public static String  VERSION_2="FIRST_TOO";//备案第二页
    public static String  VERSION_3="FIRST_THREE";//备案第三页
    public static String  VERSION_4="FIRST_FORE";//备案第四页

    public static String PAGER_SUSSFUL="pager_sussful";//成功案例
    public static String PAGER_DEBTHALL_WZP="pager_debthall_wzp";//债事大厅 未摘牌
    public static String PAGER_DEBTHALL_YZP="pager_debthall_yzp";//债事大厅 已摘牌
    public static String PAGER_MYDEBT_WZP="pager_my_debt_wzp";//我的备案 未摘牌
    public static String PAGER_MYDEBT_YZP="pager_my_debt_yzp";//我的备案 已摘牌
    public static String PAGER_MY_CHILDDEBT_WZP="pager_my_child_debt_wzp";//我的子账号备案  未摘牌
    public static String PAGER_MY_CHILDDEBT_YZP="pager_my_child_debt_yzp";//我的子账号备案 已摘牌
    public static String PAGER_MY_ZHAIPAI="pager_my_zhaipai";//我的摘牌
    public static String PAGER_MY_CHILD_ZHAIPAI="pager_my_child_zhaipai";//我的子账号摘牌
    public static String MESSAGE_INDEX="message_index";//跳转到 我的备案 已摘牌 的摘牌
    public static String IS_INFO="is_info";//是否有推送消息
    public static String GX_DT="GX_DT";//供需大厅
    public static String MY_GX_DT="MY_DT_GY";//我的供需大厅
    public static String MY_SC_DT="MY_SC_GY";//我的收藏大厅

    public static String ZC_DT="GX_DT_GY";//资产大厅
    public static String MY_ZC="GX_DT_XQ";//我的资产
    public static String MY_DT_GY="MY_DT_GY";//我的供需大厅   供应端
    public static String MY_DT_XQ="MY_DT_XQ";//我的供需大厅   需求端
    public static String MY_SC_GY="MY_SC_GY";//我的收藏大厅   供应端
    public static String MY_SC_XQ="MY_SC_XQ";//我的收藏大厅   需求端

//    /storage/emulated/0/zhenxing_app
    public static String COMPERSS_IMAGE= Environment.getExternalStorageDirectory()
        .getAbsolutePath() + File.separator + "zhenxing_app";//压缩图片存储路径

    public static String REPORT= Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator + "zhenxing_report";//报告存储路径

    public static String ACTION_NAME="com.jx.umeng";
    public static String MESSAGE = "您的身份不是解债人，请先申请解债人身份后再进行操作！点击查看解债人详情";
    public static String MESSAGE_Vip="您不是VIP，请先开通VIP后再进行查看!";
    public static String codeN = "******************";
    /**
     * 新注册用户角色
     */
    public static final String ROLE_NEW_REG = "r_new_reg";

    /**
     * 管理员角色
     */
    public static final String ROLE_SYS_ADMIN = "r_sys_admin";

    /**
     * 企业子账户用户角色
     */
    public static final String ROLE_NEW_ENTERPRISE = "r_new_enterprise";

    /**
     * 企业主账户用户角色
     */
    public static final String ROLE_MAIN_ENTERPRISE = "r_main_enterprise";

    /**
     * 支付宝支付标识
     */
    public static final int ALIPAY_PAY_FLAG = 1;

    /**
     * 微信AppId
     */
    public static final String WEIXIN_ADDID = "wxaf71137ec916d0a0";


    /**
     * 1.微信支付；2.支付宝支付；3.余额支付；4.银联支付
     */

    public static final int WEIXIN_PAY_TYPE=1;
    public static final int ALIPAY_PAY_TYPE=2;
//    public static final int WEIXIN=1;
//    public static final int WEIXIN=1;


    /**
     * 订单状态
     *
     * 1.新建、未支付 2.支付中 3.已支付 4.已完成 5.未支付 ，支付超市关闭 ，未支付取消订单
     * 2 商城订单状态 0.待支付，1.已支付 2.支付失败 3已退款 4.拒绝退款
     *
     */
    public static final int STATUS_0=0;
    public static final int STATUS_1=1;
    public static final int STATUS_2=2;
    public static final int STATUS_3=3;
    public static final int STATUS_4=4;
    public static final int STATUS_5=5;


    /**
     * 类型1. 充值企业vip 2.充值个人vip 3.解债人占用备案（摘牌） 4.充值备案次数 5.占用债权 6.大众版征信报告7.基础征信报告 8深度征信报告
     */
    public static final int PAY_TYPE_1=1;
    public static final int PAY_TYPE_2=2;
    public static final int PAY_TYPE_3=3;
    public static final int PAY_TYPE_4=4;
    public static final int PAY_TYPE_5=5;
    public static final int PAY_TYPE_6=6;
    public static final int PAY_TYPE_7=7;
    public static final int PAY_TYPE_8=8;
    public static final int PAY_TYPE_9=9;

}
