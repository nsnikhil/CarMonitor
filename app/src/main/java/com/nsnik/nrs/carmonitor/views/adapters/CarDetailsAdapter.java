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
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.nsnik.nrs.carmonitor.R;
import com.nsnik.nrs.carmonitor.model.DetailObject;
import com.nsnik.nrs.carmonitor.util.Utility;
import com.nsnik.nrs.carmonitor.util.events.CallPersonEvent;
import com.nsnik.nrs.carmonitor.util.events.OpenMapsEvent;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Contract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class CarDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER = 0, ITEMS = 1;
    private final Context mContext;
    private final CompositeDisposable mCompositeDisposable;
    private final Resources mResources;
    private List<DetailObject> mDetailObjectList;

    public CarDetailsAdapter(final Context context, final List<DetailObject> detailObjectList) {
        mContext = context;
        mCompositeDisposable = new CompositeDisposable();
        mDetailObjectList = detailObjectList;
        mResources = mContext.getResources();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.car_no_header, parent, false));
            case ITEMS:
                return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.single_detail_item, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER:
                bindHeader(holder, position);
                break;
            case ITEMS:
                bindItems(holder, position);
                break;
        }
    }

    private void bindHeader(@NonNull RecyclerView.ViewHolder holder, int position) {
        final HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        viewHolder.mCarNo.setText(mDetailObjectList.get(0).getValue());
    }

    private void bindItems(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder viewHolder = (ItemViewHolder) holder;
        final DetailObject detailObject = mDetailObjectList.get(position);
        makeString(detailObject, viewHolder.mCarItem);
    }

    private void makeString(final DetailObject detailObject, final TextView textView) {
        switch (detailObject.getKey()) {
            case Utility.DETAILS_PHONE_NO:
                textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.phone), null, null, null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setCompoundDrawableTintList(stateList(R.color.materialBlue));
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.materialBlue));
                }
                textView.setText(detailObject.getValue());
                break;
            case Utility.DETAILS_COORDINATES:
                textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.location), null, null, null);
                textView.setText(detailObject.getValue());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setCompoundDrawableTintList(stateList(R.color.materialGreen));
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.materialGreen));
                }
                break;
            case Utility.DETAILS_CARBONMONOXIDE_VALUE:
                textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ele), null, null, null);
                textView.setText(makeSmokeString(mResources.getString(R.string.gasCarbonMonoxide), Double.parseDouble(detailObject.getValue())));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setCompoundDrawableTintList(stateList(R.color.materialBrown));
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.materialBrown));
                }
                break;
            case Utility.DETAILS_METHANE_VALUE:
                textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ele), null, null, null);
                textView.setText(makeSmokeString(mResources.getString(R.string.gasMethane), Double.parseDouble(detailObject.getValue())));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setCompoundDrawableTintList(stateList(R.color.materialBrown));
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.materialBrown));
                }
                break;
            case Utility.DETAILS_NITROGEN_VALUE:
                textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.ele), null, null, null);
                textView.setText(makeSmokeString(mResources.getString(R.string.gasNitrogen), Double.parseDouble(detailObject.getValue())));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setCompoundDrawableTintList(stateList(R.color.materialBrown));
                    textView.setTextColor(ContextCompat.getColor(mContext, R.color.materialBrown));
                }
                break;
            case Utility.DETAILS_ACCIDENT_VALUE:
                if (detailObject.getValue().isEmpty()) {
                    textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.tick), null, null, null);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setCompoundDrawableTintList(stateList(R.color.materialGreen));
                        textView.setTextColor(ContextCompat.getColor(mContext, R.color.materialGreen));
                    }
                    textView.setText(mResources.getString(R.string.detailNoAccident));
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setCompoundDrawableTintList(stateList(R.color.materialRed));
                        textView.setTextColor(ContextCompat.getColor(mContext, R.color.materialRed));
                    }
                    textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext, R.drawable.alert), null, null, null);
                    textView.setText(detailObject.getValue());
                }
                break;
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

    @NonNull
    @Contract(pure = true)
    private String makeSmokeString(String gasName, double gasValue) {
        return mContext.getResources().getString(R.string.detailString, gasName, gasValue);
    }

    private void modifyList(final List<DetailObject> detailObjectList) {
        mDetailObjectList = detailObjectList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDetailObjectList != null ? mDetailObjectList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEADER;
        return ITEMS;
    }

    private void clickListener(final DetailObject detailObject) {
        switch (detailObject.getKey()) {
            case Utility.DETAILS_PHONE_NO:
                EventBus.getDefault().post(new CallPersonEvent(detailObject.getValue()));
                break;
            case Utility.DETAILS_COORDINATES:
                EventBus.getDefault().post(new OpenMapsEvent(detailObject.getValue()));
                break;
            case Utility.DETAILS_CARBONMONOXIDE_VALUE:
                Toast.makeText(mContext, detailObject.getValue(), Toast.LENGTH_SHORT).show();
                break;
            case Utility.DETAILS_METHANE_VALUE:
                Toast.makeText(mContext, detailObject.getValue(), Toast.LENGTH_SHORT).show();
                break;
            case Utility.DETAILS_NITROGEN_VALUE:
                Toast.makeText(mContext, detailObject.getValue(), Toast.LENGTH_SHORT).show();
                break;
            case Utility.DETAILS_ACCIDENT_VALUE:
                Toast.makeText(mContext, detailObject.getValue(), Toast.LENGTH_SHORT).show();
                break;
        }
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

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.detailItem)
        TextView mCarItem;

        ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mCompositeDisposable.add(RxView.clicks(mCarItem).subscribe(v -> clickListener(mDetailObjectList.get(getAdapterPosition())), Timber::d));
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.carHeader)
        TextView mCarNo;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
