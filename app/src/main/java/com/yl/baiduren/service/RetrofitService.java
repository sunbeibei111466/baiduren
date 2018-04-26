package com.yl.baiduren.service;


import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.entity.CallWxBean;
import com.yl.baiduren.entity.request_body.AssignmentEntity;
import com.yl.baiduren.entity.request_body.AuthorizeParam;
import com.yl.baiduren.entity.result.Apply_claim_Result;
import com.yl.baiduren.entity.result.Assert_Mortgae_Details_Relut;
import com.yl.baiduren.entity.result.Asset_Details_Result;
import com.yl.baiduren.entity.result.Authorization_Enterprise_Name;
import com.yl.baiduren.entity.result.Authouization_Apply_List;
import com.yl.baiduren.entity.result.Authouization_Confrim_List_Result;
import com.yl.baiduren.entity.result.CertifiedResult;
import com.yl.baiduren.entity.result.Create_Order_Result;
import com.yl.baiduren.entity.result.CreditReportEntity;
import com.yl.baiduren.entity.result.CurrencyLendingDetails_Result;
import com.yl.baiduren.entity.result.Debt1Result;
import com.yl.baiduren.entity.result.Debt2_CreditorsDemand;
import com.yl.baiduren.entity.result.DebtPersonList;
import com.yl.baiduren.entity.result.DebtPresonEntity;
import com.yl.baiduren.entity.result.Debt_Angle_Result;
import com.yl.baiduren.entity.result.Debt_Details_Result;
import com.yl.baiduren.entity.result.Debt_Type_Result;
import com.yl.baiduren.entity.result.Delist_Information_Result;
import com.yl.baiduren.entity.result.Demend_Details_Result;
import com.yl.baiduren.entity.result.HallListMode;
import com.yl.baiduren.entity.result.HomeDO;
import com.yl.baiduren.entity.result.LoginPassword;
import com.yl.baiduren.entity.result.MyMessageResult;
import com.yl.baiduren.entity.result.MyPager;
import com.yl.baiduren.entity.result.My_Bill_Details_Result;
import com.yl.baiduren.entity.result.My_Bill_Result;
import com.yl.baiduren.entity.result.My_Child_Accort_Result;
import com.yl.baiduren.entity.result.My_Delisting_Result;
import com.yl.baiduren.entity.result.My_Demend_Details_Result;
import com.yl.baiduren.entity.result.My_Record_Result;
import com.yl.baiduren.entity.result.My_Supply_Result;
import com.yl.baiduren.entity.result.Open_Member_Result;
import com.yl.baiduren.entity.result.PhysicalBorrowing_Details_Result;
import com.yl.baiduren.entity.result.Property_Rights_Result;
import com.yl.baiduren.entity.result.QueryDebtNameResult;
import com.yl.baiduren.entity.result.Recharge_Price_Result;
import com.yl.baiduren.entity.result.ReportPrice;
import com.yl.baiduren.entity.result.ReportResult;
import com.yl.baiduren.entity.result.Report_Result;
import com.yl.baiduren.entity.result.Sponsor_Details_Relult;
import com.yl.baiduren.entity.result.Supply_Demend_Details_Result;
import com.yl.baiduren.entity.result.Updata_Debt_Basic_Information;
import com.yl.baiduren.entity.result.UserResponse;
import com.yl.baiduren.entity.result.Verson_Code_Result;
import com.yl.baiduren.entity.result.ZhengXin_Result;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author 王锋 on 2017/11/8.
 */

public interface RetrofitService {

//    https://api.douban.com/v2/book/search?q=金瓶梅&tag=&start=0&count=1

    String authorization = "Authorization";

    /*
      @Query(GET请求): 注解 用于拼接参数
     */
//    @GET("book/search?")
    //name由调用者传入
//    Call<Back> getSearchBook(@Query("q") String name, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    /*
      @QueryMap(GET请求): 如果入参比较多，就可以把它们都放在Map中
     */
//    @GET("book/search?")
//    Call<Back> getSear(@QueryMap Map<String, String> options);

    /*
      @Path(GET请求): 替换url中某个字段
     * 在group和user之间有个不确定的id值需要传入，就可以这种方法。我们把待定的值字段用{}括起来，
     * 当然  {}里的名字不一定就是id，可以任取，但需和@Path后括号里的名字一样。如果在user后面还需要传入参数的话，就可以用Query拼接上
     */
//    @GET("group/{id}/user")
//    Call<Back> groupList(@Path("id") int groupId, @Query("sort") String sort);

    /*
      @Body(POST请求): 可以指定一个对象作为HTTP请求体, 比如：
     * 它会把我们传入的User实体类转换为用于传输的HTTP请求体，进行网络请求
     */
//    @POST("user/user")
//    Call<Back> createUser(@Body Back back);

    /*
      @Field(POST请求): 用于传送表单数据：
     * 注意开头必须多加上@FormUrlEncoded这句注释，不然会报错。
     * 表单自然是有多组键值对组成，这里的first_name就是键，而具体传入的first就是值啦。
     */
//    @FormUrlEncoded
//    @POST("user/edit")
//    Call<Back> updateUser(@Field("first_name") String first, @Field("last_name") String last);

    /*
      @Header/@Headers(POST请求): 用于添加请求头部：
     */
//    @POST("user/bb")
//    Call<Back> setAddHeader(@Query("name") String name, @Header("Authorization") String toke);

//    @Headers({"ContentType:text/html", "Authprzation:token"})
//    @POST("user/bc")
//    Call<Back> setAddHeaders(@Query("name") String name);

//    @GET("api/news/getNews?")
//    Call<NewsPager> getNewPage(@Query("pn") String pn, @Query("ps") String ps);

    /*
      @Text1 RxJava 与 Retiofit 结合使用时 返回Observable
     */
//    @GET("api/news/getNews?")
//    Observable<NewsPager> getNewPage1(@Query("pn") String pn, @Query("ps") String ps);


    /*
      @Text2 RxJava 与 Retiofit 结合使用时 返回Observable
     */
//    @GET("api/news/getNews?")
//    Observable<BaseEntity<NewsPager>> getNewPag2(@Query("pn") String pn, @Query("ps") String ps);

    /*
      @Text2 RxJava 与 Retiofit 结合使用时 返回Observable
     */
//    @GET("api/news/getNews?")
//    Observable<BaseEntity<News>> getNewPage3(@Query("pn") String pn, @Query("ps") String ps);

//    @GET("api/provide")
//    Observable<UserType> getUserType1(@Header(authorization) String toke);

    /*
      get请求加Header

      @param toke
     * @return
     */
//    @GET("api/provide")
//    Observable<BaseEntity<UserType>> getUserType2(@Header(authorization) String toke);

    /*
      post

      @FormUrlEncoded 传递表单数据
     */
//    @FormUrlEncoded
//    @POST("api/mypay/toPay")
//    Observable<BaseEntity<RechargeResult>> getOrderInfo(@Field("remark") String remark, @Field("amount") String amount, @Field("type") String type, @Header(authorization) String token);

    /* post + header
      传递Bean
     */
//    @POST("mobile")
//    @Headers({"Accept:application/json", "Content-Types:application/json"})
//    Observable<BaseEntity<LoginResult>> login(@Header(authorization) String token, @Body LoginInfo loginInfo);


    /*post + multipart
      上传单张图片
     */
//    @Multipart
//    @POST("api/regist/changeimage")
//    @Headers({"Content-Types:image/png"})
//    Observable<BaseEntity<String>> updataImage(@Header(authorization) String token, @Part("picName") RequestBody requestBody, @Part MultipartBody.Part picture);

//    @Multipart
//    @POST("api/image")
//    @Headers({"Content-Types:image/png"})
//    Observable<BaseEntity<List<String>>> updataImageS(@Header(authorization) String token, @Part List<MultipartBody.Part> list);

    /**
     * 注册接口
     */
    @POST("user/new/")
    Observable<BaseEntity<String>> regisierPhonr(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 密码登录
     */
    @POST("login/")
    Observable<BaseEntity<LoginPassword>> loginPassword(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 添加债事人/企业
     */
    @POST("debt/insert/")
    Observable<BaseEntity<String>> addDebt(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 查询债权人/债务人
     */
    @POST("debt/getDebtName/")
    Observable<BaseEntity<QueryDebtNameResult>> queryZsr(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 债事备案 第一步
     */
    @POST("debtrelation/insert/")
    Observable<BaseEntity<Debt1Result>> debt(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 债事备案 第二步
     */
    @POST("debtrelation/insert/")
    Observable<BaseEntity<String>> debt2(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 获取需求类别
     */
    @POST("category/getAllCategorys/")
    Observable<BaseEntity<Debt2_CreditorsDemand>> getCreditorsDemand(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的备案列表
     */
    @POST("drelation/getByUserId/")
    Observable<BaseEntity<My_Record_Result>> getDebtList(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 所有备案列表
     */
    @POST("drelation/getAllByPage/")
    Observable<BaseEntity<My_Record_Result>> getRecordDebtList(@Query("signature") String signature, @Body RequestBody requestBody);

    /*债事详情*/
    @POST("debtrelation/getDebtRelationDetail/")
    Observable<BaseEntity<Debt_Details_Result>> getDetailsResult(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 获取用户信息（我的页）
     */
    @POST("user/my/")
    Observable<BaseEntity<MyPager>> getUserInfo(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 修改个人资料
     */
    @POST("user/update/")
    Observable<BaseEntity<String>> updataUserInfo(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 修改密码
     */
    @POST("user/password/")
    Observable<BaseEntity<String>> updataUserPassword(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 修改债事详情基本信息
     */
    @POST("debtrelation/getDRBasicDetail/")
    Observable<BaseEntity<Updata_Debt_Basic_Information>> updataDebtDetail(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 修改手机号
     */
    @POST("user/mobile/")
    Observable<BaseEntity<String>> updataUserPhone(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 修改手机号
     */
    @POST("user/feedback")
    Observable<BaseEntity<String>> feedback(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 新增子账号
     */
    @POST("user/new/enterprise/")
    Observable<BaseEntity<String>> addChild(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 需求详情
     */
    @POST("demand/getDemand/")
    Observable<BaseEntity<Demend_Details_Result>> getDemendDetails(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 需求编辑
     */
    @POST("demand/update/")
    Observable<BaseEntity<String>> updataDemendDetails(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 资产详情
     */
    @POST("asset/getAsset/")
    Observable<BaseEntity<Asset_Details_Result>> getAssetDetails(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 资产抵押详情
     */
    @POST("mortgage/getMortgage/")
    Observable<BaseEntity<Assert_Mortgae_Details_Relut>> getAssetMortgageDetails(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 筛选查询
     */
    @POST("drelation/getByConditions/")
    Observable<BaseEntity<My_Record_Result>> getByConditions(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 条件查询
     */
    @POST("drelation/getByAccurate")
    Observable<BaseEntity<My_Record_Result>> getByAccurate(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 摘牌精确查询
     */
    @POST("drelation/getByAccurate")
    Observable<BaseEntity<My_Delisting_Result>> getBy2Accurate(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 债事人列表
     */
    @POST("debt/getMyDebt/")
    Observable<BaseEntity<DebtPersonList>> getDebtPresonList(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 第三个fragment 债事人列表
     */
    @POST("debt/getAboutMyDebt/")
    Observable<BaseEntity<DebtPersonList>> getThreePresonList(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 担保人详情
     */
    @POST("sponsor/getSponsor/")
    Observable<BaseEntity<Sponsor_Details_Relult>> getSponsorDetails(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 货币借贷详情
     */
    @POST("moneyLoan/getMoneyLoan/")
    Observable<BaseEntity<CurrencyLendingDetails_Result>> getMoneyLoanDetails(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 实物借贷详情
     */
    @POST("goodLoan/getGoodLoan/")
    Observable<BaseEntity<PhysicalBorrowing_Details_Result>> getGoodLoanDetails(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 产权借贷详情
     */
    @POST("propertyLoan/getPropertyLoan/")
    Observable<BaseEntity<Property_Rights_Result>> getPropertyLoanDetails(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 删除需求
     */
    @POST("demand/delete/")
    Observable<BaseEntity<String>> delete_Demand(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除资产
     */
    @POST("asset/delete/")
    Observable<BaseEntity<String>> delete_Assert(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除货币借贷
     */
    @POST("moneyLoan/delete/")
    Observable<BaseEntity<String>> delete_MoneyLoan(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除实物借贷
     */
    @POST("goodLoan/delete/")
    Observable<BaseEntity<String>> delete_GoodLoan(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除产权借贷
     */
    @POST("propertyLoan/delete/")
    Observable<BaseEntity<String>> delete_PropertyLoan(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除担保人
     */
    @POST("sponsor/delete/")
    Observable<BaseEntity<String>> delete_SponsorLoan(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除资产抵押
     */
    @POST("mortgage/delete/")
    Observable<BaseEntity<String>> delete_MortgageLoan(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 需求编辑
     */
    @POST("demand/update/")
    Observable<BaseEntity<String>> getDemandUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 需求编辑
     */
    @POST("asset/update/")
    Observable<BaseEntity<String>> getAssetUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 上传凭证 实物借贷编辑
     */
    @POST("moneyLoan/update/")
    Observable<BaseEntity<String>> getMoneyLoanUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 上传凭证 实物借贷编辑
     */
    @POST("goodLoan/update/")
    Observable<BaseEntity<String>> getGoodLoanUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 上传凭证 产权借贷编辑
     */
    @POST("propertyLoan/update")
    Observable<BaseEntity<String>> getPropertyLoanUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 上传凭证 担保人编辑
     */
    @POST("sponsor/update/")
    Observable<BaseEntity<String>> getSponsorUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 上传凭证 资产抵押编辑
     */
    @POST("mortgage/update/")
    Observable<BaseEntity<String>> getAssetMortgageUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 查询债市人详情接口
     */
    @POST("debt/getDebtDetail/")
    Observable<BaseEntity<DebtPresonEntity>> getDebtPerson_Details(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 进入摘牌获取信息
     */
    @POST("drelation/getByIdForOccupy/")
    Observable<BaseEntity<Delist_Information_Result>> getInfromation(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 生成订单
     */
    @POST("bill/create/")
    Observable<BaseEntity<Create_Order_Result>> create_Order(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 编辑债市人详情接口
     */
    @POST("debt/update/")
    Observable<BaseEntity<String>> getDebtPersonUpdata(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的摘牌列表
     */
    @POST("drelation/getBySettleId/")
    Observable<BaseEntity<My_Delisting_Result>> getgetBySettle(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 开通会员信息
     */
    @POST("user/userToVip/")
    Observable<BaseEntity<Open_Member_Result>> getUserToVip(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 上传图片
     */
    @Multipart
    @POST("image/upload/")
    Observable<BaseEntity<List<String>>> getImage(@Query("signature") String signature, @Query("uid") Long uid, @Part List<MultipartBody.Part> list);

    /**
     * 我的摘牌确认完成
     */

    @POST("debtrelation/completed/")
    Observable<BaseEntity<String>> complated(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 查询债事人
     */
    @POST("debt/getMyDebt")
    Observable<BaseEntity<DebtPersonList>> getMyDebt(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的账单列表
     */
    @POST("/bill/mybill/")
    Observable<BaseEntity<My_Bill_Result>> getMyBill(@Query("signature") String signature, @Body RequestBody requestBody);
       /**
     * 我的订单详情
     */
    @POST("bill/getBill/")
    Observable<BaseEntity<My_Bill_Details_Result>> getMyBill_Details(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 取消订单
     */
    @POST("bill/cancel/")
    Observable<BaseEntity<String>>cancle_Bill(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 删除订单
     */
    @POST("bill/delete/")
    Observable<BaseEntity<String>> delete_Bill(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 成功案例
     */
    @POST("drelation/getSuccessDrelation/")
    Observable<BaseEntity<My_Record_Result>>debt_Sussful(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的账单列表
     */
    @POST("user/validCode/")
    Observable<BaseEntity<String>> getValidCode(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 验证码登陆
     */
    @POST("user/mobileLogin/")
    Observable<BaseEntity<LoginPassword>> getMobileLogin(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 验证码登陆
     */
    @POST("user/forgetPassword/")
    Observable<BaseEntity<String>> getForgetPassword(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 验证码登陆
     */
    @POST("debt/getTotal/")
    Observable<BaseEntity<String>> getTotal();
    /**
     * 子账号列表
     */
    @POST("user/getChildUsers/")
    Observable<BaseEntity<My_Child_Accort_Result>> getChildUser(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 删除子账号
     */
    @POST("user/deleteChildUsers/")
    Observable<BaseEntity<String>> deleteChildUsers(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 备案充值
     */
    @POST("bill/getDrSinglePrice/")
    Observable<BaseEntity<Recharge_Price_Result>>getDrSingle(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 新增修改债权转让
     */
    @POST("claims/saveOrUpdate/")
    Observable<BaseEntity<String>> getSaveOrUpdate(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 查询债权转让列表
     */
    @POST("claims/query/")
    Observable<BaseEntity<List<HallListMode>>> getClaimsQuery(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 查询债权转让详情
     */
    @POST("claims/detail/")
    Observable<BaseEntity<AssignmentEntity>> getClaimsDetail(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 删除债权转让item
     */
    @POST("claims/delete/")
    Observable<BaseEntity<String>> getClaimsDelete(@Query("signature") String signature, @Body RequestBody requestBody);



    /**
     * 获取首页信息
     */
    @POST("home/getHome/")
    Observable<BaseEntity<HomeDO>> getHome(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 获取首页信息
     */
    @POST("home/getHome/")
    Observable<BaseEntity<HomeDO>> getHome();
    /**
     * 申请解债人
     */
    @POST("applicant/apply/")
    Observable<BaseEntity<Apply_claim_Result>> getAplly(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 上传供应
     */
    @POST("supply/insert/")
    Observable<BaseEntity<String>> upLoadSupply(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 编辑供应
     */
    @POST("supply/update/")
    Observable<BaseEntity<String>> updataSupply(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除供应
     */
    @POST("supply/delete/")
    Observable<BaseEntity<String>> deleteSupply(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的供应列表
     */
    @POST("supply/getMySupply/")
    Observable<BaseEntity<My_Supply_Result>> mineSupply(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 大厅供应列表
     */
    @POST("supply/getSupplys/")
    Observable<BaseEntity<My_Supply_Result>> getHallSupply(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 大查看供应详情
     */
    @POST("supply/getSupplyDetail/")
    Observable<BaseEntity<Supply_Demend_Details_Result>> getSupplyDetail(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 收藏供应
     */
    @POST("supply/collectSupply/")
    Observable<BaseEntity<String>>collectSupply(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的供应收藏列表
     */
    @POST("supply/getMyCollectSupplys/")
    Observable<BaseEntity<My_Supply_Result>>getMycollectSupply(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 取消收藏供应
     */
    @POST("supply/cancelCollectSupply/")
    Observable<BaseEntity<String>>cancleCollectSupply(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 添加需求
     */
    @POST("need/insert/")
    Observable<BaseEntity<String>>upload_Demend(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的需求列表
     */
    @POST("need/getMyNeed/")
    Observable<BaseEntity<My_Supply_Result>>getMyNeed(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 删除需求
     */
    @POST("need/delete/")
    Observable<BaseEntity<String>>delete_Demend(@Query("signature") String signature, @Body RequestBody requestBody);



    /**
     * 编辑需求
     */
    @POST("need/update/")
    Observable<BaseEntity<String>>updataDemend(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 获取需求详情
     */
    @POST("need/getNeedDetail/")
    Observable<BaseEntity<My_Demend_Details_Result>>get_Demend_Details(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 大厅需求列表
     * */
    @POST("need/getNeeds/")
    Observable<BaseEntity<My_Supply_Result>>getHall_Demend(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 大厅需求收藏
     * */
    @POST("need/collectNeed/")
    Observable<BaseEntity<String>>collectNeed(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 我的收藏需求列表
     * */
    @POST("need/getMyCollectNeeds/")
    Observable<BaseEntity<My_Supply_Result>>getMyCollectNeeds(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     * 取消我的需求收藏
     * */
    @POST("need/cancelCollectNeed/")
    Observable<BaseEntity<String>>cancleCollectNeeds(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 需求匹配供应
     * */
    @POST("need/getMathes/")
    Observable<BaseEntity<My_Supply_Result>>need_Mathes(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 供应匹配需求
     * */
    @POST("supply/getMathes/")
    Observable<BaseEntity<My_Supply_Result>>supply_Mathes(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的信息
     * */
    @POST("message/query/")
    Observable<BaseEntity<List<MyMessageResult>>> messageQuery(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 删除我的信息
     * */
    @POST("message/delete/")
    Observable<BaseEntity<String>> messageDelete(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 新增资产处置
     * */
    @POST("supply/insert/")
    Observable<BaseEntity<Long>> insertSupply_Asstes(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 修改资产处置
     * */
    @POST("supply/update/")
    Observable<BaseEntity<Long>> updateSupply_Asstes(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 我的资产处置列表
     * */
    @POST("supply/getMySupply/")
    Observable<BaseEntity<My_Supply_Result>> getMySupply(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 资产处置详情
     * */
    @POST("supply/getSupplyDetail/")
    Observable<BaseEntity<Supply_Demend_Details_Result>> getAsstesSupplyDetail(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 资产处置大厅列表
     * */
    @POST("supply/getSupplys/")
    Observable<BaseEntity<My_Supply_Result>> getSupplys(@Query("signature") String signature, @Body RequestBody requestBody);


    /**
     * 删除资产处置
     * */
    @POST("supply/delete/")
    Observable<BaseEntity<String>> delete(@Query("signature") String signature, @Body RequestBody requestBody);



    /**
     * 获取资产处置 报告价格
     * */
    @POST("supply/getReportPrice/")
    Observable<BaseEntity<ReportPrice>> getReportPrice(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 获取债事类别，资产类别
     * */
    @POST("category/getAllConditions/")
    Observable<BaseEntity<Debt_Type_Result>> getAlltype(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 支付宝支付 创建支付订单
     */
    @POST("pay/createPay/")
    Observable<BaseEntity<String>> getCreatePay(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 微信支付 创建支付订单
     */
    @POST("pay/createPay/")
    Observable<BaseEntity<CallWxBean>> getWxCreatePay(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     *解债天使
     * */
    @POST("user/debtAngel/")
    Observable<BaseEntity<Debt_Angle_Result>>debtAngel(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 获取征信报告类型
     * */
    @POST("credit/toCredit")
    Observable<BaseEntity<CreditReportEntity>> getReportType(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 判断是否认证
     * */
    @POST("authenticate/authenticate")
    Observable<BaseEntity<CertifiedResult>> whetherCertified(@Query("signature") String signature, @Body RequestBody requestBody);

       /**
     * 证信申请授权
     * */
    @POST("authorize/insert")
    Observable<BaseEntity<AuthorizeParam>> authenticate(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 申请授权回显信息
     * */
    @POST("authorize/getUser")
    Observable<BaseEntity<UserResponse>> getauthenticate(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     * 根据授权码查询企业名称接口
     * */
    @POST("authorize/getNameByAuthCode")
    Observable<BaseEntity<Authorization_Enterprise_Name>> getNameByAuthCode(@Query("signature") String signature, @Body RequestBody requestBody);



    /**
     * 我的报告列表
     * */
    @POST("credit/report")
    Observable<BaseEntity<ReportResult>> getMyReport(@Query("signature") String signature, @Body RequestBody requestBody);

//     /mobilesafe/shouji360/360safesis/360MobileSafe_6.2.3.1060.apk
//    @GET("/help/videocourse/readme.html")
//    Call<ResponseBody> retrofitDownload();

//    @GET("/disk/home?#/category?type=4&vmode=list")
//    Call<ResponseBody> retrofitDownload();
    //bc_bg_6D40C91A170D41C182511ABBB8A634A4.pdf

    @GET("credit/showStream?reportId=4")
    Call<ResponseBody> retrofitDownload();

    /**
     *实名认证
     * */
    @POST("authenticate/insert/")
    Observable<BaseEntity<String>>certification(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     *授权确认列表
     * */
    @POST("authorize/getAcceptAuths/")
    Observable<BaseEntity<Authouization_Confrim_List_Result>>authorize_confirm(@Query("signature") String signature, @Body RequestBody requestBody);
    /**
     *查询授权申请列表
     * */
    @POST("authorize/getSendAuths/")
    Observable<BaseEntity<Authouization_Apply_List>>querry_Authorize_List(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     *回复授权
     * */
    @POST("authorize/reply/")
    Observable<BaseEntity<String>>reply_Authorize(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     *征信查询模糊
     * */
    @POST("/credit/fuzzy/")
    Observable<BaseEntity<Report_Result>>report_Query(@Query("signature") String signature, @Body RequestBody requestBody);

       /**
     *征信查询页面
     * */
    @POST("/credit/toNewCredit/")
    Observable<BaseEntity<ZhengXin_Result>>zheng_Xing(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     *征信查询页面
     * */
    @POST("/user/version/")
    Observable<BaseEntity<Verson_Code_Result>>getVersion(@Query("signature") String signature, @Body RequestBody requestBody);

    /**
     *验证验证码接口
     * */
    @POST("/credit/validCode/")
    Observable<BaseEntity<String>>yan_Zheng_Code(@Query("signature") String signature, @Body RequestBody requestBody);

}

