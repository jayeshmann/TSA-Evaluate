package com.tsa.exam.Utill;

import com.tsa.exam.model.AssessorsLoginDetails;
import com.tsa.exam.model.PracResultModel;
import com.tsa.exam.model.PracticalQuestionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akhil Tripathi on 4/15/2017.
 */

public class GLOBAL {

    public static String candidatekey;
    public static int NoOfQuestions=0;
    public static String examID;
    public static String BASE_URL= "http://tsassessors.in/ISDAT/evaluate_app/assessor_api/";
    public static ArrayList<AssessorsLoginDetails> assessorLoginDetailsArrayList;
    public static String loginID="0";
    public static String batchID="0";
    public static long startTime=0l;
    public static PracResultModel pracResultModel=new PracResultModel();
    public static ArrayList<PracticalQuestionModel> practicalQuestionModelArrayList=new ArrayList<>();
    public static List<String> obtainedParcMark=new ArrayList<>();


    public static boolean cameraFlag=true;




    public static String assessorID="Assessor ID";
    public static String batchNumber="";
    public static String assPassword;
    public static String assname;
    public static String examStartDate="";
    public static String candidateName="";

}
