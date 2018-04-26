package com.yl.baiduren.fragment.tradinghall;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.tradinghall.AssignmentActivity;
import com.yl.baiduren.adapter.tradinghall.Share_structure_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.sharesDOList;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.type;


/**
 * 债权转让 --股分结构
 */
public class Share_Structure_Fragment extends BaseFragment implements View.OnClickListener {

    private Button bt_share_complete, bt_share;
    private EditText et_share_name, et_share_scale;
    private LinearLayout ll_share_parent;
    private MyRecyclerView rv_share_dataList;
    private List<AssignmentEntity.SharesDO> dataList;
    private Share_structure_Adapter share_structure_adapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.share_structure_fragment;
    }

    @Override
    public void initViews(View rootView) {
        dataList = new ArrayList<>();
        et_share_name = rootView.findViewById(R.id.et_share_name);//股东姓名
        et_share_scale = rootView.findViewById(R.id.et_share_scale);//股份占比
        bt_share_complete = rootView.findViewById(R.id.bt_share_complete);//完成按钮
        bt_share_complete.setOnClickListener(this);
        bt_share = rootView.findViewById(R.id.bt_share);//提交
        bt_share.setOnClickListener(this);
        ll_share_parent=rootView.findViewById(R.id.ll_share_parent);
        rv_share_dataList = rootView.findViewById(R.id.rv_share_dataList);
        rv_share_dataList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_share_dataList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        share_structure_adapter = new Share_structure_Adapter(getActivity());
        if (type != 0) {//表示进入详情
            if(type==1){
                //从债权大厅进入，不可编辑,删除
                setEnabled();
            }
            layoutUpdata();//从我的债权进入，可以编辑
        }
    }

    private void setEnabled() {
        share_structure_adapter.setVisibility(true);
        bt_share.setVisibility(View.GONE);
        ll_share_parent.setVisibility(View.GONE);
    }

    private void layoutUpdata() {
        if (sharesDOList != null) {
            dataList.addAll(sharesDOList);
            share_structure_adapter.setStringList(dataList);
            rv_share_dataList.setAdapter(share_structure_adapter);
        }
    }

    private void complete() {
        if (TextUtils.isEmpty(et_share_name.getText().toString().trim())) {
            ToastUtil.showShort(getActivity(), "请输入股东真实姓名");
            return;
        }
        String share_scale = et_share_scale.getText().toString().trim();
        if (TextUtils.isEmpty(share_scale)) {
            ToastUtil.showShort(getActivity(), "请输入股东占比");
            return;
        }
        Double d = Double.valueOf(share_scale);
        LUtils.e("浮点数的大小"+d);


        if (d==0||d>100){
            ToastUtil.showShort(getActivity(),"股东占比不能为零或者不能为100%");
            return;
        }

        String name = et_share_name.getText().toString().trim();
        String scale = et_share_scale.getText().toString().trim();
        AssignmentEntity.SharesDO sharesDO = new AssignmentEntity.SharesDO();
        sharesDO.setShareholders(name);
        sharesDO.setProportion(scale);
        dataList.add(sharesDO);
        share_structure_adapter.setStringList(dataList);
        rv_share_dataList.setAdapter(share_structure_adapter);
        share_structure_adapter.notifyDataSetChanged();
        if (dataList.size() != 0) {
            bt_share_complete.setText("添加股东");
            et_share_name.setText("");
            et_share_scale.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bt_share_complete) {
            complete();
        } else if (v == bt_share) {//保存
            seva();
        }
    }

    private void seva() {
        if (UserInfoUtils.IsLogin(getActivity())) {
            if (AssignmentActivity.claimsId !=-1) {
                AssignmentEntity assignmentEntity = new AssignmentEntity(DataWarehouse.getBaseRequest(getActivity()));
                assignmentEntity.setSteps(2);
                assignmentEntity.setClaimsId(AssignmentActivity.claimsId);
                assignmentEntity.setSaveOrUpdate(1);
                if (type != 0) {
                    assignmentEntity.setSaveOrUpdate(2);//修改数据
                } else {
                    assignmentEntity.setSaveOrUpdate(1);//新增
                }
                if (share_structure_adapter.getSharesDOList() != null && share_structure_adapter.getSharesDOList().size() != 0) {
                    assignmentEntity.setSharesDOS(share_structure_adapter.getSharesDOList());
                } else {
                    ToastUtil.showShort(getActivity(), "请添加数据");
                    return;
                }
                getHttpSeva(assignmentEntity);
            } else {
                ToastUtil.showShort(getActivity(), "请先填写基本信息，在填写股分结构数据");
            }

        }
    }

    private void getHttpSeva(AssignmentEntity assignmentEntity) {
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
                            ToastUtil.showShort(getActivity(),"股份结构保存成功");
                            AssignmentActivity.claimsId = Long.valueOf(data);
                        }
                    }
                });
    }
}
