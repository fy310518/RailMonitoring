package com.fy.rail.main;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.rail.R;
import com.fy.rail.bean.Record;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * describe 首页
 * Created by fangs on 2018/11/19 10:58.
 */
public class MainInputFragment extends BaseFragment {

    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvDirection)
    TextView tvDirection;
    @BindView(R.id.editNumKm)
    TextView editNumKm;
    @BindView(R.id.editTrackNum)
    TextView editTrackNum;

    @BindView(R.id.editAbnormityDesc)
    EditText editAbnormityDesc;//异常描述
    @BindView(R.id.editOther)
    EditText editOther;//其它

    @Override
    protected int setContentLayout() {
        return R.layout.main_fragment_input;
    }

    @Override
    protected void baseInit() {
        tvDate.setText(TimeUtils.Long2DataString(System.currentTimeMillis(), "yyyy-MM-dd"));

    }

    @OnClick({R.id.tvDirection, R.id.editNumKm, R.id.editTrackNum, R.id.btnSave})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvDirection://上/下 行
                selectDirection();
                break;
            case R.id.editNumKm://千米数
                numKmSelect(getDatas(453, 476, "km"), editNumKm);
                break;
            case R.id.editTrackNum://轨道号
                numKmSelect(getDatas(1, 40, ""), editTrackNum);
                break;
            case R.id.btnSave://保存到数据库
                for (int i = 1; i < 30; i++) {
                    runSaveDB(i);
                    runSaveDB(i);
                    runSaveDB(i);
                    runSaveDB(i);
                }
                break;
        }
    }

    private void selectDirection() {
        NiceDialog.init().setLayoutId(R.layout.dialog_common)
                .setDialogConvertListener(new DialogConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, CommonDialog dialog) {
                        TextView tv_top = holder.getView(R.id.tv_top);
                        TextView tv_down = holder.getView(R.id.tv_down);

                        tv_top.setText(R.string.up);
                        tv_top.setOnClickListener(v -> {
                            tvDirection.setText(R.string.up);
                            dialog.dismiss();
                        });
                        tv_down.setText(R.string.down);
                        tv_down.setOnClickListener(v -> {
                            tvDirection.setText(R.string.down);
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

    private void numKmSelect(List<String> datas, TextView tv){
        new MainInputSelectDialog()
                .setDatas(datas)
                .setClickListener(view -> tv.setText((String)view.getTag()))
                .show(getFragmentManager());
    }

    private List<String> getDatas(int form, int to, String unit){
        List<String> datas = new ArrayList<>();
        while (form <= to){
            datas.add(form + unit);
            form++;
        }

        return datas;
    }

    /**
     * 保存数据到数据库
     */
    @SuppressLint("CheckResult")
    private void runSaveDB(int i){
        Record record = new Record();
        record.setSaveDate("2018-11-" + i);
        record.setDirection(tvDirection.getText().toString().trim());
        record.setNumOfKm(editNumKm.getText().toString().trim());
        record.setTrackNum(editTrackNum.getText().toString().trim());
        record.setAbnormityDesc(editAbnormityDesc.getText().toString().trim() + i);
        record.setOtherDesc(editOther.getText().toString().trim() + i);

        Observable.just(record)
                .map(new Function<Record, Boolean>() {
                    @Override
                    public Boolean apply(Record record) throws Exception {
                        return record.save();
                    }
                }).subscribeOn(Schedulers.io())//指定的是上游发送事件的线程
                .observeOn(AndroidSchedulers.mainThread())//指定的是下游接收事件的线程
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        T.showLong("存储成功！！！");
                    } else {
                        T.showLong("存储失败！！！");
                    }
                });
    }


}
