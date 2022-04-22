package com.example.hotel.UI.Announcement;

import com.example.hotel.Bean.Announcement;

import java.util.List;

public class AnnouncementPresenter {

    private AnnouncementModel announcementModel;

    public void addAnnouncement(Announcement a,AnnouncementViewInterface viewInterface){
        announcementModel = AnnouncementModel.get();
        announcementModel.addAnnouncement(a, new AnnouncementContract() {
            @Override
            public void addAnnouncement(String objId, int i) {
                viewInterface.addAnnouncement(objId, i);
            }

            @Override
            public void getAllAnnouncement(List<Announcement> announcements) {

            }
        });
    }

    public void getAllAnnouncement(int i,AnnouncementViewInterface viewInterface){
        announcementModel = AnnouncementModel.get();
        announcementModel.getAllAnnouncement(i, new AnnouncementContract() {
            @Override
            public void addAnnouncement(String objId, int i) {

            }

            @Override
            public void getAllAnnouncement(List<Announcement> list) {
                if (viewInterface != null){
                    if (list.size() > 0)
                        viewInterface.getAllAnnouncementSucceed(list);
                    else
                        viewInterface.getAllAnnouncementError();
                } else {
                    viewInterface.getAllAnnouncementError();
                }
            }
        });
    }
}
