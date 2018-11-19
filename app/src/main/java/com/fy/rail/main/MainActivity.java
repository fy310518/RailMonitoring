package com.fy.rail.main;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.utils.AnimUtils;
import com.fy.rail.R;

import butterknife.BindView;

/**
 * describe 主界面
 * Created by fangs on 2018/11/19 10:58.
 */
public class MainActivity extends AppCompatActivity implements IBaseActivity {
    private Fragment mCurrentFrgment;//当前显示的fragment
    private int currentIndex = 0;    //当前显示的fragment的下标

    private MainInputFragment inputFragment;
    private MainExportFragment exportFragment;
    private MainMyFragment myFragment;

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return R.layout.main_activity;
    }

    @StatusBar(statusColor = R.color.transparent, navColor = R.color.navigationBarColor, applyNav = false, statusOrNavModel = 1)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {

        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_input:
                    beginTransaction(0);
                    return true;
                case R.id.navigation_export:
                    beginTransaction(1);
                    return true;
                case R.id.navigation_my:
                    beginTransaction(2);
                    return true;
            }
            return false;
        });
        navigation.setSelectedItemId(navigation.getMenu().getItem(0).getItemId());
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); 	不要调用父类的方法
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void beginTransaction(int position) {
        FragmentManager fragmentManageer = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManageer.beginTransaction();
        AnimUtils.setFragmentTransition(fragmentTransaction, currentIndex, position);

        Fragment showfragment = null;
        switch (position) {
            case 0:
                if (null == inputFragment) inputFragment = new MainInputFragment();
                showfragment = inputFragment;
                break;
            case 1:
                if (null == exportFragment) exportFragment = new MainExportFragment();
                showfragment = exportFragment;
                break;
            case 2:
                if (null == myFragment) myFragment = new MainMyFragment();
                showfragment = myFragment;
                break;
        }

        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFrgment) fragmentTransaction.hide(mCurrentFrgment);

        if (null == showfragment) return;
        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!showfragment.isAdded()) {
            fragmentTransaction.add(R.id.mainFragment, showfragment);
        } else {
            fragmentTransaction.show(showfragment);
        }

        //保存当前显示的那个Fragment
        mCurrentFrgment = showfragment;
        currentIndex = position;
        fragmentTransaction.commitAllowingStateLoss();
    }
}
