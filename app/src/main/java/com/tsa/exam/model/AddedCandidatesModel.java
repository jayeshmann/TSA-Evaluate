package com.tsa.exam.model;

/**
 * Created by Akhil Tripathi on 30-06-2017.
 */

public class AddedCandidatesModel {

    private String candidateID;
    private String candidatePassKey;
    private String candidateAadhar;

    private String status;
    private String timeLeft;

    private String loginTime;
    private String logoutTime;

    private String locked;

    private String loginAttempt;

    private String lastVistedQ;

    public String getMinimized() {
        return minimized;
    }

    public void setMinimized(String minimized) {
        this.minimized = minimized;
    }

    protected String minimized;

    public AddedCandidatesModel(String candidateID, String candidatePassKey, String candidateAadhar, String status,
                                String timeLeft, String loginTime, String logoutTime, String locked, String loginAttempt,
                                String lastVistedQ,String minimized) {
        this.candidateID = candidateID;
        this.candidatePassKey = candidatePassKey;
        this.candidateAadhar = candidateAadhar;
        this.status = status;
        this.timeLeft = timeLeft;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.locked = locked;
        this.loginAttempt = loginAttempt;
        this.lastVistedQ = lastVistedQ;
        this.minimized=minimized;
    }

    public String getLastVistedQ() {
        return lastVistedQ;
    }

    public void setLastVistedQ(String lastVistedQ) {
        this.lastVistedQ = lastVistedQ;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(String logoutTime) {
        this.logoutTime = logoutTime;
    }


    /*
       1="Not Attempted Exam"
       2="Exam NOT completed"
       3="Exam Attempted"
     */

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(String loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(String timeLeft) {
        this.timeLeft = timeLeft;
    }

    public String getCandidateAadhar() {
        return candidateAadhar;
    }

    public void setCandidateAadhar(String candidateAadhar) {
        this.candidateAadhar = candidateAadhar;
    }

    public String getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(String candidateID) {
        this.candidateID = candidateID;
    }

    public String getCandidatePassKey() {
        return candidatePassKey;
    }

    public void setCandidatePassKey(String candidatePassKey) {
        this.candidatePassKey = candidatePassKey;
    }

    public AddedCandidatesModel() {

    }
}
