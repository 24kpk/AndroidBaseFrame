package com.uk.zc.androidbaseframe;

import android.os.Bundle;
import android.widget.TextView;

import com.baseframe.core.activity.BaseActivity;
import com.baseframe.core.widget.ComTitleLayout;

public class MainActivity extends BaseActivity {
    private  TextView tv1;
    private ComTitleLayout comTitleId;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        comTitleId = (ComTitleLayout) findViewById(R.id.comTitleId);
        comTitleId.getMiddleText().setText("测试BaseActivity");

        tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("111111111111");
    }
}
