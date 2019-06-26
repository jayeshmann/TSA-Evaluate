package com.tsa.exam.model;

import io.realm.RealmObject;

/**
 * Created by Akhil Tripathi on 08-07-2017.
 */

public class LockedCanModel extends RealmObject {

    String candidateID;
    int attempted;
    String lockStaus;

    public String getCandidateID() {
        return candidateID;
    }

    public void setCandidateID(String candidateID) {
        this.candidateID = candidateID;
    }

    public int getAttempted() {
        return attempted;
    }

    public void setAttempted(int attempted) {
        this.attempted = attempted;
    }

    public String getLockStaus() {
        return lockStaus;
    }

    public void setLockStaus(String lockStaus) {
        this.lockStaus = lockStaus;
    }
}
