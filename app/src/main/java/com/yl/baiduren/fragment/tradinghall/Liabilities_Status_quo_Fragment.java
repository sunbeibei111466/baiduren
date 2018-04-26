package com.yl.baiduren.fragment.tradinghall;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.tradinghall.AssignmentActivity;
import com.yl.baiduren.adapter.tradinghall.Liabilities_Status_Adapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Timer_Select;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.CustomDatePicker;
import com.yl.baiduren.view.MyRecyclerView;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.liabilitiesDOS;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.type;


/**
 * 债权转让 --负债情况
 */
public class Liabilities_Status_quo_Fragment extends BaseFragment implements View.OnClickListener {

    private EditText et_liabilities_name, et_liabilities_code, et_liabilities_phone, et_liabilities_xx_address, et_liabilities_jine;
    private TextView et_liabilities_address, et_liabilities_time;
    private Button bt_liabilities_complete;
    private MyRecyclerView myRecyclerView;
    private Button liabilities;
    private String quCode;
    private Liabilities_Status_Adapter liabilitiesStatusAdapter;
    private List<AssignmentEntity.LiabilitiesDO> liabilitiesDOList = null;
    private LinearLayout ll_liabities_parent;

    @Override
    public int loadWindowLayout() {
        return R.layout.liabilities_status_quo_fragment;
    }

    @Override
    public void initViews(View rootView) {
        liabilitiesDOList = new ArrayList<>();
        et_liabilities_name = rootView.findViewById(R.id.et_liabilities_name);//债权方名称
        et_liabilities_code = rootView.findViewById(R.id.et_liabilities_code);//证件号
        et_liabilities_phone = rootView.findViewById(R.id.et_liabilities_phone);//联系电话
        et_liabilities_address = rootView.findViewById(R.id.et_liabilities_address);//归属地
        et_liabilities_address.setOnClickListener(this);
        et_liabilities_xx_address = rootView.findViewById(R.id.et_liabilities_xx_address);//详细地址
        et_liabilities_jine = rootView.findViewById(R.id.et_liabilities_jine);//负债金额
        et_liabilities_time = rootView.findViewById(R.id.et_liabilities_time);//负债时间
        et_liabilities_time.setOnClickListener(this);
        bt_liabilities_complete = rootView.findViewById(R.id.bt_liabilities_complete);//按钮
        bt_liabilities_complete.setOnClickListener(this);
        liabilities = rootView.findViewById(R.id.liabilities);//提交按钮
        liabilities.setOnClickListener(this);
        ll_liabities_parent=rootView.findViewById(R.id.ll_liabities_parent);
        myRecyclerView = rootView.findViewById(R.id.rv_liabilities);//recyclerView
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        liabilitiesStatusAdapter = new Liabilities_Status_Adapter(getActivity());
        if (type != 0) {//表示进入详情
            if(type==1){
                //从债权大厅进入，不可编辑
                setEnabled();
            }
            layoutUpdata();//从我的债权进入，可以编辑
        }
    }

    private void setEnabled() {
        liabilitiesStatusAdapter.setVisibility(true);
        liabilities.setVisibility(View.GONE);
        ll_liabities_parent.setVisibility(View.GONE);
    }

    private void layoutUpdata() {
        if(liabilitiesDOS!=null){
            liabilitiesDOList.addAll(liabilitiesDOS);
            liabilitiesStatusAdapter.setStringList(liabilitiesDOList);
            myRecyclerView.setAdapter(liabilitiesStatusAdapter);
        }
    }


    @Override
    public void onClick(View v) {

        if (v == bt_liabilities_complete) {
            complete();
        } else if (v == liabilities) {//提交
            getHttpData();
        } else if (v == et_liabilities_address) {//所属地
            PopupWindeUtils.showPopupWinde(getActivity(), new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_liabilities_address.setText(string);
                    quCode = quId;
                }
            });
        } else if (v == et_liabilities_time) {
            Timer_Select.getTimer(getActivity(), Timer_Select.getTime(et_liabilities_time), new CustomDatePicker.ResultHandler() {
                @Override
                public void handle(String time) {
                    et_liabilities_time.setText(time.split(" ")[0]);
                }
            });
        }
    }

    private void complete() {
        LUtils.e("--------债券转让-------");
        String name = et_liabilities_name.getText().toString().trim();//债权方名称
        String code = et_liabilities_code.getText().toString().trim();//证件号
        String phone = et_liabilities_phone.getText().toString().trim();//联系电话
        String address = et_liabilities_address.getText().toString().trim();//归属地
        String xx_address = et_liabilities_xx_address.getText().toString().trim();//详细地址
        String jine = et_liabilities_jine.getText().toString().trim();//负债金额
        String time = et_liabilities_time.getText().toString().trim();//负债时间
        if (TextUtil.isEmpty(name)){
            ToastUtil.showShort(getActivity(),"请输入债权方名称");
            return;
        }
        if (TextUtil.isEmpty(code)){
            ToastUtil.showShort(getActivity(),"请输入身份证号/企业组织机构代码");
            return;
        }
        if (TextUtil.isEmpty(phone)){
            ToastUtil.showShort(getActivity(),"请输入债权方手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(phone)){
            ToastUtil.showShort(getActivity(),"请输入正确的手机号");
            return;
        }
        if (TextUtil.isEmpty(address)){
            ToastUtil.showShort(getActivity(),"请选择归属地");
            return;
        }
        if (TextUtil.isEmpty(xx_address)){
            ToastUtil.showShort(getActivity(),"请输入债权方的详细地址");
            return;
        }
        if (TextUtil.isEmpty(jine)){
            ToastUtil.showShort(getActivity(),"请输入金额");
            return;
        }
        if (TextUtil.isEmpty(time)){
            ToastUtil.showShort(getActivity(),"请选择负债时间");
            return;
        }



        AssignmentEntity.LiabilitiesDO liabilitiesDO = new AssignmentEntity.LiabilitiesDO();

        liabilitiesDO.setName(name);
        liabilitiesDO.setIdNumber(code);
        liabilitiesDO.setMobile(phone);
        liabilitiesDO.setAddress(xx_address);
        liabilitiesDO.setArea(quCode);
        liabilitiesDO.setAreaStr(address);
        liabilitiesDO.setAmount(Long.valueOf(jine));
        liabilitiesDO.setDebtTime(Util.strParsedata(time));
        liabilitiesDO.setDebtTimeString(time);
        liabilitiesDOList.add(liabilitiesDO);
        liabilitiesStatusAdapter.setStringList(liabilitiesDOList);
        myRecyclerView.setAdapter(liabilitiesStatusAdapter);
        liabilitiesStatusAdapter.notifyDataSetChanged();
    }

    public void getHttpData() {
        if (AssignmentActivity.claimsId !=-1) {

            if (UserInfoUtils.IsLogin(getActivity())) {
                AssignmentEntity assignmentEntity = new AssignmentEntity(DataWarehouse.getBaseRequest(getActivity()));
                assignmentEntity.setClaimsId(AssignmentActivity.claimsId);
                assignmentEntity.setSteps(5);
                if (type != 0) {
                    assignmentEntity.setSaveOrUpdate(2);//修改数据
                } else {
                    assignmentEntity.setSaveOrUpdate(1);//新增
                }
                if (liabilitiesStatusAdapter.getLiabilitiesDOList()!=null&&liabilitiesStatusAdapter.getLiabilitiesDOList().size() != 0) {
                    assignmentEntity.setLiabilitiesDOS(liabilitiesStatusAdapter.getLiabilitiesDOList());
                } else {
                    ToastUtil.showShort(getActivity(), "请添加数据");
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
                                    ToastUtil.showShort(getActivity(),"负债情况保存成功");
                                    AssignmentActivity.claimsId = Long.valueOf(data);
                                    //债权Id
                                }
                            }
                        });
            }
        } else {
            ToastUtil.showShort(getActivity(), "请先填写基本信息，在填写股分结构数据");
        }
    }
}
