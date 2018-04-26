package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/3/29.
 */

public class Authouization_Apply_List {
    private List<AuthorizeResponse>dataList;

    public List<AuthorizeResponse> getDataList() {
        return dataList;
    }

    public void setDataList(List<AuthorizeResponse> dataList) {
        this.dataList = dataList;
    }
    public static class AuthorizeResponse{
        private Long id;
    /* 申请主体名称*/
        private String sendName;
        /**
         * 申请时间
         */
        private Long createTime;
        /**
         * 状态 1.待确认；2.已确认；3.已拒绝；
         */
        private int status;
        /**
         * 信息主体名称
         */
        private String searchName;
        /**
         * 授权码
         */
        private String authCode;
        /**
         * 回复原因
         */
        private String replyReason;
        /**
         * 申请原因
         */
        private String sendReason;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getSendName() {
            return sendName;
        }

        public void setSendName(String sendName) {
            this.sendName = sendName;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getSearchName() {
            return searchName;
        }

        public void setSearchName(String searchName) {
            this.searchName = searchName;
        }

        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public String getReplyReason() {
            return replyReason;
        }

        public void setReplyReason(String replyReason) {
            this.replyReason = replyReason;
        }

        public String getSendReason() {
            return sendReason;
        }

        public void setSendReason(String sendReason) {
            this.sendReason = sendReason;
        }
    }
}
