package com.tsa.exam.model;

import io.realm.RealmObject;

/**
 * Created by Akhil Tripathi on 21-07-2017.
 */

public class ExamDateModel extends RealmObject {
    private String examDate="";

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }
}
