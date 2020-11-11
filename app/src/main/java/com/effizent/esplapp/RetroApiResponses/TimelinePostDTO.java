package com.effizent.esplapp.RetroApiResponses;

import com.effizent.esplapp.RetroApiResponses.TimelineImageDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TimelinePostDTO {
    @SerializedName("Title")
    @Expose
    private String Title;

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("Images")
    @Expose
    private ArrayList<TimelineImageDTO> timelineImageDTOArrayList;

    public ArrayList<TimelineImageDTO> getTimelineImageDTOArrayList() {
        return timelineImageDTOArrayList;
    }

    public void setTimelineImageDTOArrayList(ArrayList<TimelineImageDTO> timelineImageDTOArrayList) {
        this.timelineImageDTOArrayList = timelineImageDTOArrayList;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public TimelinePostDTO() {
    }
}
