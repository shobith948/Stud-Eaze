package com.example.studeaze;

public class students //StudentsModel class
{
    private String name;
    private String password;
    private String email;
    private String usn;
    private String branch;
    private String phone;
    private String subcode;
    private long semester;

    public students(){

    }
    //Constructor
    public students(String name, String password,String subcode, String email, String usn , long semester , String branch, String phone)
    {
        this.name = name;
        this.password = password;
        this.email = email;
        this.usn = usn;
        this.subcode = subcode;
        this.semester = semester;
        this.branch = branch;
        this.phone = phone;
    }
    //getter and setter
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getsubcode() {
        return subcode;
    }

    public void setsubcode(String subcode) {
        this.subcode = subcode;
    }

    public long getSemester() {
        return semester;
    }

    public void setSemester(long semester) {
        this.semester = semester;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}