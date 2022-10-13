package com.akash.currencyconverter.data.database.databases

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.akash.currencyconverter.BALANCE_DATA_FILENAME
import com.akash.currencyconverter.data.database.dao.BalanceDao
import com.akash.currencyconverter.data.database.entity.BalanceEntity
import com.akash.currencyconverter.data.database.worker.PrepopulateDbWorker
import com.akash.currencyconverter.data.database.worker.PrepopulateDbWorker.Companion.KEY_FILENAME


@Database(
    entities = [
        BalanceEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase:RoomDatabase() {

    abstract val balanceDao:BalanceDao

    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "balance_database"
                    )
                        .addCallback(DatabaseCreationCallback(context))// if migrate the data will be lost . need to implement differently
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

        private class DatabaseCreationCallback(val context: Context): RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val prepopulateRequest = OneTimeWorkRequestBuilder<PrepopulateDbWorker>()
                    .setInputData(workDataOf(KEY_FILENAME to BALANCE_DATA_FILENAME))
                    .build();
                WorkManager.getInstance(context).enqueue(prepopulateRequest)
            }
        }
    }


}