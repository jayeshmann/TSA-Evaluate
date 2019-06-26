package com.tsa.exam.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tsa.exam.model.NOSPracticalModel;

import java.util.List;

/**
 * Created by Akhil Tripathi on 15-02-2018.
 */
@Dao
public interface NosDAO {

    @Query("SELECT * FROM nospracticalmodel")
    List<NOSPracticalModel> getAllQuestions();

    @Query("SELECT * from nospracticalmodel where mCandidateLoginId = :id")
    public List<NOSPracticalModel> loadUserById(String id);

    @Insert
    void insertNosData(NOSPracticalModel... nosPracticalModels);

    @Update
    void updateNosData(NOSPracticalModel... nosPracticalModels);

    @Delete
    void deleteNosData(NOSPracticalModel... nosPracticalModels);

    @Query("SELECT * FROM nospracticalmodel WHERE mQid = :qID AND mCandidateLoginId = :id")
    public List<NOSPracticalModel> getOptMarks(String qID,String id);

    @Query("SELECT * FROM nospracticalmodel WHERE mBatchId = :batchID GROUP BY mCandidateLoginId ORDER BY mCandidateName")
    public List<NOSPracticalModel> getCanByBatch(String batchID);

    @Query("SELECT * FROM nospracticalmodel " + "GROUP BY mCandidateLoginId")
    List<NOSPracticalModel> getCanList();

    @Query("SELECT * FROM nospracticalmodel  WHERE examStatus = :status GROUP BY mCandidateLoginId")
    List<NOSPracticalModel> getSubmitedCanList(String status);

    @Query("SELECT * FROM nospracticalmodel " + "GROUP BY mBatchId")
    List<NOSPracticalModel> getBatchList();


    /*@Query("DELETE FROM nospracticalmodel")
    public void clearTable();*/
}
