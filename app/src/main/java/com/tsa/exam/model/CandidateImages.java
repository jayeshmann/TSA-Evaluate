package com.tsa.exam.model;

/**
 * Created by Akhil Tripathi on 17-07-2017.
 */

public class CandidateImages {
    private String canID;
    private String canImage;
    private String canAadhar;
    private String getCanAadharImage;
    private String aadahrTime;
    private String aadahrDate;
    private String canDate;
    private String canTime;

    public CandidateImages(String canID, String canImage,
                           String canAadhar, String getCanAadharImage, String aadahrTime, String aadahrDate,
                           String canDate, String canTime) {
        this.canID = canID;
        this.canImage = canImage;
        this.canAadhar = canAadhar;
        this.getCanAadharImage = getCanAadharImage;
        this.aadahrTime = aadahrTime;
        this.aadahrDate = aadahrDate;
        this.canDate = canDate;
        this.canTime = canTime;
    }

    public String getAadahrTime() {
        return aadahrTime;
    }

    public void setAadahrTime(String aadahrTime) {
        this.aadahrTime = aadahrTime;
    }

    public String getAadahrDate() {
        return aadahrDate;
    }

    public void setAadahrDate(String aadahrDate) {
        this.aadahrDate = aadahrDate;
    }

    public String getCanDate() {
        return canDate;
    }

    public void setCanDate(String canDate) {
        this.canDate = canDate;
    }

    public String getCanTime() {
        return canTime;
    }

    public void setCanTime(String canTime) {
        this.canTime = canTime;
    }

    public CandidateImages() {
    }

    public String getCanID() {
        return canID;
    }

    public void setCanID(String canID) {
        this.canID = canID;
    }

    public String getCanImage() {
        return canImage;
    }

    public void setCanImage(String canImage) {
        this.canImage = canImage;
    }

    public String getCanAadhar() {
        return canAadhar;
    }

    public void setCanAadhar(String canAadhar) {
        this.canAadhar = canAadhar;
    }

    public String getGetCanAadharImage() {
        return getCanAadharImage;
    }

    public void setGetCanAadharImage(String getCanAadharImage) {
        this.getCanAadharImage = getCanAadharImage;
    }
}
