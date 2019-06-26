package com.tsa.exam.model;

import io.realm.RealmObject;

/**
 * Created by Akhil Tripathi on 06-07-2017.
 */

public class TrackModel extends RealmObject {

    private String examID;
    private String batchID;
    private String candidateID;
    private String questionID;
    private String timeVisited;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getBatchID() {
        return batchID;
    }

    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    public String getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(String candidateID) {
        this.candidateID = candidateID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getTimeVisited() {
        return timeVisited;
    }

    public void setTimeVisited(String timeVisited) {
        this.timeVisited = timeVisited;
    }

}
