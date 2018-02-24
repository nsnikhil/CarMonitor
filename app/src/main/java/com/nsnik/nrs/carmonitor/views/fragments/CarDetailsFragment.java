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

package com.nsnik.nrs.carmonitor.views.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.util.GenerateFakeGasValues;
import com.nsnik.nrs.carmonitor.viewModel.CarViewModel;

import org.jetbrains.annotations.Contract;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class CarDetailsFragment extends Fragment {

    @BindView(R.id.detailsCarNumber)
    TextView mCarNo;
    @BindView(R.id.detailsMethaneLevel)
    TextView mMethaneLevel;
    @BindView(R.id.detailsCarbonMonoxideLevel)
    TextView mCarbonMonoxideLevel;
    @BindView(R.id.detailsNitrogenLevel)
    TextView mNitrogenLevel;
    private Unbinder mUnbinder;
    private CarViewModel mCarViewModel;
    private Resources mResources;
    private CarEntity mThisCar;
    private double mMethaneValue, mCarbonMonoxideValue, mNitrogenValue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_details, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        if (getActivity() != null) mResources = getActivity().getResources();
        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);
        if (getActivity() != null && getArguments() != null) {
            final com.twitter.serial.stream.legacy.LegacySerial serial = new com.twitter.serial.stream.legacy.LegacySerial();
            try {
                mThisCar = serial.fromByteArray(getArguments().getByteArray(getActivity().getResources().getString(R.string.bundleCarObject)), CarEntity.SERIALIZER);
                if (mThisCar != null) {
                    mMethaneValue = mThisCar.getMethaneLevel();
                    mCarbonMonoxideValue = mThisCar.getCarbonMonoxideLevel();
                    mNitrogenValue = mThisCar.getNitrogenLevel();
                    setValues(mThisCar.getCarNo());
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        startTimer();
    }

    private void setValues(final String carNo) {
        mCarNo.setText(carNo);
        mCarViewModel.getMethaneLevel(carNo).observe(this, aDouble -> mMethaneLevel.setText(makeString(mResources.getString(R.string.gasMethane), aDouble)));
        mCarViewModel.getCarbonMonoxideLevel(carNo).observe(this, aDouble -> mCarbonMonoxideLevel.setText(makeString(mResources.getString(R.string.gasCarbonMonoxide), aDouble)));
        mCarViewModel.getNitrogenLevel(carNo).observe(this, aDouble -> mNitrogenLevel.setText(makeString(mResources.getString(R.string.gasNitrogen), aDouble)));
    }

    private void setNewValues() {
        if (mMethaneLevel != null) {
            mMethaneValue = GenerateFakeGasValues.getRandomValue();
            checkError(mResources.getString(R.string.gasMethane), mMethaneValue);
            mMethaneLevel.setText(makeString(mResources.getString(R.string.gasMethane), mMethaneValue));
        }
        if (mCarbonMonoxideLevel != null) {
            mCarbonMonoxideValue = GenerateFakeGasValues.getRandomValue();
            checkError(mResources.getString(R.string.gasCarbonMonoxide), mCarbonMonoxideValue);
            mCarbonMonoxideLevel.setText(makeString(mResources.getString(R.string.gasCarbonMonoxide), mCarbonMonoxideValue));
        }
        if (mNitrogenLevel != null) {
            mNitrogenValue = GenerateFakeGasValues.getRandomValue();
            checkError(mResources.getString(R.string.gasNitrogen), mNitrogenValue);
            mNitrogenLevel.setText(makeString(mResources.getString(R.string.gasNitrogen), mNitrogenValue));
        }
    }

    private void checkError(String gasName, double gasValue) {
        if (gasValue >= 80)
            Toast.makeText(getActivity(), "Warning " + gasName + " emission above the recommended limit", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        updateDatabase();
        super.onDetach();
    }

    private void updateDatabase() {
        mThisCar.setMethaneLevel(mMethaneValue);
        mThisCar.setCarbonMonoxideLevel(mCarbonMonoxideValue);
        mThisCar.setNitrogenLevel(mNitrogenValue);
        mCarViewModel.updateCar(mThisCar);
    }

    private void startTimer() {
        Handler handler = new Handler();
        final int delay = 5000;
        handler.postDelayed(new Runnable() {
            public void run() {
                setNewValues();
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @NonNull
    @Contract(pure = true)
    private String makeString(final String gasName, final Double value) {
        return gasName + " level: " + value;
    }

    private void cleanUp() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        cleanUp();
        super.onDestroy();
    }

}
