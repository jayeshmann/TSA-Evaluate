package com.tsa.exam.model;

/**
 * Created by Akhil Tripathi on 30-06-2017.
 */

public class AssessorsLoginDetails {
    private String exmID;
    private String batchID;
    private String exmStartDate;
    private String exanEndDate;
    private String exmRewgDate;
    private String batchNumber;
    private String assID;

    public String getAssID() {
        return assID;
    }

    public void setAssID(String assID) {
        this.assID = assID;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getExmID() {
        return exmID;
    }

    public void setExmID(String exmID) {
        this.exmID = exmID;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getExmStartDate() {
        return exmStartDate;
    }

    public void setExmStartDate(String exmStartDate) {
        this.exmStartDate = exmStartDate;
    }

    public String getExanEndDate() {
        return exanEndDate;
    }

    public void setExanEndDate(String exanEndDate) {
        this.exanEndDate = exanEndDate;
    }

    public String getExmRewgDate() {
        return exmRewgDate;
    }

    public void setExmRewgDate(String exmRewgDate) {
        this.exmRewgDate = exmRewgDate;
    }
}
