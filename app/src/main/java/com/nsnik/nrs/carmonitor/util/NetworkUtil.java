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

import android.arch.lifecycle.MutableLiveData;

import com.nsnik.nrs.carmonitor.dagger.scopes.ApplicationScope;
import com.nsnik.nrs.carmonitor.data.CarEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import timber.log.Timber;

@ApplicationScope
public class NetworkUtil {

    private final Retrofit mRetrofit;
    private MutableLiveData<List<CarEntity>> mCarList;

    @Inject
    NetworkUtil(final Retrofit retrofitClient) {
        mRetrofit = retrofitClient;
        mCarList = new MutableLiveData<>();
    }

    public void getDemoListServer() {
        mRetrofit.create(DemoRetrofitService.class).getCarList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CarEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<CarEntity> userEntity) {
                        mCarList.setValue(userEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d(e);
                    }
                });
    }

    public MutableLiveData<List<CarEntity>> getCarList() {
        return mCarList;
    }
}
