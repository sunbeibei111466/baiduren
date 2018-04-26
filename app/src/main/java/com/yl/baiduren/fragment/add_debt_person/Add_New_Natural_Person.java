package com.yl.baiduren.fragment.add_debt_person;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.Login_Activity_Password;
import com.yl.baiduren.activity.mypager.PersonalInformation;
import com.yl.baiduren.adapter.UphotoAdapter;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseFragment;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.DebtDO;
import com.yl.baiduren.entity.request_body.Personalinfo;
import com.yl.baiduren.entity.request_body.RegisierEntity;
import com.yl.baiduren.service.RetrofitHelper;
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
import java.util.List;

import javax.xml.transform.Templates;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.toolsfinal.StringUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/4.
 * 添加债事自然人
 */

public class Add_New_Natural_Person extends BaseFragment implements View.OnClickListener {

    private GridView recyclerView;
    private UphotoAdapter adapter;
    private Button save;

    private List<String> mlist = null;
    private EditText real_name;
    private EditText et_natural_code;
    private EditText et_natural_phone;
    private TextView et_natural_area;
    private EditText et_natural_adress;
    private String areaId;


    @Override
    public int loadWindowLayout() {
        return R.layout.add_new_natural_person;
    }

    @Override
    public void initViews(View rootView) {


        real_name = rootView.findViewById(R.id.et_natural_name);//真实姓名
        //身份证号
        et_natural_code = rootView.findViewById(R.id.et_natural_code);
        //手机号
        et_natural_phone = rootView.findViewById(R.id.et_natural_phone);
        //归属地
        et_natural_area = rootView.findViewById(R.id.et_natural_area);
        et_natural_area.setOnClickListener(this);
        //详细地址
        et_natural_adress = rootView.findViewById(R.id.et_natural_adress);
        save = rootView.findViewById(R.id.person_save);//保存
        save.setOnClickListener(this);

        recyclerView = rootView.findViewById(R.id.grid_photo);

        mlist = new ArrayList<>();
        adapter = new UphotoAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getCount() - 1) {
                    Photo_Select.open_Photo(getContext(), parent.getCount() - 1, callback);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initput() {
        String name = real_name.getText().toString().trim();
        String code = et_natural_code.getText().toString().trim();
        String phone = et_natural_phone.getText().toString().trim();
        String area = et_natural_area.getText().toString().trim();
        String adress = "";
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(getActivity(), "请输入债事人的姓名");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showShort(getActivity(), "请输入债事人的身份证号");
            return;
        }
        if (!IdCardUtil.isIDCard(code)) {
            ToastUtil.showShort(getActivity(), "请输入正确的身份证号 ");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(getActivity(), "请输入债事人的手机号");
            return;
        }
        if (!IdCardUtil.matchPhone(phone)) {
            ToastUtil.showShort(getActivity(), "请输入正确的手机号");
            return;
        }

        if (TextUtils.isEmpty(area)) {
            ToastUtil.showShort(getActivity(), "请选择债事人的归属地");
            return;
        }
        if (!TextUtils.isEmpty(et_natural_adress.getText().toString().trim())) {
            adress = et_natural_adress.getText().toString().trim();
        }


        DebtDO debtDO = new DebtDO(DataWarehouse.getBaseRequest(getActivity()));
        debtDO.setType(2);//个人
        debtDO.setName(name);
        debtDO.setIdCode(code);
        debtDO.setPhoneNumber(phone);
        debtDO.setAddress(adress);
        debtDO.setArea(areaId);
        if (adapter.getPath() != null && adapter.getPath().size() != 0) {
            String imageUrl = Util.listToString(adapter.getPath());
            debtDO.setImg(imageUrl);
        } else {
            debtDO.setImg("");
        }

        requestNetWork(debtDO);
    }

    /**
     * 添加债事人
     *
     * @param debtDO
     */
    private void requestNetWork(DebtDO debtDO) {
        if (UserInfoUtils.IsLogin(getActivity())) {
            String json = Util.toJson(debtDO);//转成json
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            LUtils.e("---json---" + json);
            RetrofitHelper.getInstance(getActivity()).getServer()
                    .addDebt(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(getActivity()) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                LUtils.e("--债事人--" + data);
                                ToastUtil.showShort(getActivity(), "添加债事自然人成功");
                                UserInfoUtils.setUuid(getActivity(), baseResponse);
                                getActivity().finish();
                            }
                        }
                    });
        } else {
            getActivity().finish();
        }
    }


    /**
     * 图片选择回掉
     */
    private GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null && resultList.size() != 0) {
                loadImage(ImageUtils.comperssImage(resultList));//获取图片地址
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };


    /**
     * 上传图片，获取网络地址
     *
     * @param resultList
     */
    private void loadImage(List<File> resultList) {
        List<MultipartBody.Part> partsList = new ArrayList<>(resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            File file = new File(resultList.get(i).getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            partsList.add(part);
        }
        RetrofitHelper.getInstance(getActivity()).getServer()
                .getImage("ssss", UserInfoUtils.getUid(getActivity()), partsList)
                .compose(compose(this.<BaseEntity<List<String>>>bindToLifecycle()))
                .subscribe(new BaseObserver<List<String>>(getActivity()) {
                    @Override
                    protected void onSuccees(String code, List<String> data, BaseRequest baseResponse) throws Exception {
                        if (data.size() != 0) {
                            UserInfoUtils.setUuid(getActivity(), baseResponse);

                            mlist.addAll(data);
                            adapter.setPath(mlist);
                            recyclerView.setAdapter(adapter);
                            String imgUrl = (String) data.get(0);
                            String[] split = imgUrl.split(",");
//                            String string=Util.listToString(data);
//                            LUtils.e("---图片地址-----",string);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == save) {
            initput();
        } else if (v == et_natural_area) {//选择地址
            PopupWindeUtils.showPopupWinde(getActivity(), new PopupWindeUtils.OnAreaDataIdListener() {
                @Override
                public void areaId(String shengId, String shiId, String quId, String string) {
                    areaId = quId;
                    et_natural_area.setText(string);
                }
            });
        }
    }
}
