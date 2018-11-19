package com.fy.rail.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.rv.adapter.OnListener;
import com.fy.baselibrary.rv.adapter.RvCommonAdapter;
import com.fy.rail.R;

import java.util.List;

/**
 * describe： 列表选择器
 * Created by fangs on 2018/11/19 17:31.
 */
public class MainInputSelectDialog extends CommonDialog {

    List<String> datas;
    OnListener.OnitemClickListener clickListener;

    public MainInputSelectDialog() {
        setWidthPixels(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeightPixels(200)
                .setGravity(Gravity.BOTTOM)
                .setAnim(R.style.AnimDown)
                .setHide(true);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.dialog_list_select;
    }

    @Override
    public void convertView(ViewHolder holder, CommonDialog dialog) {
        RvCommonAdapter adapter = new RvCommonAdapter<String>(getContext(), R.layout.dialog_list_select_item, datas) {
            @Override
            public void convert(ViewHolder holder, String content, int position) {
                holder.setText(R.id.tvItem, content);
                holder.itemView.setTag(content);
            }
        };


        RecyclerView rvDialogSelect = holder.getView(R.id.rvDialogSelect);
        rvDialogSelect.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDialogSelect.setAdapter(adapter);

        adapter.setItemClickListner(view -> {
            if(null != clickListener)clickListener.onItemClick(view);
            dismiss();
        });

        holder.setOnClickListener(R.id.tvCancel, v -> dismiss());
    }

    public MainInputSelectDialog setDatas(List<String> datas) {
        this.datas = datas;
        return this;
    }

    public MainInputSelectDialog setClickListener(OnListener.OnitemClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }
}
