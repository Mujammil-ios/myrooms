package com.mj.myrooms.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mj.myrooms.R;
import com.mj.myrooms.databinding.GridCustomerHomeBinding;
import com.mj.myrooms.object.TabModel;

import java.util.ArrayList;

public class HomeOwCustomerListAdapter extends RecyclerView.Adapter<HomeOwCustomerListAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private Activity mActivity;

    private AdapterView.OnItemClickListener onItemClickListener;

    private OnDashboardClickListener listener;
    private ArrayList<TabModel> list_data;
    int count = 0, notificationCount = 0;

    public HomeOwCustomerListAdapter(Activity mActivity, OnDashboardClickListener listener) {
        this.mActivity = mActivity;
        this.listener = listener;
    }

    public void doRefresh(ArrayList<TabModel> list_data) {
        this.list_data = list_data;
        notifyDataSetChanged();
    }

    public void doCountRefresh(int count) {
        this.count = count;
        notifyDataSetChanged();
    }

    public void doNotificationCountRefresh(int notificationCount) {
        this.notificationCount = notificationCount;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void onItemHolderClick(ViewHolder holder) {
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GridCustomerHomeBinding layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.grid_customer_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(layoutBinding, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TabModel item = list_data.get(position);
        try {
            holder.setData(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public interface OnDashboardClickListener {
//        void onPlantTypeSelectClickListener(TabModel object_plantType, boolean isSelect);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final GridCustomerHomeBinding layoutBinding;
        private HomeOwCustomerListAdapter mAdapter;

        public ViewHolder(GridCustomerHomeBinding layoutBinding, final HomeOwCustomerListAdapter mAdapter) {
            super(layoutBinding.getRoot());
            this.layoutBinding = layoutBinding;
            this.mAdapter = mAdapter;
            layoutBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mAdapter.onItemHolderClick(this);
        }

        public void setData(final TabModel object) throws Exception {
            layoutBinding.tvName.setText(object.getName());
            if (object.getName().equals(itemView.getResources().getString(R.string.booking)) && count != 0) {
                layoutBinding.notificationBadge.setText(String.valueOf(count));
                layoutBinding.notificationBadge.setVisibility(View.VISIBLE);
            } else {
                layoutBinding.notificationBadge.setVisibility(View.GONE);
            }
            if (object.getName().equals(itemView.getResources().getString(R.string.notification)) && notificationCount != 0) {
                layoutBinding.notificationAllBadge.setVisibility(View.VISIBLE);
                layoutBinding.notificationAllBadge.setText(String.valueOf(notificationCount));
            } else {
                layoutBinding.notificationAllBadge.setVisibility(View.GONE);
            }
            if (object.isSelect()) {
                layoutBinding.cvRoot.setCardBackgroundColor(mActivity.getResources().getColor(R.color.theme));
                layoutBinding.ivMenu.setImageResource(object.getImageSelected());
                layoutBinding.tvName.setTextColor(mActivity.getResources().getColor(R.color.white));
            } else {
                layoutBinding.cvRoot.setCardBackgroundColor(mActivity.getResources().getColor(R.color.white));
                layoutBinding.ivMenu.setImageResource(object.getImage());
                layoutBinding.tvName.setTextColor(mActivity.getResources().getColor(R.color.theme));
            }
        }
    }
}
