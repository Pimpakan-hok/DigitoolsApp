package com.example.digitoolsapp.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Marker {
    @SerializedName("UID")
    @Expose
    public String uniqueId;
    @SerializedName("lat")
    @Expose
    public String latitude;
    @SerializedName("lon")
    @Expose
    public String longitude;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("phone")
    @Expose
    public String phonenum;
    @SerializedName("list")
    @Expose
    public List<ListItem> list;
}
