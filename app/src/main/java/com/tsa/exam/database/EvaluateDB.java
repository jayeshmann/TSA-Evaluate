package com.tsa.exam.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.tsa.exam.DAO.NosDAO;
import com.tsa.exam.model.NOSPracticalModel;

/**
 * Created by RajkumarMann on 15-02-2018.
 */

@Database(entities = { NOSPracticalModel.class }, version = 2,exportSchema = false)
public abstract class EvaluateDB extends RoomDatabase {

    private static final String DB_NAME = "evaluatedb.db";

    private static volatile EvaluateDB instance;

    public static synchronized EvaluateDB getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static EvaluateDB create(final Context context) {
        return Room.databaseBuilder(
                context,
                EvaluateDB.class,
                DB_NAME).build();
    }

    public abstract NosDAO getNosDao();
}
