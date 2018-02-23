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


import android.arch.persistence.room.Room;
import android.content.Context;

import com.nsnik.nrs.carmonitor.dagger.qualifiers.ApplicationQualifier;
import com.nsnik.nrs.carmonitor.dagger.qualifiers.DatabaseName;
import com.nsnik.nrs.carmonitor.dagger.scopes.ApplicationScope;
import com.nsnik.nrs.carmonitor.data.AppDatabase;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class DatabaseModule {

    private static final String DATABASE_NAME = "NA";

    @NotNull
    @Provides
    @DatabaseName
    String getDatabaseName() {
        return DATABASE_NAME;
    }

    @NotNull
    @Provides
    @ApplicationScope
    AppDatabase getAppDatabase(@NotNull @ApplicationQualifier Context context, @NotNull @DatabaseName @ApplicationScope String databaseName) {
        return Room.databaseBuilder(context, AppDatabase.class, databaseName).build();
    }

}