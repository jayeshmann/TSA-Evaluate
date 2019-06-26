
package com.tsa.exam.model;

import io.realm.RealmObject;

public class PracLoginAssModel extends RealmObject {

    private String mAssessorId;
    private String mBatchId;
    private String mBatchNumber;
    private String mExamId;
    private String mExamName;
    private String mId;
    private String mJobrole;
    private String mNos;
    private String mNosMarks;
    private String mPasskey;
    private String mPc;
    private String mPcMarks;
    private String mQuestion;
    private String mSector;
    private String mActivity;

    @Override
    public String toString() {
        return "PracLoginAssModel{" +
                "mAssessorId='" + mAssessorId + '\'' +
                ", mBatchId='" + mBatchId + '\'' +
                ", mBatchNumber='" + mBatchNumber + '\'' +
                ", mExamId='" + mExamId + '\'' +
                ", mExamName='" + mExamName + '\'' +
                ", mId='" + mId + '\'' +
                ", mJobrole='" + mJobrole + '\'' +
                ", mNos='" + mNos + '\'' +
                ", mNosMarks='" + mNosMarks + '\'' +
                ", mPasskey='" + mPasskey + '\'' +
                ", mPc='" + mPc + '\'' +
                ", mPcMarks='" + mPcMarks + '\'' +
                ", mQuestion='" + mQuestion + '\'' +
                ", mSector='" + mSector + '\'' +
                ", mActivity='" + mActivity + '\'' +
                '}';
    }

    public String getmAssessorId() {
        return mAssessorId;
    }

    public void setmAssessorId(String mAssessorId) {
        this.mAssessorId = mAssessorId;
    }

    public String getmBatchId() {
        return mBatchId;
    }

    public void setmBatchId(String mBatchId) {
        this.mBatchId = mBatchId;
    }

    public String getmBatchNumber() {
        return mBatchNumber;
    }

    public void setmBatchNumber(String mBatchNumber) {
        this.mBatchNumber = mBatchNumber;
    }

    public String getmExamId() {
        return mExamId;
    }

    public void setmExamId(String mExamId) {
        this.mExamId = mExamId;
    }

    public String getmExamName() {
        return mExamName;
    }

    public void setmExamName(String mExamName) {
        this.mExamName = mExamName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmJobrole() {
        return mJobrole;
    }

    public void setmJobrole(String mJobrole) {
        this.mJobrole = mJobrole;
    }

    public String getmNos() {
        return mNos;
    }

    public void setmNos(String mNos) {
        this.mNos = mNos;
    }

    public String getmNosMarks() {
        return mNosMarks;
    }

    public void setmNosMarks(String mNosMarks) {
        this.mNosMarks = mNosMarks;
    }

    public String getmPasskey() {
        return mPasskey;
    }

    public void setmPasskey(String mPasskey) {
        this.mPasskey = mPasskey;
    }

    public String getmPc() {
        return mPc;
    }

    public void setmPc(String mPc) {
        this.mPc = mPc;
    }

    public String getmPcMarks() {
        return mPcMarks;
    }

    public void setmPcMarks(String mPcMarks) {
        this.mPcMarks = mPcMarks;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public void setmQuestion(String mQuestion) {
        this.mQuestion = mQuestion;
    }

    public String getmSector() {
        return mSector;
    }

    public void setmSector(String mSector) {
        this.mSector = mSector;
    }

    public String getmActivity() {
        return mActivity;
    }

    public void setmActivity(String mActivity) {
        this.mActivity = mActivity;
    }
}
