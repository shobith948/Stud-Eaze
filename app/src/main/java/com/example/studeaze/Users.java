package com.example.studeaze;

public class Users
{
    private String name, password, email ,usn  ,branch ,phone;
    private long semester;

    public  Users(){

    }

    public Users(String name, String password, String email, String usn , long semester , String branch, String phone)
    {
        this.name = name;
        this.password = password;
        this.email = email;
        this.usn = usn;
        this.semester = semester;
        this.branch = branch;
        this.phone = phone;
    }

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

    public void setEmail(String address)
    {
        this.email = email;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
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
