package com.akash.currencyconverter.data.database.dao

import androidx.room.*

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj:T):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(obj:List<T>):List<Long>


    @Update
    abstract fun update(obj:T)

    @Update
    abstract fun update(obj:List<T>)

    @Delete
    abstract fun delete(obj:T):Int

    @Delete
    abstract fun delete(obj:List<T>)


    @Transaction
    open fun upsert(obj:T){
        val row = insert(obj)
        if(row == -1L){
            update(obj)
        }
    }

    @Transaction
    open fun upsert(obj:List<T>){
        val rows = insert(obj)
        val updateList = arrayListOf<T>()
        for(i in rows.indices){
            if(rows[i] == -1L){
                updateList += obj[i]
            }
        }

        if(updateList.isNotEmpty()){
            update(updateList)
        }
    }

}