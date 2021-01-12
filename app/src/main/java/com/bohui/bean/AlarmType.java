package com.bohui.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 告警类型
 */
public class AlarmType implements Serializable {
    private String ID;
    private String Name;
    private int AlarmCategory;
    private int AlarmSeverity;
    private int ObjTypeID;
    private String ProbableCause;
    private String ProposedRepairAction;
    private String AlarmImage;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAlarmCategory() {
        return AlarmCategory;
    }

    public void setAlarmCategory(int alarmCategory) {
        AlarmCategory = alarmCategory;
    }

    public int getAlarmSeverity() {
        return AlarmSeverity;
    }

    public void setAlarmSeverity(int alarmSeverity) {
        AlarmSeverity = alarmSeverity;
    }

    public int getObjTypeID() {
        return ObjTypeID;
    }

    public void setObjTypeID(int objTypeID) {
        ObjTypeID = objTypeID;
    }

    public String getProbableCause() {
        return ProbableCause;
    }

    public void setProbableCause(String probableCause) {
        ProbableCause = probableCause;
    }

    public String getProposedRepairAction() {
        return ProposedRepairAction;
    }

    public void setProposedRepairAction(String proposedRepairAction) {
        ProposedRepairAction = proposedRepairAction;
    }

    public String getAlarmImage() {
        return AlarmImage;
    }

    public void setAlarmImage(String alarmImage) {
        AlarmImage = alarmImage;
    }
}
