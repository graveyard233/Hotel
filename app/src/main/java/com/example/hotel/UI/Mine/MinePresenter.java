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

            @Override
            public void getUserByUserName(User user) {

            }
        });
    }

    public void getUserByUserName(String username,MineViewInterface viewInterface){
        mineModel = MineModel.get();
        mineModel.getUserByUserName(username, new MineContract() {
            @Override
            public void getAllUserList(List<User> users) {

            }

            @Override
            public void getUserByUserName(User user) {
                if (user != null){
                    viewInterface.getUserByUserNameSucceed(user);
                } else {
                    viewInterface.getUserByUserNameError();
                }
            }
        });
    }
}
