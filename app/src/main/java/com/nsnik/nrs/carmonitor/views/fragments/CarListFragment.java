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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.viewModel.CarViewModel;
import com.nsnik.nrs.carmonitor.views.adapters.CarListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class CarListFragment extends Fragment {

    @BindView(R.id.carList)
    RecyclerView mCarListView;

    private CarListAdapter mListAdapter;
    private Unbinder mUnbinder;
    private List<CarEntity> mCarList;
    private CarViewModel mCarViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        mCarList = new ArrayList<>();
        mListAdapter = new CarListAdapter(getActivity(), mCarList);
        mCarListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCarListView.setAdapter(mListAdapter);

        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);

        mCarViewModel.getCarList().observe(this, this::modifyList);
    }

    private void modifyList(List<CarEntity> carList) {
        Timber.d(String.valueOf(carList.size()));
        mCarList = carList;
        mListAdapter.modifyList(mCarList);
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