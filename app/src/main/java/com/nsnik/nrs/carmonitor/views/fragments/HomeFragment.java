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


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.util.events.GotoDetailsEvent;
import com.nsnik.nrs.carmonitor.util.events.OpenMapsEvent;
import com.twitter.serial.stream.legacy.LegacySerial;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        if (getFragmentManager() != null)
            getFragmentManager().beginTransaction().add(R.id.homeContainer, new CarListFragment()).commit();
    }

    public void replaceFragment(CarEntity carEntity) {
        if (getFragmentManager() == null && getActivity() == null)
            return;
        final CarDetailsFragment fragment = new CarDetailsFragment();
        final Bundle bundle = new Bundle();
        final LegacySerial serial = new LegacySerial();
        try {
            final byte[] serializedEntity = serial.toByteArray(carEntity, CarEntity.SERIALIZER);
            bundle.putByteArray(getActivity().getResources().getString(R.string.bundleCarObject), serializedEntity);
            fragment.setArguments(bundle);
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.homeContainer, fragment)
                    .addToBackStack("details")
                    .commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void replaceFragment(Fragment fragment, String tag) {
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.homeContainer, fragment)
                .addToBackStack(tag)
                .commit();
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

    @Subscribe
    public void onGoToDetailEvent(GotoDetailsEvent gotoDetailsEvent) {
        replaceFragment(gotoDetailsEvent.getCarEntity());
    }

    @Subscribe
    public void onOpenMapsEvent(OpenMapsEvent openMapsEvent) {
        if (getActivity() == null)
            return;
        final MapsFragment fragment = new MapsFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(getActivity().getResources().getString(R.string.bundleLocation), openMapsEvent.getLocation());
        fragment.setArguments(bundle);
        replaceFragment(fragment, "maps");
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
