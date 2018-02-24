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

package com.nsnik.nrs.carmonitor.dagger.modules;

import android.support.annotation.NonNull;

import com.nsnik.nrs.carmonitor.dagger.qualifiers.BaseUrl;

import org.jetbrains.annotations.Contract;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private static final String BASE_URL = "https://127.0.0.1";

    @Contract(pure = true)
    @Provides
    @BaseUrl
    String getBaseUrl() {
        return BASE_URL;
    }

    @Provides
    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @NonNull
    @Provides
    OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
    }

    /*@NonNull
    @Provides
    GsonConverterFactory getGsonConverterFactory(){
        return GsonConverterFactory.create(new GsonBuilder().setLenient().registerTypeAdapterFactory(AutoValueGsonFactory.create()).create());
    }*/

    @NonNull
    @Provides
    Retrofit getRetrofitClient(final @BaseUrl String baseUrl, final OkHttpClient okHttpClient /*, final GsonConverterFactory gsonConverterFactory*/) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}
