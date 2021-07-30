package com.example.studeaze;

class marks {
    private String usn;
    private String c1;
    private String c2;
    private String c3;
    private String avg;
    private String attendence;
    private String SubCode;
    public  marks(){

    }

    public marks(String SubCode, String usn, String c1, String c2, String c3,String avg,String attendence) {
        this.SubCode= SubCode;
        this.usn = usn;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.avg = avg;
        this.attendence = attendence;
    }
    public String getSubCode(){
        return SubCode;
    }

    public void setSubCode(String SubCode){
        this.SubCode =SubCode;
    }

    public String getUsn() {
        return usn;
    }

    public void setusn(String usn) {
        this.usn = usn;
    }

    public String getc1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public String getC2() {
        return c2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public String getC3() {
        return c3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getAttendence() {
        return attendence;
    }

    public void setAttendence(String attendence) {
        this.attendence = attendence;
    }
}

