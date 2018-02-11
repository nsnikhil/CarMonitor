package com.nsnik.nrs.carmonitor.views.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.nsnik.nrs.carmonitor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(RxView.clicks(mFormSubmit).subscribe(v -> Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show(), throwable -> Timber.d(throwable.getMessage())));
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
