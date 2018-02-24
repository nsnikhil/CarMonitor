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

package com.nsnik.nrs.carmonitor.views.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.views.MainActivity;
import com.nsnik.nrs.carmonitor.views.fragments.CarDetailsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;


public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.MyViewHolder> {

    private final Context mContext;
    private final CompositeDisposable mCompositeDisposable;
    private List<CarEntity> mCarList;

    public CarListAdapter(final Context context, final List<CarEntity> carList) {
        mContext = context;
        mCarList = carList;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.car_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CarEntity carEntity = mCarList.get(position);
        holder.mCarNo.setText(carEntity.getCarNo());
    }

    @Override
    public int getItemCount() {
        return mCarList != null ? mCarList.size() : 0;
    }

    public void modifyList(List<CarEntity> newList) {
        mCarList = newList;
        notifyDataSetChanged();
    }

    private void cleanUp() {
        mCompositeDisposable.clear();
        mCompositeDisposable.dispose();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        cleanUp();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemCarNo)
        TextView mCarNo;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCompositeDisposable.add(RxView.clicks(itemView).subscribe(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    final com.twitter.serial.stream.legacy.LegacySerial serial = new com.twitter.serial.stream.legacy.LegacySerial();
                    final byte[] serializedData = serial.toByteArray(mCarList.get(getAdapterPosition()), CarEntity.SERIALIZER);
                    final Bundle bundle = new Bundle();
                    bundle.putByteArray(mContext.getResources().getString(R.string.bundleCarObject), serializedData);
                    ((MainActivity) mContext).replaceFragment(new CarDetailsFragment(), 2, bundle);
                }
            }, throwable -> Timber.d(throwable.getMessage())));
        }
    }
}