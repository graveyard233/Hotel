package com.example.hotel.UI.Mine;

import com.example.hotel.Bean.User;

import java.util.List;

public interface MineViewInterface {
    void getAllUsersSucceed(List<User> users);

    void getAllUsersError();

    void getUserByUserNameSucceed(User user);

    void getUserByUserNameError();
}
