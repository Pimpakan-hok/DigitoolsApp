package com.example.digitoolsapp.Data;

public class StatusData {

        String que_ID;
        String que_desc;
        String s_date;
        String e_date;
        String status;
        String numque;
        String que_owner_UID;

    public String getNumque() {
        return numque;
    }

    public void setNumque(String numque) {
        this.numque = numque;
    }

    public String getDes() {

        return que_desc;
    }

        public void setDes(String que_desc) {

        this.que_desc = que_desc;
    }

        public String getId() {

        return  que_ID;
    }

        public void setId(String que_ID) {

        this. que_ID = que_ID;
    }


        public String getSd() {

        return s_date;
    }

        public void setSd(String s_date1) {

        this.s_date = s_date1;
    }

        public String getEd() {

        return e_date;
    }

        public void setEd(String e_date) {

        this.e_date = e_date;
    }

        public String getStatus() {
        return status;
    }

        public void setStatus(String status) {
        this.status = status;
    }

        public String getQue_owner_UID(){
        return que_owner_UID;
    }
        public void setQue_owner_UID(String que_owner_UID){
        this.que_owner_UID = que_owner_UID;
    }
    }