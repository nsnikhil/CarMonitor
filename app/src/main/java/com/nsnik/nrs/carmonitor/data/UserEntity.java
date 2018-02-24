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

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.twitter.serial.serializer.ObjectSerializer;
import com.twitter.serial.serializer.SerializationContext;
import com.twitter.serial.stream.SerializerInput;
import com.twitter.serial.stream.SerializerOutput;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Entity
public class UserEntity {

    @Ignore
    public static final ObjectSerializer<UserEntity> SERIALIZER = new UserEntityObjectSerializer();

    @PrimaryKey
    private double mPhoneNo;
    private String mUserName;

    public double getPhoneNo() {
        return mPhoneNo;
    }

    public void setPhoneNo(double phoneNo) {
        this.mPhoneNo = phoneNo;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    private static final class UserEntityObjectSerializer extends ObjectSerializer<UserEntity> {

        @Override
        protected void serializeObject(@NotNull SerializationContext context, @NotNull SerializerOutput output, @NotNull UserEntity object) throws IOException {
            output.writeDouble(object.mPhoneNo);
            output.writeString(object.mUserName);
        }

        @Nullable
        @Override
        protected UserEntity deserializeObject(@NotNull SerializationContext context, @NotNull SerializerInput input, int versionNumber) throws IOException, ClassNotFoundException {
            final UserEntity userEntity = new UserEntity();
            userEntity.setPhoneNo(input.readDouble());
            userEntity.setUserName(input.readString());
            return userEntity;
        }
    }
}