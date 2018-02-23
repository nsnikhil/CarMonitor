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

package com.nsnik.nrs.carmonitor.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM UserEntity")
    LiveData<List<UserEntity>> getUserList();

    @Query("SELECT * FROM UserEntity WHERE mPhoneNo = :phoneNo")
    LiveData<UserEntity> getUserFromPhoneNo(int phoneNo);

    @Query("SELECT * FROM UserEntity WHERE mUserName = :userName")
    LiveData<List<UserEntity>> getUserByName(String userName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertUsers(UserEntity... userEntities);

    @Delete
    void deleteUsers(UserEntity... userEntities);

    @Query("DELETE FROM UserEntity WHERE mPhoneNo = :phoneNo")
    void deleteUserByPhoneNo(int phoneNo);

    @Query("DELETE FROM UserEntity WHERE mUserName = :userName")
    void deleteUsersByName(String userName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateUsers(UserEntity... userEntities);

}