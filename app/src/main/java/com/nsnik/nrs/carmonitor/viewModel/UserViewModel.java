/*
 *    Copyright 2018 nsnikhil
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.nsnik.nrs.carmonitor.viewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.nsnik.nrs.carmonitor.MyApplication;
import com.nsnik.nrs.carmonitor.data.UserEntity;
import com.nsnik.nrs.carmonitor.util.DbUtil;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final DbUtil mDbUtil;
    private LiveData<List<UserEntity>> mUserList;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mDbUtil = ((MyApplication) application).getDbUtil();
        mUserList = mDbUtil.getUsersList();
    }

    public LiveData<List<UserEntity>> getUserList() {
        return mUserList;
    }

    public LiveData<UserEntity> getUserByPhoneNo(double phoneNo) {
        return mDbUtil.getUserFromPhoneNo(phoneNo);
    }

    public LiveData<List<UserEntity>> getUserByName(String name) {
        return mDbUtil.getUsersByName(name);
    }

    public void insertUser(UserEntity... userEntities) {
        mDbUtil.insertUser(userEntities);
    }

    public void updateUser(UserEntity... userEntities) {
        mDbUtil.updateUser(userEntities);
    }

    public void deleteUser(UserEntity... userEntities) {
        mDbUtil.deleteUser(userEntities);
    }

    public void deleteUserByPhoneNo(double phoneNo) {
        mDbUtil.deleteUserByPhoneNo(phoneNo);
    }

    public void deleteUserByName(String name) {
        mDbUtil.deleteUserByName(name);
    }
}
