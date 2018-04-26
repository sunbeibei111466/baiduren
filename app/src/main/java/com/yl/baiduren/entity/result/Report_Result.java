package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/4/11.
 */

public class Report_Result {

    /**
     * data : {"entNameList":["中国银行嘉峪关分行","中国银行秦皇岛分行","中国银行沙坪坝支行","中国银行文福办事处","中国银行原广饶支行","中国银行驻马店分行","尹惠伍--中国银行旁","中国银行安达支行","中国银行安福支行","中国银行安康分行","中国银行安丘支行","中国银行安县支行","中国银行安乡支行","中国银行安新支行","中国银行安岳支行","中国银行霸州支行","中国银行滨州分行","中国银行博白支行","中国银行博罗支行","中国银行苍山支行"]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<String> entNameList;

        public List<String> getEntNameList() {
            return entNameList;
        }

        public void setEntNameList(List<String> entNameList) {
            this.entNameList = entNameList;
        }
    }
}
