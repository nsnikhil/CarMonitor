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

package com.nsnik.nrs.carmonitor.util.events;

import com.nsnik.nrs.carmonitor.data.CarEntity;

import org.jetbrains.annotations.Contract;

public final class GotoDetailsEvent {

    private final CarEntity mCarEntity;

    public GotoDetailsEvent(CarEntity carEntity) {
        mCarEntity = carEntity;
    }

    @Contract(pure = true)
    public CarEntity getCarEntity() {
        return mCarEntity;
    }
}
