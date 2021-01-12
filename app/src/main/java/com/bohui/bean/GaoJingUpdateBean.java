package com.bohui.bean;

import java.io.Serializable;

/**
 * 更新告警数据的实体bean
 */
public class GaoJingUpdateBean implements Serializable{
    private GaoJingDataList Alarm;
    private int AlarmMsgType;//1新告警 确认告警

    public GaoJingDataList getAlarm() {
        return Alarm;
    }

    public void setAlarm(GaoJingDataList alarm) {
        Alarm = alarm;
    }

    public int getAlarmMsgType() {
        return AlarmMsgType;
    }

    public void setAlarmMsgType(int alarmMsgType) {
        AlarmMsgType = alarmMsgType;
    }
}
