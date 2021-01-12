package com.bohui.bean;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * 页面属性
 */
public class MapBean implements Serializable {
    private boolean IsFixMainForm;
    private String MapCenterLat;
    private String MapCenterLng;
    private String MapService;
    private int MapInitialZoom;
    private int MapShowType;
    private String MapShowLeftLimit;
    private String MapShowUpLimit;
    private String MapShowDownLimit;
    private String MapShowRightLimit;
    private int MapMinZoom;
    private int MapMaxZoom;
    private List<Points> Points;
    private List<Polylines> Polylines;
    private int UID;
    private int PID;
    private String Name;
    private String Type;
    private int Top;
    private int Left;
    private int Width;
    private int Height;
    private boolean Actived;
    private int Z_index;
    private String ExAttrs;
    private String MeasObjID;
    private float CV;
    private int AlarmLevel;//告警级别 1 2 3 4
    private Bitmap bitmap;//动态图片
    private Bitmap staticBitmap;//静态图片
    private String AlarmSource;//告警位置
    private AlarmType alarmType;//告警属性信息
    private GaoJingDataList gaojing;

    public GaoJingDataList getGaojing() {
        return gaojing;
    }

    public void setGaojing(GaoJingDataList gaojing) {
        this.gaojing = gaojing;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmSource() {
        return AlarmSource;
    }

    public void setAlarmSource(String alarmSource) {
        AlarmSource = alarmSource;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getAlarmLevel() {
        return AlarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        AlarmLevel = alarmLevel;
    }

    public float getCV() {
        return CV;
    }

    public void setCV(float CV) {
        this.CV = CV;
    }

    public String getMapCenterLat() {
        return MapCenterLat;
    }

    public void setMapCenterLat(String mapCenterLat) {
        MapCenterLat = mapCenterLat;
    }

    public String getMapCenterLng() {
        return MapCenterLng;
    }

    public void setMapCenterLng(String mapCenterLng) {
        MapCenterLng = mapCenterLng;
    }

    public String getMapService() {
        return MapService;
    }

    public void setMapService(String mapService) {
        MapService = mapService;
    }

    public int getMapInitialZoom() {
        return MapInitialZoom;
    }

    public void setMapInitialZoom(int mapInitialZoom) {
        MapInitialZoom = mapInitialZoom;
    }

    public int getMapShowType() {
        return MapShowType;
    }

    public void setMapShowType(int mapShowType) {
        MapShowType = mapShowType;
    }

    public String getMapShowLeftLimit() {
        return MapShowLeftLimit;
    }

    public void setMapShowLeftLimit(String mapShowLeftLimit) {
        MapShowLeftLimit = mapShowLeftLimit;
    }

    public String getMapShowUpLimit() {
        return MapShowUpLimit;
    }

    public void setMapShowUpLimit(String mapShowUpLimit) {
        MapShowUpLimit = mapShowUpLimit;
    }

    public String getMapShowDownLimit() {
        return MapShowDownLimit;
    }

    public void setMapShowDownLimit(String mapShowDownLimit) {
        MapShowDownLimit = mapShowDownLimit;
    }

    public String getMapShowRightLimit() {
        return MapShowRightLimit;
    }

    public void setMapShowRightLimit(String mapShowRightLimit) {
        MapShowRightLimit = mapShowRightLimit;
    }

    public int getMapMinZoom() {
        return MapMinZoom;
    }

    public void setMapMinZoom(int mapMinZoom) {
        MapMinZoom = mapMinZoom;
    }

    public int getMapMaxZoom() {
        return MapMaxZoom;
    }

    public void setMapMaxZoom(int mapMaxZoom) {
        MapMaxZoom = mapMaxZoom;
    }

    public List<com.bohui.bean.Points> getPoints() {
        return Points;
    }

    public void setPoints(List<com.bohui.bean.Points> points) {
        Points = points;
    }

    public List<com.bohui.bean.Polylines> getPolylines() {
        return Polylines;
    }

    public void setPolylines(List<com.bohui.bean.Polylines> polylines) {
        Polylines = polylines;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getTop() {
        return Top;
    }

    public void setTop(int top) {
        Top = top;
    }

    public int getLeft() {
        return Left;
    }

    public void setLeft(int left) {
        Left = left;
    }

    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public boolean isActived() {
        return Actived;
    }

    public void setActived(boolean actived) {
        Actived = actived;
    }

    public int getZ_index() {
        return Z_index;
    }

    public void setZ_index(int z_index) {
        Z_index = z_index;
    }

    public boolean isFixMainForm() {
        return IsFixMainForm;
    }

    public void setFixMainForm(boolean fixMainForm) {
        IsFixMainForm = fixMainForm;
    }

    public String getExAttrs() {
        return ExAttrs;
    }

    public void setExAttrs(String exAttrs) {
        ExAttrs = exAttrs;
    }

    public String getMeasObjID() {
        return MeasObjID;
    }

    public void setMeasObjID(String measObjID) {
        MeasObjID = measObjID;
    }

    public Bitmap getStaticBitmap() {
        return staticBitmap;
    }

    public void setStaticBitmap(Bitmap staticBitmap) {
        this.staticBitmap = staticBitmap;
    }
}
