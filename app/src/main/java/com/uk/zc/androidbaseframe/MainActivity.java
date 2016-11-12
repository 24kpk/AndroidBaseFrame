package com.uk.zc.androidbaseframe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.baseframe.core.activity.BaseActivity;
import com.baseframe.core.fragment.BaseFragment;
import com.baseframe.core.utils.SharedPreferencesUtil;
import com.uk.zc.androidbaseframe.fragment.FiveFragment;
import com.uk.zc.androidbaseframe.fragment.FourFragment;
import com.uk.zc.androidbaseframe.fragment.OneFragment;
import com.uk.zc.androidbaseframe.fragment.ThreeFragment;
import com.uk.zc.androidbaseframe.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{
    // 标示了当前位置
    private static final String TAB_SP_NAME = "main_tab_selectd_pos";

    private OneFragment mFragmentOne;
    private TwoFragment mFragmentTwo;
    private ThreeFragment mFragmentThree;
    private FourFragment mFragmentFour;
    private FiveFragment mFragmentFive;

    private List<BaseFragment> mFragmentList = new ArrayList<>();

    //Button集合
    private List<Button> mButtonList = new ArrayList<>();
    //fragment 适配器
    private FragmentAdapter mFragmentAdapter;


    private ViewPager mMainViewPager;
    private SharedPreferencesUtil tabSpUtil;

    private Button mMainFwBtnTrue;
    private Button mMainSjBtnTrue;
    private Button mMainGzBtnTrue;
    private Button mMainLxrBtnTrue;
    private Button mMainWdBtnTrue;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        super.initData();
        tabSpUtil = new SharedPreferencesUtil(this, TAB_SP_NAME);

        mFragmentOne = new OneFragment();
        mFragmentTwo = new TwoFragment();
        mFragmentThree = new ThreeFragment();
        mFragmentFour = new FourFragment();
        mFragmentFive = new FiveFragment();
        mFragmentList.add(mFragmentOne);
        mFragmentList.add(mFragmentTwo);
        mFragmentList.add(mFragmentThree);
        mFragmentList.add(mFragmentFour);
        mFragmentList.add(mFragmentFive);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

//        ComTitleLayout c = (ComTitleLayout) findViewById(R.id.comTitleId);
//        mMidText = c.getMiddleText();
//        mMidText.setText(R.string.app_name);
//        c.getReturnBtn().setVisibility(View.GONE);
        mMainViewPager = (ViewPager)findViewById(R.id.m_main_viewpager);

        mMainFwBtnTrue  = (Button)findViewById(R.id.m_main_fw_btn_true);
        mMainSjBtnTrue  = (Button)findViewById(R.id.m_main_sj_btn_true);
        mMainGzBtnTrue  = (Button)findViewById(R.id.m_main_gz_btn_true);
        mMainLxrBtnTrue = (Button)findViewById(R.id.m_main_lxr_btn_true);
        mMainWdBtnTrue = (Button) findViewById(R.id.m_main_wd_btn_true);

        mMainFwBtnTrue.setOnClickListener(this);
        mMainSjBtnTrue.setOnClickListener(this);
        mMainGzBtnTrue.setOnClickListener(this);
        mMainLxrBtnTrue.setOnClickListener(this);
        mMainWdBtnTrue.setOnClickListener(this);

        mButtonList.add(mMainFwBtnTrue);
        mButtonList.add(mMainSjBtnTrue);
        mButtonList.add(mMainGzBtnTrue);
        mButtonList.add(mMainLxrBtnTrue);
        mButtonList.add(mMainWdBtnTrue);

        initAdapter();
        changAlpha(0);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.m_main_fw_btn_true:
                mMainViewPager.setCurrentItem(0, false);
                break;
            case R.id.m_main_sj_btn_true:
                mMainViewPager.setCurrentItem(1, false);
                break;
            case  R.id.m_main_gz_btn_true:
                mMainViewPager.setCurrentItem(2, false);
                break;
            case R.id.m_main_lxr_btn_true:
                mMainViewPager.setCurrentItem(3, false);
                break;
            case R.id.m_main_wd_btn_true:
                mMainViewPager.setCurrentItem(4, false);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //使用getLayoutResId指定的布局 不设置TITLE LAYOUT
    @Override
    protected boolean useComTitleLayout() {
        return false;
    }


    /**
     * 初始化adapter
     */
    private void initAdapter() {
        mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mMainViewPager.setAdapter(mFragmentAdapter);
        //viewpager滑动监听
        mMainViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changAlpha(position, positionOffset);
            }
            @Override
            public void onPageSelected(int position) {
                changAlpha(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    class FragmentAdapter extends FragmentPagerAdapter {
        //fragment 集合
        private List<BaseFragment> mFragmentList = new ArrayList<>();

        public FragmentAdapter(FragmentManager fm , List<BaseFragment> list) {
            super(fm);
            this.mFragmentList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }


    /**
     * 一开始运行、滑动和点击tab结束后设置tab的透明度，fragment的透明度和大小
     */
    private void changAlpha(int postion) {
        for (int i = 0; i < mButtonList.size(); i++) {
            if (i == postion) {
                mButtonList.get(i).setAlpha(1.0f);
                if(null != mFragmentList.get(i).getView()){
                    mFragmentList.get(i).getView().setAlpha(1.0f);
                    mFragmentList.get(i).getView().setScaleX(1.0f);
                    mFragmentList.get(i).getView().setScaleY(1.0f);
                }
            } else {
                mButtonList.get(i).setAlpha(0.0f);
                if(null != mFragmentList.get(i).getView()){
                    mFragmentList.get(i).getView().setAlpha(0.0f);
                    mFragmentList.get(i).getView().setScaleX(0.0f);
                    mFragmentList.get(i).getView().setScaleY(0.0f);
                }
            }
        }
    }

    /**
     * 根据滑动设置透明度
     */
    private void changAlpha(int pos, float posOffset) {
        int nextIndex = pos + 1;
        if(posOffset > 0){
            //设置tab的颜色渐变效果
            mButtonList.get(nextIndex).setAlpha(posOffset);
            mButtonList.get(pos).setAlpha(1 - posOffset);
            //设置fragment的颜色渐变效果
            mFragmentList.get(nextIndex).getView().setAlpha(posOffset);
            mFragmentList.get(pos).getView().setAlpha(1 - posOffset);
            //设置fragment滑动视图由大到小，由小到大的效果
            mFragmentList.get(nextIndex).getView().setScaleX(0.5F + posOffset/2);
            mFragmentList.get(nextIndex).getView().setScaleY(0.5F + posOffset/2);
            mFragmentList.get(pos).getView().setScaleX(1-(posOffset/2));
            mFragmentList.get(pos).getView().setScaleY(1-(posOffset/2));
        }
    }
}
