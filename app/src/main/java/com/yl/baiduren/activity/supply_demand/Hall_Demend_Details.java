package com.yl.baiduren.activity.supply_demand;

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
import com.yl.baiduren.entity.request_body.Collection_Supply_Body;
import com.yl.baiduren.entity.request_body.Supply_Demend_Details_Id;
import com.yl.baiduren.entity.result.My_Demend_Details_Result;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.DialogUtils;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 大厅供需管理 需求详情
 */

public class Hall_Demend_Details extends BaseActivity {
    private ImageView detail_back;
    private TextView supply_num;
    private TextView supply_name;
    private TextView supply_shuliang;
    private TextView supply_guzhi;
    private TextView supply_jigou;
    private TextView supply_area;
    private TextView supply_type;
    private long demend_id;
    private Button hall_demend_detail_sc;
    private Button hall_demend_lianxi;

    @Override
    public int loadWindowLayout() {
        return R.layout.hall_demend_details;
    }

    @Override
    public void initViews() {
        boolean myDemendCollection = getIntent().getBooleanExtra("MyDemendCollection", false);
        demend_id = getIntent().getLongExtra("id", 0);//需求id
        detail_back = findViewById(R.id.demend_detail_back);//关闭
        detail_back.setOnClickListener(listener);
        supply_num = findViewById(R.id.demend_code);//编号
        supply_type = findViewById(R.id.demend_type);//类型
        supply_name = findViewById(R.id.demend_name);//名称
        supply_shuliang = findViewById(R.id.demend_shuliang);//数量
        supply_guzhi = findViewById(R.id.demend_guzhi);//估值
        supply_area = findViewById(R.id.demned_area);//归属地
        hall_demend_detail_sc = findViewById(R.id.hall_demend_detail_sc);//收藏
        hall_demend_detail_sc.setOnClickListener(listener);
        hall_demend_lianxi = findViewById(R.id.hall_demend_lianxi);//联系我们
        hall_demend_lianxi.setOnClickListener(listener);

        if(myDemendCollection){
            hall_demend_detail_sc.setVisibility(View.GONE);
            hall_demend_lianxi.setVisibility(View.GONE);
        }

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
                    UserInfoUtils.setUuid(Hall_Demend_Details.this, baseResponse);
                    supply_num.setText(data.getCode());
                    supply_type.setText(data.getCategoryName());
                    supply_name.setText(data.getName());
                    supply_shuliang.setText(data.getNum() + "");
                    supply_guzhi.setText(data.getValueStr());
                    LUtils.e("归属地+++" + data.getAreaName());
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


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == detail_back) {
                finish();
            } else if (v == hall_demend_detail_sc) {//收藏
                collection_Suplly(demend_id);

            } else if (v == hall_demend_lianxi) {//联系我们
                DialogUtils.showPhone(Hall_Demend_Details.this);
            }

        }
    };

    private void collection_Suplly(Long supp_id) {//点击收藏
        Collection_Supply_Body entity = new Collection_Supply_Body(DataWarehouse.getBaseRequest(this));
        entity.setNeedId(supp_id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<String> baseObserver = new BaseObserver<String>(Hall_Demend_Details.this) {

            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(Hall_Demend_Details.this, baseResponse);
                    ToastUtil.showShort(Hall_Demend_Details.this, "收藏成功");
                } else {
                    ToastUtil.showShort(Hall_Demend_Details.this, "收藏失败");
                }

            }
        };
        baseObserver.setStop(true);
        RetrofitHelper.getInstance(Hall_Demend_Details.this).getServer()
                .collectNeed(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(baseObserver);
    }
}
