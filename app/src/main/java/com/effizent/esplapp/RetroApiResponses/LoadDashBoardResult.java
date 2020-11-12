package com.effizent.esplapp.RetroApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoadDashBoardResult {
    @SerializedName("Code")
    @Expose
    String Code;

    @SerializedName("Status")
    @Expose
    String Status;

    @SerializedName("Timeline_Post_List")
    @Expose
    private ArrayList<Timeline_Post_List> timelinePostDTOArrayList;

    public String getCode() {
        return Code;
    }

    public String getStatus() {
        return Status;
    }

    public ArrayList<Timeline_Post_List> getTimelinePostDTOArrayList() {
        return timelinePostDTOArrayList;
    }

    public LoadDashBoardResult() {
    }

    public LoadDashBoardResult(String code, String status, ArrayList<Timeline_Post_List> timelinePostDTOArrayList) {
        Code = code;
        Status = status;
        this.timelinePostDTOArrayList = timelinePostDTOArrayList;
    }
}
