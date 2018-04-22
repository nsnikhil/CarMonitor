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
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.data.CarEntity;
import com.nsnik.nrs.carmonitor.util.events.GotoDetailsEvent;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

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

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.car_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CarEntity carEntity = mCarList.get(position);
        holder.mCarNo.setText(carEntity.getCarNo());
        setImage(carEntity, holder.mCarStatus);
    }

    private void setImage(@NotNull final CarEntity carEntity, final ImageView imageView) {
        if (carEntity.getAccident().isEmpty()) {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.tick));
            imageView.setImageTintList(stateList(R.color.materialGreen));
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.alert));
            imageView.setImageTintList(stateList(R.color.materialRed));
        }
    }

    @NonNull
    private ColorStateList stateList(int colorId) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_enabled},
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_pressed}
        };
        int color = ContextCompat.getColor(mContext, colorId);
        int[] colors = new int[]{color, color, color, color};
        return new ColorStateList(states, colors);
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
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        cleanUp();
        super.onDetachedFromRecyclerView(recyclerView);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemCarNo)
        TextView mCarNo;
        @BindView(R.id.itemCard)
        CardView mItemCard;
        @BindView(R.id.itemCarStatus)
        ImageView mCarStatus;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCompositeDisposable.add(RxView.clicks(mItemCard).subscribe(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    EventBus.getDefault().post(new GotoDetailsEvent(mCarList.get(getAdapterPosition())));
                }
            }, Timber::d));
        }
    }
}