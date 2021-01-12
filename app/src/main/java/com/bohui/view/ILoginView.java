package com.bohui.view;


public interface ILoginView extends IBaseView{
    void success(String code);
    void onFail();
}
