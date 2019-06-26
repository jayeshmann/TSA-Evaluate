package com.tsa.exam.model;

import androidx.room.Entity;
import androidx.annotation.NonNull;

@Entity(primaryKeys = {"mCandidateLoginId", "mQid"})
public class NOSPracticalModel {
    private String examStatus;

    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }

    private String mAssessorId;
    private String mBatchId;
    private String mBatchNumber;
    ////////////////////////////////
    ////////////////////////////////
    @NonNull
    private String mCandidateLoginId;
    private String mCandidateName;
    private String mExamId;
    private String mExamName;
    private String mJobrole;
    private String mNos;
    private String mNosMarks;
    private String mPasskey;
    //////////////////////////
    /////////////////////////
    @NonNull
    private String mQid;
    private String mSector;
    private String mStep1;
    private String mStep1Marks;
    private String mStep2;
    private String mStep2Marks;
    private String mStep3;
    private String mStep3Marks;
    private String mStep4;
    private String mStep4Marks;
    private String mStep5;
    private String mStep5Marks;
    private String mStep6;
    private String mStep6Marks;
    private String mQuestion;
    ///////////////////////////
    ///////////////////////////
    private int mStepObtMarks1;
    private int mStepObtMarks2;
    private int mStepObtMarks3;
    private int mStepObtMarks4;
    private int mStepObtMarks5;
    private int mStepObtMarks6;

    public int getStepObtMarks1() {
        return mStepObtMarks1;
    }

    public void setStepObtMarks1(int mStepObtMarks1) {
        this.mStepObtMarks1 = mStepObtMarks1;
    }

    public int getStepObtMarks2() {
        return mStepObtMarks2;
    }

    public void setStepObtMarks2(int mStepObtMarks2) {
        this.mStepObtMarks2 = mStepObtMarks2;
    }

    public int getStepObtMarks3() {
        return mStepObtMarks3;
    }

    public void setStepObtMarks3(int mStepObtMarks3) {
        this.mStepObtMarks3 = mStepObtMarks3;
    }

    public int getStepObtMarks4() {
        return mStepObtMarks4;
    }

    public void setStepObtMarks4(int mStepObtMarks4) {
        this.mStepObtMarks4 = mStepObtMarks4;
    }

    public int getStepObtMarks5() {
        return mStepObtMarks5;
    }

    public void setStepObtMarks5(int mStepObtMarks5) {
        this.mStepObtMarks5 = mStepObtMarks5;
    }

    public int getStepObtMarks6() {
        return mStepObtMarks6;
    }

    public void setStepObtMarks6(int mStepObtMarks6) {
        this.mStepObtMarks6 = mStepObtMarks6;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
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

    public String getQid() {
        return mQid;
    }

    public void setQid(String qid) {
        mQid = qid;
    }

    public String getSector() {
        return mSector;
    }

    public void setSector(String sector) {
        mSector = sector;
    }

    public String getStep1() {
        return mStep1;
    }

    public void setStep1(String step1) {
        mStep1 = step1;
    }

    public String getStep1Marks() {
        return mStep1Marks;
    }

    public void setStep1Marks(String step1Marks) {
        mStep1Marks = step1Marks;
    }

    public String getStep2() {
        return mStep2;
    }

    public void setStep2(String step2) {
        mStep2 = step2;
    }

    public String getStep2Marks() {
        return mStep2Marks;
    }

    public void setStep2Marks(String step2Marks) {
        mStep2Marks = step2Marks;
    }

    public String getStep3() {
        return mStep3;
    }

    public void setStep3(String step3) {
        mStep3 = step3;
    }

    public String getStep3Marks() {
        return mStep3Marks;
    }

    public void setStep3Marks(String step3Marks) {
        mStep3Marks = step3Marks;
    }

    public String getStep4() {
        return mStep4;
    }

    public void setStep4(String step4) {
        mStep4 = step4;
    }

    public String getStep4Marks() {
        return mStep4Marks;
    }

    public void setStep4Marks(String step4Marks) {
        mStep4Marks = step4Marks;
    }

    public String getStep5() {
        return mStep5;
    }

    public void setStep5(String step5) {
        mStep5 = step5;
    }

    public String getStep5Marks() {
        return mStep5Marks;
    }

    public void setStep5Marks(String step5Marks) {
        mStep5Marks = step5Marks;
    }

    public String getStep6() {
        return mStep6;
    }

    public void setStep6(String step6) {
        mStep6 = step6;
    }

    public String getStep6Marks() {
        return mStep6Marks;
    }

    public void setStep6Marks(String step6Marks) {
        mStep6Marks = step6Marks;
    }

    @Override
    public String toString() {
        return "NOSPracticalModel{" +
                "mAssessorId='" + mAssessorId + '\'' +
                ", mBatchId='" + mBatchId + '\'' +
                ", mBatchNumber='" + mBatchNumber + '\'' +
                ", mCandidateLoginId='" + mCandidateLoginId + '\'' +
                ", mCandidateName='" + mCandidateName + '\'' +
                ", mExamId='" + mExamId + '\'' +
                ", mExamName='" + mExamName + '\'' +
                ", mJobrole='" + mJobrole + '\'' +
                ", mNos='" + mNos + '\'' +
                ", mNosMarks='" + mNosMarks + '\'' +
                ", mPasskey='" + mPasskey + '\'' +
                ", mQid='" + mQid + '\'' +
                ", mSector='" + mSector + '\'' +
                ", mStep1='" + mStep1 + '\'' +
                ", mStep1Marks='" + mStep1Marks + '\'' +
                ", mStep2='" + mStep2 + '\'' +
                ", mStep2Marks='" + mStep2Marks + '\'' +
                ", mStep3='" + mStep3 + '\'' +
                ", mStep3Marks='" + mStep3Marks + '\'' +
                ", mStep4='" + mStep4 + '\'' +
                ", mStep4Marks='" + mStep4Marks + '\'' +
                ", mStep5='" + mStep5 + '\'' +
                ", mStep5Marks='" + mStep5Marks + '\'' +
                ", mStep6='" + mStep6 + '\'' +
                ", mStep6Marks='" + mStep6Marks + '\'' +
                ", mQuestion='" + mQuestion + '\'' +
                ", mStepObtMarks1=" + mStepObtMarks1 +
                ", mStepObtMarks2=" + mStepObtMarks2 +
                ", mStepObtMarks3=" + mStepObtMarks3 +
                ", mStepObtMarks4=" + mStepObtMarks4 +
                ", mStepObtMarks5=" + mStepObtMarks5 +
                ", mStepObtMarks6=" + mStepObtMarks6 +
                '}';
    }

}
