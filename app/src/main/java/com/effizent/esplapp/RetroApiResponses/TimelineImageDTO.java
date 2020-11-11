package com.effizent.esplapp.RetroApiResponses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimelineImageDTO {

    @SerializedName("Image")
    @Expose
    private String Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
