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

import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.model.DetailObject;

import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static final String DETAILS_CAR_NO = "carNo";
    public static final String DETAILS_PHONE_NO = "phoneNo";
    public static final String DETAILS_COORDINATES = "coordinates";
    public static final String DETAILS_CARBONMONOXIDE_VALUE = "carbonmonoxideValue";
    public static final String DETAILS_METHANE_VALUE = "methaneValue";
    public static final String DETAILS_NITROGEN_VALUE = "nitrogenValue";
    public static final String DETAILS_ACCIDENT_VALUE = "accidentValue";

    public static List<DetailObject> entityToDetails(final CarEntity carEntity) {
        final List<DetailObject> detailObjects = new ArrayList<>();
        detailObjects.add(new DetailObject(DETAILS_CAR_NO, carEntity.getCarNo()));
        detailObjects.add(new DetailObject(DETAILS_PHONE_NO, carEntity.getPhone()));
        detailObjects.add(new DetailObject(DETAILS_COORDINATES, carEntity.getCoordinates()));
        detailObjects.add(new DetailObject(DETAILS_CARBONMONOXIDE_VALUE, String.valueOf(carEntity.getCarbonMonoxideLevel())));
        detailObjects.add(new DetailObject(DETAILS_METHANE_VALUE, String.valueOf(carEntity.getMethaneLevel())));
        detailObjects.add(new DetailObject(DETAILS_NITROGEN_VALUE, String.valueOf(carEntity.getNitrogenLevel())));
        detailObjects.add(new DetailObject(DETAILS_ACCIDENT_VALUE, carEntity.getAccident()));
        return detailObjects;
    }

}
