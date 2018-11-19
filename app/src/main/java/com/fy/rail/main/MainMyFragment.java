package com.fy.rail.main;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.startactivity.StartActivity;
import com.fy.baselibrary.utils.Constant;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;
import com.fy.rail.R;

import butterknife.OnClick;

/**
 * describe 我的
 * Created by fangs on 2018/11/19 11:01.
 */
public class MainMyFragment extends BaseFragment {

    @Override
    protected int setContentLayout() {
        return R.layout.main_fragment_my;
    }

    @Override
    protected void baseInit() {

    }

    @OnClick({R.id.btnMyInfo, R.id.btnMyAbout, R.id.btnMyClose})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMyInfo:
                T.showLong("修改个人信息");
                break;
            case R.id.btnMyAbout:
                T.showLong("关于我们");
                break;
            case R.id.btnMyClose:
                showExitDialog();
                break;
        }
    }

    private void showExitDialog() {
        NiceDialog.init().setLayoutId(R.layout.dialog_common)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        TextView tv_top = holder.getView(R.id.tv_top);
                        TextView tv_down = holder.getView(R.id.tv_down);
                        tv_top.setText(R.string.sureExit);
                        tv_down.setText(R.string.close);
                        tv_down.setTextColor(ResUtils.getColor(R.color.kernel));
                        tv_down.setOnClickListener(v -> {
                            SpfUtils.saveBooleanToSpf(Constant.isLogin, false);
                            JumpUtils.exitApp(getActivity(), StartActivity.class);
                            dialog.dismiss();
                        });

                        holder.setOnClickListener(R.id.tvCancel, v -> dialog.dismiss());
                    }
                }).setWidthPixels(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.BOTTOM)
                .setAnim(R.style.AnimDown)
                .setHide(true)
                .show(getFragmentManager());
    }
}
