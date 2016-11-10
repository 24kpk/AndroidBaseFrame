package com.baseframe.core.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseframe.core.R;

/**
 * author: C_CHEUNG
 * created on: 2016/11/10
 * description: 定义通用的标题栏
 */
public class ComTitleLayout extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private TextView mMiddleText,returnBnt, mLeftText, mTvMsg, mBtnSet, mBtnSetLine;
    private RelativeLayout mBtnReturn;
    private ProgressBar mPb;
    private View mHorLine;

    public ComTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View mView = LayoutInflater.from(context).inflate(R.layout.comtitlelay, this);

        mMiddleText = (TextView) mView.findViewById(R.id.text_middle);
        mLeftText = (TextView) mView.findViewById(R.id.txt_title);
        mBtnReturn = (RelativeLayout) mView.findViewById(R.id.ll_bntreturn);
        mTvMsg = (TextView) mView.findViewById(R.id.title_msg_tv);
        mBtnSet = (TextView) mView.findViewById(R.id.title_set_btn);
        mBtnSetLine = (TextView) mView.findViewById(R.id.title_set_line_btn);
        mPb = (ProgressBar) mView.findViewById(R.id.pb_top);
        returnBnt = (TextView) mView.findViewById(R.id.returnbnt);
        mHorLine = mView.findViewById(R.id.title_divider_line);
        init(attrs);
    }
    protected void init(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs,
                R.styleable.commentTitle);
        String title = ta.getString(R.styleable.commentTitle_text);
        boolean backButtonEnable = ta.getBoolean(
                R.styleable.commentTitle_backButtonEnable, true);
        boolean middleTvEnable = ta.getBoolean(
                R.styleable.commentTitle_middleTextViewEnable, true);
        boolean rightButtonEnable = ta.getBoolean(
                R.styleable.commentTitle_rightButtonEnable, false);
        boolean imageButtonEnable = ta.getBoolean(
                R.styleable.commentTitle_imageButtonEnable, false);
        int rightButtonBackgroundResId = ta.getResourceId(
                R.styleable.commentTitle_rightButtonBackground, -1);
        int imageButtonBackgroundResId = ta.getResourceId(
                R.styleable.commentTitle_imageButtonBackground, -1);
        ta.recycle();

        if (backButtonEnable) {
            mBtnReturn.setVisibility(VISIBLE);
        } else {
            mBtnReturn.setVisibility(GONE);
        }
        if (middleTvEnable) {
            mLeftText.setVisibility(GONE);
            mMiddleText.setVisibility(VISIBLE);
        } else {
            mLeftText.setVisibility(VISIBLE);
            mMiddleText.setVisibility(GONE);
        }

        mBtnReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Activity) mContext).finish();
    }

    /**
     * @return 获取中间TextView
     */
    public TextView getMiddleText() {
        return mMiddleText;
    }

    /**
     * @return 左边紧靠返回按钮TextView
     */
    public TextView getLeftText() {
        return mLeftText;
    }


    /**
     * @return 获取返回按钮
     */
    public RelativeLayout getReturnBtn() {
        return mBtnReturn;
    }

    /**
     * @return 获取左上角button
     */
    public TextView getLeftLogo() {
        return returnBnt;
    }


    /**
     * 右上角的红点
     *
     * @return
     */
    public TextView getMsgTv() {
        return mTvMsg;
    }

    /**
     * @return 获取最右Button
     */
    public TextView getRighterTv() {
        return mBtnSet;
    }

    /**
     * @return 获取偏右Button
     */
    public TextView getRightTv() {
        return mBtnSetLine;
    }


    public void setPbVisibility(int visible) {
        if (mPb != null)
            mPb.setVisibility(visible);
    }

    public View getDivider() {
        return mHorLine;
    }
}


