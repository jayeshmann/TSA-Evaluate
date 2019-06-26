package com.tsa.exam.model;

/**
 * Created by Akhil Tripathi on 4/15/2017.
 */

public class QuestionModel {

    private int qId;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int checked=0;
    private String questionStatus;
    private String image;
    private String optionImage;
    private String marks;
    private String image_path;
    private String examduration;
    private String sector;
    private String jobrole;
    private String exam_id;
    private String batch_id;
    private String cid;
    private String cname;
    private String assessor_id;
    private String nos;
    private String correct_ans;
    private String pc;
    private String pc_marks;
    private String shuffle;
    private String questionID;

    @Override
    public String toString() {
        return "QuestionModel{" +
                "qId=" + qId +
                ", question='" + question + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", checked=" + checked +
                ", questionStatus='" + questionStatus + '\'' +
                ", image='" + image + '\'' +
                ", optionImage='" + optionImage + '\'' +
                ", marks='" + marks + '\'' +
                ", image_path='" + image_path + '\'' +
                ", examduration='" + examduration + '\'' +
                ", sector='" + sector + '\'' +
                ", jobrole='" + jobrole + '\'' +
                ", exam_id='" + exam_id + '\'' +
                ", batch_id='" + batch_id + '\'' +
                ", cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                ", assessor_id='" + assessor_id + '\'' +
                ", nos='" + nos + '\'' +
                ", correct_ans='" + correct_ans + '\'' +
                ", pc='" + pc + '\'' +
                ", pc_marks='" + pc_marks + '\'' +
                ", shuffle='" + shuffle + '\'' +
                ", questionID='" + questionID + '\'' +
                ", candidate_login_id='" + candidate_login_id + '\'' +
                '}';
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getPc_marks() {
        return pc_marks;
    }

    public void setPc_marks(String pc_marks) {
        this.pc_marks = pc_marks;
    }

    public String getShuffle() {
        return shuffle;
    }

    public void setShuffle(String shuffle) {
        this.shuffle = shuffle;
    }

    public String getCandidate_login_id() {
        return candidate_login_id;
    }

    public void setCandidate_login_id(String candidate_login_id) {
        this.candidate_login_id = candidate_login_id;
    }


    private String candidate_login_id;


    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;

    }

    public String getJobrole() {
        return jobrole;
    }

    public void setJobrole(String jobrole) {
        this.jobrole = jobrole;

    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;

    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;

    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;

    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;

    }

    public String getAssessor_id() {
        return assessor_id;
    }

    public void setAssessor_id(String assessor_id) {
        this.assessor_id = assessor_id;

    }

    public String getNos() {
        return nos;
    }

    public void setNos(String nos) {
        this.nos = nos;

    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;

    }

    public QuestionModel(){}


    public String getOptionImage() {
        return optionImage;
    }

    public void setOptionImage(String optionImage) {
        this.optionImage = optionImage;

    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;

    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;

    }

    public String getExamduration() {
        return examduration;
    }

    public void setExamduration(String examduration) {
        this.examduration = examduration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;

    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;

    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;

    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;

    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;

    }

    public String getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(String questionStatus) {
        this.questionStatus = questionStatus;
    }

}