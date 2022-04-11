package com.example.hotel.UI.Room;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

public class CalendarDialog {
    private Activity context;
    private int res;
    private CustomerViewInterface listener;
    private AlertDialog dlg;

    public CalendarDialog(Activity context, int res) {
        this.context = context;
        this.res = res;
    }

    /**
     * 调用这个构造方法之后必须调用init方法
     */
    public CalendarDialog() {

    }

    public void init(Activity context, int res) {
        this.context = context;
        this.res = res;
    }

    /**
     * 在调用这个方法之前最好先调用setOnCustomerViewCreated来控制dialog自定义界面上的内容
     */
    public void showDlg() {
        dlg = new AlertDialog.Builder(context).create();
        dlg.setCanceledOnTouchOutside(true);
        dlg.setCancelable(true);
        dlg.show();
        Window window = dlg.getWindow();
        // 下面的清除flag主要是为了在dialog中有editText时弹出软件盘所用。
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        window.setContentView(res);
        if (listener != null) {
            listener.getCustomerView(window, dlg);
        }
    }

    public void setDlgIfClick(boolean ifClick) {
        if (dlg != null) {
            dlg.setCancelable(ifClick);
            dlg.setCanceledOnTouchOutside(ifClick);
        }
    }

    public void dismissDlg() {
        if (dlg != null) {
            dlg.dismiss();
            context = null;
            listener = null;
            dlg = null;
        }
    }

    public AlertDialog getDlg() {
        return dlg;
    }

    public interface CustomerViewInterface {

        void getCustomerView(final Window window, final AlertDialog dlg);
    }

    public interface ClickCallBack {

        void onOk(CalendarDialog dlg);

        void onCancel(CalendarDialog dlg);
    }

    public void setOnCustomerViewCreated(CustomerViewInterface listener) {
        this.listener = listener;
    }

}
