package com.bohui.bean;

import java.io.Serializable;

/**
 * 地图组件
 */
public class GMapBean implements Serializable {
    private boolean IsFixMainForm;
    private String MapCenterLat;
    private String MapCenterLng;
    private String MapService;
    private int MapInitialZoom;
    private int MapShowType;
    private String MapShowLeftLimitprivate;
    private String MapShowUpLimit;
    private String MapShowDownLimit;
    private String MapShowRightLimit;
    private int MapMinZoom;
    private int MapMaxZoom;
    private String Points;//这个点还是有用的
    private String UID;
    private String PID;
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
    private Polyline Polylines;

    public boolean isFixMainForm() {
        return IsFixMainForm;
    }

    public void setFixMainForm(boolean fixMainForm) {
        IsFixMainForm = fixMainForm;
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

    public String getMapShowLeftLimitprivate() {
        return MapShowLeftLimitprivate;
    }

    public void setMapShowLeftLimitprivate(String mapShowLeftLimitprivate) {
        MapShowLeftLimitprivate = mapShowLeftLimitprivate;
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

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
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

    public Polyline getPolylines() {
        return Polylines;
    }

    public void setPolylines(Polyline polylines) {
        Polylines = polylines;
    }
}
