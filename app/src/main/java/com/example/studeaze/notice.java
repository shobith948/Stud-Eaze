package com.example.studeaze;

class notice {
    private String date;
    private String time;
    private String note_head;
    private String note_desc;
    public  notice(){

    }

    public notice(String date, String time, String note_head, String note_desc) {
        this.date = date;
        this.time = time;
        this.note_head = note_head;
        this.note_desc = note_desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote_head() {
        return note_head;
    }

    public void setNote_head(String note_head) {
        this.note_head = note_head;
    }

    public String getNote_desc() {
        return note_desc;
    }

    public void setNote_desc(String note_desc) {
        this.note_desc = note_desc;
    }
}

