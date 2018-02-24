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
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.data.UserEntity;
import com.nsnik.nrs.carmonitor.viewModel.CarViewModel;
import com.nsnik.nrs.carmonitor.viewModel.UserViewModel;
import com.nsnik.nrs.carmonitor.views.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class LoginFragment extends Fragment {

    @BindView(R.id.formName)
    TextInputEditText mFormName;
    @BindView(R.id.formPhone)
    TextInputEditText mFormPhone;
    @BindView(R.id.formCarNo)
    TextInputEditText mFormCarNo;

    @BindView(R.id.fromSubmit)
    FloatingActionButton mFormSubmit;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;
    private CarViewModel mCarViewModel;
    private UserViewModel mUserViewModel;

    private Resources mResources;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        mCompositeDisposable = new CompositeDisposable();
        if (getActivity() != null) mResources = getActivity().getResources();

        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mCompositeDisposable.add(RxView.clicks(mFormSubmit).subscribe(v -> insertIntoDatabase(), throwable -> Timber.d(throwable.getMessage())));
    }

    private void insertIntoDatabase() {
        if (verifyFields()) {

            UserEntity userEntity = new UserEntity();
            userEntity.setPhoneNo(Double.parseDouble(mFormPhone.getText().toString()));
            userEntity.setUserName(mFormName.getText().toString());

            CarEntity carEntity = new CarEntity();
            carEntity.setCarNo(mFormCarNo.getText().toString());
            carEntity.setCarbonMonoxideLevel(0.00);
            carEntity.setMethaneLevel(0.00);
            carEntity.setNitrogenLevel(0.00);

            mUserViewModel.insertUser(userEntity);
            mCarViewModel.insertCar(carEntity);

            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    private boolean verifyFields() {
        if (mFormName.getText().toString().isEmpty()) {
            mFormName.setError(mResources.getString(R.string.formErrorName));
            return false;
        }
        if (mFormPhone.getText().toString().isEmpty()) {
            mFormPhone.setError(mResources.getString(R.string.formErrorPhone));
            return false;
        }
        if (mFormCarNo.getText().toString().isEmpty()) {
            mFormCarNo.setError(mResources.getString(R.string.formErrorCarNo));
            return false;
        }
        return true;
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
