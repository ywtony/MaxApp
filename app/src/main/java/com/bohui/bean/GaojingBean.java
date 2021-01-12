package com.bohui.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 告警实体
 */
public class GaojingBean implements Serializable{
    private int TotalDataNum;
    private int CurPageNum;
    private int TotalPageNum;
    private List<GaoJingDataList> DataList;

    public int getTotalDataNum() {
        return TotalDataNum;
    }

    public void setTotalDataNum(int totalDataNum) {
        TotalDataNum = totalDataNum;
    }

    public int getCurPageNum() {
        return CurPageNum;
    }

    public void setCurPageNum(int curPageNum) {
        CurPageNum = curPageNum;
    }

    public int getTotalPageNum() {
        return TotalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        TotalPageNum = totalPageNum;
    }

    public List<GaoJingDataList> getDataList() {
        return DataList;
    }

    public void setDataList(List<GaoJingDataList> dataList) {
        DataList = dataList;
    }
}
