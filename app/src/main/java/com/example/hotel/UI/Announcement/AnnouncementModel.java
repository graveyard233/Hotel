package com.example.hotel.UI.Announcement;

import android.util.Log;

import com.example.hotel.Bean.Announcement;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class AnnouncementModel {
    private static final String TAG = "AnnouncementModel";
    private volatile static AnnouncementModel mInstance;

    private AnnouncementModel(){

    }

    public static AnnouncementModel get(){
        if (mInstance == null){
            Log.i("AnnouncementModel", "get: 没有单例，创建一个announcementModel");
            synchronized (AnnouncementModel.class){
                if (mInstance == null){
                    mInstance = new AnnouncementModel();
                }
            }
        }
        else {
            Log.i("AnnouncementModel", "已经有一个了");
        }
        return mInstance;
    }

    public Announcement getAllAnnouncements(){
        return null;
    }

    public Announcement addAnnouncement(Announcement announcement,AnnouncementContract contract){
        announcement.save(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if (e == null){
                    Log.i(TAG, "done: " + s);
                    if (contract != null){
                        contract.addAnnouncement(s,1);
                    }
                    else {
                        Log.e(TAG, "add Error" + e.getMessage());
                        if (contract != null){
                            contract.addAnnouncement(e.getMessage(),2);
                        }
                    }
                }
            }
        });
        return null;
    }

    public Announcement getAllAnnouncement(int i,AnnouncementContract contract){
        String bql = "select * from Announcement";
        BmobQuery<Announcement> bmobQuery = new BmobQuery<>();
        bmobQuery.setSQL(bql);
        if (i == 1){
            bmobQuery.doSQLQuery(new SQLQueryListener<Announcement>() {
                @Override
                public void done(BmobQueryResult<Announcement> bmobQueryResult, BmobException e) {
                    List<Announcement> list = new ArrayList<>();
                    if (e == null){
                        list = bmobQueryResult.getResults();
                        if (list != null && list.size() > 0 ){
                            for (int j = 0; j < list.size(); j++) {
                                Log.i(TAG, "done: " +
                                        list.get(j).getTitle());
                            }

                            if (contract != null){
                                contract.getAllAnnouncement(list);
                            }
                        }else {
                            Log.i(TAG, "done: 查询成功，无数据返回");
                        }
                    } else {
                        Log.i(TAG, "error: " + e.getMessage());
                    }
                }
            });
        }


        return null;
    }
}
