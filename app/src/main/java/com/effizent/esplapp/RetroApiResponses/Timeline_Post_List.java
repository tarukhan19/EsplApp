package com.effizent.esplapp.RetroApiResponses;

import com.effizent.esplapp.Model.Images;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Timeline_Post_List {
    @SerializedName("Title")
    @Expose
    private String Title;

    @SerializedName("Id")
    @Expose
    private String Id;

    @SerializedName("Description")
    @Expose
    private String Description;

    @SerializedName("ProfilePic")
    @Expose
    private String ProfilePic;

    @SerializedName("Name")
    @Expose
    private String Name;

    @SerializedName("Date")
    @Expose
    private String Date;

    @SerializedName("Images")
    @Expose
    private ArrayList<Images> timelineImageDTOArrayList;
    public Timeline_Post_List() {
    }
    public Timeline_Post_List(String title, String id, String description, String profilePic, String name, String date, ArrayList<Images> timelineImageDTOArrayList) {
        Title = title;
        Id = id;
        Description = description;
        ProfilePic = profilePic;
        Name = name;
        Date = date;
        this.timelineImageDTOArrayList = timelineImageDTOArrayList;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
    public ArrayList<Images> getTimelineImageDTOArrayList() {
        return timelineImageDTOArrayList;
    }

    public void setTimelineImageDTOArrayList(ArrayList<Images> timelineImageDTOArrayList) {
        this.timelineImageDTOArrayList = timelineImageDTOArrayList;
    }



}
