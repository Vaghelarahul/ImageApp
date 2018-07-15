package com.example.batman.eckovationtask.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagesDataModel {

    @SerializedName("photos")
    @Expose
    private Photos photos;

    public Photos getPhotos() {
        return photos;
    }
}
