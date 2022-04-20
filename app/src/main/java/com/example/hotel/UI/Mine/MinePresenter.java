package com.example.hotel.UI.Mine;

import com.example.hotel.Bean.User;

import java.util.List;

public class MinePresenter {

    private MineModel mineModel;

    public void getMineModel(MineViewInterface viewInterface){
        mineModel = MineModel.get();
        mineModel.getAllUserList(new MineContract() {
            @Override
            public void getAllUserList(List<User> users) {
                if (viewInterface != null){
                    if (users.size() > 0)
                        viewInterface.getAllUsersSucceed(users);
                    else
                        viewInterface.getAllUsersError();
                }
                else {
                    viewInterface.getAllUsersError();
                }
            }
        });
    }
}
