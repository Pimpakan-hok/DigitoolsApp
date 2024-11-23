package com.example.digitoolsapp.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListItem {
    @SerializedName("toolid")
    @Expose
    public String toolid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("quantity")
    @Expose
    public String quantity;

}
