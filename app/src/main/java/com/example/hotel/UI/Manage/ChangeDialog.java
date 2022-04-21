package com.example.hotel.UI.Manage;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.hotel.R;

public class ChangeDialog extends AlertDialog implements View.OnClickListener {
    EditText editText;

    TextView textView_old;
    String oldText;

    TextView textView_ok;
    TextView textView_cancel;

    public ChangeDialog(@NonNull Context context) {
        super(context,0);
    }

    public ChangeDialog(@NonNull Context context,String oldText) {
        super(context,0);
        this.oldText = oldText;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_dialog_view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(params);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        editText = findViewById(R.id.dialog_new_input);
        textView_old = findViewById(R.id.dialog_old);

        textView_old.setText(oldText);

        textView_ok = findViewById(R.id.change_dialog_ok);
        textView_ok.setOnClickListener(this);
        textView_cancel = findViewById(R.id.change_dialog_cancel);
        textView_cancel.setOnClickListener(this);
    }

    public void setOldText(String oldText) {
        this.oldText = oldText;
    }

    public String getInputText(){
        if (!editText.getText().toString().equals(""))
            return editText.getText().toString();
        else return "";
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void OnItemClick(ChangeDialog dialog, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        listener.OnItemClick(this,view);
    }
}
