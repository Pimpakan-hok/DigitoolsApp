package com.example.digitoolsapp.Data;


import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Listtools {

    private String toolid;

    private String name;

    public String getToolid() {
        return toolid;
    }

    public void setToolid(String toolid) {
        this.toolid = toolid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}