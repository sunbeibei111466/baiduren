package com.yl.baiduren.activity.mypager;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yl.baiduren.R;
import com.yl.baiduren.adapter.My_Message_Adapter;
import com.yl.baiduren.base.BaseActivity;
import com.yl.baiduren.base.BaseEntity;
import com.yl.baiduren.base.BaseObserver;
import com.yl.baiduren.base.BaseRequest;
import com.yl.baiduren.data.DataWarehouse;
import com.yl.baiduren.entity.request_body.DeleteItem;
import com.yl.baiduren.entity.request_body.MyMessageEntity;
import com.yl.baiduren.entity.result.MyMessageResult;
import com.yl.baiduren.service.RetrofitHelper;
import com.yl.baiduren.utils.Constant;
import com.yl.baiduren.utils.LUtils;
import com.yl.baiduren.utils.ScreenUtil;
import com.yl.baiduren.utils.SecurityUtils;
import com.yl.baiduren.utils.SharedUtil;
import com.yl.baiduren.utils.ToastUtil;
import com.yl.baiduren.utils.UserInfoUtils;
import com.yl.baiduren.utils.Util;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/12/18.\
 * 我的消息
 */

public class My_Message extends BaseActivity {

    public static int pn = 1;
    public static int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;

    private MaterialRefreshLayout layout;
    private SwipeMenuRecyclerView recyclerView;
    private ImageView iv_my_message;
    private My_Message_Adapter adapter;
    private List<MyMessageResult> myMessageResults;

    @Override
    public int loadWindowLayout() {
        return R.layout.my_message;
    }

    @Override
    public void initViews() {

        SharedUtil.getSharedUtil().putBoolean(My_Message.this, Constant.IS_INFO, false);
        iv_my_message = findViewById(R.id.iv_my_message);
        iv_my_message.setOnClickListener(listener);
        layout = findViewById(R.id.message_layout);
        layout.setMaterialRefreshListener(materialRefreshListener);
        recyclerView = findViewById(R.id.message_recyclview);
        adapter = new My_Message_Adapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setSwipeMenuCreator(swipeMenuCreator);
        recyclerView.setSwipeMenuItemClickListener(swipeMenuItemClickListener);

    }

    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            SwipeMenuItem deleteItem = new SwipeMenuItem(My_Message.this)
                    .setBackground(R.color.red)
                    .setImage(R.drawable.delete)
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)//设置高，这里使用match_parent，就是与item的高相同
                    .setWidth(ScreenUtil.getScreenWidth(My_Message.this) / 5);//设置宽
            swipeRightMenu.addMenuItem(deleteItem);//设置右边的侧滑
        }
    };

    private SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            if (adapter.getMyMessageResults() != null && adapter.getMyMessageResults().size() != 0) {
                Long id = adapter.getMyMessageResults().get(menuBridge.getPosition()).getId();
                adapter.getMyMessageResults().remove(menuBridge.getPosition());
                delete(id);
                adapter.notifyDataSetChanged();
            }
        }
    };


    private void delete(Long id) {
        DeleteItem entity = new DeleteItem(DataWarehouse.getBaseRequest(this));
        entity.setId(id);
        String json = Util.toJson(entity);
        LUtils.e("json-----" + json);
        String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
        BaseObserver<String> observer = new BaseObserver<String>(this) {


            @Override
            protected void onSuccees(String code, String data, BaseRequest baseResponse) throws Exception {
                if (code.equals("1")) {
                    UserInfoUtils.setUuid(My_Message.this, baseResponse);
                    ToastUtil.showShort(My_Message.this, "删除成功");
                    layout.finishRefresh();
                    layout.finishRefreshLoadMore();
                }
            }

            @Override
            protected void onCodeError(String code, String mess) throws Exception {
                super.onCodeError(code, mess);
                layout.finishRefresh();
                layout.finishRefreshLoadMore();
            }
        };
        observer.setStop(true);
        RetrofitHelper.getInstance(this).getServer()
                .messageDelete(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                .compose(compose(this.<BaseEntity<String>>bindToLifecycle()))
                .subscribe(observer);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            layout.autoRefresh();
        }
    }

    private void requsetWork() {

        MyMessageEntity entity = new MyMessageEntity(DataWarehouse.getBaseRequest(this));
        if (UserInfoUtils.IsLogin(this)) {
            entity.setPageNo(pn);
            entity.setPageSize(ps);
            String json = Util.toJson(entity);
            LUtils.e("json-----" + json);
            String signature = SecurityUtils.md5Signature(json, SecurityUtils.privateKeyTest);//加密
            BaseObserver<List<MyMessageResult>> observer = new BaseObserver<List<MyMessageResult>>(this) {
                @Override
                protected void onSuccees(String code, List<MyMessageResult> data, BaseRequest baseResponse) throws Exception {
                    if (code.equals("1")) {
                        UserInfoUtils.setUuid(My_Message.this, baseResponse);
                        if (data.size() != 0) {
                            displayData(data);
                            hideImageView();
                        } else {
                            if (adapter.getList() != null && adapter.getList().size() != 0) {
                                hideImageView();
                            } else {
                                showImageView();
                            }
                        }
                        layout.finishRefresh();
                        layout.finishRefreshLoadMore();
                    }
                }
            };
            observer.setStop(true);
            RetrofitHelper.getInstance(this).getServer()
                    .messageQuery(signature, RequestBody.create(MediaType.parse(Constant.JSON), json))
                    .compose(compose(this.<BaseEntity<List<MyMessageResult>>>bindToLifecycle()))
                    .subscribe(observer);
        }
    }

    private void displayData(List<MyMessageResult> data) {

        if (status == REFRESH) {
            if (myMessageResults != null) {
                myMessageResults.clear();
                LUtils.e("-----myMessageResults---空------");
            }
            myMessageResults = data;
            adapter.setMyMessageResults(myMessageResults);
            recyclerView.setAdapter(adapter);
        }
        if (status == LOADMORE) {
            int size = data.size();
            List<MyMessageResult> dataListBeanList2 = data;
            this.myMessageResults.addAll(dataListBeanList2);
            adapter.setMyMessageResults(myMessageResults);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(size - 1);
            adapter.notifyDataSetChanged();
        }

        layout.finishRefresh();
        layout.finishRefreshLoadMore();
    }


    private MaterialRefreshListener materialRefreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            status = REFRESH;
            pn = 1;
            requsetWork();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            super.onRefreshLoadMore(materialRefreshLayout);
            status = LOADMORE;
            pn = pn + 1;
            requsetWork();
        }
    };


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == iv_my_message) {
                finish();
            }

        }
    };
}
