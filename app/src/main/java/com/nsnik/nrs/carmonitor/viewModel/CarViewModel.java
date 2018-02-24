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
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.util.DbUtil;

import java.util.List;

public class CarViewModel extends AndroidViewModel {

    private final DbUtil mDbUtil;
    private LiveData<List<CarEntity>> mCarList;

    public CarViewModel(@NonNull Application application) {
        super(application);
        mDbUtil = ((MyApplication) application).getDbUtil();
        mCarList = mDbUtil.getCarList();
    }

    public LiveData<List<CarEntity>> getCarList() {
        return mCarList;
    }

    public LiveData<Double> getMethaneLevel(String carNo) {
        return mDbUtil.getCarMethaneLevel(carNo);
    }

    public LiveData<Double> getCarbonMonoxideLevel(String carNo) {
        return mDbUtil.getCarbonMonoxideLevel(carNo);
    }

    public LiveData<Double> getNitrogenLevel(String carNo) {
        return mDbUtil.getNitrogenLevel(carNo);
    }

    public void insertCar(CarEntity... carEntities) {
        mDbUtil.insertCar(carEntities);
    }

    public void deleteCar(CarEntity... carEntities) {
        mDbUtil.deleteCar(carEntities);
    }

    public void updateCar(CarEntity... carEntities) {
        mDbUtil.updateCar(carEntities);
    }

    public void deleteCarByNumber(String carNumber) {
        mDbUtil.deleteCarByNumber(carNumber);
    }
}
