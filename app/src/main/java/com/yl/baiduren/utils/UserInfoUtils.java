package com.yl.baiduren.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.greenentity.LoginSuccess;
import com.yl.baiduren.entity.result.MyPager;

import java.util.List;

/**
 * 用户
 */

public class UserInfoUtils {


    public static boolean IsLogin(Context context) {
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(context).getBaseRequestDao().loadAll();
        return baseRequests != null && baseRequests.size() != 0;
    }

    public static BaseRequest getBaseRequest(Context cxt){
        if(IsLogin(cxt)){
            List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(cxt).getBaseRequestDao().loadAll();
            return baseRequests.get(0);
        }
        return null;
    }

    public static Long getUid(Context cxt){
        if(IsLogin(cxt)){
            List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(cxt).getBaseRequestDao().loadAll();
            return baseRequests.get(0).getUid();
        }
        return null;
    }

    public static String getMobile(Context cxt){
        if(IsLogin(cxt)){
            List<LoginSuccess> loginSuccessList=GreenDaoUtils.getInstance(cxt).getLoginSuccessDao().loadAll();
            if (loginSuccessList.size() != 0 && !TextUtils.isEmpty(loginSuccessList.get(0).getMobile())) {
                    return loginSuccessList.get(0).getMobile();
            }
        }
        return null;
    }

    /**
     * 判断是否有没按次数  true 有
     *
     * @param activity
     * @return
     */
    public static boolean isRecordNumber(Activity activity) {
        List<MyPager> myPagerList = GreenDaoUtils.getInstance(activity).getMyPagerDao().loadAll();
        if (myPagerList.size() != 0) {
            if (!myPagerList.get(0).getRecordNumber().equals("0")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 请求成功后 ，更新uuid
     *
     * @param activity
     * @param baseResponse
     */
    public static void setUuid(Activity activity, BaseRequest baseResponse) {
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(activity).getBaseRequestDao().loadAll();
        if (baseRequests.size() != 0) {
            BaseRequest baseRequest = baseRequests.get(0);
            baseRequest.setUuid(baseResponse.getUuid());
            GreenDaoUtils.getInstance(activity).getBaseRequestDao().update(baseRequest);
        }
    }

    /**
     * 删除登陆成功用户信息
     *
     * @param context
     */
    public static void deleteLoginSuccess(Context context) {
        GreenDaoUtils.getInstance(context).getLoginSuccessDao().deleteAll();
    }

    /**
     * 删除用户基本参数
     *
     * @param context
     */
    public static void deleteBaseRequest(Context context) {
        GreenDaoUtils.getInstance(context).getBaseRequestDao().deleteAll();
    }

    /**
     * 删除我的页信息
     *
     * @param context
     */
    public static void deleteMyPager(Context context) {
        GreenDaoUtils.getInstance(context).getMyPagerDao().deleteAll();
    }

    /**
     * 删除资产类型数据
     */
    public static void deleteCategory(Context context){
        GreenDaoUtils.getInstance(context).getCategoryDoDao().deleteAll();
    }


    public static void setBaseRequest(Context cxt){
        com.yl.baiduren.data.BaseRequest mBaseRequest=DataWarehouse.getApplication(cxt).baseRequest;
        if(IsLogin(cxt)){
            if(getBaseRequest(cxt)!=null){
                BaseRequest baseRequest=getBaseRequest(cxt);
                mBaseRequest.accessToken=baseRequest.getAccessToken();
                mBaseRequest.loginUsername=baseRequest.getLoginUsername();
                mBaseRequest.platform=baseRequest.getPlatform();
                mBaseRequest.uid=baseRequest.getUid();
                mBaseRequest.uuid=baseRequest.getUuid();
            }
        }
    }
}
