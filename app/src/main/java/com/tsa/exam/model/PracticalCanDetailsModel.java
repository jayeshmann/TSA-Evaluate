
package com.tsa.exam.model;

import io.realm.RealmObject;

public class PracticalCanDetailsModel  extends RealmObject {

    private String mAadharCardId;
    private String mCandidateBatch;
    private String mCandidateLoginId;
    private String mCandidateName;
    private String mCandidateUserId;
    private String mEnrollmentNumber;
    private String mId;

    public String getAadharCardId() {
        return mAadharCardId;
    }

    public void setAadharCardId(String aadharCardId) {
        mAadharCardId = aadharCardId;
    }

    public String getCandidateBatch() {
        return mCandidateBatch;
    }

    public void setCandidateBatch(String candidateBatch) {
        mCandidateBatch = candidateBatch;
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

    public String getCandidateUserId() {
        return mCandidateUserId;
    }

    public void setCandidateUserId(String candidateUserId) {
        mCandidateUserId = candidateUserId;
    }

    public String getEnrollmentNumber() {
        return mEnrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        mEnrollmentNumber = enrollmentNumber;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

}
