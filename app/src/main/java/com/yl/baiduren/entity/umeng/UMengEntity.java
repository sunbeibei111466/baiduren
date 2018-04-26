package com.yl.baiduren.entity.umeng;

/**
 * Created by Android_apple on 2018/1/24.
 */

public class UMengEntity {


    /**
     * a : {"nameValuePairs":{"display_type":"notification","extra":{"nameValuePairs":{"userName":"wangfeng"}},"msg_id":"usshw8c151678964043410","body":{"nameValuePairs":{"after_open":"go_custom","play_lights":"false","ticker":"自定义title","play_vibrate":"true","custom":"自定义内容-----行为","text":"自定义内容","title":"自定义title","play_sound":"true"}},"random_min":0}}
     * activity :
     * after_open : go_custom
     * alias :
     * builder_id : 0
     * clickOrDismiss : true
     * custom : 自定义内容-----行为
     * display_type : notification
     * extra : {"userName":"wangfeng"}
     * icon :
     * img :
     * largeIcon :
     * msg_id : usshw8c151678964043410
     * play_lights : false
     * play_sound : true
     * play_vibrate : true
     * pulled_package :
     * pulled_service :
     * random_min : 0
     * screen_on : false
     * sound :
     * text : 自定义内容
     * ticker : 自定义title
     * title : 自定义title
     * url :
     */

    private ABean a;
    private String activity;//通知栏点击后打开的Activity
    /**
     * 点击"通知"的后续行为，默认为打开app。
     * "go_app": 打开应用
     * "go_url": 跳转到URL
     * "go_activity": 打开特定的activity
     * "go_custom": 用户自定义内容。
     */
    private String after_open;
    private String alias;
    private int builder_id;// 自定义通知样式:
    private boolean clickOrDismiss;
    private String custom;
    private String display_type;//必填 消息类型，值可以为: notification-通知，message-消息
    private ExtraBeanX extra;
    private String icon;// 自定义通知图标:
    private String img;
    private String largeIcon;
    private String msg_id;
    private boolean play_lights;
    private boolean play_sound;
    private boolean play_vibrate;
    private String pulled_package;
    private String pulled_service;
    private int random_min;
    private boolean screen_on;
    private String sound;// 自定义通知声音:
    private String text;
    private String ticker;
    private String title;
    /**
     * 可选 当"after_open"为"go_url"时，必填。
     * 通知栏点击后跳转的URL，要求以http或者https开头
     */
    private String url;

    public ABean getA() {
        return a;
    }

    public void setA(ABean a) {
        this.a = a;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAfter_open() {
        return after_open;
    }

    public void setAfter_open(String after_open) {
        this.after_open = after_open;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getBuilder_id() {
        return builder_id;
    }

    public void setBuilder_id(int builder_id) {
        this.builder_id = builder_id;
    }

    public boolean isClickOrDismiss() {
        return clickOrDismiss;
    }

    public void setClickOrDismiss(boolean clickOrDismiss) {
        this.clickOrDismiss = clickOrDismiss;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public ExtraBeanX getExtra() {
        return extra;
    }

    public void setExtra(ExtraBeanX extra) {
        this.extra = extra;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public boolean isPlay_lights() {
        return play_lights;
    }

    public void setPlay_lights(boolean play_lights) {
        this.play_lights = play_lights;
    }

    public boolean isPlay_sound() {
        return play_sound;
    }

    public void setPlay_sound(boolean play_sound) {
        this.play_sound = play_sound;
    }

    public boolean isPlay_vibrate() {
        return play_vibrate;
    }

    public void setPlay_vibrate(boolean play_vibrate) {
        this.play_vibrate = play_vibrate;
    }

    public String getPulled_package() {
        return pulled_package;
    }

    public void setPulled_package(String pulled_package) {
        this.pulled_package = pulled_package;
    }

    public String getPulled_service() {
        return pulled_service;
    }

    public void setPulled_service(String pulled_service) {
        this.pulled_service = pulled_service;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public boolean isScreen_on() {
        return screen_on;
    }

    public void setScreen_on(boolean screen_on) {
        this.screen_on = screen_on;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ABean {
        /**
         * nameValuePairs : {"display_type":"notification","extra":{"nameValuePairs":{"userName":"wangfeng"}},"msg_id":"usshw8c151678964043410","body":{"nameValuePairs":{"after_open":"go_custom","play_lights":"false","ticker":"自定义title","play_vibrate":"true","custom":"自定义内容-----行为","text":"自定义内容","title":"自定义title","play_sound":"true"}},"random_min":0}
         */

        private NameValuePairsBeanXX nameValuePairs;

        public NameValuePairsBeanXX getNameValuePairs() {
            return nameValuePairs;
        }

        public void setNameValuePairs(NameValuePairsBeanXX nameValuePairs) {
            this.nameValuePairs = nameValuePairs;
        }

        public static class NameValuePairsBeanXX {
            /**
             * display_type : notification
             * extra : {"nameValuePairs":{"userName":"wangfeng"}}
             * msg_id : usshw8c151678964043410
             * body : {"nameValuePairs":{"after_open":"go_custom","play_lights":"false","ticker":"自定义title","play_vibrate":"true","custom":"自定义内容-----行为","text":"自定义内容","title":"自定义title","play_sound":"true"}}
             * random_min : 0
             */

            private String display_type;
            private ExtraBean extra;
            private String msg_id;
            private BodyBean body;
            private int random_min;

            public String getDisplay_type() {
                return display_type;
            }

            public void setDisplay_type(String display_type) {
                this.display_type = display_type;
            }

            public ExtraBean getExtra() {
                return extra;
            }

            public void setExtra(ExtraBean extra) {
                this.extra = extra;
            }

            public String getMsg_id() {
                return msg_id;
            }

            public void setMsg_id(String msg_id) {
                this.msg_id = msg_id;
            }

            public BodyBean getBody() {
                return body;
            }

            public void setBody(BodyBean body) {
                this.body = body;
            }

            public int getRandom_min() {
                return random_min;
            }

            public void setRandom_min(int random_min) {
                this.random_min = random_min;
            }

            public static class ExtraBean {
                /**
                 * nameValuePairs : {"userName":"wangfeng"}
                 */

                private NameValuePairsBean nameValuePairs;

                public NameValuePairsBean getNameValuePairs() {
                    return nameValuePairs;
                }

                public void setNameValuePairs(NameValuePairsBean nameValuePairs) {
                    this.nameValuePairs = nameValuePairs;
                }

                public static class NameValuePairsBean {
                    /**
                     * userName : wangfeng
                     */

                    private String userName;

                    public String getUserName() {
                        return userName;
                    }

                    public void setUserName(String userName) {
                        this.userName = userName;
                    }
                }
            }

            public static class BodyBean {
                /**
                 * nameValuePairs : {"after_open":"go_custom","play_lights":"false","ticker":"自定义title","play_vibrate":"true","custom":"自定义内容-----行为","text":"自定义内容","title":"自定义title","play_sound":"true"}
                 */

                private NameValuePairsBeanX nameValuePairs;

                public NameValuePairsBeanX getNameValuePairs() {
                    return nameValuePairs;
                }

                public void setNameValuePairs(NameValuePairsBeanX nameValuePairs) {
                    this.nameValuePairs = nameValuePairs;
                }

                public static class NameValuePairsBeanX {
                    /**
                     * after_open : go_custom
                     * play_lights : false
                     * ticker : 自定义title
                     * play_vibrate : true
                     * custom : 自定义内容-----行为
                     * text : 自定义内容
                     * title : 自定义title
                     * play_sound : true
                     */

                    private String after_open;
                    private String play_lights;
                    private String ticker;
                    private String play_vibrate;
                    private String custom;
                    private String text;
                    private String title;
                    private String play_sound;

                    public String getAfter_open() {
                        return after_open;
                    }

                    public void setAfter_open(String after_open) {
                        this.after_open = after_open;
                    }

                    public String getPlay_lights() {
                        return play_lights;
                    }

                    public void setPlay_lights(String play_lights) {
                        this.play_lights = play_lights;
                    }

                    public String getTicker() {
                        return ticker;
                    }

                    public void setTicker(String ticker) {
                        this.ticker = ticker;
                    }

                    public String getPlay_vibrate() {
                        return play_vibrate;
                    }

                    public void setPlay_vibrate(String play_vibrate) {
                        this.play_vibrate = play_vibrate;
                    }

                    public String getCustom() {
                        return custom;
                    }

                    public void setCustom(String custom) {
                        this.custom = custom;
                    }

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getPlay_sound() {
                        return play_sound;
                    }

                    public void setPlay_sound(String play_sound) {
                        this.play_sound = play_sound;
                    }
                }
            }
        }
    }

    public static class ExtraBeanX {
        /**
         * userName : wangfeng
         */

        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
