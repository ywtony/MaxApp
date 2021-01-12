package com.bohui.bean;

import java.io.Serializable;

/**
 * 告警数据列表
 */
public class GaoJingDataList implements Serializable {
    private String LastChangedTime;
    private String ProbableCause;
    private String ProposedRepairAction;
    private String SpecificProblem;
    private String AlarmSource;
    private String ClearedTime;
    private String ClearUser;
//    private boolean Cleared;

    private String RaisedTime;
    private int MeasObjID;
    private String MeasObjName;
    private String OrganizationCode;
    private int MeasZoneID;
    private String MeasZoneName;
    private int AlarmSeverity;
    private String AdditionalText;
    private boolean Acked;
    private String AckTime;
    private String AckUser;
    private String Remarks;
    private int AlarmID;
    private int SeqNo;
    private float RealDistance;
    private boolean Original;
    private float Longitude;
    private float Latitude;
    private int ParentAlarmID;
    private int MemberAlarmCount;
    private AlarmType AlarmType;
    private int YearSeqNo;
    private int MonthSeqNo;
    /**
     * 告警等级
     */
    private String AlarmLevel;
    /**
     * 风险等级
     */
    private String RiskLevel;
    /**
     * 现场反馈
     */
    private String FieldFeedback;
    /**
     * 核实人
     */
    private String FieldConfirmer;
    /**
     * 核实时间
     */
    private String FieldConfirmTime;
    private String Recorder;
    private int AlarmMsgType;

    public int getAlarmMsgType() {
        return AlarmMsgType;
    }

    public void setAlarmMsgType(int alarmMsgType) {
        AlarmMsgType = alarmMsgType;
    }

    public int getYearSeqNo() {
        return YearSeqNo;
    }

    public void setYearSeqNo(int yearSeqNo) {
        YearSeqNo = yearSeqNo;
    }

    public int getMonthSeqNo() {
        return MonthSeqNo;
    }

    public void setMonthSeqNo(int monthSeqNo) {
        MonthSeqNo = monthSeqNo;
    }

    public String getAlarmLevel() {
        return AlarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        AlarmLevel = alarmLevel;
    }

    public String getRiskLevel() {
        return RiskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        RiskLevel = riskLevel;
    }

    public String getFieldFeedback() {
        return FieldFeedback;
    }

    public void setFieldFeedback(String fieldFeedback) {
        FieldFeedback = fieldFeedback;
    }

    public String getFieldConfirmer() {
        return FieldConfirmer;
    }

    public void setFieldConfirmer(String fieldConfirmer) {
        FieldConfirmer = fieldConfirmer;
    }

    public String getFieldConfirmTime() {
        return FieldConfirmTime;
    }

    public void setFieldConfirmTime(String fieldConfirmTime) {
        FieldConfirmTime = fieldConfirmTime;
    }

    public String getRecorder() {
        return Recorder;
    }

    public void setRecorder(String recorder) {
        Recorder = recorder;
    }

    public String getLastChangedTime() {
        return LastChangedTime;
    }

    public void setLastChangedTime(String lastChangedTime) {
        LastChangedTime = lastChangedTime;
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

    public String getSpecificProblem() {
        return SpecificProblem;
    }

    public void setSpecificProblem(String specificProblem) {
        SpecificProblem = specificProblem;
    }

    public String getAlarmSource() {
        return AlarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        AlarmSource = alarmSource;
    }

    public String getClearedTime() {
        return ClearedTime;
    }

    public void setClearedTime(String clearedTime) {
        ClearedTime = clearedTime;
    }

    public String getClearUser() {
        return ClearUser;
    }

    public void setClearUser(String clearUser) {
        ClearUser = clearUser;
    }

//    public boolean isCleared() {
//        return Cleared;
//    }
//
//    public void setCleared(boolean cleared) {
//        Cleared = cleared;
//    }


    public String getRaisedTime() {
        return RaisedTime;
    }

    public void setRaisedTime(String raisedTime) {
        RaisedTime = raisedTime;
    }

    public int getMeasObjID() {
        return MeasObjID;
    }

    public void setMeasObjID(int measObjID) {
        MeasObjID = measObjID;
    }

    public String getMeasObjName() {
        return MeasObjName;
    }

    public void setMeasObjName(String measObjName) {
        MeasObjName = measObjName;
    }

    public String getOrganizationCode() {
        return OrganizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        OrganizationCode = organizationCode;
    }

    public int getMeasZoneID() {
        return MeasZoneID;
    }

    public void setMeasZoneID(int measZoneID) {
        MeasZoneID = measZoneID;
    }

    public String getMeasZoneName() {
        return MeasZoneName;
    }

    public void setMeasZoneName(String measZoneName) {
        MeasZoneName = measZoneName;
    }

    public int getAlarmSeverity() {
        return AlarmSeverity;
    }

    public void setAlarmSeverity(int alarmSeverity) {
        AlarmSeverity = alarmSeverity;
    }

    public String getAdditionalText() {
        return AdditionalText;
    }

    public void setAdditionalText(String additionalText) {
        AdditionalText = additionalText;
    }

    public boolean isAcked() {
        return Acked;
    }

    public void setAcked(boolean acked) {
        Acked = acked;
    }

    public String getAckTime() {
        return AckTime;
    }

    public void setAckTime(String ackTime) {
        AckTime = ackTime;
    }

    public String getAckUser() {
        return AckUser;
    }

    public void setAckUser(String ackUser) {
        AckUser = ackUser;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public int getAlarmID() {
        return AlarmID;
    }

    public void setAlarmID(int alarmID) {
        AlarmID = alarmID;
    }

    public int getSeqNo() {
        return SeqNo;
    }

    public void setSeqNo(int seqNo) {
        SeqNo = seqNo;
    }

    public float getRealDistance() {
        return RealDistance;
    }

    public void setRealDistance(float realDistance) {
        RealDistance = realDistance;
    }

    public boolean isOriginal() {
        return Original;
    }

    public void setOriginal(boolean original) {
        Original = original;
    }

    public float getLongitude() {
        return Longitude;
    }

    public void setLongitude(float longitude) {
        Longitude = longitude;
    }

    public float getLatitude() {
        return Latitude;
    }

    public void setLatitude(float latitude) {
        Latitude = latitude;
    }

    public int getParentAlarmID() {
        return ParentAlarmID;
    }

    public void setParentAlarmID(int parentAlarmID) {
        ParentAlarmID = parentAlarmID;
    }

    public int getMemberAlarmCount() {
        return MemberAlarmCount;
    }

    public void setMemberAlarmCount(int memberAlarmCount) {
        MemberAlarmCount = memberAlarmCount;
    }

    public com.bohui.bean.AlarmType getAlarmType() {
        return AlarmType;
    }

    public void setAlarmType(com.bohui.bean.AlarmType alarmType) {
        AlarmType = alarmType;
    }
}
