package com.tsa.exam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tsa.exam.MainActivity;
import com.tsa.exam.model.AllImageModel;
import com.tsa.exam.model.CandidateImages;
import com.tsa.exam.model.FeedbackModel;
import com.tsa.exam.model.QuestionModel;
import com.tsa.exam.model.ResultModel;
import com.tsa.exam.Utill.GLOBAL;
import com.tsa.exam.model.AddedCandidatesModel;


import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    static final int DATABASE_VERSION = 1;

    // Database Name
    static final String DATABASE_NAME = "examination_db";

    // Contacts table name
    public static final String TABLE_EXAM = "examination_table";

    ///////////////Contacts Table Columns names////////////////
    private static final String EX_ID = "id";
    private static final String QUESTION = "question";
    private static final String OPTION1 = "option1";
    private static final String OPTION2 = "option2";
    private static final String OPTION3 = "option3";
    private static final String OPTION4 = "option4";
    private static final String OPTION5 = "option5";
    private static final String STATUS = "status";
    private static final String IMAGE = "image";
    private static final String CHEACKED = "cheacked";
    private static final String OPTION_IMAGE = "option_image";
    private static final String IMAGE_PATH = "image_path";
    private static final String MARKS = "marks";
    private static final String EXAMDURATION = "examduration";
    private static final String SECTOR = "Security";
    private static final String JOB_ROLE = "jobrole";
    private static final String EXAM_ID = "exam_id";
    private static final String BATCH_ID = "batch_id";
    private static final String C_ID = "cid";
    private static final String C_NAME = "cname";
    private static final String ASSESSOR_ID = "assessor_id";
    private static final String NOS = "nos";
    private static final String CORRECT_ANSWER = "correct_ans";
    private static final String CANDIDATE_LOGIN_ID = "candidate_login_id";
    private static final String PC = "pc";
    private static final String PC_MARKS = "pc_marks";
    private static final String SHUFFLE = "shuffle";
    private static final String QUESTION_ID = "question_id";


    /////////////////////////////////////////////////////////////////

    /////////////////////////////////FEED BACK TABLE////////////////////////////////////
    public static final String TABLE_FEEDBACK = "feedback_table";
    ////////////////////////////////////////////////////////////////////////////////////
    private static final String QUESTION1 = "question1";
    private static final String QUESTION2 = "question2";
    private static final String QUESTION3 = "question3";
    private static final String QUESTION4 = "question4";
    private static final String QUESTION5 = "question5";
    private static final String QUESTION6 = "question6";
    private static final String QUESTION7 = "question7";
    private static final String QUESTION8 = "question8";
    private static final String QUESTION9 = "question9";
    private static final String QUESTION10 = "question10";
    private static final String F_CANID = "can_id";

    ///////////////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////

    private static final String TABLE_RESULT = "result_table";
    private static final String R_EXAM_ID = "exam_id";
    private static final String R_BATCH_ID = "batch_id";
    private static final String R_CANDIDATE_ID = "c_id";
    private static final String R_CANDIDATE_NAME = "c_name";
    private static final String R_NOS = "nos";
    private static final String R_QUESTION_ID = "q_id";
    private static final String R_SHUFFLE = "shuffle";
    private static final String R_CURRECT_ANSWER = "curr_answer";
    private static final String R_QUESTION_MARKS = "q_marks";
    private static final String R_ATTEMTED_ANSWER = "attemted_ans";
    private static final String R_ASS_ID = "assID";
    private static final String R_QUESTION_REVIEW = "questionReview";
    private static final String R_CANDIDATE_LOGIN_ID = "candidateLoginID";
    private static final String R_MARKS = "marks";
    private static final String R_OBTAIN_MARKS = "obtainedMarks";
    private static final String R_PC = "pc";

    /////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////
    private static final String TABLE_ADDED_CANDIDATE = "added_candidate_table";
    private static final String CANDIDATE_ID = "candidate_id";
    private static final String PASS_KEY = "pass_key";
    private static final String AADHAR_ID = "aadhar_id";
    private static final String CAN_STATUS = "status";
    private static final String TIME_LEFT = "time_left";
    private static final String LOGIN_TIME = "login_time";
    private static final String LOGOUT_TIME = "logout_time";
    private static final String NO_ATTEMPT = "no_attempt";
    private static final String MY_LOCKED = "my_locked";
    private static final String LAST_VISITED_Q = "last_visited_Q";
    //////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////
    private static final String Track_Table = "track_table";
    private static final String T_EXAM_ID = "exam_id";
    private static final String T_BATCH_ID = "batch_id";
    private static final String T_CANDIDATE_ID = "candidate_id";
    private static final String T_QUESTION_ID = "question_id";
    private static final String T_TIME_VISITED = "time_visited";
    private static final String T_RESPONSE = "response";
    private static final String T_SEQUENCE = "sequence";
    ///////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////

    private static final String CAN_IMAGE_TABLE = "can_image_table";
    private static final String I_CAN_ID = "can_id";
    private static final String I_AADHAR_ID = "aadhar_id";
    private static final String I_CANDIDATE_IMAGE = "can_image";
    private static final String I_AADHAR_IMAGE = "aadhar_image";
    private static final String I_AADHAR_TIME = "aadahr_time";
    private static final String I_AADHAR_DATE = "aadahr_date";
    private static final String I_CAN_TIME = "can_time";
    private static final String I_CAN_DATE = "can_date";
    ///////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////
    private static final String ALL_IMAGE_TABLE = "all_image_table";
    private static final String A_EXAM_ID = "all_exam";
    private static final String A_BATCH_NUMBER = "all_batch_number";
    private static final String A_CAN_ID = "all_can_id";
    private static final String A_IMAGE = "all_image";
    private static final String A_DATE = "all_date";
    private static final String A_TIME = "all_time";
    //////////////////////////////////////////////////////////////////

//    private static final String VIDEO = "all_video_table";
    private static final String TABLE_VIDEO = "video_table";
    private static final String V_BATCH_ID = "batch_id";
    private static final String V_EXAMID = "exam_id";
    private static final String V_CANDIDATE_ID = "candidate_id";
    private static final String V_VIDEO = "video";







    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        ////////////////////////////////////////////////////////////////////////////////////////////
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + ALL_IMAGE_TABLE + "(" + A_EXAM_ID + " TEXT,"
                + A_BATCH_NUMBER + " TEXT," + A_CAN_ID + " TEXT,"
                + A_IMAGE + " TEXT," + A_DATE + " TEXT,"
                + A_TIME + " TEXT" + ")";


//        String CREATE_VIDEO = "CREATE TABLE " +VIDEO+"("+batch_id+ "TEXT",+exam_id +"TEXT",+candidate_id +"TEXT"

        ////////////////////////////////////////////////////////////////////////////////////////////

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_EXAM + "(" + EX_ID + " TEXT,"
                + QUESTION + " TEXT," + OPTION1 + " TEXT,"
                + OPTION2 + " TEXT," + OPTION3 + " TEXT,"
                + OPTION4 + " TEXT," + OPTION5 + " TEXT,"
                + STATUS + " TEXT," + IMAGE + " TEXT," + CHEACKED + " TEXT," + OPTION_IMAGE +
                " TEXT," + IMAGE_PATH + " TEXT," + MARKS + " TEXT," + EXAMDURATION + " TEXT," + SECTOR + " TEXT,"
                + JOB_ROLE + " TEXT," + EXAM_ID + " TEXT," + BATCH_ID + " TEXT," + C_ID + " TEXT," +
                C_NAME + " TEXT," + ASSESSOR_ID + " TEXT," + NOS + " TEXT," + CORRECT_ANSWER + " TEXT,"
                + CANDIDATE_LOGIN_ID + " TEXT," + PC + " TEXT,"
                + PC_MARKS + " TEXT," + SHUFFLE + " TEXT," + QUESTION_ID + " TEXT" + ")";


        // Table create statement
        String CREATE_TABLE_IMAGE = "CREATE TABLE " + CAN_IMAGE_TABLE + "(" +
                I_CAN_ID + " TEXT," +
                I_AADHAR_ID + " TEXT," +
                I_CANDIDATE_IMAGE + " TEXT," +
                I_AADHAR_IMAGE + " TEXT," +
                I_AADHAR_TIME + " TEXT," +
                I_AADHAR_DATE + " TEXT," +
                I_CAN_TIME + " TEXT," +
                I_CAN_DATE + " TEXT);";


        //Table create table feedback
        String CREATE_TABLE_FEEDBACK = "CREATE TABLE " + TABLE_FEEDBACK + "(" +
                QUESTION1 + " TEXT," +
                QUESTION2 + " TEXT," +
                QUESTION3 + " TEXT," +
                QUESTION4 + " TEXT," +
                QUESTION5 + " TEXT," +
                QUESTION6 + " TEXT," +
                QUESTION7 + " TEXT," +
                QUESTION8 + " TEXT," +
                QUESTION9 + " TEXT," +
                QUESTION10 + " TEXT," +
                F_CANID + " TEXT);";

        //Table create table feedback
        String CREATE_TABLE_RESULT = "CREATE TABLE " + TABLE_RESULT + "(" +
                R_EXAM_ID + " TEXT," +
                R_BATCH_ID + " TEXT," +
                R_CANDIDATE_ID + " TEXT," +
                R_CANDIDATE_NAME + " TEXT," +
                R_NOS + " TEXT," +
                R_QUESTION_ID + " TEXT," +
                R_SHUFFLE + " TEXT," +
                R_CURRECT_ANSWER + " TEXT," +
                R_QUESTION_MARKS + " TEXT," +
                R_ATTEMTED_ANSWER + " TEXT," +
                R_ASS_ID + " TEXT," +
                R_QUESTION_REVIEW + " TEXT," +
                R_CANDIDATE_LOGIN_ID + " TEXT," +
                R_MARKS + " TEXT," +
                R_OBTAIN_MARKS + " TEXT," +
                R_PC + " TEXT);";


        //Table create table feedback
        String CREATE_TABLE_ADDED_CANDIDATE = "CREATE TABLE " + TABLE_ADDED_CANDIDATE + "(" +
                CANDIDATE_ID + " TEXT," +
                PASS_KEY + " TEXT," + AADHAR_ID + " TEXT," +
                CAN_STATUS + " TEXT," + TIME_LEFT + " TEXT," +
                LOGIN_TIME + " TEXT," + LOGOUT_TIME + " TEXT,"
                + NO_ATTEMPT + " TEXT," + MY_LOCKED + " TEXT," + LAST_VISITED_Q + " TEXT);";


        //Table create table feedback
        String CREATE_TABLE_TRACK = "CREATE TABLE " + Track_Table + "(" +
                T_EXAM_ID + " TEXT," +
                T_BATCH_ID + " TEXT," +
                T_CANDIDATE_ID + " TEXT," +
                T_QUESTION_ID + " TEXT," +
                T_TIME_VISITED + " TEXT," +
                T_RESPONSE + " TEXT," +
                T_SEQUENCE + " TEXT);";

        // FOR VIDEO TABLE CREATION

        String CREATE_TABLE_VIDEO = "CREATE TABLE " + TABLE_VIDEO + "(" + V_BATCH_ID + " TEXT,"
                + V_CANDIDATE_ID + " TEXT," + V_EXAMID + " TEXT,"
                + V_VIDEO + " TEXT" + ")";


        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_TABLE_FEEDBACK);
        db.execSQL(CREATE_TABLE_IMAGE);
        db.execSQL(CREATE_TABLE_RESULT);
        db.execSQL(CREATE_TABLE_ADDED_CANDIDATE);
        db.execSQL(CREATE_TABLE_TRACK);
        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_TABLE_VIDEO);
        }

    //////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM);

        // Create tables again
        onCreate(db);
    }
    //////////////////////////////////////////////////////////////////////////////

    ////////////////////////////Adding new contact//////////////////////////////////////////////////
    public void addQuestions(ArrayList<QuestionModel> questionModelArrayList, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < questionModelArrayList.size(); i++) {

            values.put(EX_ID, "" + questionModelArrayList.get(i).getqId());
            values.put(QUESTION, "" + questionModelArrayList.get(i).getQuestion());
            values.put(OPTION1, "" + questionModelArrayList.get(i).getOptionA());
            values.put(OPTION2, "" + questionModelArrayList.get(i).getOptionB());
            values.put(OPTION3, "" + questionModelArrayList.get(i).getOptionC());
            values.put(OPTION4, "" + questionModelArrayList.get(i).getOptionD());
            values.put(OPTION5, "");
            values.put(STATUS, "" + questionModelArrayList.get(i).getQuestionStatus());
            values.put(IMAGE, "" + questionModelArrayList.get(i).getImage());
            values.put(CHEACKED, "" + questionModelArrayList.get(i).getChecked());
            values.put(OPTION_IMAGE, "" + questionModelArrayList.get(i).getOptionImage());
            values.put(IMAGE_PATH, "" + questionModelArrayList.get(i).getImage_path());
            values.put(MARKS, "" + questionModelArrayList.get(i).getMarks());
            values.put(EXAMDURATION, "" + questionModelArrayList.get(i).getExamduration());
            values.put(SECTOR, "" + questionModelArrayList.get(i).getSector());
            values.put(JOB_ROLE, "" + questionModelArrayList.get(i).getJobrole());
            values.put(EXAM_ID, "" + questionModelArrayList.get(i).getExam_id());
            values.put(BATCH_ID, "" + questionModelArrayList.get(i).getBatch_id());
            values.put(C_ID, "" + questionModelArrayList.get(i).getCid());
            values.put(C_NAME, "" + questionModelArrayList.get(i).getCname());
            values.put(ASSESSOR_ID, "" + questionModelArrayList.get(i).getAssessor_id());
            values.put(NOS, "" + questionModelArrayList.get(i).getNos());
            values.put(CORRECT_ANSWER, "" + questionModelArrayList.get(i).getCorrect_ans());
            values.put(CANDIDATE_LOGIN_ID, "" + questionModelArrayList.get(i).getCandidate_login_id());
            values.put(PC, "" + questionModelArrayList.get(i).getPc());
            values.put(PC_MARKS, "" + questionModelArrayList.get(i).getPc_marks());
            values.put(SHUFFLE, "" + questionModelArrayList.get(i).getShuffle());
            values.put(QUESTION_ID, "" + questionModelArrayList.get(i).getQuestionID());


            // Inserting Row
            db.insert(TABLE_EXAM, null, values);
        }
        db.close(); // Closing database connection
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////Adding Feedback///////////////////////////
    public void addFeedBack(FeedbackModel feedbackModelArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(QUESTION1, "" + feedbackModelArrayList.getFeedback1());
        values.put(QUESTION2, "" + feedbackModelArrayList.getFeedback2());
        values.put(QUESTION3, "" + feedbackModelArrayList.getFeedback3());
        values.put(QUESTION4, "" + feedbackModelArrayList.getFeedback4());
        values.put(QUESTION5, "" + feedbackModelArrayList.getFeedback5());
        values.put(QUESTION6, "" + feedbackModelArrayList.getFeedback6());
        values.put(QUESTION7, "" + feedbackModelArrayList.getFeedback7());
        values.put(QUESTION8, "" + feedbackModelArrayList.getFeedback8());
        values.put(QUESTION9, "" + feedbackModelArrayList.getFeedback9());
        values.put(QUESTION10, "" + feedbackModelArrayList.getFeedback10());
        values.put(F_CANID, "" + feedbackModelArrayList.getCanID());

        // Inserting Row
        db.insert(TABLE_FEEDBACK, null, values);

        db.close(); // Closing database connection
    }
    ////////////////////////////////////////////////////////////////////

    ////////////////////////get FeedBack////////////////////
    public FeedbackModel getFeedBackByID(String canID) {
        ArrayList<FeedbackModel> feedbackModelArrayList = new ArrayList<FeedbackModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FEEDBACK + " where can_id= " + "'" + canID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FeedbackModel feedbackModel = new FeedbackModel();
                feedbackModel.setFeedback1(cursor.getString(0));
                feedbackModel.setFeedback2(cursor.getString(1));
                feedbackModel.setFeedback3(cursor.getString(2));
                feedbackModel.setFeedback4(cursor.getString(3));
                feedbackModel.setFeedback5(cursor.getString(4));
                feedbackModel.setFeedback6(cursor.getString(5));
                feedbackModel.setFeedback7(cursor.getString(6));
                feedbackModel.setFeedback8(cursor.getString(7));
                feedbackModel.setFeedback9(cursor.getString(8));
                feedbackModel.setFeedback10(cursor.getString(9));
                feedbackModel.setCanID("" + cursor.getString(10));
                feedbackModelArrayList.add(feedbackModel);
            } while (cursor.moveToNext());


        }

        // return contact list
        return feedbackModelArrayList.get(0);
    }
    ////////////////////////////////////////////////////////

    /////////////////////Getting All Contacts//////////////
    public ArrayList<QuestionModel> getAllQuestions() {
        ArrayList<QuestionModel> questationList = new ArrayList<QuestionModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXAM + " where " + CANDIDATE_LOGIN_ID + "= '"
                + GLOBAL.loginID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionModel questionModel = new QuestionModel();
                questionModel.setqId(Integer.parseInt(cursor.getString(0)));
                questionModel.setQuestion(cursor.getString(1));
                questionModel.setOptionA(cursor.getString(2));
                questionModel.setOptionB(cursor.getString(3));
                questionModel.setOptionC(cursor.getString(4));
                questionModel.setOptionD(cursor.getString(5));
                questionModel.setQuestionStatus(cursor.getString(7));
                questionModel.setImage(cursor.getString(8));
                questionModel.setChecked(Integer.parseInt(cursor.getString(9)));
                questionModel.setOptionImage(cursor.getString(10));
                questionModel.setImage_path(cursor.getString(11));
                questionModel.setMarks(cursor.getString(12));
                questionModel.setExamduration(cursor.getString(13));
                questionModel.setSector(cursor.getString(14));
                questionModel.setJobrole(cursor.getString(15));
                questionModel.setExam_id(cursor.getString(16));
                questionModel.setBatch_id(cursor.getString(17));
                questionModel.setCid(cursor.getString(18));
                questionModel.setCname(cursor.getString(19));
                questionModel.setAssessor_id(cursor.getString(20));
                questionModel.setNos(cursor.getString(21));
                questionModel.setCorrect_ans(cursor.getString(22));
                questionModel.setCandidate_login_id(cursor.getString(23));
                questionModel.setPc(cursor.getString(24));
                questionModel.setPc_marks(cursor.getString(25));
                questionModel.setShuffle(cursor.getString(26));
                questionModel.setQuestionID(cursor.getString(27));


                questationList.add(questionModel);
            } while (cursor.moveToNext());

        }

        // return contact list
        return questationList;
    }
    ///////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////
    public ArrayList<QuestionModel> getQuestionByID(int id, String loginID) {
        ArrayList<QuestionModel> questationList = new ArrayList<QuestionModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXAM + " where id=" + id + " and " + CANDIDATE_LOGIN_ID + "= '" + loginID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionModel questionModel = new QuestionModel();
                questionModel.setqId(Integer.parseInt(cursor.getString(0)));
                questionModel.setQuestion(cursor.getString(1));
                questionModel.setOptionA(cursor.getString(2));
                questionModel.setOptionB(cursor.getString(3));
                questionModel.setOptionC(cursor.getString(4));
                questionModel.setOptionD(cursor.getString(5));
                questionModel.setQuestionStatus(cursor.getString(7));
                questionModel.setImage(cursor.getString(8));
                questionModel.setChecked(Integer.parseInt(cursor.getString(9)));
                questionModel.setOptionImage(cursor.getString(10));
                questionModel.setImage_path(cursor.getString(11));
                questionModel.setMarks(cursor.getString(12));
                questionModel.setExamduration(cursor.getString(13));
                questionModel.setSector(cursor.getString(14));
                questionModel.setJobrole(cursor.getString(15));
                questionModel.setExam_id(cursor.getString(16));
                questionModel.setBatch_id(cursor.getString(17));
                questionModel.setCid(cursor.getString(18));
                questionModel.setCname(cursor.getString(19));
                questionModel.setAssessor_id(cursor.getString(20));
                questionModel.setNos(cursor.getString(21));
                questionModel.setCorrect_ans(cursor.getString(22));
                questionModel.setCandidate_login_id(cursor.getString(23));
                questionModel.setPc(cursor.getString(24));
                questionModel.setPc_marks(cursor.getString(25));
                questionModel.setShuffle(cursor.getString(26));
                questionModel.setQuestionID(cursor.getString(27));


                questationList.add(questionModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return questationList;
    }
    //////////////////////////////////////////////////////////////////////////////

    //////////////// Updating single contact/////////////
    public int updateChecked(int id, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = EX_ID + "=" + id + " and " + CANDIDATE_LOGIN_ID + "= " + "'"+GLOBAL.loginID+"'";
        //String[] whereArgs = new String[]{String.valueOf(id)};
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(CHEACKED, value);
        // updating row
        return db.update(TABLE_EXAM, dataToInsert, where,
                null);
    }
    /////////////////////////////////////////////////////

    ///////////////////////////////////////////////////
    public int updateStatus(int id, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = EX_ID + "=" + id + " and " + CANDIDATE_LOGIN_ID + "= " + "'"+GLOBAL.loginID+"'";

        //String[] whereArgs = new String[]{String.valueOf(id)};
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(STATUS, value);

        // updating row
        return db.update(TABLE_EXAM, dataToInsert, where,
                null);
    }
    ////////////////////////////////////////////////////

    //////////////////////////////
    public void clearTable(String canID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_EXAM +" where candidate_login_id="+"'"+canID+"'");
    }

    //////////////////////////////
    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_EXAM);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////
    public void clearTableCandidateAdded(String canID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ADDED_CANDIDATE +" where candidate_id= "+"'"+canID+"'");
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////
    public void clearTableCandidateAdded() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ADDED_CANDIDATE);
    }
    ////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////
    public void clearTableResult(String canID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_RESULT +" where candidateLoginID= "+"'"+canID+"'");
    }
    /////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////
    public void clearTableFeedBack(String canID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_FEEDBACK +" where can_id= "+"'"+canID+"'");
    }
    ////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////

    public void clearImageTable(String canID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + CAN_IMAGE_TABLE +" where can_id= "+"'"+canID+"'");
    }

    public void clearVideoTable(String candidate_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_VIDEO +" where can_id= "+"'"+candidate_id+"'");
    }

    //////////////////////////////
    public boolean isEmpty() {
        String selectQuery = "SELECT  * FROM " + TABLE_EXAM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() == 0)
            return true;
        else
            return false;
    }
    //////////////////////////////

    /////////////////////////////////////////////////////////////
    public void saveResult(ResultModel resultModelArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(R_EXAM_ID, "" + resultModelArrayList.getExamID());
        values.put(R_BATCH_ID, "" + resultModelArrayList.getBatchID());
        values.put(R_CANDIDATE_ID, "" + resultModelArrayList.getCandidateID());
        values.put(R_CANDIDATE_NAME, "" + resultModelArrayList.getCandidateName());
        values.put(R_NOS, "" + resultModelArrayList.getNos());
        values.put(R_QUESTION_ID, "" + resultModelArrayList.getQuestionID());
        values.put(R_SHUFFLE, "" + resultModelArrayList.getShuffleOrder());
        values.put(R_CURRECT_ANSWER, "" + resultModelArrayList.getCorrectAnswer());
        values.put(R_QUESTION_MARKS, "" + resultModelArrayList.getQuestionMarks());
        values.put(R_ATTEMTED_ANSWER, "" + resultModelArrayList.getAttdemptedAnswer());
        values.put(R_ASS_ID, "" + resultModelArrayList.getAssID());
        values.put(R_QUESTION_REVIEW, "" + resultModelArrayList.getQuestionReview());
        values.put(R_CANDIDATE_LOGIN_ID, "" + resultModelArrayList.getCandidateLoginID());
        values.put(R_MARKS, "" + resultModelArrayList.getMarks());
        values.put(R_OBTAIN_MARKS, "" + resultModelArrayList.getObtainedMarks());
        values.put(R_PC, "" + resultModelArrayList.getPc());


        // Inserting Row
        db.insert(TABLE_RESULT, null, values);
        db.close(); // Closing database connection
    }
    //////////////////////////////////////////////////////////////

    /////////////////////get FeedBack////////////////////////////
    public ArrayList<ResultModel> getResult(String loginID) {
        ArrayList<ResultModel> resultModelArrayList = new ArrayList<ResultModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESULT + " where " + R_CANDIDATE_LOGIN_ID + "= '" + loginID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ResultModel resultModel = new ResultModel();
                resultModel.setExamID(cursor.getString(0));
                resultModel.setBatchID(cursor.getString(1));
                resultModel.setCandidateID(cursor.getString(2));
                resultModel.setCandidateName(cursor.getString(3));
                resultModel.setNos(cursor.getString(4));
                resultModel.setQuestionID(cursor.getString(5));
                resultModel.setShuffleOrder(cursor.getString(6));
                resultModel.setCorrectAnswer(cursor.getString(7));
                resultModel.setQuestionMarks(cursor.getString(8));
                resultModel.setAttdemptedAnswer(cursor.getString(9));
                resultModel.setAssID(cursor.getString(10));
                resultModel.setQuestionReview(cursor.getString(11));
                resultModel.setCandidateLoginID(cursor.getString(12));
                resultModel.setMarks(cursor.getString(13));
                resultModel.setObtainedMarks(cursor.getString(14));
                resultModel.setPc(cursor.getString(15));
                resultModelArrayList.add(resultModel);

            } while (cursor.moveToNext());

        }
        ////return contact list////
        return resultModelArrayList;
    }
    /////////////////////////////////////////////////////////////

    /////////////////////get FeedBack/////////////////
    public ArrayList<ResultModel> getAllResult() {
        ArrayList<ResultModel> resultModelArrayList = new ArrayList<ResultModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RESULT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ResultModel resultModel = new ResultModel();
                resultModel.setExamID(cursor.getString(0));
                resultModel.setBatchID(cursor.getString(1));
                resultModel.setCandidateID(cursor.getString(2));
                resultModel.setCandidateName(cursor.getString(3));
                resultModel.setNos(cursor.getString(4));
                resultModel.setQuestionID(cursor.getString(5));
                resultModel.setShuffleOrder(cursor.getString(6));
                resultModel.setCorrectAnswer(cursor.getString(7));
                resultModel.setQuestionMarks(cursor.getString(8));
                resultModel.setAttdemptedAnswer(cursor.getString(9));
                resultModel.setAssID(cursor.getString(10));
                resultModel.setQuestionReview(cursor.getString(11));
                resultModel.setCandidateLoginID(cursor.getString(12));
                resultModel.setMarks(cursor.getString(13));
                resultModel.setObtainedMarks(cursor.getString(14));
                resultModel.setPc(cursor.getString(15));
                resultModelArrayList.add(resultModel);

            } while (cursor.moveToNext());

        }
        ////return contact list////
        return resultModelArrayList;
    }
    //////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////
    public void addCandidates(ArrayList<AddedCandidatesModel> addedCandidatesModels) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < addedCandidatesModels.size(); i++) {

            values.put(CANDIDATE_ID, "" + addedCandidatesModels.get(i).getCandidateID());
            values.put(PASS_KEY, "" + addedCandidatesModels.get(i).getCandidatePassKey());
            values.put(AADHAR_ID, "" + addedCandidatesModels.get(i).getCandidateAadhar());
            values.put(CAN_STATUS, "" + addedCandidatesModels.get(i).getStatus());
            values.put(TIME_LEFT, "" + addedCandidatesModels.get(i).getTimeLeft());
            values.put(LOGIN_TIME, "" + addedCandidatesModels.get(i).getLoginTime());
            values.put(LOGOUT_TIME, "" + addedCandidatesModels.get(i).getLogoutTime());
            values.put(NO_ATTEMPT, "" + addedCandidatesModels.get(i).getLoginAttempt());
            values.put(MY_LOCKED, "" + addedCandidatesModels.get(i).getLocked());
            values.put(LAST_VISITED_Q, "" + addedCandidatesModels.get(i).getLastVistedQ());

            // Inserting Row
            db.insert(TABLE_ADDED_CANDIDATE, null, values);
        }
        db.close(); // Closing database connection
    }
    //////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////
    public int updateCanStatus(String canID, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = CANDIDATE_ID + "=" + "'" + canID + "'";

        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(CAN_STATUS, status);

        // updating row
        return db.update(TABLE_ADDED_CANDIDATE, dataToInsert, where,
                null);
    }
    //////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////
    public int updateQVisited(String canID, String visited) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = CANDIDATE_ID + "=" + "'" + canID + "'";

        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(LAST_VISITED_Q, visited);

        // updating row
        return db.update(TABLE_ADDED_CANDIDATE, dataToInsert, where,
                null);
    }
    /////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////
    public int updateCanTimeLeft(String canID, String timeLeft) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = CANDIDATE_ID + "=" + "'" + canID + "'";

        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(TIME_LEFT, timeLeft);

        // updating row
        return db.update(TABLE_ADDED_CANDIDATE, dataToInsert, where,
                null);
    }
    //////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////
    public int updateLogoutTime(String canID, String logoutTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = CANDIDATE_ID + "=" + "'" + canID + "'";

        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(LOGOUT_TIME, logoutTime);

        // updating row
        return db.update(TABLE_ADDED_CANDIDATE, dataToInsert, where,
                null);
    }
    ///////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////
    public int updateLockStatus(String canID, String lock) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = CANDIDATE_ID + "=" + "'" + canID + "'";

        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(MY_LOCKED, lock);

        // updating row
        return db.update(TABLE_ADDED_CANDIDATE, dataToInsert, where,
                null);
    }
    //////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////
    public int updateAttempts(String canID, String attempts) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = CANDIDATE_ID + "=" + "'" + canID + "'";

        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put(NO_ATTEMPT, attempts);

        // updating row
        return db.update(TABLE_ADDED_CANDIDATE, dataToInsert, where,
                null);
    }

    ///////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////

    public ArrayList<AddedCandidatesModel> getCandidates() {
        ArrayList<AddedCandidatesModel> addedCandidatesModels = new ArrayList<AddedCandidatesModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ADDED_CANDIDATE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddedCandidatesModel addedCandidatesModel = new AddedCandidatesModel();
                addedCandidatesModel.setCandidateID(cursor.getString(0));
                addedCandidatesModel.setCandidatePassKey(cursor.getString(1));
                addedCandidatesModel.setCandidateAadhar(cursor.getString(2));
                addedCandidatesModel.setStatus(cursor.getString(3));
                addedCandidatesModel.setTimeLeft(cursor.getString(4));
                addedCandidatesModel.setLoginTime(cursor.getString(5));
                addedCandidatesModel.setLogoutTime(cursor.getString(6));
                addedCandidatesModel.setLoginAttempt(cursor.getString(7));
                addedCandidatesModel.setLocked(cursor.getString(8));
                addedCandidatesModel.setLastVistedQ(cursor.getString(9));

                addedCandidatesModels.add(addedCandidatesModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return addedCandidatesModels;
    }
    //////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////
    public ArrayList<AddedCandidatesModel> getCandidatesByID(String canID) {
        ArrayList<AddedCandidatesModel> addedCandidatesModels = new ArrayList<AddedCandidatesModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ADDED_CANDIDATE + " where candidate_id= " + "'"+canID+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddedCandidatesModel addedCandidatesModel = new AddedCandidatesModel();
                addedCandidatesModel.setCandidateID(cursor.getString(0));
                addedCandidatesModel.setCandidatePassKey(cursor.getString(1));
                addedCandidatesModel.setCandidateAadhar(cursor.getString(2));
                addedCandidatesModel.setStatus(cursor.getString(3));
                addedCandidatesModel.setTimeLeft(cursor.getString(4));
                addedCandidatesModel.setLoginTime(cursor.getString(5));
                addedCandidatesModel.setLogoutTime(cursor.getString(6));
                addedCandidatesModel.setLoginAttempt(cursor.getString(7));
                addedCandidatesModel.setLocked(cursor.getString(8));
                addedCandidatesModel.setLastVistedQ(cursor.getString(9));
                addedCandidatesModels.add(addedCandidatesModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return addedCandidatesModels;
    }

    //////Getting All Contacts//////////
    public int getQuestionsSize() {
        ArrayList<QuestionModel> questationList = new ArrayList<QuestionModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXAM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionModel questionModel = new QuestionModel();
                questionModel.setqId(Integer.parseInt(cursor.getString(0)));
                questionModel.setQuestion(cursor.getString(1));
                questionModel.setOptionA(cursor.getString(2));
                questionModel.setOptionB(cursor.getString(3));
                questionModel.setOptionC(cursor.getString(4));
                questionModel.setOptionD(cursor.getString(5));
                questionModel.setQuestionStatus(cursor.getString(7));
                questionModel.setImage(cursor.getString(8));
                questionModel.setChecked(Integer.parseInt(cursor.getString(9)));
                questionModel.setOptionImage(cursor.getString(10));
                questionModel.setImage_path(cursor.getString(11));
                questionModel.setMarks(cursor.getString(12));
                questionModel.setExamduration(cursor.getString(13));
                questionModel.setSector(cursor.getString(14));
                questionModel.setJobrole(cursor.getString(15));
                questionModel.setExam_id(cursor.getString(16));
                questionModel.setBatch_id(cursor.getString(17));
                questionModel.setCid(cursor.getString(18));
                questionModel.setCname(cursor.getString(19));
                questionModel.setAssessor_id(cursor.getString(20));
                questionModel.setNos(cursor.getString(21));
                questionModel.setCorrect_ans(cursor.getString(22));
                questionModel.setCandidate_login_id(cursor.getString(23));
                questionModel.setPc(cursor.getString(24));
                questionModel.setPc_marks(cursor.getString(25));
                questionModel.setShuffle(cursor.getString(26));
                questionModel.setQuestionID(cursor.getString(27));


                questationList.add(questionModel);
            } while (cursor.moveToNext());

        }

        // return contact list
        return questationList.size();
    }
    ////////////////////////////////////

    //////////////////////////////////////////////////////////////


    public ArrayList<CandidateImages> getImage(String canID) {


        ArrayList<CandidateImages> candidateImages = new ArrayList<CandidateImages>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + CAN_IMAGE_TABLE + " where can_id= " + "'"+canID+"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CandidateImages addedCandidatesModel = new CandidateImages();
                addedCandidatesModel.setCanID(cursor.getString(0));
                addedCandidatesModel.setCanAadhar(cursor.getString(1));
                addedCandidatesModel.setCanImage(cursor.getString(2));
                addedCandidatesModel.setGetCanAadharImage(cursor.getString(3));
                addedCandidatesModel.setAadahrTime(cursor.getString(4));
                addedCandidatesModel.setAadahrDate(cursor.getString(5));
                addedCandidatesModel.setCanTime(cursor.getString(6));
                addedCandidatesModel.setCanDate(cursor.getString(7));

                candidateImages.add(addedCandidatesModel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return candidateImages;
    }

    ///////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////

    public void addImage(CandidateImages candidateImages, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(I_CAN_ID, "" + candidateImages.getCanID());
        values.put(I_AADHAR_ID, "" + candidateImages.getCanAadhar());
        values.put(I_CANDIDATE_IMAGE, "" + candidateImages.getCanImage());
        values.put(I_AADHAR_IMAGE, "" + candidateImages.getGetCanAadharImage());
        values.put(I_AADHAR_TIME, "" + candidateImages.getAadahrTime());
        values.put(I_AADHAR_DATE, "" + candidateImages.getAadahrDate());
        values.put(I_CAN_TIME, "" + candidateImages.getCanTime());
        values.put(I_CAN_DATE, "" + candidateImages.getCanDate());

        ////////////Inserting Row//////////////
        db.insert(CAN_IMAGE_TABLE, null, values);

        db.close();
    }



    //  for video functionality

   /* public ArrayList<VideoModel> getVideo(String candidate_id) {


        ArrayList<VideoModel> videoModels = new ArrayList<VideoModel>();
        ArrayList<VideoModel> videoModels1 = new ArrayList<VideoModel>();

        // Select All Query
        String selectQuery = "SELECT V_VIDEO FROM TABLE_VIDEO where can_id='"+ candidate_id+"';";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VideoModel addvideomodel = new VideoModel();
                addvideomodel.setCandidate_id(cursor.getString(0));
                addvideomodel.setBatch_id(cursor.getString(1));
                addvideomodel.setExam_id(cursor.getString(2));
                addvideomodel.setVideo(cursor.getString(3));


                videoModels1.add(addvideomodel);
            } while (cursor.moveToNext());
        }

        // return contact list
        return videoModels1;
    }*/

    ///////////////////////////////////////////////////////////////

    ////////////////////////////////extra/////////////////////////////////////////////
    public void addVidDb(String C_ID, String B_ID, String EX_ID, String V) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(V_BATCH_ID, "" + B_ID);
        values.put(V_EXAMID, "" + EX_ID);
        values.put(V_CANDIDATE_ID, "" + C_ID);
        values.put(V_VIDEO, "" + V);


        ////////////Inserting Row//////////////
        db.insert(TABLE_VIDEO, null, values);

        db.close();
    }

   ///////////////////////////////////end////////////////////////////////




    ///////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////
    public void saveImage(AllImageModel allImageModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(A_EXAM_ID, "" + allImageModel.getExamID());
        values.put(A_BATCH_NUMBER, "" + allImageModel.getBatchNumber());
        values.put(A_CAN_ID, "" + allImageModel.getCanID());
        values.put(A_IMAGE, "" + allImageModel.getImage());
        values.put(A_DATE, "" + allImageModel.getDate());
        values.put(A_TIME, "" + allImageModel.getTime());

        // Inserting Row
        db.insert(ALL_IMAGE_TABLE, null, values);

        db.close(); // Closing database connection
    }
    ////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////
    public ArrayList<AllImageModel> getImageByID(String canID) {
        ArrayList<AllImageModel> allImageModels = new ArrayList<AllImageModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + ALL_IMAGE_TABLE + " where all_can_id=" + canID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                AllImageModel allImageModel = new AllImageModel();
                allImageModel.setBatchNumber(cursor.getString(0));
                allImageModel.setCanID(cursor.getString(1));
                allImageModel.setImage(cursor.getString(2));
                allImageModel.setDate(cursor.getString(3));
                allImageModel.setTime(cursor.getString(4));
                allImageModels.add(allImageModel);

            } while (cursor.moveToNext());
        }

        // return contact list
        return allImageModels;
    }

//    public static DatabaseHandler getDatbaseHandlerInstance(MainActivity mainActivity) {
//        return null;
//    }
//    ////////////////////////////////////////////////////////////////


}