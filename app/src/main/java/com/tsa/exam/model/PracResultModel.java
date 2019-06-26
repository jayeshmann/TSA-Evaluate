package com.tsa.exam.model;

import io.realm.RealmObject;

public class PracResultModel  extends RealmObject {

    private String mAssessorId;
    private String mBatchId;
    private String mCandidateLoginId;
    private String mCandidateName;
    private String mExamId;
    private String mNos;
    private String mObtainMarks;
    private String mPc;
    private String mPcMarks;
    private String mQid;

    public String getAssessorId() {
        return mAssessorId;
    }

    public void setAssessorId(String assessorId) {
        mAssessorId = assessorId;
    }

    public String getBatchId() {
        return mBatchId;
    }

    public void setBatchId(String batchId) {
        mBatchId = batchId;
    }

    public String getCandidateLoginId() {
        return mCandidateLoginId;
    }

    public void setCandidateLoginId(String candidateLoginId) {
        mCandidateLoginId = candidateLoginId;
    }

    public String getCandidateName() {
        return mCandidateName;
    }

    public void setCandidateName(String candidateName) {
        mCandidateName = candidateName;
    }

    public String getExamId() {
        return mExamId;
    }

    public void setExamId(String examId) {
        mExamId = examId;
    }

    public String getNos() {
        return mNos;
    }

    public void setNos(String nos) {
        mNos = nos;
    }

    public String getObtainMarks() {
        return mObtainMarks;
    }

    public void setObtainMarks(String obtainMarks) {
        mObtainMarks = obtainMarks;
    }

    public String getPc() {
        return mPc;
    }

    public void setPc(String pc) {
        mPc = pc;
    }

    public String getPcMarks() {
        return mPcMarks;
    }

    public void setPcMarks(String pcMarks) {
        mPcMarks = pcMarks;
    }

    public String getQid() {
        return mQid;
    }

    public void setQid(String qid) {
        mQid = qid;
    }

}
