package com.akash.currencyconverter.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.akash.currencyconverter.data.database.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class BalanceDao: BaseDao<BalanceEntity>() {

    @Query("select * from balance_table")
    abstract fun getAllBalance(): Flow<List<BalanceEntity>>

    @Query("select * from balance_table where id = :id")
    abstract fun getBalanceById(id: String): Flow<BalanceEntity>



    @Query(
        "select * from balance_table where currency_code like :query"
    )
    abstract fun getBalanceByCurrencyCode(query: String): Flow<List<BalanceEntity>>

}