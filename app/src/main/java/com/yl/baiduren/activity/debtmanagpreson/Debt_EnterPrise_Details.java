package com.yl.baiduren.activity.debtmanagpreson;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.adapter.ImageShowAdapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.Debt_Details_Entity;
import com.yl.baiduren.entity.result.DebtPresonEntity;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.Util;
import com.yl.baiduren.view.MyRecyclerView;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Debt_EnterPrise_Details extends BaseActivity implements View.OnClickListener{

    private Long debtEnterPriseId;
    private TextView debt_enter_name,tv_enter_name,tv_enter_code,tv_enter_area,tv_enter_fr_name
            ,tv_enter_idcord,tv_enter_phone,tv_enter_fr_area,tv_enter_address,tv_enter_sy_type
            ,tv_enter_xy_type,tv_enter_hy_type;
    private MyRecyclerView detail_enter_photo;
    private ImageView iv_enter_left,editor_enter_icon;
    private DebtPresonEntity debtPresonEntity;
    private ImageShowAdapter imageShowAdapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_debt__enter_prise__details;
    }

    @Override
    public void initViews() {
        int describe = getIntent().getIntExtra("describe", 0);
        debtEnterPriseId=getIntent().getLongExtra("debtEnterPriseId",0);
        debt_enter_name=findViewById(R.id.debt_enter_name);//title
        iv_enter_left=findViewById(R.id.iv_enter_left);//back
        iv_enter_left.setOnClickListener(this);
        editor_enter_icon=findViewById(R.id.editor_enter_icon);//编辑
        editor_enter_icon.setOnClickListener(this);
        tv_enter_name=findViewById(R.id.tv_enter_name);//企业全称
        tv_enter_code=findViewById(R.id.tv_enter_code);//企业证号
        tv_enter_area=findViewById(R.id.tv_enter_area);//企业所属地
        tv_enter_fr_name=findViewById(R.id.tv_enter_fr_name);//法人姓名
        tv_enter_idcord=findViewById(R.id.tv_enter_idcord);//法人身份证号
        tv_enter_phone=findViewById(R.id.tv_enter_phone);//联系电话
        tv_enter_fr_area=findViewById(R.id.tv_enter_fr_area);//法人所属地
        tv_enter_address=findViewById(R.id.tv_enter_address);//详细地址
        tv_enter_hy_type=findViewById(R.id.tv_enter_hy_type);//行业类型
        tv_enter_sy_type=findViewById(R.id.tv_enter_sy_type);//上游行业类型
        tv_enter_xy_type=findViewById(R.id.tv_enter_xy_type);//上游行业类型
        detail_enter_photo=findViewById(R.id.detail_enter_photo);
        detail_enter_photo.setLayoutManager(new GridLayoutManager(this, 4));
        if(describe ==2){
            editor_enter_icon.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(debtEnterPriseId!=0){
            getHttp();
        }
    }

    public void getHttp() {

        Debt_Details_Entity entity=new Debt_Details_Entity(DataWarehouse.getBaseRequest(this));
        entity.setId(debtEnterPriseId);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitHelper.getInstance(this).getServer()
                .getDebtPerson_Details(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<DebtPresonEntity>>bindToLifecycle()))
                .subscribe(new BaseObserver<DebtPresonEntity>(this) {
                    @Override
                    protected void onSuccees(String code, DebtPresonEntity data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            if (data.getType()==1){//债事人
                                if(!TextUtils.isEmpty(data.getName())){
                                    tv_enter_name.setText(data.getName());//企业全称
                                    debt_enter_name.setText(data.getName());
                                }
                                if(!TextUtils.isEmpty(data.getCompanyCode())){//企业证号
                                    tv_enter_code.setText(data.getCompanyCode());
                                }

                                if(!TextUtils.isEmpty(data.getAreaStr())){//所属地
                                    tv_enter_area.setText(data.getAreaStr());
                                }

                                if(!TextUtils.isEmpty(data.getLegalName())){//法人姓名
                                    tv_enter_fr_name.setText(data.getLegalName());
                                }

                                if(!TextUtils.isEmpty(data.getIdCode())){//法人身份证号
                                    tv_enter_idcord.setText(data.getIdCode());
                                }

                                if(!TextUtils.isEmpty(data.getPhoneNumber())){//法人联系电话
                                    tv_enter_phone.setText(data.getPhoneNumber());
                                }

                                if(!TextUtils.isEmpty(data.getLegalAreaStr())){//法人所属地
                                    tv_enter_fr_area.setText(data.getLegalAreaStr());
                                }

                                if(!TextUtils.isEmpty(data.getAddress())){//详细地址
                                    tv_enter_address.setText(data.getAddress());
                                }


                                if(!TextUtils.isEmpty(data.getCompanyCategory())){//上游行业类型
                                    tv_enter_hy_type.setText(data.getCompanyCategory());
                                }

                                if(!TextUtils.isEmpty(data.getCompanyUpCategory())){//上游行业类型
                                    tv_enter_sy_type.setText(data.getCompanyUpCategory());
                                }

                                if(!TextUtils.isEmpty(data.getCompanyDownCategory())){//上游行业类型
                                    tv_enter_xy_type.setText(data.getCompanyDownCategory());
                                }

                                imageShowAdapter=new ImageShowAdapter(Debt_EnterPrise_Details.this);
                                if(!TextUtils.isEmpty(data.getImg())){//设置图片
                                    String [] image=data.getImg().split(",");
                                    imageShowAdapter.setImageArry(image);
                                    detail_enter_photo.setAdapter(imageShowAdapter);
                                }else{
                                    imageShowAdapter.getImageList().clear();
                                    detail_enter_photo.setAdapter(imageShowAdapter);
                                    imageShowAdapter.notifyDataSetChanged();
                                }
                                debtPresonEntity=data;
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v==iv_enter_left){//返回
           finish();
        }else if(v== editor_enter_icon){//编辑

            Intent intent = new Intent(this, Debt_EnterPrise_Updata.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("debtPresonEntity", debtPresonEntity);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }
}
