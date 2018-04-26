package com.yl.baiduren.activity.tradinghall;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.yl.baiduren.R;
import com.yl.baiduren.adapter.AssignmentPagerAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AssignmentDetailBody;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
import com.yl.baiduren.fragment.tradinghall.Assets_Status_quo_Fragment;
import com.yl.baiduren.fragment.tradinghall.Creditor_rights_Information_Fragment;
import com.yl.baiduren.fragment.tradinghall.Investment_situation_Fragment;
import com.yl.baiduren.fragment.tradinghall.Liabilities_Status_quo_Fragment;
import com.yl.baiduren.fragment.tradinghall.Operation_Condition_Fragment;
import com.yl.baiduren.fragment.tradinghall.Share_Structure_Fragment;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.NScrollViewpager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * 债权转让
 */
public class AssignmentActivity extends BaseActivity implements View.OnClickListener {


    private NScrollViewpager viewpager;
    private FragmentManager mFragmentManager;
    private Creditor_rights_Information_Fragment oneFragment;//基本信息
    private TextView text1, text2, text3, text4, text5, text6;
    private List<TextView> textList;
    private int indexPage = 0;//默认在第一页
    public static int type;
    public static Long claimsId =-1l;//债权Id
    public static AssignmentEntity.BaseClaimsVo baseClaimsVo = null;//基本信息数据
    public static List<AssignmentEntity.SharesDO> sharesDOList = null;//股份
    public static List<AssignmentEntity.InvestDO> investDOList = null;//投资
    public static List<AssignmentEntity.AssetsDO> assetsDOList = null;//资产
    public static List<AssignmentEntity.LiabilitiesDO> liabilitiesDOS = null;//负债
    public static List<AssignmentEntity.ManageDO> manageDOList = null;//经营

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_assignment;
    }

    @Override
    public void initViews() {
        type = getIntent().getIntExtra("type", 0);
        claimsId = getIntent().getLongExtra("claimsId", -1);
        initView();

        if (claimsId == -1) {//直接进到新增债权页
            initData();
            LUtils.e("--新增--", "---");
        }else{
            LUtils.e("--编辑--", "---");
            initServeData();
        }
        initIndexPage();
    }

    private void initServeData() {
        if (claimsId != -1) {
            if (UserInfoUtils.IsLogin(this)) {
                AssignmentDetailBody assignmentDetailBody = new AssignmentDetailBody(DataWarehouse.getBaseRequest(this));
                assignmentDetailBody.setClaimsId(claimsId);
                String json = Util.toJson(assignmentDetailBody);//转成json
                LUtils.e("---json--", json);
                String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
                LUtils.e("---signature---" + signature);
                RetrofitHelper.getInstance(this).getServer()
                        .getClaimsDetail(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                        .compose(compose(this.<BaseEntity<AssignmentEntity>>bindToLifecycle()))
                        .subscribe(new BaseObserver<AssignmentEntity>(this) {
                            @Override
                            protected void onSuccees(String code, AssignmentEntity data, BaseRequest baseResponse) throws Exception {
                                if (code.equals("1")) {
                                    baseClaimsVo = data.getBaseClaimsVo();//基本信息数据
                                    sharesDOList = data.getSharesDOS();//股份
                                    investDOList = data.getInvestDOS();//投资
                                    assetsDOList = data.getAssetsDOS();//资产
                                    liabilitiesDOS = data.getLiabilitiesDOS();//负债
                                    manageDOList = data.getManageDOS();//经营
                                    LUtils.e("----data-----", data.getBaseClaimsVo().getEnterpriseName());
                                    initData();
                                }
                            }
                        });
            }
        }

    }


    private void initData() {

        mFragmentManager = getSupportFragmentManager();
        oneFragment = new Creditor_rights_Information_Fragment();
        Share_Structure_Fragment tooFragment = new Share_Structure_Fragment();
        Investment_situation_Fragment threeFragment = new Investment_situation_Fragment();
        Assets_Status_quo_Fragment foreFragment = new Assets_Status_quo_Fragment();
        Liabilities_Status_quo_Fragment fiveFragment = new Liabilities_Status_quo_Fragment();
        Operation_Condition_Fragment sixFragment = new Operation_Condition_Fragment();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(oneFragment);
        fragmentList.add(tooFragment);
        fragmentList.add(threeFragment);
        fragmentList.add(foreFragment);
        fragmentList.add(fiveFragment);
        fragmentList.add(sixFragment);

        AssignmentPagerAdapter mAdapter = new AssignmentPagerAdapter(mFragmentManager, fragmentList);
        viewpager.setScanScroll(false);
        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(0, false);
    }

    private void initView() {
        viewpager = (NScrollViewpager) findViewById(R.id.assignment_pager);

        text1 = (TextView) findViewById(R.id.assignmet_data);
        text1.setOnClickListener(listener);
        text2 = (TextView) findViewById(R.id.assignmet_structure);
        text2.setOnClickListener(listener);
        text3 = (TextView) findViewById(R.id.assignmet_situation);
        text3.setOnClickListener(listener);
        text4 = (TextView) findViewById(R.id.assignmet_asset);
        text4.setOnClickListener(listener);
        text5 = (TextView) findViewById(R.id.assignmet_debt);
        text5.setOnClickListener(listener);
        text6 = (TextView) findViewById(R.id.assignmet_business);
        text6.setOnClickListener(listener);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(listener);

        textList = new ArrayList<>();
        textList.add(text1);
        textList.add(text2);
        textList.add(text3);
        textList.add(text4);
        textList.add(text5);
        textList.add(text6);

        indexPage = getIntent().getIntExtra("vul", -1);
    }

    private void initIndexPage() {
        if (indexPage == 0) {
            text1.performClick();
        } else if (indexPage == 1) {
            text2.performClick();
        } else if (indexPage == 2) {
            text3.performClick();
        } else if (indexPage == 3) {
            text4.performClick();
        } else if (indexPage == 4) {
            text5.performClick();
        } else if (indexPage == 5) {
            text6.performClick();
        }
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.assignmet_data || indexPage == 0) {
            text1.setTag(true);
            setTextViewColor();
            viewpager.setCurrentItem(0, false);
        } else if (id == R.id.assignmet_structure || indexPage == 1) {
            text2.setTag(true);
            setTextViewColor();
            viewpager.setCurrentItem(1, false);
        } else if (id == R.id.assignmet_situation || indexPage == 2) {
            text3.setTag(true);
            setTextViewColor();
            viewpager.setCurrentItem(2, false);
        } else if (id == R.id.assignmet_asset || indexPage == 3) {
            text4.setTag(true);
            setTextViewColor();
            viewpager.setCurrentItem(3, false);
        } else if (id == R.id.assignmet_debt || indexPage == 4) {
            text5.setTag(true);
            setTextViewColor();
            viewpager.setCurrentItem(4, false);
        } else if (id == R.id.assignmet_business || indexPage == 5) {
            text6.setTag(true);
            setTextViewColor();
            viewpager.setCurrentItem(5, false);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.assignmet_data:
                    text1.setTag(true);
                    setTextViewColor();
                    viewpager.setCurrentItem(0, false);
                    break;
                case R.id.assignmet_structure:
                    text2.setTag(true);
                    setTextViewColor();
                    viewpager.setCurrentItem(1, false);
                    break;
                case R.id.assignmet_situation:
                    text3.setTag(true);
                    setTextViewColor();
                    viewpager.setCurrentItem(2, false);
                    break;
                case R.id.assignmet_asset:
                    text4.setTag(true);
                    setTextViewColor();
                    viewpager.setCurrentItem(3, false);
                    break;
                case R.id.assignmet_debt:
                    text5.setTag(true);
                    setTextViewColor();
                    viewpager.setCurrentItem(4, false);
                    break;
                case R.id.assignmet_business:
                    text6.setTag(true);
                    setTextViewColor();
                    viewpager.setCurrentItem(5, false);
                    break;
                case  R.id.iv_back:
                    finish();
                    break;
            }
        }
    };




    private void setTextViewColor() {
        for (int i = 0; i < textList.size(); i++) {
            if (textList.get(i).getTag() != null && (Boolean) (textList.get(i).getTag())) {
                textList.get(i).setTextColor(getResources().getColor(R.color.blue));
                textList.get(i).setTag(false);
                textList.get(i).setBackgroundColor(getResources().getColor(R.color.white));

            } else {
                textList.get(i).setTextColor(getResources().getColor(R.color.creditor_rights));
                textList.get(i).setBackgroundColor(getResources().getColor(R.color.f5f5f5));
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseClaimsVo = null;//基本信息数据
        sharesDOList = null;//股份
        investDOList = null;//投资
        assetsDOList = null;//资产
        liabilitiesDOS = null;//负债
        manageDOList = null;
        claimsId=-1l;
        type=0;
    }
}
