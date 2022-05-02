package com.example.hotel.UI.Manage.UserManage;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.hotel.Bean.User;
import com.example.hotel.R;
import com.example.hotel.UI.Manage.ChangeDialog;
import com.google.android.material.textfield.TextInputLayout;

public class ChangeUserInfoDialog extends AlertDialog implements View.OnClickListener{

    User user_dialog;
    private TextInputLayout IDcard_input;
    private TextInputLayout username_input;
    private TextInputLayout phone_input;
    private RadioGroup radioGroup;
    private TextView textView_ok;
    private TextView textView_cancel;
    private RadioButton r1;
    private RadioButton r2;

    protected ChangeUserInfoDialog(@NonNull Context context) {
        super(context,0);
    }

    public ChangeUserInfoDialog(@NonNull Context context, User user) {
        super(context, 0);
        user_dialog = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_user_info_dialog_view);
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

        IDcard_input = findViewById(R.id.change_user_info_input_layout_IDcard);
        IDcard_input.getEditText().setText(user_dialog.getIDcard());

        username_input = findViewById(R.id.change_user_info_input_layout_username);
        username_input.getEditText().setText(user_dialog.getUsername());

        phone_input = findViewById(R.id.change_user_info_input_layout_phone);
        phone_input.getEditText().setText(user_dialog.getPhone());

        radioGroup = findViewById(R.id.change_user_info_radioGroup);
        r1 = findViewById(R.id.change_user_info_radioButton1);
        r2 = findViewById(R.id.change_user_info_radioButton2);
        if (user_dialog.getSex().equals("女")){
            r2.setChecked(true);
        } else {
            r1.setChecked(true);
        }

        textView_ok = findViewById(R.id.change_user_info_ok);
        textView_ok.setOnClickListener(this);
        textView_cancel = findViewById(R.id.change_user_info_cancel);
        textView_cancel.setOnClickListener(this);
    }



    private OnDialogItemClickListener listener;

    public interface OnDialogItemClickListener{
        void OnItemClick(ChangeUserInfoDialog dialog, View view);
    }
    public void setDialogOnItemClickListener(OnDialogItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.OnItemClick(this,view);
    }

    public User getChangeUser(){
        if (checkUserInput()){
            user_dialog.setUsername(username_input.getEditText().getText().toString());
            user_dialog.setIDcard(IDcard_input.getEditText().getText().toString());
            if (r1.isChecked()){
                user_dialog.setSex("男");
            } else {
                user_dialog.setSex("女");
            }
            user_dialog.setPhone(phone_input.getEditText().getText().toString());
            return user_dialog;
        } else
            return null;

    }

    private Boolean checkUserInput(){
        if (username_input.getEditText().getText().toString().equals("") ||
        IDcard_input.getEditText().getText().toString().equals("") ||
        phone_input.getEditText().getText().toString().equals("")){
            return false;
        } else {
            return true;
        }

    }
}
