package com.samplebankapp.my.bankingapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 11/23/2016.
 */
public class TransactionLogs {
    int transactionId;
    String transactionType;
    String transactionAccNum;
    double transactionAmount;
    String transDateAndTime;

    public TransactionLogs(){

    }

    public TransactionLogs(String tranType, String accNum, double tranAmount, String dtm){
        this.transactionType = tranType;
        this.transactionAccNum = accNum;
        this.transactionAmount = tranAmount;
        this.transDateAndTime = dtm;
    }

    public TransactionLogs(int id, String tranType,String accNum, double tranAmount, String dtm){
        this.transactionId = id;
        this.transactionType = tranType;
        this.transactionAccNum = accNum;
        this.transactionAmount = tranAmount;
        this.transDateAndTime = dtm;
    }

    //getters
    public int getTransactionId(){
        return this.transactionId;
    }

    public String getTransactionType(){
        return this.transactionType;
    }

    public String getTransactionAccNum(){
        return this.transactionAccNum;
    }

    public double getTransactionAmount(){
        return this.transactionAmount;
    }

    public String getTransDateAndTime(){
        return this.transDateAndTime;
    }

    //setters
    public void setTransactionId(int id){
        this.transactionId = id;
    }

    public void setTransactionType(String tranType){
        this.transactionType = tranType;
    }

    public void setTransactionAccNum(String accNum){
        this.transactionAccNum = accNum;
    }

    public void setTransactionAmount(double amount){
        this.transactionAmount = amount;
    }

    public void setTransDateAndTime(String dtm){
        this.transDateAndTime = dtm;
    }

}
