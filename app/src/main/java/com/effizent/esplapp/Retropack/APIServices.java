package com.effizent.esplapp.Retropack;

import com.effizent.esplapp.RetroApiResponses.LoadDashBoardResult;
import com.effizent.esplapp.RetroApiResponses.CheckUserStatus;
import com.effizent.esplapp.RetroApiResponses.CreatePostResult;
import com.effizent.esplapp.RetroApiResponses.LoadDepartmentResult;
import com.effizent.esplapp.RetroApiResponses.LoginResult;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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


    @Multipart
    @POST("Save_Emp_Timeline_Post.ashx?")
    Call<CreatePostResult> createPost(
            @Part("EmpId") RequestBody empId,
            @Part("Title") RequestBody title,
            @Part("Description") RequestBody description,
            @Part("Department") RequestBody department,
            @Part List<MultipartBody.Part> files);




    @GET("Department.ashx?")
    Call<LoadDepartmentResult> loadDepartment(
           );

    @FormUrlEncoded
    @POST("Show_Timeline_Post.ashx?")
    Call<LoadDashBoardResult> loadDashboardData(
            @Field("EmpId") String empId);


}
