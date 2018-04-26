package com.yl.baiduren.base;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver
 * Created by jaycee on 2017/6/23.
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {


    private static final String TAG = "BaseObserver";
    private Activity mContext;
    private Gson gson;

    private boolean isStop = false;

    protected BaseObserver(Activity context) {
        this.mContext = context;
        gson = new Gson();
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (!isStop) {
            onRequestStart();//开始请求 + 进度条
        }
    }

    @Override
    public void onNext(BaseEntity<T> value) {

        Log.e(TAG, "--网络请求成功----" + gson.toJson(value));
        if (!isStop) {
            onRequestEnd();//请求结束
        }

        if (value.isSuccess()) {//成功  code=1
            try {
                //返回页面数据，uuid
                onSuccees(value.getCode(), value.getDataResult(), value.getBaseResponse());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (value.isRe_landing()) {//重新登陆  code=4
            try {
                loginAgain();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (value.getCode().equals("2")) {//请求成功，code错误
            try {
                onCodeError(value.getCode(), value.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { //code 3
            ToastUtil.showShort(mContext, value.getMessage());
        }
    }


    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "--网络请求错误----" + e.toString());
        onRequestEnd();//请求结束
        try {
            if (e instanceof ConnectException) {
                String CONNECTEXCEPTION = "网络连接异常，请检查您的网络状态";
                onFailure(e, CONNECTEXCEPTION);
            } else if (e instanceof TimeoutException) {
                String TIMEOUTEXCEPTION = "网络连接超时，请检查您的网络状态，稍后重试";
                onFailure(e, TIMEOUTEXCEPTION);
            } else if (e instanceof NetworkErrorException) {
                String NETWORKERROREXCEPTION = "网络错误，请检查您的网络状态";
                onFailure(e, NETWORKERROREXCEPTION);
            } else if (e instanceof UnknownHostException) {
                String UNKNOWNHOSTEXCEPTION = "网络异常，请检查您的网络状态";
                onFailure(e, UNKNOWNHOSTEXCEPTION);
            } else {
                String SERVEREXCEPTION = "服务器异常,请稍后再试";
                onFailure(e, SERVEREXCEPTION);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param code
     * @param data
     * @param baseResponse
     * @throws Exception
     */
    protected abstract void onSuccees(String code, T data, BaseRequest baseResponse) throws Exception;

    /**
     * 返回成功了,但是code错误
     *
     * @param
     * @throws Exception
     */
    protected void onCodeError(String code, String mess) throws Exception {
        Util.getToast(mess);
    }

    /**
     * 返回失败
     *
     * @param e
     * @throws Exception
     */
    protected void onFailure(Throwable e, String type) throws Exception {
        Util.getInstance().getIsNetWork(type);
    }

    /**
     * 开始请求
     */
    protected void onRequestStart() {
        DialogUtils.showDrawDialog(mContext);
//        DialogUtils.showPopup(mContext);
    }

    /**
     * 结束请求
     */
    protected void onRequestEnd() {
        DialogUtils.closeDialog();
//        DialogUtils.closePopup();
    }

    /**
     * 重新登陆
     */
    private void loginAgain() {
        UserInfoUtils.deleteBaseRequest(mContext);
        UserInfoUtils.deleteLoginSuccess(mContext);
        UserInfoUtils.deleteMyPager(mContext);
        UserInfoUtils.deleteCategory(mContext);
        DialogUtils.showDialogZsr(mContext, true, "您的账号在异地登陆,请修改密码或重新登陆", new DialogUtils.OnButtonEventListener() {
            @Override
            public void onConfirm() {
                mContext.startActivity(new Intent(mContext, Login_Activity_Password.class));
            }

            @Override
            public void onCancel() {
            }
        });
    }

}
