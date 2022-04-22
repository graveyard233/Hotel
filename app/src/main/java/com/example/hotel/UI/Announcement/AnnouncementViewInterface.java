package com.example.hotel.UI.Announcement;

import com.example.hotel.Bean.Announcement;

import java.util.List;

public interface AnnouncementViewInterface {

    void addAnnouncement(String objId,int i);

    void getAllAnnouncementSucceed(List<Announcement> list);

    void getAllAnnouncementError();
}
