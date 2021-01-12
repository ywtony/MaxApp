package com.bohui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bohui.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

/**
 * 宇视NVR列表
 */
public class YuShiVideoFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customgjsumbit_layout, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}