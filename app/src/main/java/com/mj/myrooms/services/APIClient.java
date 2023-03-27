package com.mj.myrooms.services;

import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ihsanbal.logging.Level;
import com.ihsanbal.logging.LoggingInterceptor;
import com.mj.myrooms.constant.ApiConstant;
import com.mj.myrooms.constant.Constant;
import com.mj.myrooms.utils.PreferenceUtils;
import com.mj.myrooms.utils.Utility;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static final String RQ_REGISTER = "register";
    public static final String RQ_LOGIN = "login";
    public static final String RQ_CREATE_USER = "create_user";
    public static final String RQ_FORGOT_PASSWORD = "forgotPassword";
    public static final String RQ_VERIFY_OTP = "verifyOtp";
    public static final String RQ_VERIFY_NEW_OTP = "verifyNewOtp";
    public static final String RQ_CHANGE_PASSWORD = "changePassword";
    public static final String RQ_RESET_PASSWORD = "resetPassword";
    public static final String RQ_UPDATE_PROFILE = "updateProfile";
    public static final String RQ_GET_ROLE = "get_role";
    public static final String RQ_GET_EDIT_TOLL = "editTollParking";
    public static final String RQ_GET_DELETE_TOLL = "deleteToll";
    public static final String RQ_GET_CANCEL_DUTY_NOTIFICATION = "getCancelNotification";
    public static final String RQ_GET_PENDING_DUTY_NOTIFICATION = "pendingBookingsCount";
    public static final String RQ_GET_COUNT_ALL_NOTIFICATION = "countAllNotification";
    public static final String RQ_REMOVE_NOTIFICATION = "removeNotification";
    public static final String RQ_REMOVE_ALL_NOTIFICATION = "removeAllNotification";
    public static final String RQ_ADD_COMPANY = "addCompany";
    public static final String RQ_INVITE_SUPPLIER_NOTIFICATION = "inviteSupplierNotification";
    public static final String RQ_REACTIVATE = "reactivate";
    public static final String RQ_DEACTIVATE = "deactivate";

    // driver
    public static final String RQ_BOOKING_PAST = "pastBookings";
    public static final String RQ_BOOKING_TODAY = "todayBookings";
    public static final String RQ_BOOKING_FUTURE = "futureBookings";

    public static final String RQ_BOOKING_ONGOING = "ongoingBookings";
    public static final String RQ_BOOKING_UPCOMING = "upcomingBookings";
    public static final String RQ_BOOKING_COMPLETED = "completedBookings";
    public static final String RQ_GET_PENDING_BOOKING = "pendingBookings";

    public static final String RQ_CREATE_BOOKING = "createBooking";
    public static final String RQ_UPDATE_BOOKING = "updateBooking";
    public static final String RQ_CANCEL_BOOKING = "cancelBooking";

    public static final String RQ_ACCEPT_BOOKING = "acceptBooking";
    public static final String RQ_REJECT_BOOKING = "rejectBooking";

    public static final String RQ_START_BOOKING = "startBooking";
    public static final String RQ_REACHED_PICKUP = "reachedPickup";
    public static final String RQ_REACHED_DROP = "reachedDrop";
    public static final String RQ_END_DUTY = "endDuty";
    public static final String RQ_START_HALT = "startHalt";
    public static final String RQ_END_HALT = "endHalt";
    public static final String RQ_ADD_TOLLPARKING = "addTollParking";
    public static final String RQ_ADD_GPX = "addGpx";
    public static final String RQ_BLOCK_CAR = "blockCar";
    public static final String RQ_ASSIGN_CAR = "assignCar";
    public static final String RQ_SUBLET_BOOKING = "subletBooking";
    public static final String RQ_GET_PACKAGE = "getPackages";
    public static final String RQ_PACKAGE_DETAIL = "getPackageDetails";
    public static final String RQ_DEFAULT_PACKAGE_DETAIL = "getDefaultPackageDetails";
    public static final String RQ_PRE_INVOICE = "preInvoiceList";
    public static final String RQ_FINAL_INVOICE = "finalInvoiceList";
    public static final String RQ_UPDATE_LOCATION = "updateLocation";
    public static final String RQ_GET_LOCATION = "getLocation";
    public static final String RQ_EDIT_PRE_INVOICE = "editPreInvoice";
    public static final String RQ_FINALIZE_INVOICE = "finalizeInvoice";
    public static final String RQ_INVITE_PACKAGES = "getInvitePackages";
    public static final String RQ_SUBSCRIPTION_LIST = "subscriptionsList";
    public static final String RQ_ADD_CRATES = "addCrates";
    public static final String RQ_GET_ALL_PACKAGES = "getAllPackages";
    public static final String RQ_GET_SAME_PACKAGES = "getSamePackages";
    public static final String RQ_ADD_UPDATE_RATES = "addupdateiRates";

    // list
    public static final String RQ_GET_TOLL_PARKING = "getTollParking";
    public static final String RQ_GET_CUSTOMER = "getCustomerList";
    public static final String RQ_GET_MANUAL_CUSTOMER = "getManualCustomerList";
    public static final String RQ_GET_COMPANY = "companyList";
    public static final String RQ_GET_COUNTRY = "countryList";
    public static final String RQ_GET_RENTAl_LOCATION = "getRentalLocation";
    public static final String RQ_GET_SUPPLIER = "getSuppliers";
    public static final String RQ_GET_SUPPLIER_BRANCH = "getSupplierBranches";
    public static final String RQ_GET_SUPPLIER_PACKAGE = "getSupplierPackages";
    public static final String RQ_GET_PACKAGE_CAR = "getPackageCars";
    public static final String RQ_GET_ALL_CAR = "getAllCars";
    public static final String RQ_GET_ALL_DRIVER = "getAllDrivers";
    public static final String RQ_GET_ALL_SUPPLIER = "getAllSuppliers";
    public static final String RQ_GET_DEFAULT_CITY = "getDefaultCities";
    public static final String RQ_GET_DEFAULT_CITY_PACKAGE = "getDefaultCityPackages";
    public static final String RQ_GET_CITY_PACKAGE = "getCityPackages";
    public static final String RQ_GET_CUSTOMER_CITY = "getCustomerCities";
    public static final String RQ_GET_STATE = "getState";
    public static final String RQ_GET_CITY = "getCity";
    public static final String RQ_GET_GPX = "getGPX";
    public static final String RQ_GET_SUPPLIER_COMPANY = "getCompany";
    public static final String RQ_GET_BRANCH = "getBranch";
    public static final String RQ_GET_SERVED_BRANCH = "getServedByBranch";
    public static final String RQ_GET_MEMBERSHIP = "getMembership";
    public static final String RQ_GET_RAZORPAY_KEY = "razorPaykey";
    public static final String RQ_GET_ADD_MEMBERSHIP = "addMembership";
    public static final String RQ_GET_REGENERATE_OTP = "regenerateOTP";
    public static final String RQ_GET_INVITE_SUPPLIER = "inviteSupplier";
    public static final String RQ_GET_UPDATE_SUB_INVOICE = "updateSubInvoice";
    public static final String RQ_GET_CAR_SEGMENT = "getCarSegments";
    public static final String RQ_GET_CAR_TYPE = "getCarType";
    public static final String RQ_GET_UPDATE_PACKAGE_CAR_SEGMENT = "updatepackageCarsegments";
    public static final String RQ_GET_NEXT_DUTY = "nextDuty";
    public static final String RQ_SHARE_INVOICE = "shareInvoice";

    public static final String RQ_BOOKING_DETAILS = "bookingDetails";
    public static final String RQ_LOG_DETAILS = "logdetails";
    public static final String RQ_ADD_DUTY_SLIP = "addDutySlip";
    public static final String BASE_URL = "http://192.168.43.8:3001/api/v1/user/";



    public static APIInterface appInterface_server_user() {
        return getClient(BASE_URL).create(APIInterface.class);
    }

    public static APIInterface appInterface_server_usertype() {
        if (Utility.isLoggedIn()) {
            if (PreferenceUtils.getInstance().getUser().getRoleId() == Constant.user_type_customer) {
                return getClient(BASE_URL).create(APIInterface.class);
            } else if (PreferenceUtils.getInstance().getUser().getRoleId() == Constant.user_type_owner) {
                return getClient(BASE_URL).create(APIInterface.class);
            } else {
                return getClient(BASE_URL).create(APIInterface.class);
            }
        } else {
            return getClient(BASE_URL).create(APIInterface.class);
        }
    }

    public static OkHttpClient.Builder getClientBuilder() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder request = original.newBuilder();

//                request.addHeader(ApiConstant.HEADER_CONTENT_TYPE, ApiConstant.CONTENT_FORM_DATA);
//                if (!TextUtils.isEmpty(PreferenceUtils.getInstance().getAuthToken())) {
//                    request.addHeader(ApiConstant.HEADER_AUTH_TOKEN, PreferenceUtils.getInstance().getAuthToken());
//                }

                return chain.proceed(request.build());
            }
        });

        httpClient.addInterceptor(new LoggingInterceptor.Builder()
                .loggable(true)
                .setLevel(Level.BASIC)
                .log(Platform.INFO)
                .request("Request")
                .response("Response")
                .build());

        return httpClient;
    }

    public static Retrofit getClient(String baseUrl) {
        OkHttpClient.Builder httpClient = getClientBuilder();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .build();

        return retrofit;
    }

    public static RequestBody toRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    // * * * * * * * * * * URLs * * * * * * * * * * //


}
