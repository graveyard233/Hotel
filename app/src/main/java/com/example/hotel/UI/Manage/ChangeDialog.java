package com.example.hotel.UI.Manage;

import android.app.Activity;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.hotel.R;

public class ChangeDialog {
    private Activity activity;
    private AlertDialog.Builder dialog;

    public ChangeDialog(Activity activity){
        this.activity = activity;
    }

    public void EditDialog(String title,Activity activity){
        this.activity = activity;
        dialog = new AlertDialog.Builder(activity);
        dialog.setCancelable(true);
        dialog.setTitle(title);

        dialog.show();
    }

    public void EditDialog(String title){
        dialog = new AlertDialog.Builder(activity);
        dialog.setView(R.layout.change_dialog_view);
        dialog.setCancelable(true);
        dialog.setTitle(title);
        dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = dialog.create();


//        dialog.setContentView(R.layout.change_dialog_view);

        alertDialog.show();
    }
}
