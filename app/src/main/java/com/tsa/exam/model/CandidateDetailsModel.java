package com.tsa.exam.model;

/**
 * Created by Akhil Tripathi on 30-06-2017.
 */

public class CandidateDetailsModel {

    private String id;
    private String batchID;
    private String CandidateLoginID;
    private String CandidateName;
    private String EnrollmentNumber;
    private String AadharCardID;
    private String CandidateUserID;

    public String getCanStaus() {
        return canStaus;
    }

    public void setCanStaus(String canStaus) {
        this.canStaus = canStaus;
    }

    private String canStaus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getCandidateLoginID() {
        return CandidateLoginID;
    }

    public void setCandidateLoginID(String candidateLoginID) {
        CandidateLoginID = candidateLoginID;
    }

    public String getCandidateName() {
        return CandidateName;
    }

    public void setCandidateName(String candidateName) {
        CandidateName = candidateName;
    }

    public String getEnrollmentNumber() {
        return EnrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        EnrollmentNumber = enrollmentNumber;
    }

    public String getAadharCardID() {
        return AadharCardID;
    }

    public void setAadharCardID(String aadharCardID) {
        AadharCardID = aadharCardID;
    }

    public String getCandidateUserID() {
        return CandidateUserID;
    }

    public void setCandidateUserID(String candidateUserID) {
        CandidateUserID = candidateUserID;
    }
}
