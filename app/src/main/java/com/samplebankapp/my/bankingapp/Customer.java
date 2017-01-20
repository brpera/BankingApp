package com.samplebankapp.my.bankingapp;

/**
 * Created by lenovo on 11/22/2016.
 */
public class Customer {
    int CustID;
    String CustFname;
    String CustMname;
    String CustLname;
    String CustAddress;
    String CustConNo;
    String CustPassword;
    String CustAccNum;

    public Customer(){

    }

    public Customer(int id, String num, String fName, String mName, String lName, String address, String conNo, String pWord){
        this.CustID = id;
        this.CustAccNum = num;
        this.CustFname = fName;
        this.CustMname = mName;
        this.CustLname = lName;
        this.CustAddress = address;
        this.CustConNo = conNo;
        this.CustPassword = pWord;
    }

    public Customer(String num, String fName, String mName, String lName, String address, String conNo, String pWord){
        this.CustAccNum = num;
        this.CustFname = fName;
        this.CustMname = mName;
        this.CustLname = lName;
        this.CustAddress = address;
        this.CustConNo = conNo;
        this.CustPassword = pWord;
    }

    public int getID(){
        return this.CustID;
    }

    public void setID(int id){
        this.CustID = id;
    }

    public String getAccNum(){
        return this.CustAccNum;
    }

    public void setAccNum(String accnum){
        this.CustAccNum = accnum;
    }

    public String getFname(){
        return this.CustFname;
    }

    public void setFname(String fn){
        this.CustFname = fn;
    }

    public String getMname(){
        return this.CustMname;
    }

    public void setMname(String mn){
        this.CustMname = mn;
    }

    public String getLname(){
        return this.CustLname;
    }

    public void setLname(String ln){
        this.CustLname = ln;
    }

    public String getAddress(){
        return this.CustAddress;
    }

    public void setAddress(String address){
        this.CustAddress = address;
    }

    public String getContact(){
        return this.CustConNo;
    }

    public void setContact(String conno){
        this.CustConNo = conno;
    }

    public String getPassword(){
        return this.CustPassword;
    }

    public void setPassword(String pw){
        this.CustPassword = pw;
    }
}
