package com.bohui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.bohui.R;
import com.bohui.presenter.IBasePresenter;
import com.bohui.view.IBaseView;


/**
 * 任意Activity必须继承自BaseActivity<p>
 * Activity在onCreate时自动绑定Presenter<p>
 * Activity需要实现自己的View接口<p>
 */
public abstract class BaseActivity<P extends IBasePresenter> extends Activity implements IBaseView {

    protected P mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        mPresenter.onViewCreate();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 为Activity绑定Presenter
     *
     * @return P
     */
    protected abstract P createPresenter(

    );

}
