package com.yl.baiduren;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.ScanCapuActivity;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.entity.ZXingEventBus;
import com.yl.baiduren.entity.result.Verson_Code_Result;
import com.yl.baiduren.entity.result.WebEntity;
import com.yl.baiduren.fragment.DemandFragment;
import com.yl.baiduren.fragment.ForeFragment;
import com.yl.baiduren.fragment.OneFragment;
import com.yl.baiduren.fragment.ThreeFragment;
import com.yl.baiduren.fragment.TooFragment;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.ActivityCollector;
import com.yl.baiduren.utils.AppInfoUtil;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import org.feezu.liuli.timeselector.Utils.TextUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.yl.baiduren.fragment.OneFragment.highLight;

public class MainActivity extends BaseActivity implements View.OnClickListener {

//    110926136910501

    private final static int BOTTOM_MENU_ONE = 0;//ONE
    private final static int BOTTOM_MENU_TOO = 1;//too
    private final static int BOTTOM_MENU_THREE = 2;//three
    private final static int BOTTOM_MENU_FORE = 3;//fore
    private final static int BOTTOM_MENU_XUQIU = 4;//需求对对碰
    private int currMenu = BOTTOM_MENU_ONE;//默认首页
    private FragmentManager fragmentManager;
    private LinearLayout ll_One, ll_Too, ll_Three, ll_Fore;
    private Fragment oneFragment, tooFragment, threeFragment, foreFragment, demandFragment;
    public ImageView id_btn_home_oneyuan;
    private int num = 0;
    private long singTime = 0L;//记录按下时长

    private Dialog dialog;
    public static LinearLayout ll_parent_gx;
    private ImageView iv_home;
    private ImageView iv_too;
    private ImageView iv_three;
    private ImageView iv_fore;
    private TextView tv_home;
    private TextView tv_jzlc;
    private TextView tv_zsr;
    private TextView tv_wo;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        initView();
        initEvents();//初始化事件

    }

    private void initView() {

        id_btn_home_oneyuan = findViewById(R.id.id_btn_home_oneyuan);
        id_btn_home_oneyuan.setOnClickListener(this);//需求对对碰
        ll_parent_gx = findViewById(R.id.ll_parent_gx);
        ll_parent_gx.setOnClickListener(this);//需求对对碰 父控件

        //父布局
        ll_One = findViewById(R.id.ll_one);
        ll_Too = findViewById(R.id.ll_too);
        ll_Three = findViewById(R.id.ll_three);
        ll_Fore = findViewById(R.id.ll_fore);

        //图片
        iv_home = findViewById(R.id.iv_home);
        iv_too = findViewById(R.id.iv_too);
        iv_three = findViewById(R.id.iv_three);
        iv_fore = findViewById(R.id.iv_fore);

        //字体
        tv_home = findViewById(R.id.tv_home);
        tv_jzlc = findViewById(R.id.tv_jzlc);
        tv_zsr = findViewById(R.id.tv_zsr);
        tv_wo = findViewById(R.id.tv_wo);

        currMenu = BOTTOM_MENU_ONE;
    }

    private void initEvents() {
        ll_One.setOnClickListener(this);
        ll_Too.setOnClickListener(this);
        ll_Three.setOnClickListener(this);
        ll_Fore.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LUtils.e("*******---onResume---**********");
        performClickEv();
        Util.getInstance().setCJ(this);
        getVersion_Code();
    }


    @Override
    protected void onPause() {
        super.onPause();
        LUtils.e("*******---onPause---**********");
        Util.getInstance().setCJ(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        LUtils.e("*******---onStop---**********");
        Util.getInstance().setCJ(this);

    }


    /**
     * 因为启动模式是singleTask
     * onNewIntent 在onReaume 之后执行
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        currMenu = getIntent().getIntExtra("currMenu", 0);
        performClickEv();
    }

    /**
     * 触发点击事件
     */
    private void performClickEv() {
        if (this.currMenu == BOTTOM_MENU_ONE) {// 首页
            ll_One.performClick();
        } else if (this.currMenu == BOTTOM_MENU_TOO) {//
            ll_Too.performClick();
        } else if (this.currMenu == BOTTOM_MENU_THREE) {//
            ll_Three.performClick();
        } else if (this.currMenu == BOTTOM_MENU_FORE) {// 我的
            ll_Fore.performClick();
        } else if (currMenu == BOTTOM_MENU_XUQIU) {//需求对对碰
            id_btn_home_oneyuan.performClick();
        }
    }

    /**
     * 进行选中Tab的处理
     * @param i
     */
    private void selectTab(int i) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        hideFragments(trans);
        defaultTextColor();
        switch (i) {
            case BOTTOM_MENU_ONE:
                iv_home.setSelected(true);
                tv_home.setTextColor(getResources().getColor(R.color.blue));
                if (oneFragment == null) {
                    oneFragment = new OneFragment();
                    trans.add(R.id.fl_contentLayout, oneFragment);
                } else {
                    trans.show(oneFragment);
                }
                break;
            case BOTTOM_MENU_TOO:
                iv_too.setSelected(true);
                tv_jzlc.setTextColor(getResources().getColor(R.color.blue));
                if (tooFragment == null) {
                    tooFragment = new TooFragment();
                    trans.add(R.id.fl_contentLayout, tooFragment);
                } else {
                    trans.show(tooFragment);
                }
                break;
            case BOTTOM_MENU_THREE:
                iv_three.setSelected(true);
                tv_zsr.setTextColor(getResources().getColor(R.color.blue));
                if (UserInfoUtils.IsLogin(this)) {
                    if (threeFragment == null) {
                        threeFragment = new ThreeFragment();
                        trans.add(R.id.fl_contentLayout, threeFragment);
                    } else {
                        trans.show(threeFragment);
                    }
                } else {
                    startActivity(new Intent(this, Login_Activity_Password.class).putExtra("backPage", 3));
                }
                break;
            case BOTTOM_MENU_FORE:

                //显示我的页
                iv_fore.setSelected(true);
                tv_wo.setTextColor(getResources().getColor(R.color.blue));
                if (foreFragment == null) {
                    foreFragment = new ForeFragment();
                    trans.add(R.id.fl_contentLayout, foreFragment);
                } else {
                    trans.show(foreFragment);
                }
                break;
            case BOTTOM_MENU_XUQIU:
                //需求对对碰
                if (demandFragment == null) {
                    demandFragment = new DemandFragment();
                    trans.add(R.id.fl_contentLayout, demandFragment);
                } else {
                    trans.show(demandFragment);
                }
                break;
        }

        trans.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.ll_one) {
            currMenu=BOTTOM_MENU_ONE;
        } else if (id == R.id.ll_too) {
            currMenu=BOTTOM_MENU_TOO;
        } else if (id == R.id.ll_three) {
            currMenu=BOTTOM_MENU_THREE;
        } else if (id == R.id.ll_fore) {
            currMenu=BOTTOM_MENU_FORE;
        } else if (id == R.id.id_btn_home_oneyuan) {//需求对对碰
            currMenu=BOTTOM_MENU_XUQIU;
        }
        selectTab(currMenu);
    }

    /**
     * 隐藏fragment
     *
     * @param trans
     */
    private void hideFragments(FragmentTransaction trans) {
        //隐藏首页Fragment
        if (oneFragment != null) {
            trans.hide(oneFragment);
        }
        //隐藏债事管理Fragment
        if (tooFragment != null) {
            trans.hide(tooFragment);
        }
        //隐藏债事人管理Fragment
        if (threeFragment != null) {
            trans.hide(threeFragment);
        }

        //隐藏我的Fragment
        if (this.foreFragment != null) {
            trans.hide(foreFragment);
        }
        if (demandFragment != null) {
            trans.hide(demandFragment);
        }
    }

    /**
     * 默认字体颜色 图片选中状态
     */
    private void defaultTextColor(){
        tv_home.setTextColor(getResources().getColor(R.color.light_hei));
        tv_jzlc.setTextColor(getResources().getColor(R.color.light_hei));
        tv_zsr.setTextColor(getResources().getColor(R.color.light_hei));
        tv_wo.setTextColor(getResources().getColor(R.color.light_hei));

        iv_home.setSelected(false);
        iv_too.setSelected(false);
        iv_three.setSelected(false);
        iv_fore.setSelected(false);
    }



    /**
     * 接收到点击事件吊起二维码扫描
     *
     * @param zXingEventBus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCurrentItem(ZXingEventBus zXingEventBus) {
        if (zXingEventBus.isClick()) {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            intentIntegrator.setPrompt("二维码扫描");//底部的提示文字，设为""可以置空
            intentIntegrator.setCameraId(0);//选择前置后者后置摄像头
            intentIntegrator.setCaptureActivity(ScanCapuActivity.class);//设置竖屏
            intentIntegrator.setBeepEnabled(true);//扫描完成时的声响
            intentIntegrator.initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (!TextUtil.isEmpty(result.getContents())) {
                dialog = DialogUtils.ShowDefaultProDialog(MainActivity.this);
                if (result.getContents().contains("jxzcbd.com")) {
                    String url = result.getContents();
                    LUtils.e("---------扫码得到-------" + url);
                    getHttp(url);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getHttp(String webUri) {
        dialog.show();
        List<BaseRequest> baseRequestList = GreenDaoUtils.getInstance(this).getBaseRequestDao().loadAll();
        if (baseRequestList.size() != 0) {


            Long uid = baseRequestList.get(0).getUid();
            String token = baseRequestList.get(0).getAccessToken();

            String url = webUri + "&uid=" + uid + "&token=" + token;
            LUtils.e("------拼接后------" + url);

            OkHttpClient okHttpClient = new OkHttpClient();
            //创建request形
            Request request = new Request.Builder()
                    .get()
                    .url(url)//地址
                    .addHeader("User-Agent", "Android")
                    .build();

            //创建call对象
            okhttp3.Call call = okHttpClient.newCall(request);

            //请求网络
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    ToastUtil.showShort(MainActivity.this, "服务器异常，请稍后再试");
                    DismissDialog();
                }

                @Override
                public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                    final String string = response.body().string();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            LUtils.e("--------请求后------" + string);
                            Gson gson = new Gson();
                            final WebEntity webEntity = gson.fromJson(string, WebEntity.class);
                            if (webEntity.getCode().equals("1")) {
                                ToastUtil.showShort(MainActivity.this, webEntity.getMessage());
                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtil.showShort(MainActivity.this, webEntity.getMessage());
                                    }
                                }, 1000);
                            }
                            DismissDialog();
                        }
                    });


                }
            });
        } else {
            ToastUtil.showShort(MainActivity.this, "请先登陆App端，在进行扫码登陆");
        }
    }

    private void getVersion_Code() {
        com.yl.baiduren.data.BaseRequest baseRequest = new com.yl.baiduren.data.BaseRequest();
        baseRequest.platform = 2;
        String json = Util.toJson(baseRequest);
        LUtils.e("json--Verson---" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<Verson_Code_Result> baseObserver = new BaseObserver<Verson_Code_Result>(this) {

            @Override
            protected void onSuccees(String code, Verson_Code_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(MainActivity.this, baseResponse);
                    if (data != null) {
                        String versionNum = data.getVersionNum();
                        String updataMeassage = data.getUpdateItems();
                        String force = data.getIsForce();
                        checkUpdate(versionNum, updataMeassage, force);
                    }
                }
            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .getVersion(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))

                .compose(compose(this.<BaseEntity<Verson_Code_Result>>bindToLifecycle()))
                .subscribe(baseObserver);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) { //按下的如果是BACK
            if ((System.currentTimeMillis() - singTime) > 2000) {
                if (AppInfoUtil.getForceUpdate()) {
                    ActivityCollector.finishAll();
                } else {
                    ToastUtil.showShort(MainActivity.this, "再按一次退出");
                    singTime = System.currentTimeMillis();
                }
            } else {
                ActivityCollector.finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void checkUpdate(String version, String updatesMassages, String force) {

        if (AppInfoUtil.whetherToUpdate(version)) {//有更新
            LUtils.e("------更新----", "" + AppInfoUtil.whetherToUpdate(version));
            AppInfoUtil.setForceUpdate(force);
            String prompt = updatesMassages;//更新提示内容
            DialogUtils.showDialogVersion(this, true, prompt, new DialogUtils.OnButtonEventListener1() {
                @Override
                public void onConfirm() {
//                    AppInfoUtil.intit_getClick(getApplication());
                    AppInfoUtil.init360(getApplication(), AppInfoUtil.getAppPkgName());
                    Util.getInstance().setCJ(MainActivity.this);
                }

                @Override
                public void onCancel(AlertDialog dialog) {
                    if (AppInfoUtil.getForceUpdate()) {
                        ActivityCollector.finishAll();
                    } else {  //
                        dialog.dismiss();
                    }
                    Util.getInstance().setCJ(MainActivity.this);
                }

            });
        } else {
            LUtils.e("------没更新----");
        }
    }

    public void next(View view) {
        num = num + 1;
        if (highLight.isShowing() && highLight.isNext())//如果开启next模式
        {
            if (num < 6) {
                highLight.next();
            } else {
                highLight.remove();
            }

        } else {
            highLight.remove();
        }
    }

    public void skip(View view) {
        highLight.remove();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(oneFragment);
        transaction.remove(tooFragment);
        transaction.remove(threeFragment);
        transaction.remove(foreFragment);
        transaction.commitAllowingStateLoss();
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * 关闭dialog进度条
     */
    public void DismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }


}
