package com.yl.baiduren.activity.supply_demand;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.baiduren.R;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.DeleteItem;
import com.yl.baiduren.entity.request_body.Supply_Demend_Details_Id;
import com.yl.baiduren.entity.result.My_Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 我的供需管理 需求详情
 */

public class My_Demend_Details extends BaseActivity {
    private ImageView detail_back;
    private TextView supply_num;
    private TextView supply_name;
    private TextView supply_shuliang;
    private TextView supply_guzhi;
    private TextView supply_jigou;
    private TextView supply_area;
    private TextView supply_type;
    private long demend_id;
    private Button my_demend_detail_delete;
    private Button my_demend_updata;
    private My_Demend_Details_Result myDemendDetailsResult;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_demend_details;
    }

    @Override
    public void initViews() {
        demend_id = getIntent().getLongExtra("demend_id", 0);//需求id
        detail_back = findViewById(R.id.demend_detail_back);//关闭
        detail_back.setOnClickListener(listener);
        supply_num = findViewById(R.id.demend_code);//编号
        supply_type = findViewById(R.id.demend_type);//类型
        supply_name = findViewById(R.id.demend_name);//名称
        supply_shuliang = findViewById(R.id.demend_shuliang);//数量
        supply_guzhi = findViewById(R.id.demend_guzhi);//估值
        supply_area = findViewById(R.id.demned_area);//归属地
        my_demend_detail_delete = findViewById(R.id.my_demend_detail_delete);//删除需求
        my_demend_detail_delete.setOnClickListener(listener);
        my_demend_updata = findViewById(R.id.my_demend_updata);//修改需求
        my_demend_updata.setOnClickListener(listener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (demend_id != 0) {
            requestWork();
        }
    }

    private void requestWork() {//需求详情
        Supply_Demend_Details_Id entity = new Supply_Demend_Details_Id(DataWarehouse.getBaseRequest(this));
        entity.setId(demend_id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);

        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<My_Demend_Details_Result> baseObserver = new BaseObserver<My_Demend_Details_Result>(this) {

            @Override
            protected void onSuccees(String code, My_Demend_Details_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(My_Demend_Details.this, baseResponse);
                    myDemendDetailsResult = data;
                    supply_num.setText(data.getCode());
                    supply_type.setText(data.getCategoryName());
                    supply_name.setText(data.getName());
                    supply_shuliang.setText(data.getNum() + "");
                    supply_guzhi.setText(data.getValueStr());
                    supply_area.setText(data.getAreaName());

                }
            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .get_Demend_Details(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<My_Demend_Details_Result>>bindToLifecycle()))
                .subscribe(baseObserver);

    }

    private void delete(long id) {

        if (UserInfoUtils.IsLogin(this)) {
            DeleteItem deleteItem = new DeleteItem();
            deleteItem.setId(id);

            String json = Util.toJson(deleteItem);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
            retrofitService.delete_Demend(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                    .subscribe(new BaseObserver<String>(My_Demend_Details.this) {
                        @Override
                        protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                ToastUtil.showShort(My_Demend_Details.this, "删除成功");
                                new android.os.Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1000);
                            }
                        }
                    });
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == detail_back) {
                finish();
            } else if (v == my_demend_detail_delete) {//删除需求
                delete(demend_id);

            } else if (v == my_demend_updata) {//更改需求
                startActivity(new Intent(My_Demend_Details.this, Upload_Demend.class)
                        .putExtra("myDemendDetailsResult", myDemendDetailsResult));
            }

        }
    };
}
