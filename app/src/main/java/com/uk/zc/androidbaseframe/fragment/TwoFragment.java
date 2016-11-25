package com.uk.zc.androidbaseframe.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baseframe.core.utils.ToastUtil;
import com.uk.zc.androidbaseframe.R;
import com.uk.zc.androidbaseframe.bean.UserBean;

import net.entity.BaseBean;
import net.request.HttpRequest;
import net.ui.base.BaseNetFragment;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author: C_CHEUNG
 * created on: 2016/11/12
 * description: TwoFragment
 */
public class TwoFragment extends BaseNetFragment {
    String POST_URL = "http://182.254.209.88/api/v1/user/login?os_type=android&version=1.1.0";
    private String POST_TAG = "POST_TAG";

    Button btn;
    TextView tv;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_two;
    }

    @Override
    public void initView(View parentView, Bundle savedInstanceState) {
        super.initView(parentView, savedInstanceState);
        btn = (Button) parentView.findViewById(R.id.btn2);
        tv = (TextView) parentView.findViewById(R.id.text2);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == btn) {
            Map<String, String> params = new HashMap<>();
            params.put("ct", "1");
            params.put("un", "18729564163");

            params.put("mid", "18729564163");
            params.put("pw", "12345678");
            params.put("pushsvc", "2");
            HttpRequest.submitPostResponseBean(mActivity, POST_URL, params, UserBean.class, POST_TAG, TwoFragment.this);
        }
    }


    @Override
    public void onSuccess(BaseBean baseBean, Call call, Response response) {
        if (baseBean!=null){
            if (baseBean.getCode() == 1 && baseBean.getResult() !=null){
                UserBean usr = (UserBean) baseBean.getResult();
                tv.setText(usr.getUser_id() + "");
            }else {
                ToastUtil.showToast(mActivity,baseBean.getMsg());
            }

        }
    }


}
