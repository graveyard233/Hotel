package com.example.hotel.UI.Manage.CommentManage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hotel.Bean.Announcement;
import com.example.hotel.R;
import com.example.hotel.UI.Announcement.AnnouncementPresenter;
import com.example.hotel.UI.Announcement.AnnouncementViewInterface;
import com.example.hotel.UI.Base.BaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class EditeAnnouncementActivity extends BaseActivity implements View.OnClickListener {


    private ImageView img_ok;
    private TextInputEditText title;
    private EditText text;

    private AnnouncementPresenter ap = new AnnouncementPresenter();

    @Override
    protected void initViews() {
        title = findViewById(R.id.announcement_add_title);
        text = findViewById(R.id.announcement_add_text);
        img_ok = findViewById(R.id.announcement_add_img_ok);
        img_ok.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.announcement_add_img_ok:
                Announcement a_this = new Announcement(title.getText().toString(),
                        text.getText().toString());
                addNew(a_this);
                break;
        }
    }

    private void addNew(Announcement a){

        ap.addAnnouncement(a, new AnnouncementViewInterface() {
            @Override
            public void addAnnouncement(String objId, int i) {
                if (i == 1){
                    System.out.println(objId);
                    Toast.makeText(getApplicationContext(),"添加成功,objId:" + objId,Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (i == 2){
                    System.out.println(objId);
                    Snackbar.make(findViewById(R.id.announcement_add_coord),"添加失败",Snackbar.LENGTH_SHORT).show();
                } else {
                    System.out.println("ffff");
                }
            }

            @Override
            public void getAllAnnouncementSucceed(List<Announcement> list) {

            }

            @Override
            public void getAllAnnouncementError() {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edite_announcement;
    }
}