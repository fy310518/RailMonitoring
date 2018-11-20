package com.fy.rail.main;

import android.Manifest;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fy.baselibrary.aop.annotation.NeedPermission;
import com.fy.baselibrary.base.BaseFragment;
import com.fy.baselibrary.base.ViewHolder;
import com.fy.baselibrary.base.dialog.CommonDialog;
import com.fy.baselibrary.base.dialog.DialogConvertListener;
import com.fy.baselibrary.base.dialog.NiceDialog;
import com.fy.baselibrary.utils.FileUtils;
import com.fy.baselibrary.utils.ResUtils;
import com.fy.baselibrary.utils.T;
import com.fy.baselibrary.utils.TimeUtils;
import com.fy.excelcreator.ColourUtil;
import com.fy.excelcreator.ZzExcelCreator;
import com.fy.excelcreator.ZzFormatCreator;
import com.fy.rail.R;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

/**
 * describe： 导出
 * Created by fangs on 2018/11/19 11:38.
 */
public class MainExportFragment extends BaseFragment {
    StringBuilder fileName = new StringBuilder();

    @BindView(R.id.tvStartDate)
    TextView tvStartDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;
    @BindView(R.id.tvDirection)
    TextView tvDirection;

    @Override
    protected int setContentLayout() {
        return R.layout.main_fragtment_export;
    }

    @Override
    protected void baseInit() {
        tvStartDate.setText(TimeUtils.Long2DataString(System.currentTimeMillis() - 30 * 24 * 3600 * 1000L, "yyyy-MM-dd"));
        tvEndDate.setText(TimeUtils.Long2DataString(System.currentTimeMillis(), "yyyy-MM-dd"));
    }

    @OnClick({R.id.tvStartDate, R.id.tvEndDate, R.id.tvDirection, R.id.btnExport})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvStartDate:

                break;
            case R.id.tvEndDate:

                break;
            case R.id.tvDirection:
                selectDirection();
                break;
            case R.id.btnExport://导出 Excel
                fileName.delete(0, fileName.length());
                fileName.append(tvDirection.getText().toString().trim())
                        .append(tvEndDate.getText().toString().trim());
                createExcel(fileName.toString());

                StringBuilder excelFile = new StringBuilder();
                excelFile.append(FileUtils.getPath("铁路", 2)).append("/").append(fileName).append(".xls");
                String[] titleArray = ResUtils.getStrArray(R.array.titleArray);
                for (int i = 0; i < titleArray.length; i++){
                    addData(excelFile.toString(), 0, i, titleArray[i]);
                }
                T.showLong("导出 Excel 成功！！！");
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

    /**
     * 创建Excel 文件
     * @param fileName
     */
    @NeedPermission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void createExcel(String fileName) {
        try {
            ZzExcelCreator
                    .getInstance()
                    .createExcel(FileUtils.getPath("铁路", 2), fileName)  //生成excel文件
                    .createSheet("sheetB")        //生成sheet工作表
                    .createSheet("sheetA")        //生成sheet工作表
                    .close();

        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定的 Excel 文件添加一条数据
     */
    private void addData(String filePath, int row, int col, String content){
        try {
            ZzExcelCreator.getInstance()
                    .openExcel(new File(filePath))  //打开Excel文件
                    .openSheet(0);

            //设置单元格内容格式
            WritableCellFormat format = ZzFormatCreator
                    .getInstance()
                    .createCellFont(WritableFont.ARIAL)  //设置字体
                    .setAlignment(Alignment.CENTRE, VerticalAlignment.CENTRE)  //设置对齐方式(水平和垂直)
                    .setFontSize(14)                    //设置字体大小
                    .setFontColor(Colour.ROSE)          //设置字体颜色
                    .setFontBold(true)                  //设置是否加粗，默认false
                    .setUnderline(true)                 //设置是否画下划线，默认false
                    //.setDoubleUnderline(true)         //设置是否画双重下划线，默认false,和setUnderline只有一个生效
                    .setItalic(true)                    //设置是否斜体
                    .setWrapContent(true, 100)          //设置是否自适应宽高，如果自适应，必须设置最大列宽（不能太大，否则可能无效）。
//                    .setBackgroundColor(ColourUtil.getCustomColor1("#99cc00"))  //设置单元格背景颜色，如果不设置边框，边框色会和背景色一致。
                    .setBorder(Border.ALL, BorderLineStyle.THIN, ColourUtil.getCustomColor2("#dddddd"))  //设置边框样式
                    .getCellFormat();

            ZzExcelCreator.getInstance()
                    .setColumnWidth(col, content.getBytes().length)   //设置列宽(如果自适应宽度，代表内容字节的长度,即str.getBytes().length)
                    .setRowHeight(row, 200)    //设置行高
                    .fillContent(col, row, content, format)  //填入字符串
//                    .fillNumber(colInt, rowInt, Double.parseDouble(str), format)  //填入数字
                    .close();

        } catch (IOException | BiffException | WriteException e) {
            e.printStackTrace();
        }
    }
}
