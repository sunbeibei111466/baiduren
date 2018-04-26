package com.yl.baiduren.fragment.tradinghall;

import android.icu.text.UnicodeSetSpanner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.debtrecord.Debt_CreditorsAssets;
import com.yl.baiduren.activity.tradinghall.AssignmentActivity;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
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

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.baseClaimsVo;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.claimsId;
import static com.yl.baiduren.activity.tradinghall.AssignmentActivity.type;


/**
 * 债权转让 -- 基本信息
 */
public class Creditor_rights_Information_Fragment extends BaseFragment implements View.OnClickListener {

    private EditText et_creditor_enterpriseName;//企业全称
    private EditText et_creditor_enterprise_CardNumber;//企业证号
    private TextView et_creditor_belongTo_lndustry;//企业所属地
    private EditText et_creditor_legal_person_name;//法人姓名
    private EditText et_creditor_legal_preson_code;//身份证号
    private EditText et_creditor_legal_preson_phone;//联系电话
    private EditText et_creditor_legal_preson_detailed_address;//细地址
    private EditText et_creditor_hylx;//所属行业类型
    private EditText et_creditor_sy_hylx;//上游行业类型
    private EditText et_creditor_xy_hylx;//下游行业类型
    private EditText et_creditor_ylx;//简介
    private TextView et_creditor_legal_preson_address;//法人所 属 地
    private Button bt_creditor;//保存
    private String qyCode, frCode;//企业，法人 地址Code
    private ImageView creditor_logo;
    private String imageUrl = "";
    private String frName;
    private String qyZh;
    private String frIdCard;
    private String frPhone;
    private String frAddress;


    @Override
    public int loadWindowLayout() {
        return R.layout.creditor_rights_information_fragment;
    }

    @Override
    public void initViews(View rootView) {

        ArrayList<String> pathList = new ArrayList<>();
        et_creditor_enterpriseName = rootView.findViewById(R.id.et_creditor_enterpriseName);
        et_creditor_enterprise_CardNumber = rootView.findViewById(R.id.et_creditor_enterprise_CardNumber);
        et_creditor_belongTo_lndustry = rootView.findViewById(R.id.et_creditor_belongTo_lndustry);
        et_creditor_belongTo_lndustry.setOnClickListener(this);
        et_creditor_legal_person_name = rootView.findViewById(R.id.et_creditor_legal_person_name);
        et_creditor_legal_preson_code = rootView.findViewById(R.id.et_creditor_legal_preson_code);
        et_creditor_legal_preson_phone = rootView.findViewById(R.id.et_creditor_legal_preson_phone);
        et_creditor_legal_preson_address = rootView.findViewById(R.id.et_creditor_legal_preson_address);
        et_creditor_legal_preson_address.setOnClickListener(this);
        //详细地址
        et_creditor_legal_preson_detailed_address = rootView.findViewById(R.id.et_creditor_legal_preson_detailed_address);
        et_creditor_hylx = rootView.findViewById(R.id.et_creditor_hylx);
        et_creditor_sy_hylx = rootView.findViewById(R.id.et_creditor_sy_hylx);
        et_creditor_xy_hylx = rootView.findViewById(R.id.et_creditor_xy_hylx);
        et_creditor_ylx = rootView.findViewById(R.id.et_creditor_ylx);
        bt_creditor = rootView.findViewById(R.id.bt_creditor);
        bt_creditor.setOnClickListener(this);
        creditor_logo = rootView.findViewById(R.id.creditor_logo);
        creditor_logo.setOnClickListener(onClickListener);
        if (type != 0) {
            if (type == 1) {
                //从债权大厅进入，不可编辑
                setEnabled();
            }
            layoutUpdata();//从我的债权进入，可以编辑
        }
    }

    @Override
    public void onClick(View v) {
        if (v == bt_creditor) {//保存
            seva();
        } else if (v == et_creditor_belongTo_lndustry) {//企业所属地
            PopupWindeUtils.showPopupWinde(getActivity(), new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_creditor_belongTo_lndustry.setText(string);
                    qyCode = quId;
                }
            });
        } else if (v == et_creditor_legal_preson_address) {//法人所属地
            PopupWindeUtils.showPopupWinde(getActivity(), new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    et_creditor_legal_preson_address.setText(string);
                    frCode = quId;
                }
            });
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Photo_Select.open_Photo(getActivity(), 7, onHanlderResultCallback);
        }
    };


    private void layoutUpdata() {
        if (baseClaimsVo != null) {
            if (isNull(baseClaimsVo.getEnterpriseName())) {
                et_creditor_enterpriseName.setText(baseClaimsVo.getEnterpriseName());
            }
            if (isNull(baseClaimsVo.getEnterpriseNumber())) {
                et_creditor_enterprise_CardNumber.setText(baseClaimsVo.getEnterpriseNumber());
            }
            if (isNull(baseClaimsVo.getAreaStr())) {
                et_creditor_belongTo_lndustry.setText(baseClaimsVo.getAreaStr());
                if (isNull(baseClaimsVo.getArea())) {
                    qyCode = baseClaimsVo.getArea();
                }
            }
            if (isNull(baseClaimsVo.getLawyerName())) {
                et_creditor_legal_person_name.setText(baseClaimsVo.getLawyerName());
            }
            if (isNull(baseClaimsVo.getIdCard())) {
                et_creditor_legal_preson_code.setText(baseClaimsVo.getIdCard());
            }
            if (isNull(baseClaimsVo.getMobile())) {
                et_creditor_legal_preson_phone.setText(baseClaimsVo.getMobile());
            }
            if (isNull(baseClaimsVo.getAddressCodeStr())) {
                et_creditor_legal_preson_address.setText(baseClaimsVo.getAddressCodeStr());
                if (isNull(baseClaimsVo.getAddressCode())) {
                    frCode = baseClaimsVo.getAddressCode();
                }
            }

            if (isNull(baseClaimsVo.getAddress())) {
                et_creditor_legal_preson_detailed_address.setText(baseClaimsVo.getAddress());
            }
            if (isNull(baseClaimsVo.getIndustryType())) {
                et_creditor_hylx.setText(baseClaimsVo.getIndustryType());
            }
            if (isNull(baseClaimsVo.getUpIndustryType())) {
                et_creditor_sy_hylx.setText(baseClaimsVo.getUpIndustryType());
            }
            if (isNull(baseClaimsVo.getDownIndustryType())) {
                et_creditor_xy_hylx.setText(baseClaimsVo.getDownIndustryType());
            }
            if (isNull(baseClaimsVo.getProfile())) {
                et_creditor_ylx.setText(baseClaimsVo.getProfile());
            }
            if (isNull(baseClaimsVo.getImgUrl())) {
                imageUrl = baseClaimsVo.getImgUrl();
                Glide.with(getActivity()).load(imageUrl).into(creditor_logo);
            }

        } else {
            LUtils.e("---空---");
        }
    }

    private boolean isNull(String string) {
        return !TextUtil.isEmpty(string);
    }

    private void setEnabled() {
        et_creditor_enterpriseName.setEnabled(false);
        et_creditor_enterprise_CardNumber.setEnabled(false);
        et_creditor_belongTo_lndustry.setEnabled(false);
        et_creditor_belongTo_lndustry.setEnabled(false);
        et_creditor_legal_person_name.setEnabled(false);
        et_creditor_legal_preson_code.setEnabled(false);
        et_creditor_legal_preson_phone.setEnabled(false);
        et_creditor_legal_preson_address.setEnabled(false);
        et_creditor_legal_preson_detailed_address.setEnabled(false);
        et_creditor_hylx.setEnabled(false);
        et_creditor_sy_hylx.setEnabled(false);
        et_creditor_xy_hylx.setEnabled(false);
        et_creditor_ylx.setEnabled(false);
        bt_creditor.setVisibility(View.GONE);
        creditor_logo.setEnabled(false);
    }
    private boolean isNullEntreprise(){//企业组非空判断
        //企业证号
        qyZh = et_creditor_enterprise_CardNumber.getText().toString().trim();
        LUtils.e(qyZh+"+++++++++++++++++++++++++++++");
        return !TextUtil.isEmpty(qyZh) && !TextUtil.isEmpty(et_creditor_belongTo_lndustry.getText().toString().trim());
    }
    private boolean isNullFren(){//法人非空判断

        //法人姓名
        frName = et_creditor_legal_person_name.getText().toString().trim();
        //身份证号
        frIdCard = et_creditor_legal_preson_code.getText().toString().trim();
        //联系电话
        frPhone = et_creditor_legal_preson_phone.getText().toString().trim();
        //细地址
        frAddress = et_creditor_legal_preson_detailed_address.getText().toString().trim();
        return !TextUtil.isEmpty(frName) && !TextUtil.isEmpty(frIdCard)
                && !TextUtil.isEmpty(frPhone) && !TextUtil.isEmpty(frAddress)
                && !TextUtil.isEmpty(et_creditor_legal_preson_address.getText().toString().trim());


    }

    private void seva() {
        String qyName = et_creditor_enterpriseName.getText().toString().trim();//企业全称
        String hy_type = et_creditor_hylx.getText().toString().trim();//所属行业类型
        String hy_type_sy = et_creditor_sy_hylx.getText().toString().trim();//上游行业类型
        String hy_type_xy = et_creditor_xy_hylx.getText().toString().trim();//下游行业类型
        String jj = et_creditor_ylx.getText().toString().trim();//简介
        if (TextUtil.isEmpty(qyName)){
           ToastUtil.showShort(getActivity(),"请输入企业全称");
           return;
       }
       if (isNullEntreprise()||isNullFren()){
            LUtils.e(isNullEntreprise()+"++++++++++++++++++++++");
           if (isNullEntreprise()){
               if (!CompanyCodeUtil.isCompanyCode(qyZh)){
                  ToastUtil.showShort(getActivity(),"请输入正确的企业证号");
                  return;
              }
           }
           if (isNullFren()){
               if (!IdCardUtil.isIDCard(frIdCard)){
                   ToastUtil.showShort(getActivity(),"请输入正确的身份证号");
                   return;
               }
               if (!IdCardUtil.matchPhone(frPhone)){
                   ToastUtil.showShort(getActivity(),"请输入正确的手机号");
                   return;
               }
           }

       }else{
           ToastUtil.showShort(getActivity(),"同颜色*为一组问题，至少填写全一组问题");
           return;
       }



       if (TextUtil.isEmpty(hy_type)){
           ToastUtil.showShort(getActivity(),"请输入企业所属行业类型");
           return;
       }
       if (TextUtil.isEmpty(hy_type_sy)){
           ToastUtil.showShort(getActivity(),"请输入上游行业类型");
           return;
       }
       if (TextUtil.isEmpty(hy_type_xy)){
           ToastUtil.showShort(getActivity(),"请输入下游行业类型");
           return;
       }



        if (UserInfoUtils.IsLogin(getActivity())) {
            AssignmentEntity assignmentEntity = new AssignmentEntity(DataWarehouse.getBaseRequest(getActivity()));
            assignmentEntity.setSteps(1);
            if (claimsId != -1) {
                assignmentEntity.setClaimsId(claimsId);
            }
            if (type != 0) {
                assignmentEntity.setSaveOrUpdate(2);//修改数据
            } else {
                assignmentEntity.setSaveOrUpdate(1);//新增
            }
            AssignmentEntity.BaseClaimsVo baseClaimsVo = new AssignmentEntity.BaseClaimsVo();
            baseClaimsVo.setEnterpriseName(qyName);
            baseClaimsVo.setEnterpriseNumber(qyZh);
            if (isNull(qyCode)) {
                baseClaimsVo.setArea(qyCode);
            }
            baseClaimsVo.setLawyerName(frName);
            baseClaimsVo.setIdCard(frIdCard);
            baseClaimsVo.setMobile(frPhone);
            if (isNull(frCode)) {
                baseClaimsVo.setAddressCode(frCode);
            }

            baseClaimsVo.setAddress(frAddress);
            baseClaimsVo.setIndustryType(hy_type);
            baseClaimsVo.setUpIndustryType(hy_type_sy);
            baseClaimsVo.setDownIndustryType(hy_type_xy);
            baseClaimsVo.setProfile(jj);

            if (!TextUtil.isEmpty(imageUrl)) {
                baseClaimsVo.setImgUrl(imageUrl);
            } else {
                baseClaimsVo.setImgUrl("");
            }
            assignmentEntity.setBaseClaimsVo(baseClaimsVo);
            getHttpSeva(assignmentEntity);
        }
    }

    /**
     * 保存
     *
     * @param assignmentEntity
     */
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
                            LUtils.e("----data-----", data);
                            ToastUtil.showShort(getActivity(),"保存基本信息成功");
                            AssignmentActivity.claimsId = Long.valueOf(data);//债权Id
                        }
                    }
                });
    }

    //图片回调，返回图片集
    private GalleryFinal.OnHanlderResultCallback onHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList.size() != 0) {
                getHttpImage(ImageUtils.comperssImage(resultList));
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            LUtils.e("------+++++--" + errorMsg);
        }
    };

    /**
     * 图片上传
     *
     * @param resultList 图片
     */
    private void getHttpImage(List<File> resultList) {
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
                            imageUrl = data.get(0);
                            Glide.with(getActivity()).load(imageUrl).into(creditor_logo);
                        }
                    }
                });
    }
}
