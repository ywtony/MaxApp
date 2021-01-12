package com.bohui.bean;


public class MessageEvent {
    public MessageEvent() {
    }

    public MessageEvent(int what) {
        this.what = what;
    }

    public static final int UPDATE_INDEX_DATA = 0;//更新首页数据
    public static final int LOAD_INDEX_PAGE = 1;//加载页面数据
    public static final int UPDATE_MSG_COUNT = 2;//更新消息记录的值
    public static final int UPDATE_ALARM_MSG = 3;//定时更新告警信息
    public static final int LOAD_INDEX_PAGE_ALARM = 4;//定位
    public static final int MAP_REFRESH = 5;//定位


    //fragment切换
    public static final int TO_MAPVIEW = 6;//地图
    public static final int TO_INDEX = 7;//首页
    public static final int TO_TUBIA0 = 8;//图表
    public static final int TO_MAPVIEW1 = 9;//地图
    public static final int TO_INDEX1 = 10;//首页
    public static final int TO_TUBIA01 = 11;//图表
    public static final int TO_LOCATION = 12;//定位
    public static final int SHOW_LOCATION = 13;//展示位置
    public static final int REFRESH_GJLIST = 14;//刷新告警列表
    public int what;
    public int count;
    public String type = "";
    public String state = "";
    public Object obj;


}
