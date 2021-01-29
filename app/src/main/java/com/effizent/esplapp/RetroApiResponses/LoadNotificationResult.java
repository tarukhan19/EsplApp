package com.effizent.esplapp.RetroApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoadNotificationResult {
    @SerializedName("Code")
    @Expose
    String Code;

    @SerializedName("Status")
    @Expose
    String Status;

    @SerializedName("List")
    @Expose
    private ArrayList<NotificationListApi> notificationListApiArrayList;

    public String getCode() {
        return Code;
    }

    public String getStatus() {
        return Status;
    }

    public ArrayList<NotificationListApi> getNotificationList() {
        return notificationListApiArrayList;
    }

    public LoadNotificationResult() {
    }

    public LoadNotificationResult(String code, String status, ArrayList<NotificationListApi> notificationListApiArrayList) {
        Code = code;
        Status = status;
        this.notificationListApiArrayList = notificationListApiArrayList;
    }
}
