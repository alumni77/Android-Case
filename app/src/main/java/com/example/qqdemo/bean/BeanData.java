package com.example.qqdemo.bean;

import java.util.List;

public class BeanData {

    private BasicEntity basic;
    private String code;
    private NowEntity now;
    private ReferEntity refer;

    public BasicEntity getBasic() {
        return basic;
    }

    public void setBasic(BasicEntity basic) {
        this.basic = basic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public NowEntity getNow() {
        return now;
    }

    public void setNow(NowEntity now) {
        this.now = now;
    }

    public ReferEntity getRefer() {
        return refer;
    }

    public void setRefer(ReferEntity refer) {
        this.refer = refer;
    }

    public static class BasicEntity {
        private String fxLink;
        private String updateTime;

        public String getFxLink() {
            return fxLink;
        }

        public void setFxLink(String fxLink) {
            this.fxLink = fxLink;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class NowEntity {
        private String cloud;
        private String dew;
        private String feelsLike;
        private String humidity;
        private String icon;
        private String obsTime;
        private String precip;
        private String pressure;
        private String temp;
        private String text;
        private String vis;
        private String wind360;
        private String windDir;
        private String windScale;
        private String windSpeed;

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getDew() {
            return dew;
        }

        public void setDew(String dew) {
            this.dew = dew;
        }

        public String getFeelsLike() {
            return feelsLike;
        }

        public void setFeelsLike(String feelsLike) {
            this.feelsLike = feelsLike;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getObsTime() {
            return obsTime;
        }

        public void setObsTime(String obsTime) {
            this.obsTime = obsTime;
        }

        public String getPrecip() {
            return precip;
        }

        public void setPrecip(String precip) {
            this.precip = precip;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }

        public String getTemp() {
            return temp;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind360() {
            return wind360;
        }

        public void setWind360(String wind360) {
            this.wind360 = wind360;
        }

        public String getWindDir() {
            return windDir;
        }

        public void setWindDir(String windDir) {
            this.windDir = windDir;
        }

        public String getWindScale() {
            return windScale;
        }

        public void setWindScale(String windScale) {
            this.windScale = windScale;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }
    }

    public static class ReferEntity {
        private List<String> licenseList;
        private List<String> sourcesList;

        public List<String> getLicenseList() {
            return licenseList;
        }

        public void setLicenseList(List<String> licenseList) {
            this.licenseList = licenseList;
        }

        public List<String> getSourcesList() {
            return sourcesList;
        }

        public void setSourcesList(List<String> sourcesList) {
            this.sourcesList = sourcesList;
        }
    }
}
