package com.example.batman.eckovationtask.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {

    @SerializedName("photo")
    @Expose
    private List<ImageData> photo;

    public List<ImageData> getPhoto() {
        return photo;
    }
}
