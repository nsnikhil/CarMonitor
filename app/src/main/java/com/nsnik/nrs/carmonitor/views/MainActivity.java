package com.nsnik.nrs.carmonitor.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.views.fragments.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainToolbar)Toolbar mMainToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize(){
        setSupportActionBar(mMainToolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.mainContainer,new LoginFragment()).commit();
    }
}