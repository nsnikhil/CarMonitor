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
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.twitter.serial.serializer.ObjectSerializer;
import com.twitter.serial.serializer.SerializationContext;
import com.twitter.serial.stream.SerializerInput;
import com.twitter.serial.stream.SerializerOutput;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Entity
public class CarEntity {

    @Ignore
    public static final ObjectSerializer<CarEntity> SERIALIZER = new CarEntityObjectSerializer();

    @NotNull
    @PrimaryKey
    @SerializedName("carno")
    private String mCarNo;
    @SerializedName("meval")
    private double mMethaneLevel;
    @SerializedName("coval")
    private double mCarbonMonoxideLevel;
    @SerializedName("noval")
    private double mNitrogenLevel;
    @SerializedName("coordinates")
    private String mCoordinates;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("accident")
    private String mAccident;

    public String getCarNo() {
        return mCarNo;
    }

    public void setCarNo(String cardNo) {
        this.mCarNo = cardNo;
    }

    public double getMethaneLevel() {
        return mMethaneLevel;
    }

    public void setMethaneLevel(double methaneLevel) {
        this.mMethaneLevel = methaneLevel;
    }

    public double getCarbonMonoxideLevel() {
        return mCarbonMonoxideLevel;
    }

    public void setCarbonMonoxideLevel(double carbonMonoxideLevel) {
        this.mCarbonMonoxideLevel = carbonMonoxideLevel;
    }

    public double getNitrogenLevel() {
        return mNitrogenLevel;
    }

    public void setNitrogenLevel(double nitrogenLevel) {
        this.mNitrogenLevel = nitrogenLevel;
    }

    public String getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(String coordinates) {
        this.mCoordinates = coordinates;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }

    public String getAccident() {
        return mAccident;
    }

    public void setAccident(String accident) {
        this.mAccident = accident;
    }

    private static final class CarEntityObjectSerializer extends ObjectSerializer<CarEntity> {

        @Override
        protected void serializeObject(@NotNull SerializationContext context, @NotNull SerializerOutput output, @NotNull CarEntity object) throws IOException {
            output.writeString(object.mCarNo);
            output.writeDouble(object.mMethaneLevel);
            output.writeDouble(object.mCarbonMonoxideLevel);
            output.writeDouble(object.mNitrogenLevel);
            output.writeString(object.mCoordinates);
            output.writeString(object.mPhone);
            output.writeString(object.mAccident);
        }

        @NonNull
        @Override
        protected CarEntity deserializeObject(@NotNull SerializationContext context, @NotNull SerializerInput input, int versionNumber) throws IOException {
            final CarEntity carEntity = new CarEntity();
            carEntity.setCarNo(input.readString());
            carEntity.setMethaneLevel(input.readDouble());
            carEntity.setCarbonMonoxideLevel(input.readDouble());
            carEntity.setNitrogenLevel(input.readDouble());
            carEntity.setCoordinates(input.readString());
            carEntity.setPhone(input.readString());
            carEntity.setAccident(input.readString());
            return carEntity;
        }
    }

}
