package com.bohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 额外属性，用来标注、图片、文本、或者按钮
 */
public class ExAttrs implements Serializable {
    private String imageUrl;//图片路径
    private boolean pageActived;//是否可点击 true就是可点击，false不可点击
    private int pageID;//页面的ID
    private String pageName;//页面名称
    private String fontFamily;
    private boolean fontWeight;
    private float fontSize;
    private String color;
    private String backgroundColor;
    private String textAlign;
    private int displayStyle;
    private int switchOnOff;
    private String caption;
    private int objectType;
    private int check;
    private String normalUrl;
    private String tipUrl;
    private String preAlarmUrl;
    private String alarmUrl;
    private String faultUrl;
    private int refreshTime;
    private boolean isSetRegion;
    private int refreshInterval;
    private List<StatusList> statusList;
    private String normalOffUrl;
    private String normalOnUrl;
    private String statusMeasObjID;
    private String switchMeasObjID;
    private int distriType;
    private int deployType;
    private int dataType;
    private boolean border;
    private int borderWidth;
    private String borderColor;
    private List<String> relMeasObjIDs;
    private int strokeWidth;
    private String stroke;
    private List<ZJPoint> points;
    private boolean showTable;
    private String disableColor;
    private int freq;
    private boolean opacity;//false字体的背景颜色生效，true背景颜色是透明色
    private String playUrl;
    private String url;
    private String user;
    private String pwd;
    private int vendor;//海康1，大华2，宇视3）
    private Object func;
    private Object csfunc;
    private Object desc;
    private Object cswitchCaption;
    private boolean cswitch;
    private Object dicCode;
    private Object upFunc;
    private Object plotBands;

    public Object getFunc() {
        return func;
    }

    public void setFunc(Object func) {
        this.func = func;
    }

    public Object getCsfunc() {
        return csfunc;
    }

    public void setCsfunc(Object csfunc) {
        this.csfunc = csfunc;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

    public Object getCswitchCaption() {
        return cswitchCaption;
    }

    public void setCswitchCaption(Object cswitchCaption) {
        this.cswitchCaption = cswitchCaption;
    }

    public boolean isCswitch() {
        return cswitch;
    }

    public void setCswitch(boolean cswitch) {
        this.cswitch = cswitch;
    }

    public Object getDicCode() {
        return dicCode;
    }

    public void setDicCode(Object dicCode) {
        this.dicCode = dicCode;
    }

    public Object getUpFunc() {
        return upFunc;
    }

    public void setUpFunc(Object upFunc) {
        this.upFunc = upFunc;
    }

    public Object getPlotBands() {
        return plotBands;
    }

    public void setPlotBands(Object plotBands) {
        this.plotBands = plotBands;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getVendor() {
        return vendor;
    }

    public void setVendor(int vendor) {
        this.vendor = vendor;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public boolean isOpacity() {
        return opacity;
    }

    public void setOpacity(boolean opacity) {
        this.opacity = opacity;
    }

    public int getDistriType() {
        return distriType;
    }

    public void setDistriType(int distriType) {
        this.distriType = distriType;
    }

    public int getDeployType() {
        return deployType;
    }

    public void setDeployType(int deployType) {
        this.deployType = deployType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public List<String> getRelMeasObjIDs() {
        return relMeasObjIDs;
    }

    public void setRelMeasObjIDs(List<String> relMeasObjIDs) {
        this.relMeasObjIDs = relMeasObjIDs;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getStroke() {
        return stroke;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public List<ZJPoint> getPoints() {
        return points;
    }

    public void setPoints(List<ZJPoint> points) {
        this.points = points;
    }

    public boolean isShowTable() {
        return showTable;
    }

    public void setShowTable(boolean showTable) {
        this.showTable = showTable;
    }

    public String getDisableColor() {
        return disableColor;
    }

    public void setDisableColor(String disableColor) {
        this.disableColor = disableColor;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getNormalOffUrl() {
        return normalOffUrl;
    }

    public void setNormalOffUrl(String normalOffUrl) {
        this.normalOffUrl = normalOffUrl;
    }

    public String getNormalOnUrl() {
        return normalOnUrl;
    }

    public void setNormalOnUrl(String normalOnUrl) {
        this.normalOnUrl = normalOnUrl;
    }

    public String getStatusMeasObjID() {
        return statusMeasObjID;
    }

    public void setStatusMeasObjID(String statusMeasObjID) {
        this.statusMeasObjID = statusMeasObjID;
    }

    public String getSwitchMeasObjID() {
        return switchMeasObjID;
    }

    public void setSwitchMeasObjID(String switchMeasObjID) {
        this.switchMeasObjID = switchMeasObjID;
    }

    public List<StatusList> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusList> statusList) {
        this.statusList = statusList;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isPageActived() {
        return pageActived;
    }

    public void setPageActived(boolean pageActived) {
        this.pageActived = pageActived;
    }

    public int getPageID() {
        return pageID;
    }

    public void setPageID(int pageID) {
        this.pageID = pageID;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public boolean isFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(boolean fontWeight) {
        this.fontWeight = fontWeight;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(String textAlign) {
        this.textAlign = textAlign;
    }

    public int getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(int displayStyle) {
        this.displayStyle = displayStyle;
    }

    public int getSwitchOnOff() {
        return switchOnOff;
    }

    public void setSwitchOnOff(int switchOnOff) {
        this.switchOnOff = switchOnOff;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public String getNormalUrl() {
        return normalUrl;
    }

    public void setNormalUrl(String normalUrl) {
        this.normalUrl = normalUrl;
    }

    public String getTipUrl() {
        return tipUrl;
    }

    public void setTipUrl(String tipUrl) {
        this.tipUrl = tipUrl;
    }

    public String getPreAlarmUrl() {
        return preAlarmUrl;
    }

    public void setPreAlarmUrl(String preAlarmUrl) {
        this.preAlarmUrl = preAlarmUrl;
    }

    public String getAlarmUrl() {
        return alarmUrl;
    }

    public void setAlarmUrl(String alarmUrl) {
        this.alarmUrl = alarmUrl;
    }

    public String getFaultUrl() {
        return faultUrl;
    }

    public void setFaultUrl(String faultUrl) {
        this.faultUrl = faultUrl;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(int refreshTime) {
        this.refreshTime = refreshTime;
    }

    public boolean isSetRegion() {
        return isSetRegion;
    }

    public void setSetRegion(boolean setRegion) {
        isSetRegion = setRegion;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
