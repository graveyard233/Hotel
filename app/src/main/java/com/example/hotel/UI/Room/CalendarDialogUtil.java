package com.example.hotel.UI.Room;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.hotel.R;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarDialogUtil {
    public static CalendarDialog showChooseDateDialog(SampleDecorator decorator,final Activity context, final String title, final String okText, final String cancelText, final CalendarDialog.ClickCallBack clickCallBack, final TextView view) {
        final CalendarDialog CalendarDialog = new CalendarDialog(context, R.layout.dialog_choosedate_layout);
        CalendarDialog.setOnCustomerViewCreated(new CalendarDialog.CustomerViewInterface() {
            @Override
            public void getCustomerView(Window window, AlertDialog dlg) {


                TextView tv_title = (TextView) window.findViewById(R.id.title);
                Button left_button = (Button) window.findViewById(R.id.left_button);
                Button right_button = (Button) window.findViewById(R.id.right_button);
                final CalendarPickerView pickerView = (CalendarPickerView) window.findViewById(R.id.calendar_picker);
//                Calendar lastYear = Calendar.getInstance();
//                lastYear.add(Calendar.DAY_OF_WEEK, -1);
//                Calendar currentYear = Calendar.getInstance();
//                currentYear.add(Calendar.DAY_OF_WEEK, 0);
//                SampleDecorator decorator = SampleDecorator.get();
                List<CalendarCellDecorator> d = new ArrayList<>();
                d.add(decorator);
                pickerView.setDecorators(d);
                Calendar nextYear = Calendar.getInstance();
                nextYear.add(Calendar.MONTH, 2);
                //只能看之后两个月的日程
                pickerView.init(new Date(),nextYear.getTime()).withSelectedDate(new Date())
                        .inMode(CalendarPickerView.SelectionMode.RANGE);
                //点击范围之外时的提示
                pickerView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
                    @Override
                    public void onInvalidDateSelected(Date date) {
                        Toast.makeText(context, "非法的日期", Toast.LENGTH_SHORT).show();
                    }
                });
                if (!TextUtils.isEmpty(title)) {
                    tv_title.setText(title);
                } else {
                    tv_title.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(cancelText)) {
                    left_button.setText(cancelText);
                }
                left_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCallBack.onCancel(CalendarDialog);
                    }
                });
                if (!TextUtils.isEmpty(okText)) {
                    right_button.setText(okText);
                }
                right_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCallBack.onOk(CalendarDialog);
                        long time = pickerView.getSelectedDate().getTime();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String result = format.format(time);
//                        view.setText(result);
                    }
                });
            }
        });
        CalendarDialog.showDlg();
        return CalendarDialog;
    }

}
