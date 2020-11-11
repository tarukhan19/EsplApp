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
    private ArrayList<TimelinePostDTO> timelinePostDTOArrayList;

    public String getCode() {
        return Code;
    }

    public String getStatus() {
        return Status;
    }

    public ArrayList<TimelinePostDTO> getTimelinePostDTOArrayList() {
        return timelinePostDTOArrayList;
    }
}
