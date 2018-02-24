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

package com.nsnik.nrs.carmonitor.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nsnik.nrs.carmonitor.BuildConfig;
import com.nsnik.nrs.carmonitor.MyApplication;
import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.util.SimpleIdlingResource;
import com.nsnik.nrs.carmonitor.viewModel.CarViewModel;
import com.nsnik.nrs.carmonitor.views.fragments.CarListFragment;
import com.nsnik.nrs.carmonitor.views.fragments.LoginFragment;
import com.squareup.leakcanary.RefWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String[] FRAGMENT_TAGS = {"login", "carList", "carDetails"};
    @BindView(R.id.mainToolbar)
    Toolbar mMainToolbar;
    private CarViewModel mCarViewModel;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        setSupportActionBar(mMainToolbar);
        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);

        mCarViewModel.getCarList().observe(this, carEntities -> {
            if (carEntities != null)
                if (carEntities.size() <= 0)
                    getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, new LoginFragment(), FRAGMENT_TAGS[0]).commit();
                else
                    getSupportFragmentManager().beginTransaction().add(R.id.mainContainer, new CarListFragment(), FRAGMENT_TAGS[1]).commit();
        });
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    public void replaceFragment(Fragment fragment, int tag, Bundle bundle) {
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(FRAGMENT_TAGS[tag])
                .replace(R.id.mainContainer, fragment, FRAGMENT_TAGS[tag])
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            RefWatcher refWatcher = MyApplication.getRefWatcher(this);
            refWatcher.watch(this);
        }
    }
}