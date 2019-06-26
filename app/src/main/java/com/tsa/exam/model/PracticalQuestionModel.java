
package com.tsa.exam.model;

import io.realm.RealmObject;

public class PracticalQuestionModel extends RealmObject {

    private String mActivity;
    private String mAssessorId;
    private String mBatchId;
    private String mBatchNumber;
    private String mCandidateLoginId;
    private String mExamId;
    private String mExamName;
    private String mJobrole;
    private String mNos;
    private String mNosMarks;
    private String mPasskey;
    private String mPc;
    private String mPcMarks;
    private String mQid;
    private String mQuestion;
    private String mSector;
    private String candidateName;

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getActivity() {
        return mActivity;
    }

    public void setActivity(String activity) {
        mActivity = activity;
    }

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

    public String getBatchNumber() {
        return mBatchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        mBatchNumber = batchNumber;
    }

    public String getCandidateLoginId() {
        return mCandidateLoginId;
    }

    public void setCandidateLoginId(String candidateLoginId) {
        mCandidateLoginId = candidateLoginId;
    }

    public String getExamId() {
        return mExamId;
    }

    public void setExamId(String examId) {
        mExamId = examId;
    }

    public String getExamName() {
        return mExamName;
    }

    public void setExamName(String examName) {
        mExamName = examName;
    }

    public String getJobrole() {
        return mJobrole;
    }

    public void setJobrole(String jobrole) {
        mJobrole = jobrole;
    }

    public String getNos() {
        return mNos;
    }

    public void setNos(String nos) {
        mNos = nos;
    }

    public String getNosMarks() {
        return mNosMarks;
    }

    public void setNosMarks(String nosMarks) {
        mNosMarks = nosMarks;
    }

    public String getPasskey() {
        return mPasskey;
    }

    public void setPasskey(String passkey) {
        mPasskey = passkey;
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

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getSector() {
        return mSector;
    }

    public void setSector(String sector) {
        mSector = sector;
    }

}
