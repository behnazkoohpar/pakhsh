package com.koohpar.dstrbt.api;

import android.app.DownloadManager;

import com.koohpar.dstrbt.data.model.api.BannerResponse;
import com.koohpar.dstrbt.data.model.api.BrandResponse;
import com.koohpar.dstrbt.data.model.api.CategoryResponse;
import com.koohpar.dstrbt.data.model.api.CategoryStuffResponse;
import com.koohpar.dstrbt.data.model.api.DetailReportListResponse;
import com.koohpar.dstrbt.data.model.api.GetLastVersionResponse;
import com.koohpar.dstrbt.data.model.api.MarketerListResponse;
import com.koohpar.dstrbt.data.model.api.ProfileUserResponse;
import com.koohpar.dstrbt.data.model.api.ReportListResponse;
import com.koohpar.dstrbt.data.model.api.SpecialOfferResponse;
import com.koohpar.dstrbt.data.model.api.base.Data;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by shb on 10/31/2017.
 */

public interface ICallApi {

    @FormUrlEncoded
    @POST("/WS/sign_in")
    Call<Data> signIn(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/check_activation_code")
    Call<Data> activation(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/sign_in")
    Call<Data> sendForgetPassword(@FieldMap HashMap<String, String> mParams);

    @FormUrlEncoded
    @POST("/WS/register_user")
    Call<Data> register(@FieldMap HashMap<String, String> mParams);

    @FormUrlEncoded
    @POST("/WS/login_user")
    Call<Data> login(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_profile_user")
    Call<Data<ProfileUserResponse>> profileUser(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/set_profile_user_person")
    Call<Data> editProfileUser(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/set_profile_user_image")
    Call<Data> editPicture(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/set_profile_user_address")
    Call<Data> editLocation(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/set_profile_user_net")
    Call<Data> editInternet(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_all_banner_list")
    Call<Data<BannerResponse>> getAllBanerList(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_stuff_category")
    Call<Data<CategoryResponse>> getAllCategory(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_all_special_offer")
    Call<Data<SpecialOfferResponse>> getAllSpecialOffer(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_stuff_list_from_category")
    Call<Data<CategoryStuffResponse>> getAllCategoryStuff(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_brand_list")
    Call<Data<BrandResponse>> getAllBrand(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_stuff_list_from_brand")
    Call<Data<CategoryStuffResponse>> getAllCategoryStuffByBrand(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/search_with_keyword")
    Call<Data<CategoryStuffResponse>> getSearchByKeyword(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/set_request")
    Call<Data> setRequest(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_last_version")
    Call<Data<GetLastVersionResponse>> getLastVersion(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/forget_password")
    Call<Data> forgetPassword(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_all_request")
    Call<Data<ReportListResponse>> getReportList(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_detail_request")
    Call<Data<DetailReportListResponse>> getDetailReport(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/delete_request")
    Call<Data> deleteRequest(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/delete_item_request")
    Call<Data> deleteItemRequest(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/edit_item_request")
    Call<Data> editItemRequest(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/check_code_marketer")
    Call<Data<MarketerListResponse>> checkCode(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/set_notify_inventory_increase")
    Call<Data> setnotif(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST("/WS/get_notify_inventory_list")
    Call<Data<CategoryStuffResponse>> getNotifyInventory(HashMap<String, String> map);
}
