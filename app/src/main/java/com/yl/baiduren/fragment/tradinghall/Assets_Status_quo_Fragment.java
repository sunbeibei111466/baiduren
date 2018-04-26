package com.yl.baiduren.fragment.tradinghall;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.tradinghall.AssignmentActivity;
import com.yl.baiduren.adapter.tradinghall.AssetsStatusExpandAdapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.ICallBack;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.AsstesEntity;
import com.yl.baiduren.entity.AsstesStatusProject;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyExpandListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.assetsDOList;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.type;


/**
 * 债权转让 --资产现状
 */
public class Assets_Status_quo_Fragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    private EditText et_assets_qiyeName, et_assets_name, et_assets_jiazhi;
    private RadioGroup et_assets_scale;
    private RadioButton rb_assets_shi;
    private Button bt_assets_complete;
    private ImageView iv_add_new_assets;
    private MyExpandListView assets_list;
    //产品名数据源
    private List<AsstesStatusProject> asstesStatusProjectList = null;
    //资产信息数据源
    private List<AsstesEntity> asstesEntityList = null;
    private final int COMPLETE = 1;
    private String assterType = "固定资产";
    private AssetsStatusExpandAdapter adapter;
    private Button but_asstes;
    private LinearLayout ll_assets_parent;

    @Override
    public int loadWindowLayout() {
        return R.layout.assets_status_quo_fragment;
    }

    @Override
    public void initViews(View rootView) {
        asstesStatusProjectList = new ArrayList<>();
        et_assets_qiyeName = rootView.findViewById(R.id.et_assets_qiyeName);//产业名称
        et_assets_scale = rootView.findViewById(R.id.et_assets_scale);//RadioGroup
        et_assets_scale.setOnCheckedChangeListener(this);
        rb_assets_shi = rootView.findViewById(R.id.rb_assets_shi);//固定资产
        RadioButton rb_assets_fou = rootView.findViewById(R.id.rb_assets_fou);
        et_assets_name = rootView.findViewById(R.id.et_assets_name);//资产名称
        et_assets_jiazhi = rootView.findViewById(R.id.et_assets_jiazhi);//资产价值
        bt_assets_complete = rootView.findViewById(R.id.bt_assets_complete);//保存
        iv_add_new_assets = rootView.findViewById(R.id.iv_add_new_assets);//添加资产
        assets_list = rootView.findViewById(R.id.assets_list);//容器
        ll_assets_parent = rootView.findViewById(R.id.ll_assets_parent);
        but_asstes = rootView.findViewById(R.id.but_asstes);
        adapter = new AssetsStatusExpandAdapter(getActivity());
        initEditTextStyle();
        initListener();
        if (type != 0) {//表示进入详情
            if (type == 1) {
                //从债权大厅进入，不可编辑
                setEnabled();
            }
            layoutUpdata();//从我的债权进入，可以编辑
        }
        initAdapter();

    }

    private void setEnabled() {
        adapter.setVisibility(true);
        but_asstes.setVisibility(View.GONE);
        ll_assets_parent.setVisibility(View.GONE);
    }

    private void layoutUpdata() {
        if (assetsDOList != null) {

            for (int i = 0; i < assetsDOList.size(); i++) {
                AsstesStatusProject asstesStatusProject = new AsstesStatusProject();
                asstesStatusProject.setLndustryName(assetsDOList.get(i).getIndustryName());
                List<AsstesEntity> asstesEntityList = new ArrayList<>();
                for (int j = 0; j < assetsDOList.get(i).getAssets().size(); j++) {
                    AsstesEntity asstesEntity = new AsstesEntity();
                    asstesEntity.setAsstesName(assetsDOList.get(i).getAssets().get(j).get("assetsName"));
                    asstesEntity.setAsstesValue(assetsDOList.get(i).getAssets().get(j).get("amount"));
                    String type = assetsDOList.get(i).getAssets().get(j).get("type");
                    String str = "固定资产";
                    if (type.equals("2")) {
                        str = "技术资产";
                    }
                    asstesEntity.setAsstesType(str);
                    asstesEntityList.add(asstesEntity);
                }
                asstesStatusProject.setAsstesEntityList(asstesEntityList);
                asstesStatusProjectList.add(asstesStatusProject);
            }
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_assets_complete://完成
                    addAssets();
                    break;
                case R.id.iv_add_new_assets://添加资产
                    clearEditview();
                    break;
                case R.id.but_asstes:
                    getHttpData();
                    break;
            }
        }
    };


    /**
     * 点击完成
     */
    private void addAssets() {
        //判断项目名称是否存在
        if (isCanAddProject()) {
            if (isCanAddAssets()) {
                if (et_assets_qiyeName.isEnabled()) {
                    createFirstInfo();
                    //按钮变成添加股东
                    int ADD = 2;
                    updateBtnStyle(ADD);
                    addNewShareholder();
                    updateLayoutState(false);
                    for (int i = 0; i < asstesStatusProjectList.size(); i++) {
                        LUtils.e("--项目名---" + asstesStatusProjectList.get(i).getLndustryName());
                        for (int j = 0; j < asstesStatusProjectList.get(i).getAsstesEntityList().size(); j++) {
                            LUtils.e("--股东名---" + asstesStatusProjectList.get(i).getAsstesEntityList().get(j).getAsstesName());
                            LUtils.e("--股东金额---" + asstesStatusProjectList.get(i).getAsstesEntityList().get(j).getAsstesType());
                            LUtils.e("--股东占比---" + asstesStatusProjectList.get(i).getAsstesEntityList().get(j).getAsstesValue());
                        }
                    }
                } else {
                    addNewShareholder();
                    updateLayoutState(false);
                    for (int i = 0; i < asstesStatusProjectList.size(); i++) {
                        LUtils.e("--项目名---" + asstesStatusProjectList.get(i).getLndustryName());
                        for (int j = 0; j < asstesStatusProjectList.get(i).getAsstesEntityList().size(); j++) {
                            LUtils.e("--股东名---" + asstesStatusProjectList.get(i).getAsstesEntityList().get(j).getAsstesName());
                            LUtils.e("--股东金额---" + asstesStatusProjectList.get(i).getAsstesEntityList().get(j).getAsstesType());
                            LUtils.e("--股东占比---" + asstesStatusProjectList.get(i).getAsstesEntityList().get(j).getAsstesValue());
                        }
                    }
                }
            } else {
                ToastUtil.showShort(getActivity(), "请补全股东信息");
            }
        } else {
            ToastUtil.showShort(getActivity(), "请填写项目名称");
        }
        updateAdapter();
    }


    public void getHttpData() {

        if (AssignmentActivity.claimsId != null) {
            List<AssignmentEntity.AssetsDO> assetsDOList = new ArrayList<>();
            List<AsstesStatusProject> asstesStatusProjects = adapter.getAsstesStatusProjectsList();
            if (asstesStatusProjects != null && asstesStatusProjects.size() != 0) {
                for (int i = 0; i < asstesStatusProjects.size(); i++) {
                    AssignmentEntity.AssetsDO assetsDO = new AssignmentEntity.AssetsDO();
                    List<Map<String, String>> mapList = new ArrayList<>();
                    assetsDO.setIndustryName(asstesStatusProjects.get(i).getLndustryName());
                    for (int j = 0; j < asstesStatusProjects.get(i).getAsstesEntityList().size(); j++) {
                        HashMap<String, String> map = new HashMap<>();
                        if (asstesStatusProjects.get(i).getAsstesEntityList().get(j).getAsstesType().equals("固定资产")) {
                            map.put("type", "1");
                        } else {
                            map.put("type", "2");
                        }
                        map.put("assetsName", asstesStatusProjects.get(i).getAsstesEntityList().get(j).getAsstesName());
                        map.put("amount", asstesStatusProjects.get(i).getAsstesEntityList().get(j).getAsstesValue());
                        mapList.add(map);
                    }
                    assetsDO.setAssets(mapList);
                    assetsDOList.add(assetsDO);
                }

            } else {
                ToastUtil.showShort(getActivity(), "请填写数据");
                return;
            }

            AssignmentEntity assignmentEntity = new AssignmentEntity(DataWarehouse.getBaseRequest(getActivity()));
            assignmentEntity.setSteps(4);
            assignmentEntity.setClaimsId(AssignmentActivity.claimsId);
            assignmentEntity.setSaveOrUpdate(1);
            assignmentEntity.setAssetsDOS(assetsDOList);
            getHttp(assignmentEntity);
        } else {
            ToastUtil.showShort(getActivity(), "请先填写基本信息，在填写股分结构数据");
        }

    }

    private void getHttp(AssignmentEntity assignmentEntity) {
        String json = Util.toJson(assignmentEntity);//转成json
        LUtils.e("---json--", json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        LUtils.e("---signature---" + signature);
        RetrofitHelper.getInstance(getActivity()).getServer()
                .getSaveOrUpdate(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(getActivity()) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            LUtils.e("----data-----", data);
                            ToastUtil.showShort(getActivity(),"资产现状保存成功");
                            AssignmentActivity.claimsId = Long.valueOf(data);//债权Id
                        }
                    }
                });
    }

    /**
     * 初始化数据
     */
    private void initAdapter() {

        adapter.setAsstesStatusProjectsList(asstesStatusProjectList);
        assets_list.setAdapter(adapter);
        adapter.setICallBack(new ICallBack() {
            @Override
            public void updateData() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void updateAdapter() {
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    private void updateLayoutState(boolean b) {
        et_assets_qiyeName.setEnabled(b);//产业名称
//        //清空股东信息
        et_assets_name.setText("");
        et_assets_jiazhi.setText("");
        rb_assets_shi.setChecked(true);

    }

    private void addNewShareholder() {
        AsstesEntity asstesEntity = new AsstesEntity();
        asstesEntity.setAsstesType(assterType);
        asstesEntity.setAsstesName(et_assets_name.getText().toString().trim());//资产name
        asstesEntity.setAsstesValue(et_assets_jiazhi.getText().toString().trim());//资产价值
        asstesEntityList.add(asstesEntity);
    }

    private void updateBtnStyle(int type) {
        if (type == COMPLETE) {//变为完成按钮
            bt_assets_complete.setBackgroundDrawable(getResources().getDrawable(R.drawable.debt_button_back));
            bt_assets_complete.setTextColor(getResources().getColor(R.color.blue));
            bt_assets_complete.setText("完成");
        } else {//变成添加股东按钮
            bt_assets_complete.setBackgroundDrawable(getResources().getDrawable(R.drawable.debt_button_back));
            bt_assets_complete.setTextColor(getResources().getColor(R.color.blue));
            bt_assets_complete.setText("添加资产");
        }
    }

    private void createFirstInfo() {
        asstesEntityList = new ArrayList<>();//资产信息
        //创建产业信息
        AsstesStatusProject asstesStatusProject = new AsstesStatusProject();
        asstesStatusProject.setLndustryName(et_assets_qiyeName.getText().toString().trim());//产业名称
        //添加第n个资产信息
        asstesStatusProject.setAsstesEntityList(asstesEntityList);
        asstesStatusProjectList.add(asstesStatusProject);
    }


    /**
     * 清空四个输入框的内容
     */
    private void clearEditview() {
        updateBtnStyle(COMPLETE);
        et_assets_qiyeName.setEnabled(true);
        et_assets_qiyeName.setText("");
        updateLayoutState(true);
    }

    /**
     * 是否可添加产业
     *
     * @return
     */
    private boolean isCanAddProject() {
        boolean is;
        //产业名称不为空时
        is = !et_assets_qiyeName.getText().toString().equals("");
        return is;
    }

    /**
     * 是否可添加资产
     */
    private boolean isCanAddAssets() {
        boolean is;
        //股东姓名  投资金额 股份占比
        is = !et_assets_name.getText().toString().equals("") &&
                !et_assets_jiazhi.getText().toString().equals("");
        return is;
    }


    private void initEditTextStyle() {
        setHintTextSize("请输入企业旗下产业名称", et_assets_qiyeName);
        setHintTextSize("请输入资产名称", et_assets_name);
        setHintTextSize("请输入资产价值", et_assets_jiazhi);
    }

    private void initListener() {
        bt_assets_complete.setOnClickListener(onClickListener);
        iv_add_new_assets.setOnClickListener(onClickListener);
        but_asstes.setOnClickListener(onClickListener);
    }

    /**
     * 设置editText的提示文字大小
     *
     * @param hintStr
     * @param editText
     */
    private void setHintTextSize(String hintStr, EditText editText) {
        SpannableString ss = new SpannableString(hintStr);
        //设置字体颜色
        editText.setHintTextColor(getResources().getColor(R.color.light_bai));
        //设置字体大小（绝对值,单位：像素）
        //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素
        ss.setSpan(new AbsoluteSizeSpan(13, true), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == et_assets_scale) {
            if (checkedId == R.id.rb_assets_shi) {//固定资产
                assterType = "固定资产";
            } else {//技术资产
                assterType = "技术资产";
            }
        }
    }


}
