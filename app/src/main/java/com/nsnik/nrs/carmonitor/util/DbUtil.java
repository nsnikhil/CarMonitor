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

package com.nsnik.nrs.carmonitor.util;

import android.arch.lifecycle.LiveData;

import com.nsnik.nrs.carmonitor.dagger.scopes.ApplicationScope;
import com.nsnik.nrs.carmonitor.data.AppDatabase;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.data.UserEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@ApplicationScope
public class DbUtil {

    private final AppDatabase mAppDatabase;

    @Inject
    DbUtil(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    public void insertUser(UserEntity... userEntities) {
        Single<long[]> single = Single.fromCallable(() -> mAppDatabase.getUserDao().insertUsers(userEntities)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<long[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(long[] longs) {
                for (long aLong : longs) {
                    Timber.d(String.valueOf(aLong));
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void deleteUser(UserEntity... userEntities) {
        Completable completable = Completable.fromCallable(() -> {
            mAppDatabase.getUserDao().deleteUsers(userEntities);
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Timber.d("Deleted Successfully");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void updateUser(UserEntity... userEntities) {
        Single<Integer> single = Single.fromCallable(() -> mAppDatabase.getUserDao().updateUsers(userEntities)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Timber.d(String.valueOf(integer));
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void deleteUserByPhoneNo(double phoneNo) {
        Completable completable = Completable.fromCallable(() -> {
            mAppDatabase.getUserDao().deleteUserByPhoneNo(phoneNo);
            return null;
        }).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread());
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Timber.d("Deleted Successfully");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void deleteUserByName(String name) {
        Completable completable = Completable.fromCallable(() -> {
            mAppDatabase.getUserDao().deleteUsersByName(name);
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Timber.d("Deleted Successfully");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public LiveData<List<UserEntity>> getUsersList() {
        return mAppDatabase.getUserDao().getUserList();
    }

    public LiveData<UserEntity> getUserFromPhoneNo(double phoneNo) {
        return mAppDatabase.getUserDao().getUserFromPhoneNo(phoneNo);
    }

    public LiveData<List<UserEntity>> getUsersByName(String name) {
        return mAppDatabase.getUserDao().getUserByName(name);
    }

    public void insertCar(CarEntity... carEntities) {
        Single<long[]> single = Single.fromCallable(() -> mAppDatabase.getCarDao().insertCars(carEntities)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<long[]>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(long[] longs) {
                for (long aLong : longs) {
                    Timber.d(String.valueOf(aLong));
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }
        });
    }

    public void deleteCar(CarEntity... carEntities) {
        Completable completable = Completable.fromCallable(() -> {
            mAppDatabase.getCarDao().deleteCars(carEntities);
            return null;
        }).observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Timber.d("Deleted Successfully");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void updateCar(CarEntity... carEntities) {
        Single<Integer> single = Single.fromCallable(() -> mAppDatabase.getCarDao().updateCars(carEntities)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        single.subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(Integer integer) {
                Timber.d(String.valueOf(integer));
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e.getMessage());
            }
        });
    }

    public void deleteCarByNumber(String carNumber) {
        Completable completable = Completable.fromCallable(() -> {
            mAppDatabase.getCarDao().deleteCarByNo(carNumber);
            return null;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        completable.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Timber.d("Deleted Successfully");
            }

            @Override
            public void onError(Throwable e) {
                Timber.d(e);
            }
        });
    }

    public LiveData<List<CarEntity>> getCarList() {
        return mAppDatabase.getCarDao().getCarList();
    }

    public LiveData<Double> getCarMethaneLevel(String carNo) {
        return mAppDatabase.getCarDao().getCarMethaneLevel(carNo);
    }

    public LiveData<Double> getCarbonMonoxideLevel(String carNo) {
        return mAppDatabase.getCarDao().getCarbonMonoxideLevel(carNo);
    }

    public LiveData<Double> getNitrogenLevel(String carNo) {
        return mAppDatabase.getCarDao().getNitrogenLevel(carNo);
    }

}
