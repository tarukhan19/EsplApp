package com.effizent.esplapp.RetroApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationListApi {
    @SerializedName("Title")
    @Expose
    private String Title;

    @SerializedName("NotificationMessage")
    @Expose
    private String NotificationMessage;

    @SerializedName("CreatedDate")
    @Expose
    private String CreatedDate;

    public NotificationListApi() {
    }

    public NotificationListApi(String title, String notificationMessage, String createdDate) {
        Title = title;
        NotificationMessage = notificationMessage;
        CreatedDate = createdDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNotificationMessage() {
        return NotificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        NotificationMessage = notificationMessage;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }
}
