package com.bohui.presenter;


import com.bohui.view.IBaseView;

/**
 * Created by yangwei on 2017/6/21.
 */
public class IBasePresneterImpl implements IBasePresenter<IBaseView> {
    private IBaseView baseView;

    @Override
    public void onViewCreate() {

    }

    @Override
    public void onViewResume() {

    }

    @Override
    public void onViewDestroy() {

    }

    @Override
    public void attachView(IBaseView view) {
        this.baseView = view;
    }

    @Override
    public void detachView() {
        baseView = null;
    }
}
