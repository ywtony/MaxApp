package com.bohui.presenter;


import com.bohui.view.IBaseView;

/**
 * Created by ZHAOWEIWEI on 2016/7/11.
 */
public interface IBasePresenter<V extends IBaseView> {

    /**
     * ViewCreate的时候调用该方法
     */
    public void onViewCreate();

    /**
     * ViewResume的时候调用该方法
     */
    public void onViewResume();

    /**
     * ViewDestroy的时候调用该方法
     */
    public void onViewDestroy();

    /**
     * 将View绑定到Presenter
     */
    public void attachView(V view);

    /**
     * 在View已经销毁的时候调用该方法，防止内存泄漏
     */
    public void detachView();

}
