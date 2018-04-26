package com.yl.baiduren.entity.result;

import java.util.List;

/**
 * Created by sunbeibei on 2018/3/6.
 */

public class Debt_Type_Result {
    private List<CategoryDO> clist;
    private List<TypesDO> tlist;

    public List<CategoryDO> getClist() {
        return clist;
    }

    public void setClist(List<CategoryDO> clist) {
        this.clist = clist;
    }

    public List<TypesDO> getTlist() {
        return tlist;
    }

    public void setTlist(List<TypesDO> tlist) {
        this.tlist = tlist;
    }
    public class CategoryDO {
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        /**
         * 类别名称

         */
        private String name;
        /**
         * 类别图片地址
         *
         * @return
         */
        private String image;
    }
    public class TypesDO {
        private Long id;
        private String name;
        //角标图片

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAngleimg() {
            return angleimg;
        }

        public void setAngleimg(String angleimg) {
            this.angleimg = angleimg;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        private String angleimg;
        //头像图片
        private String headimg;
    }



}
