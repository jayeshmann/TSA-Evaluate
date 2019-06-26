package com.tsa.exam.model;

/**
 * Created by Akhil Tripathi on 30-06-2017.
 */

public class ResultModel {

    private String examID;
    private String batchID;
    private String candidateID;
    private String candidateName;
    private String nos;
    private String questionID;
    private String shuffleOrder;
    private String correctAnswer;
    private String questionMarks;
    private String attdemptedAnswer;
    private String assID;
    private String questionReview;
    private String candidateLoginID;
    private String marks;
    private String obtainedMarks;
    private String pc;

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public ResultModel() {

    }

    public ResultModel(String examID, String batchID, String candidateID, String candidateName, String nos,
                       String questionID, String shuffleOrder, String correctAnswer, String questionMarks,
                       String attdemptedAnswer,
                       String assID, String questionReview, String candidateLoginID, String marks, String obtainedMarks) {
        this.examID = examID;
        this.batchID = batchID;
        this.candidateID = candidateID;
        this.candidateName = candidateName;
        this.nos = nos;
        this.questionID = questionID;
        this.shuffleOrder = shuffleOrder;
        this.correctAnswer = correctAnswer;
        this.questionMarks = questionMarks;
        this.attdemptedAnswer = attdemptedAnswer;
        this.assID = assID;
        this.questionReview = questionReview;
        this.candidateLoginID = candidateLoginID;
        this.marks = marks;
        this.obtainedMarks = obtainedMarks;
    }

    public String getAssID() {
        return assID;
    }

    public void setAssID(String assID) {
        this.assID = assID;
    }

    public String getQuestionReview() {
        return questionReview;
    }

    public void setQuestionReview(String questionReview) {
        this.questionReview = questionReview;
    }

    public String getCandidateLoginID() {
        return candidateLoginID;
    }

    public void setCandidateLoginID(String candidateLoginID) {
        this.candidateLoginID = candidateLoginID;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(String obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
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

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getNos() {
        return nos;
    }

    public void setNos(String nos) {
        this.nos = nos;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getShuffleOrder() {
        return shuffleOrder;
    }

    public void setShuffleOrder(String shuffleOrder) {
        this.shuffleOrder = shuffleOrder;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionMarks() {
        return questionMarks;
    }

    public void setQuestionMarks(String questionMarks) {
        this.questionMarks = questionMarks;
    }

    public String getAttdemptedAnswer() {
        return attdemptedAnswer;
    }

    public void setAttdemptedAnswer(String attdemptedAnswer) {
        this.attdemptedAnswer = attdemptedAnswer;
    }
}
