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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.util.Utility;
import com.nsnik.nrs.carmonitor.util.events.CallPersonEvent;
import com.nsnik.nrs.carmonitor.views.adapters.CarDetailsAdapter;
import com.twitter.serial.stream.legacy.LegacySerial;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


public class CarDetailsFragment extends Fragment {

    @BindView(R.id.detailList)
    RecyclerView mDetailsList;

    private static final int PERMISSION_REQUEST_CODE = 111;
    private Unbinder mUnbinder;
    private CarEntity mCarEntity;
    private CompositeDisposable mCompositeDisposable;
    private CarDetailsAdapter mCarDetailsAdapter;

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
        mCarDetailsAdapter = new CarDetailsAdapter(getActivity(), Utility.entityToDetails(mCarEntity));
        mDetailsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDetailsList.setAdapter(mCarDetailsAdapter);
    }

    private void initialize() {
        setData();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void checkPermission(String phoneNo) {
        if (getActivity() == null)
            return;
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

    @Subscribe
    public void onCallPersonEvent(CallPersonEvent callPersonEvent) {
        checkPermission(callPersonEvent.getPhoneNo());
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
