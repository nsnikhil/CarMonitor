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
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class CarEntity {

    @NotNull
    @PrimaryKey
    private String mCarNo;
    private double mMethaneLevel;
    private double mCarbonMonoxideLevel;
    private double mNitrogenLevel;

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

}
