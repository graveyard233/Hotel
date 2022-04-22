package com.example.hotel.UI.Announcement;

import com.example.hotel.Bean.Announcement;

import java.util.List;

public interface AnnouncementContract {

    void addAnnouncement(String objId,int i);

    void getAllAnnouncement(List<Announcement> announcements);
}
