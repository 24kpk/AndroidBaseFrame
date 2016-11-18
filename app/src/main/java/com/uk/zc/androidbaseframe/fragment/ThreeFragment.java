package com.uk.zc.androidbaseframe.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uk.zc.androidbaseframe.R;

import net.request.HttpRequest;
import net.ui.base.BaseNetFragment;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author: C_CHEUNG
 * created on: 2016/11/12
 * description: ThreeFragment
 */
public class ThreeFragment extends BaseNetFragment {
    String REQUEST_URL = "http://www.baidu.com";
    String REQUEST_TAG = "REQUEST_TAG";
    Button btn;
    TextView textView;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_three;
    }

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        super.initView(parentView, savedInstanceState);
        btn = (Button) parentView.findViewById(R.id.btn3);
        textView = (TextView) parentView.findViewById(R.id.text3);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btn){
            textView.setText("");
            HttpRequest.submitGet(mActivity,REQUEST_URL,REQUEST_TAG,this);
        }
    }

    @Override
    public <T> void onSuccess(T t, Call call, Response response) {
        textView.setText(t.toString());
    }
}
