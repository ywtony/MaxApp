package io.ionic.starter.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-07-17.
 */

public class JiankongModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String station_id;
    private String station_name;
    private String station_addr;
    private String instrument_count;
    private String online_count;
    private String offline_count;
    private String danger_count;
    private String cloud_url;
    private String cloud_username;
    private String cloud_pwd;
    private String dev_name;
    private String video_chanles;
    private String video_wins;

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }

    public String getStation_addr() {
        return station_addr;
    }

    public void setStation_addr(String station_addr) {
        this.station_addr = station_addr;
    }

    public String getInstrument_count() {
        return instrument_count;
    }

    public void setInstrument_count(String instrument_count) {
        this.instrument_count = instrument_count;
    }

    public String getOnline_count() {
        return online_count;
    }

    public void setOnline_count(String online_count) {
        this.online_count = online_count;
    }

    public String getOffline_count() {
        return offline_count;
    }

    public void setOffline_count(String offline_count) {
        this.offline_count = offline_count;
    }

    public String getDanger_count() {
        return danger_count;
    }

    public void setDanger_count(String danger_count) {
        this.danger_count = danger_count;
    }

    public String getCloud_url() {
        return cloud_url;
    }

    public void setCloud_url(String cloud_url) {
        this.cloud_url = cloud_url;
    }

    public String getCloud_username() {
        return cloud_username;
    }

    public void setCloud_username(String cloud_username) {
        this.cloud_username = cloud_username;
    }

    public String getCloud_pwd() {
        return cloud_pwd;
    }

    public void setCloud_pwd(String cloud_pwd) {
        this.cloud_pwd = cloud_pwd;
    }

    public String getDev_name() {
        return dev_name;
    }

    public void setDev_name(String dev_name) {
        this.dev_name = dev_name;
    }

    public String getVideo_chanles() {
        return video_chanles;
    }

    public void setVideo_chanles(String video_chanles) {
        this.video_chanles = video_chanles;
    }

    public String getVideo_wins() {
        return video_wins;
    }

    public void setVideo_wins(String video_wins) {
        this.video_wins = video_wins;
    }
}
