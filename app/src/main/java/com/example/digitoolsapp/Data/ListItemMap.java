package com.example.digitoolsapp.Data;

public class ListItemMap {

    private String toolid;
    private String name;
    private String quantity;

    public ListItemMap(String toolid , String name , String quantity){
        this.toolid = toolid;
        this.name = name;
        this.quantity = quantity;
    }
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
