package com.tsa.exam.model;

import io.realm.RealmObject;

/**
 * Created by Akhil Tripathi on 09-07-2017.
 */

public class AttemptedSequenceModel extends RealmObject{
    private String qID;
    private String questionID;
    private String optionClicked;
    private String canLoginID;

    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getOptionClicked() {
        return optionClicked;
    }

    public void setOptionClicked(String optionClicked) {
        this.optionClicked = optionClicked;
    }

    public String getCanLoginID() {
        return canLoginID;
    }

    public void setCanLoginID(String canLoginID) {
        this.canLoginID = canLoginID;
    }
}
