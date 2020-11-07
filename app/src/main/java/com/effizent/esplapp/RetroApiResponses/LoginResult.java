package com.effizent.esplapp.RetroApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoginResult {
    @SerializedName("Code")
    @Expose
    private String Code;

    @SerializedName("Message")
    @Expose
    private String Message;

    @SerializedName("Details")
    @Expose
    private ArrayList<Details> detailsArrayList;

    public String getCode() {
        return Code;
    }


    public String getMessage() {
        return Message;
    }

    public ArrayList<Details> getDetailsArrayList() {
        return detailsArrayList;
    }

    public LoginResult(String code, String message, ArrayList<Details> detailsArrayList) {
        Code = code;
        Message = message;
        this.detailsArrayList = detailsArrayList;
    }
}
