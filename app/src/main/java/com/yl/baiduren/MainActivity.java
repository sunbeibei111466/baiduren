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

    private List<LinearLayout> linearLayoutList;
    private List<TextView> textList;
    private List<ImageView> imageViewList;
    private Dialog dialog;
    public static LinearLayout ll_parent_gx;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        initView();

    }

    private void initView() {

        fragmentManager = getSupportFragmentManager();
        id_btn_home_oneyuan = findViewById(R.id.id_btn_home_oneyuan);
        id_btn_home_oneyuan.setOnClickListener(this);//需求对对碰
        ll_parent_gx = findViewById(R.id.ll_parent_gx);
        ll_parent_gx.setOnClickListener(this);//需求对对碰 父控件

        //父布局
        ll_One = findViewById(R.id.ll_one);
        ll_One.setOnClickListener(this);
        ll_Too = findViewById(R.id.ll_too);
        ll_Too.setOnClickListener(this);
        ll_Three = findViewById(R.id.ll_three);
        ll_Three.setOnClickListener(this);
        ll_Fore = findViewById(R.id.ll_fore);
        ll_Fore.setOnClickListener(this);


        linearLayoutList = new ArrayList<>();
        linearLayoutList.add(ll_One);
        linearLayoutList.add(ll_Too);
        linearLayoutList.add(ll_Three);
        linearLayoutList.add(ll_Fore);

        //图片
        ImageView iv_home = findViewById(R.id.iv_home);
        ImageView iv_too = findViewById(R.id.iv_too);
        ImageView iv_three = findViewById(R.id.iv_three);
        ImageView iv_fore = findViewById(R.id.iv_fore);
        iv_home.setSelected(true);
        imageViewList = new ArrayList<>();
        imageViewList.add(iv_home);
        imageViewList.add(iv_too);
        imageViewList.add(iv_three);
        imageViewList.add(iv_fore);


        //字体
        TextView tv_home = findViewById(R.id.tv_home);
        TextView tv_jzlc = findViewById(R.id.tv_jzlc);
        TextView tv_zsr = findViewById(R.id.tv_zsr);
        TextView tv_wo = findViewById(R.id.tv_wo);
        textList = new ArrayList<>();
        textList.add(tv_home);
        textList.add(tv_jzlc);
        textList.add(tv_zsr);
        textList.add(tv_wo);

        currMenu = BOTTOM_MENU_ONE;
    }


    @Override
    protected void onResume() {
        super.onResume();


        if (this.currMenu == BOTTOM_MENU_ONE) {// 首页
            ll_One.performClick();
            LUtils.e("----onResume---首页------");
        } else if (this.currMenu == BOTTOM_MENU_TOO) {//
            ll_Too.performClick();
            LUtils.e("----onResume---解债流程------");
        } else if (this.currMenu == BOTTOM_MENU_THREE) {//
            ll_Three.performClick();
            LUtils.e("----onResume---债事人------");
        } else if (this.currMenu == BOTTOM_MENU_FORE) {// 我的
            ll_Fore.performClick();
            LUtils.e("----onResume---我的------");
        } else if (currMenu == BOTTOM_MENU_XUQIU) {//需求对对碰
            id_btn_home_oneyuan.performClick();
            LUtils.e("----onResume---需求对对碰------");
        }

        getVersion_Code();
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

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.ll_one) {
            ll_One.setTag(true);
            currMenu = BOTTOM_MENU_ONE;
            changeFragment();
            setLinearLayoutListColor();

        } else if (id == R.id.ll_too) {
            LUtils.e("------点击-----解债流程-------");
            ll_Too.setTag(true);
            currMenu = BOTTOM_MENU_TOO;
            changeFragment();
            setLinearLayoutListColor();

        } else if (id == R.id.ll_three) {
            LUtils.e("------点击-----债事人-------");
            ll_Three.setTag(true);
            currMenu = BOTTOM_MENU_THREE;
            changeFragment();
            setLinearLayoutListColor();

        } else if (id == R.id.ll_fore) {
            LUtils.e("------点击-----我的-------");
            ll_Fore.setTag(true);
            currMenu = BOTTOM_MENU_FORE;
            changeFragment();
            setLinearLayoutListColor();

        } else if (id == R.id.id_btn_home_oneyuan) {//需求对对碰
            LUtils.e("------点击-----需求对对碰-------");
            currMenu = BOTTOM_MENU_XUQIU;
            changeFragment();
            for (int i = 0; i < linearLayoutList.size(); i++) {
                textList.get(i).setTextColor(getResources().getColor(R.color.light_hei));
                imageViewList.get(i).setSelected(false);
            }
        }

    }

    private void setLinearLayoutListColor() {

        for (int i = 0; i < linearLayoutList.size(); i++) {
            if (linearLayoutList.get(i).getTag() != null && (Boolean) (linearLayoutList.get(i).getTag())) {
                textList.get(i).setTextColor(getResources().getColor(R.color.blue));
                linearLayoutList.get(i).setTag(false);
                imageViewList.get(i).setSelected(true);
            } else {
                textList.get(i).setTextColor(getResources().getColor(R.color.light_hei));
                imageViewList.get(i).setSelected(false);
            }
        }
    }

    /**
     * 切换fragmnet
     */
    private void changeFragment() {
        FragmentTransaction trans = fragmentManager.beginTransaction();

        if (currMenu == BOTTOM_MENU_ONE) {//首页Fragment

            if (oneFragment == null) {
                oneFragment = new OneFragment();
                trans.add(R.id.fl_contentLayout, oneFragment);
            } else {
                trans.show(oneFragment);
            }
            //隐藏 Fragment
            if (tooFragment != null) {
                trans.hide(tooFragment);
            }
            //隐藏 Fragment
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

        } else if (currMenu == BOTTOM_MENU_TOO) {//

            if (tooFragment == null) {
                tooFragment = new TooFragment();
                trans.add(R.id.fl_contentLayout, tooFragment);
            } else {
                trans.show(tooFragment);
            }


            //隐藏 Fragment
            if (oneFragment != null) {
                trans.hide(oneFragment);
            }

            //隐藏 Fragment
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
        } else if (currMenu == BOTTOM_MENU_THREE) {//债事人管理

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
            //隐藏首页Fragment
            if (oneFragment != null) {
                trans.hide(oneFragment);
            }
            //隐藏债事管理Fragment
            if (tooFragment != null) {
                trans.hide(tooFragment);
            }
            //隐藏我的Fragment
            if (this.foreFragment != null) {
                trans.hide(foreFragment);
            }
            if (demandFragment != null) {
                trans.hide(demandFragment);
            }
        } else if (currMenu == BOTTOM_MENU_FORE) {

            //显示我的页
            if (foreFragment == null) {
                foreFragment = new ForeFragment();
                trans.add(R.id.fl_contentLayout, foreFragment);
            } else {
                trans.show(foreFragment);
            }

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

            if (demandFragment != null) {
                trans.hide(demandFragment);
            }
        } else if (currMenu == BOTTOM_MENU_XUQIU) {//需求对对碰
            LUtils.e("-------需求对对碰---fragment------");
            if (demandFragment == null) {
                demandFragment = new DemandFragment();
                trans.add(R.id.fl_contentLayout, demandFragment);
            } else {
                trans.show(demandFragment);
            }

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
        }
        trans.commitAllowingStateLoss();
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
                    AppInfoUtil.intit_getClick(getApplication());
                }

                @Override
                public void onCancel(AlertDialog dialog) {
                    if (AppInfoUtil.getForceUpdate()) {
                        ActivityCollector.finishAll();
                    } else {  //
                        dialog.dismiss();
                    }
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
