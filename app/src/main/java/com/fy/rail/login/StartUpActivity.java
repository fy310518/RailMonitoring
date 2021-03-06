package com.fy.rail.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.ClickFilter;
import com.fy.baselibrary.aop.annotation.StatusBar;
import com.fy.baselibrary.application.IBaseActivity;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.statusbar.StatusBarContentColor;
import com.fy.baselibrary.utils.AppUtils;
import com.fy.baselibrary.utils.Constant;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.drawable.TintUtils;
import com.fy.rail.R;
import com.fy.rail.main.MainActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 欢迎页
 * 注意：1、欢迎页，背景图片不能是 svg图片
 *      2、使用此欢迎页项目应用id 需要和项目包名一致
 * Created by fangs on 2017/12/12.
 */
public class StartUpActivity extends AppCompatActivity implements IBaseActivity, View.OnClickListener {

    private int skip = 3;
    @BindView(R.id.tvSkip)
    TextView tvSkip;

    @Override
    public boolean isShowHeadView() {
        return false;
    }

    @Override
    public int setView() {
        return R.layout.login_startup_activity;
    }

    @StatusBar(statusColor = R.color.transparent, statusAlpha = 255, navColor = R.color.navigationBarColor, navAlpha = 255, statusOrNavModel = 1)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        StatusBarContentColor.setStatusTextColor(this, true, true);

        Drawable back = TintUtils.getTintDrawable(R.drawable.shape_ellipse_rect, 0, R.color.alphaBlack);
        tvSkip.setText(ResUtils.getReplaceStr(R.string.skip, skip));
        tvSkip.setBackground(back);

        hideLoadView();
    }

    @ClickFilter()
    @OnClick({R.id.tvSkip})
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tvSkip) {
            intoMainOrLogin();
        }
    }

    @SuppressLint("CheckResult")
    private void hideLoadView() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(skip + 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(aLong -> tvSkip.setText(ResUtils.getReplaceStr(R.string.skip, skip - aLong)))
                .compose(RxHelper.bindToLifecycle(this))
                .subscribe(aLong -> {
                    if (aLong == 2L)intoMainOrLogin();
                });
    }

    /**
     * 根据条件 判断进入登录页还是主界面
     */
    private void intoMainOrLogin() {
        if (Constant.isMustAppLogin && !SpfUtils.getSpfSaveBoolean(Constant.isLogin)) {
            JumpUtils.jump(this, AppUtils.getLocalPackageName() + ".login.LoginActivity", null);
        } else {
            JumpUtils.jump(this, MainActivity.class, null);
        }

        finish();
    }
}
