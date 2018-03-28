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


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.twitter.serial.stream.legacy.LegacySerial;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


public class CarDetailsFragment extends Fragment {

    @BindView(R.id.detailsCarNumber)
    TextView mCarNo;
    @BindView(R.id.detailsMethaneLevel)
    TextView mMethaneLevel;
    @BindView(R.id.detailsCarbonMonoxideLevel)
    TextView mCarbonMonoxideLevel;
    @BindView(R.id.detailsNitrogenLevel)
    TextView mNitrogenLevel;
    private static final int PERMISSION_REQUEST_CODE = 111;
    @BindView(R.id.detailsCoordinates)
    TextView mCoordinates;
    @BindView(R.id.detailsPhone)
    TextView mPhone;

    private Unbinder mUnbinder;
    @BindView(R.id.detailsAccident)
    TextView mAccident;
    private CarEntity mCarEntity;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_details, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void setData() {
        if (getArguments() == null && getActivity() == null)
            return;
        final LegacySerial serial = new LegacySerial();
        try {
            mCarEntity = serial.fromByteArray(getArguments().getByteArray(getActivity().getResources().getString(R.string.bundleCarObject)), CarEntity.SERIALIZER);
            setValues();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setValues() {
        if (getActivity() == null)
            return;
        mCarNo.setText(mCarEntity.getCarNo());
        mCoordinates.setText(mCarEntity.getCoordinates());
        mPhone.setText(mCarEntity.getPhone());
        mCarbonMonoxideLevel.setText(makeString(getActivity().getResources().getString(R.string.gasCarbonMonoxide), mCarEntity.getCarbonMonoxideLevel()));
        mNitrogenLevel.setText(makeString(getActivity().getResources().getString(R.string.gasNitrogen), mCarEntity.getNitrogenLevel()));
        mMethaneLevel.setText(makeString(getActivity().getResources().getString(R.string.gasMethane), mCarEntity.getMethaneLevel()));
        mAccident.setText(mCarEntity.getAccident());
    }

    private String makeString(String gasName, double gasValue) {
        return gasName + ": " + gasValue;
    }

    private void initialize() {
        setData();
        mCompositeDisposable = new CompositeDisposable();
        listeners();
    }

    private void listeners() {
        mCompositeDisposable.add(RxView.clicks(mCoordinates).subscribe(v -> {
            openMaps(mCoordinates.getText().toString());
        }, Timber::d));
        mCompositeDisposable.add(RxView.clicks(mPhone).subscribe(v -> {
            checkPermission(mPhone.getText().toString());
        }, Timber::d));
    }

    private void openMaps(String location) {
        Uri gmmIntentUri = Uri.parse("geo:" + location);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (getActivity() != null && mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void checkPermission(String phoneNo) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
            return;
        }
        callPhone(phoneNo);
    }

    private void callPhone(String phoneNo) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNo));
        startActivity(callIntent);
    }

    private void cleanUp() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
        }
    }

    @Override
    public void onDestroy() {
        cleanUp();
        super.onDestroy();
    }

}
