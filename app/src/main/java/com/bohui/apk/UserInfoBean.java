package com.bohui.apk;

public class UserInfoBean {

    /**
     * code : 0
     * data : {"avatar":"string","bindQQ":true,"bindWB":true,"bindWX":true,"both":0,"city":"string","gender":0,"id":"string","identity":"string","imageDto":{"id":"string","imgId":"string"},"manager":0,"mutSix":"string","mutThree":"string","nickName":"string","price":0,"username":"string","videoAuth":0}
     * msg : string
     */

    private int      code;
    private DataBean data;
    private String   msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * avatar : string
         * bindQQ : true
         * bindWB : true
         * bindWX : true
         * both : 0
         * city : string
         * gender : 0
         * id : string
         * identity : string
         * imageDto : {"id":"string","imgId":"string"}
         * manager : 0
         * mutSix : string
         * mutThree : string
         * nickName : string
         * price : 0
         * username : string
         * videoAuth : 0
         */

        private String       avatar;
        private boolean      bindQQ;
        private boolean      bindWB;
        private boolean      bindWX;
        private long         both;
        private String       city;
        private int          gender;
        private String       id;
        private String       identity;
        private ImageDtoBean imageDto;
        private String       mutSix;
        private String       mutThree;
        private String nickName = "";
        private double price;
        private String username = "";
        private int    videoAuth;
        private String deviceId;
        private int curTime;//余额

        public int getCurTime() {
            return curTime;
        }

        public void setCurTime(int curTime) {
            this.curTime = curTime;
        }

        private int manager;//0是管理员 1是普通用户
        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public boolean isBindQQ() {
            return bindQQ;
        }

        public void setBindQQ(boolean bindQQ) {
            this.bindQQ = bindQQ;
        }

        public boolean isBindWB() {
            return bindWB;
        }

        public void setBindWB(boolean bindWB) {
            this.bindWB = bindWB;
        }

        public boolean isBindWX() {
            return bindWX;
        }

        public void setBindWX(boolean bindWX) {
            this.bindWX = bindWX;
        }

        public long getBoth() {
            return both;
        }

        public void setBoth(long both) {
            this.both = both;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public ImageDtoBean getImageDto() {
            return imageDto;
        }

        public void setImageDto(ImageDtoBean imageDto) {
            this.imageDto = imageDto;
        }

        public int getManager() {
            return manager;
        }

        public void setManager(int manager) {
            this.manager = manager;
        }

        public String getMutSix() {
            return mutSix;
        }

        public void setMutSix(String mutSix) {
            this.mutSix = mutSix;
        }

        public String getMutThree() {
            return mutThree;
        }

        public void setMutThree(String mutThree) {
            this.mutThree = mutThree;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getVideoAuth() {
            return videoAuth;
        }

        public void setVideoAuth(int videoAuth) {
            this.videoAuth = videoAuth;
        }

        public static class ImageDtoBean {
            /**
             * id : string
             * imgId : string
             */

            private String id;
            private String imgId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImgId() {
                return imgId;
            }

            public void setImgId(String imgId) {
                this.imgId = imgId;
            }
        }
    }
}
