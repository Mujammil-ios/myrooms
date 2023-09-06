package com.mj.myrooms.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
public class HomeOwCustomerAdapter{

}
//public class HomeOwCustomerAdapter extends RecyclerView.Adapter<HomeOwCustomerAdapter.ViewHolder> {
//    private final String TAG = getClass().getSimpleName();
//    private Activity mActivity;
//
//    private AdapterView.OnItemClickListener onItemClickListener;
//    private OnHomeOwCustomerClickListener listener;
//    private ArrayList<BookingModel> list_data;
//
//    public HomeOwCustomerAdapter(Activity mActivity, OnHomeOwCustomerClickListener listener) {
//        this.mActivity = mActivity;
//        this.listener = listener;
//    }
//
//    public void doRefresh(ArrayList<BookingModel> list_data) {
//        this.list_data = list_data;
//        notifyDataSetChanged();
//    }
//
//    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public void onItemHolderClick(ViewHolder holder) {
//        if (onItemClickListener != null)
//            onItemClickListener.onItemClick(null, holder.itemView, holder.getAdapterPosition(), holder.getItemId());
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ListBookingPastSupplierBinding layoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_booking_past_supplier, parent, false);
//        ViewHolder viewHolder = new ViewHolder(layoutBinding, this);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        BookingModel item = list_data.get(position);
//        try {
//            holder.setData(item);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list_data.size();
//    }
//
//    public interface OnHomeOwCustomerClickListener {
//        void onOptionClickListener(View view, BookingModel object_booking, boolean is_sub);

//        void onDetailClickListener(BookingModel object_booking);
//        void onDutyDetailClickListener(BookingModel object_booking);
//
//        void onViewInvoiceClickListener(BookingModel object_booking);
//
//        void onEditDutyTypeClickListener(BookingModel object_booking);
//
//        void onAddTollParkingClickListener(BookingModel object_booking);
//    }

//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private final ListBookingPastSupplierBinding layoutBinding;
//        private PastBookingSupplierAdapter mAdapter;
//
//        public ViewHolder(ListBookingPastSupplierBinding layoutBinding, final PastBookingSupplierAdapter mAdapter) {
//            super(layoutBinding.getRoot());
//            this.layoutBinding = layoutBinding;
//            this.mAdapter = mAdapter;
//            layoutBinding.getRoot().setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            mAdapter.onItemHolderClick(this);
//        }
//
//        public void setData(final BookingModel object) throws Exception {
//            Gson gson = new Gson();
//            String json = gson.toJson(object);
//            Log.d("invoice_status", "---"+ json.toString());
//            layoutBinding.ivOption.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (object.getStatus().equals("6") || object.getApiStatus() > 0) {
//                        listener.onOptionClickListener(layoutBinding.ivOption, object, true);
//                    } else {
//                        listener.onOptionClickListener(layoutBinding.ivOption, object, false);
//                    }
//                }
//            });
//            if(object.getInvoiceStatus().equals("Final Invoice Generated") && object.getPrePurchaseStatus().equals("1")){
//                layoutBinding.btnAddTollParking.setVisibility(View.GONE);
//                layoutBinding.btnEditDutyType.setVisibility(View.GONE);
//                layoutBinding.tvInvoiceStatus.setVisibility(View.VISIBLE);
//                layoutBinding.tvInvoiceStatus.setBackgroundColor(mActivity.getResources().getColor(R.color.green));
//                layoutBinding.tvInvoiceStatus.setText(mActivity.getResources().getString(R.string.final_invoice_generated));
//
//            }else{
//                layoutBinding.btnAddTollParking.setVisibility(View.VISIBLE);
//                layoutBinding.btnEditDutyType.setVisibility(View.VISIBLE);
//                layoutBinding.tvInvoiceStatus.setVisibility(View.VISIBLE);
//                layoutBinding.tvInvoiceStatus.setBackgroundColor(mActivity.getResources().getColor(R.color.orange));
//                layoutBinding.tvInvoiceStatus.setText(mActivity.getResources().getString(R.string.pre_invoice_generated));
//
//
//            }

//            layoutBinding.tvRentalCity.setText(object.getRentalCity());
//            layoutBinding.tvBookingno.setText(object.getBookingno());
//            layoutBinding.tvCustomerBranch.setText(object.getCustomerBranch());
//            layoutBinding.tvGuestname.setText(object.getGuestname());
//            layoutBinding.tvCartype.setText(object.getCartype());
//            layoutBinding.tvPackage.setText(object.getPackages());
//            layoutBinding.tvActualPackage.setText(object.getPackages());
//            layoutBinding.tvActualPackage.setText(object.getPackages());
//            layoutBinding.tvInvoiceno.setText(object.getInvoiceNo());
//            if (object.getEdited_final_amount() != null && Integer.parseInt(object.getEdited_final_amount()) > 0) {
//                layoutBinding.tvInvoiceAmount.setText(object.getEdited_final_amount());
//            } else {
//                layoutBinding.tvInvoiceAmount.setText(object.getInvoiceAmountInRs());
//            }
//
//            if (object.getItem().equals("Duty Slip") || object.getItem().equals("Toll/Parking"))
//                layoutBinding.tvDutySlipStatus.setText(mActivity.getResources().getString(R.string.yes));
//            else
//                layoutBinding.tvDutySlipStatus.setText(mActivity.getResources().getString(R.string.no));
//
//            if (Integer.parseInt(object.getFinvoiceid()) > 0) {
//                layoutBinding.tvInvoicePreFinal.setText(mActivity.getResources().getString(R.string.final_invoice));
//            } else if (Integer.parseInt(object.getInvoiceid()) > 0) {
//                layoutBinding.tvInvoicePreFinal.setText(mActivity.getResources().getString(R.string.pre_invoice));
//            } else {
//                layoutBinding.tvInvoicePreFinal.setText(mActivity.getResources().getString(R.string.pending));
//            }
//
//            layoutBinding.tvPickupdate.setText(DateTimeUtils.getInstance().formatDateTime(
//                    object.getPickupdate(),
//                    DateFormats.yyyyMMdd.getLabel(),
//                    DateFormats.ddMMyyyy.getLabel()));
//            layoutBinding.tvInvoiceDate.setText(DateTimeUtils.getInstance().formatDateTime(
//                    object.getInvoiceDate(),
//                    DateFormats.yyyyMMdd.getLabel(),
//                    DateFormats.ddMMyyyy.getLabel()));
//
//            if (!TextUtils.isEmpty(object.getInvoiceStatus()) && object.getInvoiceStatus().equalsIgnoreCase(Constant.status_pre_invoice)) {
//                layoutBinding.tvInvoiceStatus.setVisibility(View.VISIBLE);
//                layoutBinding.tvInvoiceStatus.setBackgroundColor(mActivity.getResources().getColor(R.color.orange));
//                layoutBinding.tvInvoiceStatus.setText(mActivity.getResources().getString(R.string.pre_invoice_generated));
//            } else if (!TextUtils.isEmpty(object.getInvoiceStatus()) && object.getInvoiceStatus().equalsIgnoreCase(Constant.status_final_invoice)) {
//                layoutBinding.tvInvoiceStatus.setVisibility(View.VISIBLE);
//                layoutBinding.tvInvoiceStatus.setBackgroundColor(mActivity.getResources().getColor(R.color.green));
//                layoutBinding.tvInvoiceStatus.setText(mActivity.getResources().getString(R.string.final_invoice_generated));
//            } else {
//                layoutBinding.tvInvoiceStatus.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
//                layoutBinding.tvInvoiceStatus.setVisibility(View.GONE);
//            }
//
//            layoutBinding.btnViewDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onDutyDetailClickListener(object);
//                }
//            });
//
//            layoutBinding.btnAddTollParking.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onAddTollParkingClickListener(object);
//                }
//            });
//
//            layoutBinding.btnEditDutyType.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onEditDutyTypeClickListener(object);
//                }
//            });
//        }
//    }
//}