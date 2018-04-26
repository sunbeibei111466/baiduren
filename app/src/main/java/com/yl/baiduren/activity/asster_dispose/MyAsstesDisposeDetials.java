package com.yl.baiduren.activity.asster_dispose;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.yl.baiduren.Downlod_Pdf_Activity;
import com.yl.baiduren.R;
import com.yl.baiduren.activity.credit_reporting_queries.Conpany_RenZheng;
import com.yl.baiduren.activity.credit_reporting_queries.ZhengXing_Query;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.DeleteItem;
import com.yl.baiduren.entity.request_body.My_Bill_Body;
import com.yl.baiduren.entity.request_body.SupplyDO;
import com.yl.baiduren.entity.request_body.Supply_Demend_Details_Id;
import com.yl.baiduren.entity.result.ReportResult;
import com.yl.baiduren.entity.result.Supply_Demend_Details_Result;
import com.yl.baiduren.service.DownloadProgressHandler;
import com.yl.baiduren.service.ProgressHelper;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.service.RetrofitService;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.PopupWindeUtils;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 我的资产详情
 */
public class MyAsstesDisposeDetials extends BaseActivity {

    private ImageView left_image;
    private ImageView right_image;
    private ImageView dispose_asset_detials_back;
    private ImageView my_detail_image_detials;
    private String[] image = new String[]{};
    private int current = 0;
    private TextView tv_xy_baogao;
    private Long id;
    private Button bt_delete;
    private Button bt_updata;
    private TextView tv_name, tv_guzhi, tv_pgjg, tv_gsd;
    private TextView my_asstes_d_text;
    private Supply_Demend_Details_Result demendDetailsResult;
    private TextView tv_sl;
    private TextView tv_xy;
    private boolean isuyCredit = false;//是否购买报告
    private int reportId = 0;//报告id
    private boolean authCompany = false;//是否认正企业
    private TextView select_baogao;
    private Integer reporid = null;
    private Long assId;
    private String conpany_name;
    private int type = 0;
    private String reportUrl;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_my_asstes_dispose_detials;
    }

    @Override
    public void initViews() {
        id = getIntent().getLongExtra("id", 0);
        tv_name = findViewById(R.id.tv_name);//名称
        tv_guzhi = findViewById(R.id.tv_num);//估值
        tv_pgjg = findViewById(R.id.tv_pgjg);//评估机构
        tv_gsd = findViewById(R.id.tv_gsd);//归属地
        tv_sl = findViewById(R.id.tv_sl); //数量
        my_asstes_d_text = findViewById(R.id.my_asstes_d_text);//详细信息
        dispose_asset_detials_back = findViewById(R.id.my_dispose_asset_detials_back);
        dispose_asset_detials_back.setOnClickListener(listener);
        my_detail_image_detials = findViewById(R.id.my_detail_image_detials);
        left_image = findViewById(R.id.my_left_image);//左箭头
        left_image.setOnClickListener(listener);
        right_image = findViewById(R.id.my_right_image);//右箭头
        right_image.setOnClickListener(listener);

        tv_xy_baogao = findViewById(R.id.tv_xy_baogao);//信用报告
        tv_xy_baogao.setOnClickListener(listener);
        tv_xy = findViewById(R.id.tv_xy);//信用报告是否购买
        select_baogao = findViewById(R.id.select_baogao);
        select_baogao.setOnClickListener(listener);
        TextView tv_pg_baogao = findViewById(R.id.tv_pg_baogao);
        tv_pg_baogao.setOnClickListener(listener);
        TextView tv_gz = findViewById(R.id.tv_gz);
        bt_delete = findViewById(R.id.bt_delete);//删除
        bt_delete.setOnClickListener(listener);
        bt_updata = findViewById(R.id.bt_updata);//修改
        bt_updata.setOnClickListener(listener);


    }

    @Override
    protected void onResume() {
        super.onResume();
        left_image.setVisibility(View.GONE);
        type = 0;
        if (id != 0) {
            requestWork();
        }
    }

    private void requestWork() {
        Supply_Demend_Details_Id entity = new Supply_Demend_Details_Id(DataWarehouse.getBaseRequest(this));
        entity.setId(id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);

        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<Supply_Demend_Details_Result> baseObserver = new BaseObserver<Supply_Demend_Details_Result>(this) {

            @Override
            protected void onSuccees(String code, Supply_Demend_Details_Result data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(MyAsstesDisposeDetials.this, baseResponse);
                    if (type == 1) {
                        ToastUtil.showShort(MyAsstesDisposeDetials.this, "更改报告成功");
                    } else if (type == 2) {
                        ToastUtil.showShort(MyAsstesDisposeDetials.this, "选择报告成功");
                    }
                    demendDetailsResult = data;
                    if (data.getImg() == null) {
                        right_image.setVisibility(View.GONE);
                        left_image.setVisibility(View.GONE);
                    }
                    if (data.getImg() != null) {
                        image = data.getImg().split(",");
                        LUtils.e(image.length + "数组测大小");
                        Glide.with(MyAsstesDisposeDetials.this).load(image[0]).
                                placeholder(R.drawable.banner).error(R.drawable.banner).into(my_detail_image_detials);
                        left_image.setVisibility(View.GONE);
                        if (image.length == 1) {
                            right_image.setVisibility(View.GONE);
                        }
                    }
                    Long assid = data.getId();
                    assId = assid;
                    String companyName = data.getCompanyName();
                    conpany_name = companyName;


                    tv_name.setText(data.getName());
                    tv_guzhi.setText(data.getValueStr());
                    tv_pgjg.setText(data.getEvaluation());
                    tv_gsd.setText(data.getAreaName());
                    my_asstes_d_text.setText(data.getDescribe());
                    tv_sl.setText(data.getNum() + "");

                    isuyCredit = data.getBuyCredit();//是否购买报告

                    authCompany = data.getAuthCompany();//是否认正企业
                    Integer genre = data.getGenre();
                    if (genre.equals(1)) {
                        tv_xy.setVisibility(View.GONE);
                        tv_xy_baogao.setVisibility(View.GONE);
                        select_baogao.setVisibility(View.GONE);
                    }

                    if (isuyCredit) {
                        tv_xy.setText("信用报告(已购买)");
                        tv_xy_baogao.setText("预览");
                        select_baogao.setText("更改报告");
                        type = 1;

                    } else {
                        select_baogao.setText("选择报告");
                        tv_xy.setText("信用报告(未购买)");
                        tv_xy_baogao.setText("点击购买");
                        type = 2;

                    }
                    reportId = data.getReportId();//报告id
                    reportUrl = data.getReportUrl();


                }
            }
        };

        RetrofitHelper.getInstance(this).getServer()
                .getSupplyDetail(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<Supply_Demend_Details_Result>>bindToLifecycle()))
                .subscribe(baseObserver);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == dispose_asset_detials_back) {
                finish();
            } else if (v == left_image) {//左箭头
                setLeftImage();
            } else if (v == right_image) {//右箭头
                setRghtImage();
            } else if (v == tv_xy_baogao) {//信用报告
                if (tv_xy_baogao.getText().toString().equals("点击购买")) {
                    if (authCompany) {//判断是否认证企业
                        startActivity(new Intent(MyAsstesDisposeDetials.this, ZhengXing_Query.class).putExtra("assid", assId).putExtra("conpany", conpany_name));
                    } else {
                        ToastUtil.showShort(MyAsstesDisposeDetials.this, "请先进行企业认证后再购买");
                        startActivity(new Intent(MyAsstesDisposeDetials.this, Conpany_RenZheng.class));
                    }
//                    startActivity(new Intent(MyAsstesDisposeDetials.this, CreditReportPage.class).putExtra("from","ass_details"));

                } else {//
                    Intent intent = new Intent(MyAsstesDisposeDetials.this, Downlod_Pdf_Activity.class);
                    intent.putExtra("reportUrl", reportUrl);
                    intent.putExtra("name", conpany_name);
                    startActivity(intent);

                }
            }
//            else if (v == tv_pg_baogao) {//评估报告
//                if (tv_pg_baogao.getText().toString().equals("点击购买")) {
//                    startActivity(new Intent(MyAsstesDisposeDetials.this, Purchase_Report.class).putExtra("from","ass_details"));
//                } else {
//                    File file=new File(ImageUtils.getReportUrl("估值报告").getPath());
//                    if(file!=null){
//                        retrofitDownload(file);
//                    }else {
//                        LUtils.e("--评估--flie是空");
//                    }
//                }
//            }
            else if (v == bt_delete) {//删除
                delete(id);
            } else if (v == bt_updata) {//编辑
                updata();
            } else if (v == select_baogao) {//选择报告
                ask();
            }
        }
    };

    private void ask() {
        if (UserInfoUtils.IsLogin(this)) {
            My_Bill_Body entity = new My_Bill_Body(DataWarehouse.getBaseRequest(this));
            int pn = 1;
            entity.setPageNo(pn);
            int ps = 100;
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<ReportResult> baseObserver = new BaseObserver<ReportResult>(this) {

                @Override
                protected void onSuccees(String code, ReportResult data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(MyAsstesDisposeDetials.this, baseResponse);
                        List<ReportResult.Report> dataList = data.getDataList();
                        if (dataList.size() != 0) {
                            PopupWindeUtils.queryPopupWinde2(MyAsstesDisposeDetials.this, dataList, new PopupWindeUtils.OnClickListView() {
                                @Override
                                public void onStringClick(Integer id, String name) {
                                    reporid = id;
                                    getHttpUpLoad(id);

                                }
                            });
                        }

                    }

                }
            };
            baseObserver.setStop(true);
            RetrofitHelper.getInstance(this).getServer()
                    .getMyReport(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<ReportResult>>bindToLifecycle()))
                    .subscribe(baseObserver);

        } else {
            finish();
        }
    }

    private void getHttpUpLoad(Integer reportId) {//更改报告

        if (UserInfoUtils.IsLogin(this)) {
            SupplyDO supplyDO = new SupplyDO(DataWarehouse.getBaseRequest(this));
            supplyDO.setReportId(reportId);
            supplyDO.setId(assId);
            supplyDO.setType(3);
            String json = Util.toJson(supplyDO);//转成json

            LUtils.e("---json--", json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
            Observable<BaseEntity<Long>> observable = null;
            observable = retrofitService.updateSupply_Asstes(signature, RequestBody.create(MediaType.parse(Constant.JSON), json));
            observable.compose(compose(this.<BaseEntity<Long>>bindToLifecycle()))
                    .subscribe(new BaseObserver<Long>(this) {
                        @Override
                        protected void onSuccees(String code, Long data, BaseRequest baseResponse) throws Exception {
                            if (code.equals("1")) {
                                requestWork();
                            }
                        }
                    });
        }
    }


    private void updata() {
        Intent intent = new Intent(MyAsstesDisposeDetials.this, AddAssetsDispose.class);
        intent.putExtra("demendDetailsResult", demendDetailsResult);
        startActivity(intent);
    }

    private void delete(Long id) {
        DeleteItem deleteItem = new DeleteItem(DataWarehouse.getBaseRequest(this));
        deleteItem.setId(id);
        String json = Util.toJson(deleteItem);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        RetrofitService retrofitService = RetrofitHelper.getInstance(this).getServer();
        retrofitService.delete(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(new BaseObserver<String>(MyAsstesDisposeDetials.this) {
                    @Override
                    protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                        if (code.equals("1")) {
                            ToastUtil.showShort(MyAsstesDisposeDetials.this, "删除成功");
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

    private void setRghtImage() {

        ++current;
        Glide.with(MyAsstesDisposeDetials.this).load(image[current]).
                placeholder(R.drawable.banner).
                error(R.drawable.banner).into(my_detail_image_detials);
        if (current == image.length - 1) {//右箭头消失
            right_image.setVisibility(View.GONE);
            left_image.setVisibility(View.VISIBLE);
            return;
        } else if (current > 0) {
            left_image.setVisibility(View.VISIBLE);
        }
    }

    private void setLeftImage() {

        --current;
        Glide.with(MyAsstesDisposeDetials.this).load(image[current]).
                placeholder(R.drawable.banner).
                error(R.drawable.banner).into(my_detail_image_detials);
        if (current == 0) {//左箭头消失
            left_image.setVisibility(View.GONE);
            right_image.setVisibility(View.VISIBLE);
            return;
        } else if (current < image.length) {
            right_image.setVisibility(View.VISIBLE);
        }


    }

    private void retrofitDownload(final File file) {
        //监听下载进度
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载中");
        dialog.setMessage("报告将下载到手机根目录下的zhenxing_report文件夹中");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .baseUrl("http://192.168.5.113:8080/");
//        "http://192.168.5.113:8080/credit/showStream?reportId="
//        http://msoftdl.360.cn
//        "https://geekori.com"
        //https://pan.baidu.com/
//        https://pic.bincrea.com/
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        RetrofitService retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(RetrofitService.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                LUtils.e("是否在主线程中运行", String.valueOf(Looper.getMainLooper() == Looper.myLooper()));
                LUtils.e("onProgress", String.format("%d%% done\n", (100 * bytesRead) / contentLength));
                LUtils.e("done", "--->" + String.valueOf(done));
                dialog.setMax((int) (contentLength / 1024));
                dialog.setProgress((int) (bytesRead / 1024));

                if (done) {
                    Toast.makeText(MyAsstesDisposeDetials.this, "已下载到zhenxing_report文件夹中", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });

        Call<ResponseBody> call = retrofit.retrofitDownload();
        call.enqueue(new Callback<ResponseBody>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    LUtils.e("--挂载---存储路径-----", file.getAbsolutePath());
                    InputStream is = response.body().byteStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
