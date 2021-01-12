package com.bohui.inter;

/**
 * 定义绘制类型
 * * 绘制类型
 * StaticPicture 绘制静态图片
 * StaticLabel 绘制静态文本
 * DynamicLabel 绘制动态文本
 * ControlButton 绘制控制按钮
 * DynamicPicture 绘制动态图片
 * Bgground 绘制背景图片
 */
public interface DrawWhat {
    String Bgground = "Bgground";
    String StaticPicture = "StaticPicture";
    String StaticLabel = "StaticLabel";
    String DynamicLabel = "DynamicLabel";
    String ControlButton = "ControlButton";
    String DynamicPicture = "DynamicPicture";
    String StatusSwitch = "StatusSwitch";
    String DistributeChart = "DistributeChart";
    String Distribute = "Distribute";
    String MapClass = "MapClass";
    String NetVideo = "NetVideo";

}
