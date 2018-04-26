package com.yl.baiduren.fragment.tradinghall;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.Operation_Condition_Adapter;
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

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.claimsId;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.manageDOList;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.type;


/**
 * 债权转让 --经营状况
 */
public class Operation_Condition_Fragment extends BaseFragment implements View.OnClickListener {


    private MyRecyclerView recyclerView;
    private EditText et_operation_named;
    private RadioGroup et_share_scale;
    private EditText et_operation_jiazhi;
    private Button bt_operation_complete, bt_operation;
    private ArrayList<AssignmentEntity.ManageDO> list = new ArrayList<>();
    private Operation_Condition_Adapter adapter;
    private String isyes="1";
    private LinearLayout ll_operation_parent;

    @Override
    public int loadWindowLayout() {
        return R.layout.operation_condition_fragment;
    }

    @Override
    public void initViews(View rootView) {

        recyclerView = rootView.findViewById(R.id.operaton_dataList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        et_operation_named = rootView.findViewById(R.id.et_operation_name);//产业名称
        et_share_scale = rootView.findViewById(R.id.et_share_scale);//盈亏状况
        et_share_scale.setOnCheckedChangeListener(changeListener);
        RadioButton buttonyes = rootView.findViewById(R.id.rb_operation_shi);
        RadioButton btton_no = rootView.findViewById(R.id.rb_operation_fou);
        et_operation_jiazhi = rootView.findViewById(R.id.et_operation_jiazhi);//具体价值
        bt_operation_complete = rootView.findViewById(R.id.bt_operation_complete);//完成
        bt_operation_complete.setOnClickListener(this);
        bt_operation = rootView.findViewById(R.id.bt_operation);
        bt_operation.setOnClickListener(this);
        ll_operation_parent=rootView.findViewById(R.id.ll_operation_parent);
        adapter = new Operation_Condition_Adapter(getActivity());

        if (type != 0) {//表示进入详情
            if(type==1){
                //从债权大厅进入，不可编辑
                setEnabled();
            }
            layoutUpdata();//从我的债权进入，可以编辑
        }
    }

    private void setEnabled() {
        adapter.setVisibility(true);
        bt_operation.setVisibility(View.GONE);
        ll_operation_parent.setVisibility(View.GONE);
    }

    private void layoutUpdata() {
        if(manageDOList!=null){
            list.addAll(manageDOList);
            adapter.setData(list);
            recyclerView.setAdapter(adapter);
        }
    }

    /**
     * 保存已填信息
     */
    private void save() {
        String name = et_operation_named.getText().toString().trim();
        String vaul = et_operation_jiazhi.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(getActivity(), "请输入产业名称");
            return;
        }
        if (TextUtils.isEmpty(vaul)) {
            ToastUtil.showShort(getActivity(), "请输入具体价值");
            return;
        }
        AssignmentEntity.ManageDO manageDO = new AssignmentEntity.ManageDO();
        manageDO.setIndustry(name);
        manageDO.setIsProfit(isyes);
        manageDO.setAmount(Long.valueOf(vaul));
        list.add(manageDO);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list.size() != 0) {
            et_operation_named.setText("");
            et_operation_jiazhi.setText("");
            et_share_scale.clearCheck();
        }
    }


    @Override
    public void onClick(View v) {
        if (v == bt_operation_complete) {//保存
            save();
        } else if (v == bt_operation) {//提交
            getHttpData();
        }
    }

    public void getHttpData() {
        if (claimsId!=-1) {
            if (UserInfoUtils.IsLogin(getActivity())) {
                AssignmentEntity assignmentEntity = new AssignmentEntity(DataWarehouse.getBaseRequest(getActivity()));
                assignmentEntity.setSteps(6);
                assignmentEntity.setClaimsId(claimsId);
                if (type != 0) {
                    assignmentEntity.setSaveOrUpdate(2);//修改数据
                } else {
                    assignmentEntity.setSaveOrUpdate(1);//新增
                }
                if (adapter.getList()!=null&& adapter.getList().size() != 0) {
                    assignmentEntity.setManageDOS(adapter.getList());
                } else {
                    ToastUtil.showShort(getActivity(), "请填写数据");
                }
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
                                    ToastUtil.showShort(getActivity(),"经营状况保存成功");
                                    LUtils.e("----data-----", data);
                                    claimsId = Long.valueOf(data);//债权Id
                                }
                            }
                        });
            }
        }else {
            ToastUtil.showShort(getActivity(), "请先填写基本信息，在填写股分结构数据");
        }
    }

    private RadioGroup.OnCheckedChangeListener changeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.rb_operation_shi:
                    isyes = "1";
                    break;
                case R.id.rb_operation_fou:
                    isyes = "0";
                    break;
            }
        }
    };
}

