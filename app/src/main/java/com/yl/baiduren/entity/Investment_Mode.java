package com.yl.baiduren.entity;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/7.
 */

public class Investment_Mode {

    private String projectName;
    private List<Chlie> chlieList;

    public Investment_Mode() {
    }

    public Investment_Mode(String projectName, List<Chlie> chlieList) {
        this.projectName = projectName;
        this.chlieList = chlieList;
    }

    public static class Chlie {
        private String gudName;
        private String jine;
        private String zhanbi;


        public Chlie(String gudName, String jine, String zhanbi) {
            this.gudName = gudName;
            this.jine = jine;
            this.zhanbi = zhanbi;
        }

        public String getGudName() {
            return gudName;
        }

        public void setGudName(String gudName) {
            this.gudName = gudName;
        }

        public String getJine() {
            return jine;
        }

        public void setJine(String jine) {
            this.jine = jine;
        }

        public String getZhanbi() {
            return zhanbi;
        }

        public void setZhanbi(String zhanbi) {
            this.zhanbi = zhanbi;
        }
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Chlie> getChlieList() {
        return chlieList;
    }

    public void setChlieList(List<Chlie> chlieList) {
        this.chlieList = chlieList;
    }
}
