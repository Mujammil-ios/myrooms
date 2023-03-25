package com.mj.myrooms.services;

import com.google.gson.JsonObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface APIInterface {
    @GET
    Call<JsonObject> get(@Url String url);

    @FormUrlEncoded
    @POST
    Call<JsonObject> post(@Url String url,
                          @FieldMap Map<String, String> params);



    @Multipart
    @POST
    Call<JsonObject> updateProfile(@Url String url,
                                   @PartMap Map<String, RequestBody> params,
                                   @Part MultipartBody.Part file);
    @Multipart
    @POST
    Call<JsonObject> getProfile(@Url String url,
                                @PartMap Map<String, RequestBody> params);
}
