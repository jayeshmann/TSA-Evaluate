package com.tsa.exam.model;

/**
 * Created by Akhil Tripathi on 05-07-2017.
 */

public class ShowCandidatesModel {

    public String getLofinID() {
        return lofinID;
    }

    public void setLofinID(String lofinID) {
        this.lofinID = lofinID;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    private String lofinID;
    private String candidateName;

    @Override
    public String toString() {
        return "ShowCandidatesModel{" +
                "lofinID='" + lofinID + '\'' +
                ", candidateName='" + candidateName + '\'' +
                '}';
    }
}
