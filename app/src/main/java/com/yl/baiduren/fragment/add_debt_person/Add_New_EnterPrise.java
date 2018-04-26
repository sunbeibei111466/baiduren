package com.yl.baiduren.fragment.add_debt_person;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.base.IDeleteCall;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.DebtDO;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.CompanyCodeUtil;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.GreenDaoUtils;
import com.yl.baiduren.utils.IdCardUtil;
import com.yl.baiduren.utils.ImageUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.Photo_Select;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/4.
 * 添加债事企业
 */

public class Add_New_EnterPrise extends BaseFragment implements View.OnClickListener {

    private GridView recyclerView;
    private UphotoAdapter adapter;
    private ArrayList<String> photolist = null;
    private EditText et_enterprise_name, et_legal_phone, et_legal_detaile_adress, et_blong_type;
    private EditText et_enterprise_code, et_legal_name, et_legal_code, et_up_type, et_down_type;
    private TextView et_enterprise_area, et_legal_adress;
    private Button enter_prise_save;
    private String qiyAreaId = null, farAreaId = null;

    @Override
    public int loadWindowLayout() {
        return R.layout.add_new_enterprise;
    }

    @Override
    public void initViews(View rootView) {

        et_enterprise_name = rootView.findViewById(R.id.et_enterprise_name);//企业名称
        et_enterprise_code = rootView.findViewById(R.id.et_enterprise_code);//三证合一代码
        et_enterprise_area = rootView.findViewById(R.id.et_enterprise_area);//企业归属地
        et_enterprise_area.setOnClickListener(this);

        et_legal_name = rootView.findViewById(R.id.et_legal_name);//法人姓名
        et_legal_code = rootView.findViewById(R.id.et_legal_code);//法人身份证号
        et_legal_phone = rootView.findViewById(R.id.et_legal_phone);//法人手机号
        et_legal_adress = rootView.findViewById(R.id.et_legal_adress);//法人所属地
        et_legal_adress.setOnClickListener(this);
        et_legal_detaile_adress = rootView.findViewById(R.id.et_legal_detaile_adress);//法人详细地址
        et_blong_type = rootView.findViewById(R.id.et_blong_type);//所属行业类型
        et_up_type = rootView.findViewById(R.id.et_up_type);//上游行业类型
        et_down_type = rootView.findViewById(R.id.et_down_type);//下游行业类型
        enter_prise_save = rootView.findViewById(R.id.enter_prise_save);
        enter_prise_save.setOnClickListener(this);

        recyclerView = rootView.findViewById(R.id.recycler_photo);
        photolist = new ArrayList<>();
        adapter = new UphotoAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getCount() - 1) {

                    //调用相机
                    Photo_Select.open_Photo(getContext(), parent.getCount() - 1, callback);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

        if (v == enter_prise_save) {
            save();
        } else if (et_enterprise_area == v) {//企业所属地
            PopupWindeUtils.showPopupWinde(getActivity(), new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    qiyAreaId = quId;
                    et_enterprise_area.setText(string);
                }
            });
        } else if (et_legal_adress == v) {
            PopupWindeUtils.showPopupWinde(getActivity(), new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    farAreaId = quId;
                    et_legal_adress.setText(string);
                }
            });
        }
    }

    /**
     * 判断第一组 是否填满
     *
     * @return
     */
    private boolean isEnterpriseNull() {
        return !TextUtils.isEmpty(et_enterprise_code.getText().toString().trim()) &&
                !TextUtils.isEmpty(et_enterprise_area.getText().toString().trim());
    }

    private boolean isLegalNull() {
        return !TextUtils.isEmpty(et_legal_name.getText().toString().trim()) &&
                !TextUtils.isEmpty(et_legal_code.getText().toString().trim()) &&
                !TextUtils.isEmpty(et_legal_phone.getText().toString().trim()) &&
                !TextUtils.isEmpty(et_legal_adress.getText().toString().trim()) &&
                !TextUtils.isEmpty(et_legal_detaile_adress.getText().toString());

    }

    private void save() {


        if (TextUtils.isEmpty(et_enterprise_name.getText().toString().trim())) {
            ToastUtil.showShort(getActivity(), "请输入准确的企业全称");
            return;
        }

        if (isEnterpriseNull() || isLegalNull()) {
            if (isEnterpriseNull()) {//判断第一组
                if (CompanyCodeUtil.isCompanyCode(et_enterprise_code.getText().toString().trim())) {
                    ToastUtil.showShort(getActivity(), "请输入正确的三证合一代码");
                    return;
                }
            }
            if (isLegalNull()) {//判断第二组
                if (!IdCardUtil.isIDCard(et_legal_code.getText().toString().trim())) {
                    ToastUtil.showShort(getActivity(), "请输入正确的法人身份证号");
                    return;
                }
                if (!IdCardUtil.matchPhone(et_legal_phone.getText().toString().trim())) {
                    ToastUtil.showShort(getActivity(), "请输入正确的手机号码");
                    return;
                }
            }
        } else {
            ToastUtil.showShort(getActivity(), "同颜色*为一组问题，至少填写全一组问题");
            return;
        }

        if (TextUtils.isEmpty(et_blong_type.getText().toString().trim())) {
            ToastUtil.showShort(getActivity(), "请输入所属行业类型");
            return;
        }
        if (TextUtils.isEmpty(et_up_type.getText().toString().trim())) {
            ToastUtil.showShort(getActivity(), "请输入上游行业类型");
            return;
        }
        if (TextUtils.isEmpty(et_down_type.getText().toString().trim())) {
            ToastUtil.showShort(getActivity(), "请输入下游行业类型");
            return;
        }

        String qiyName = et_enterprise_name.getText().toString().trim();//企业名称
        String qiyCode = et_enterprise_code.getText().toString().trim();//三证合一代码

        String frName = et_legal_name.getText().toString().trim();//法人姓名
        String frCode = et_legal_code.getText().toString().trim();//法人身份证号
        String frPhonr = et_legal_phone.getText().toString().trim();//法人手机号码
        String frAddress = et_legal_detaile_adress.getText().toString().trim();//法人详细地址
        String hyType = et_blong_type.getText().toString().trim();//所属行业类型
        String upType = et_up_type.getText().toString().trim();//上游行业类型
        String downType = et_down_type.getText().toString().trim();//下游行业类型
        DebtDO debtDO = new DebtDO(DataWarehouse.getBaseRequest(getActivity()));
        debtDO.setType(1);

        debtDO.setName(qiyName);//企业名为必传
        //企业
        debtDO.setCompanyCode(qiyCode);
        debtDO.setArea(qiyAreaId);
        //法人
        debtDO.setLegalName(frName);
        debtDO.setIdCode(frCode);
        debtDO.setPhoneNumber(frPhonr);
        debtDO.setLegalArea(farAreaId);
        debtDO.setAddress(frAddress);
        debtDO.setCompanyCategory(hyType);
        debtDO.setCompanyUpCategory(upType);
        debtDO.setCompanyDownCategory(downType);
        if (adapter.getPath() != null && adapter.getPath().size() != 0) { //设置图片参数
            String string = Util.listToString(adapter.getPath());
            debtDO.setImg(string);
        } else {
            debtDO.setImg("");
        }

        requestNetWork(debtDO);

    }

    private void requestNetWork(DebtDO debtDO) {
        if (UserInfoUtils.IsLogin(getActivity())) {
            String json = Util.toJson(debtDO);//转成json
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            LUtils.e("---json---" + json);

            BaseObserver<String> baseObserver = new BaseObserver<String>(getActivity()) {
                @Override
                protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        ToastUtil.showShort(getActivity(), "添加债事企业成功");
                        UserInfoUtils.setUuid(getActivity(), baseResponse);
                        getActivity().finish();
                    }
                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(getActivity()).getServer()
                    .addDebt(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(baseObserver);
        } else {
            getActivity().finish();
        }
    }


    private GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList.size() != 0) {
                loadImage(ImageUtils.comperssImage(resultList));//获取图片地址

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    private void loadImage(List<File> resultList) {
        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        List<BaseRequest> baseRequests = GreenDaoUtils.getInstance(getActivity()).getBaseRequestDao().loadAll();
        Long uid = baseRequests.get(0).getUid();
        RetrofitHelper.getInstance(getActivity()).getServer()
                .getImage("ssss", uid, partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(getActivity()) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(getActivity(), baseResponse);

                            photolist.addAll(data);
                            adapter.setPath(photolist);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                });
    }


}
