package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/18.
 */

public class QueryDebtNameResult {

    private List<DebtNameResponse> list ;
    public static class DebtNameResponse{
        private Integer id;
        private String name;

        public DebtNameResponse(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public List<DebtNameResponse> getList() {
        return list;
    }

    public void setList(List<DebtNameResponse> list) {
        this.list = list;
    }
}
