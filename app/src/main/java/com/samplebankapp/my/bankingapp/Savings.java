package com.samplebankapp.my.bankingapp;

/**
 * Created by lenovo on 11/22/2016.
 */
public class Savings {
    int savingsId;
    String accountNum;
    double savingsAmount;

    public Savings(){

    }

    public Savings(String num, double amount){
        this.accountNum = num;
        this.savingsAmount = amount;
    }

    public Savings(int id,  String num, double amount){
        this.savingsId = id;
        this.accountNum = num;
        this.savingsAmount = amount;
    }

    //Setters
    public void setId(int id){
        this.savingsId = id;
    }


    public void setAccountNum(String num){
        this.accountNum = num;
    }

    public void setSaveAmount(double amount){
        this.savingsAmount = amount;
    }

    //Getters
    public int getSavingsIdId(){
        return this.savingsId;
    }


    public String getAccountNum(){
        return this.accountNum;
    }

    public double getSavingsAmount(){
        return this.savingsAmount;
    }


}
