package com.effizent.esplapp.Retropack;

import com.effizent.esplapp.RetroApiResponses.CheckUserStatus;
import com.effizent.esplapp.RetroApiResponses.LoginResult;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIServices {

    @FormUrlEncoded
    @POST("Check_Effi_Learn_MobileNo.ashx?")
    Call<CheckUserStatus> checkMobileNo(
            @Field("MobileNo") String mobileNo);

    @FormUrlEncoded
    @POST("EMS_Employee_LogIn.ashx?")
    Call<LoginResult> loginUser(
            @Field("Email") String mobileNo,
            @Field("Password") String password,
            @Field("DeviceId") String DeviceId);


//    @Multipart
//    @POST("Effi_Learn_Registration.ashx?")
//    Call<RegistrationResult> registrationUser(
//            @Part("Name") RequestBody name,
//            @Part("Email") RequestBody email,
//            @Part("MobileNo") RequestBody mobileNo,
//            @Part("Address") RequestBody address,
//            @Part("District") RequestBody district,
//            @Part("State") RequestBody state,
//            @Part("Pincode") RequestBody pincode,
//            @Part("Password") RequestBody password,
//            @Part("City") RequestBody city,
//            @Part("Gender") RequestBody gender,
//            @Part("DeviceType") RequestBody deviceType,
//            @Part("DeviceId") RequestBody deviceId,
//            @Part MultipartBody.Part adharcard,
//            @Part MultipartBody.Part pancard);







}
