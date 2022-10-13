package com.akash.currencyconverter.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balance_table")
data class BalanceEntity(
    val currency_name:String,
    val currency_code:String,
    val balance:Float
){
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}
