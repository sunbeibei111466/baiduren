package com.yl.baiduren.entity;

import java.util.List;

/**
 * Created by Android_apple on 2017/12/12.
 */

public class Area {


    /**
     * state : ok
     * message :
     */

    private String state;
    private String message;
    private List<DataBean> data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 3521
         * name : 北京
         * child : [{"id":3555,"name":"北京市","child":[{"id":3898,"name":"东城区","child":null},{"id":3899,"name":"西城区","child":null},{"id":3900,"name":"崇文区","child":null},{"id":3901,"name":"宣武区","child":null},{"id":3902,"name":"朝阳区","child":null},{"id":3903,"name":"丰台区","child":null},{"id":3904,"name":"石景山区","child":null},{"id":3905,"name":"海淀区","child":null},{"id":3906,"name":"门头沟区","child":null},{"id":3907,"name":"房山区","child":null},{"id":3908,"name":"通州区","child":null},{"id":3909,"name":"顺义区","child":null},{"id":3910,"name":"昌平区","child":null},{"id":3911,"name":"大兴区","child":null},{"id":3912,"name":"怀柔区","child":null},{"id":3913,"name":"平谷区","child":null},{"id":3914,"name":"密云县","child":null},{"id":3915,"name":"延庆县","child":null}]}]
         */

        private int id;
        private String name;
        private List<ChildBeanX> child;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChildBeanX> getChild() {
            return child;
        }

        public void setChild(List<ChildBeanX> child) {
            this.child = child;
        }

        public static class ChildBeanX {
            /**
             * id : 3555
             * name : 北京市
             * child : [{"id":3898,"name":"东城区","child":null},{"id":3899,"name":"西城区","child":null},{"id":3900,"name":"崇文区","child":null},{"id":3901,"name":"宣武区","child":null},{"id":3902,"name":"朝阳区","child":null},{"id":3903,"name":"丰台区","child":null},{"id":3904,"name":"石景山区","child":null},{"id":3905,"name":"海淀区","child":null},{"id":3906,"name":"门头沟区","child":null},{"id":3907,"name":"房山区","child":null},{"id":3908,"name":"通州区","child":null},{"id":3909,"name":"顺义区","child":null},{"id":3910,"name":"昌平区","child":null},{"id":3911,"name":"大兴区","child":null},{"id":3912,"name":"怀柔区","child":null},{"id":3913,"name":"平谷区","child":null},{"id":3914,"name":"密云县","child":null},{"id":3915,"name":"延庆县","child":null}]
             */

            private int id;
            private String name;
            private List<ChildBean> child;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ChildBean> getChild() {
                return child;
            }

            public void setChild(List<ChildBean> child) {
                this.child = child;
            }

            public static class ChildBean {
                /**
                 * id : 3898
                 * name : 东城区
                 * child : null
                 */

                private int id;
                private String name;
                private Object child;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getChild() {
                    return child;
                }

                public void setChild(Object child) {
                    this.child = child;
                }
            }
        }
    }
}
