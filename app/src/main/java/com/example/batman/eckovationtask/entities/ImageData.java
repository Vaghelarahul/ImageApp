package com.example.batman.eckovationtask.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageData {

        //     "id":"43409263291",
//             "secret":"8b0676cc36",
//             "server":"1829",
//             "farm":2,

        @SerializedName("id")
        @Expose
        private String imageId;

        @SerializedName("secret")
        @Expose
        private String secret;

        @SerializedName("server")
        @Expose
        private String server;

        @SerializedName("farm")
        @Expose
        private Integer farm;

        public String getImageId() {
            return imageId;
        }

        public String getSecret() {
            return secret;
        }

        public String getServer() {
            return server;
        }

        public Integer getFarm() {
            return farm;
        }

        public String getPhotoUrl() {
            // http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
            String url = "http://farm" + farm + ".staticflickr.com/" + server
                    + "/" + imageId + "_" + secret + ".jpg";
            return url;
        }
}
