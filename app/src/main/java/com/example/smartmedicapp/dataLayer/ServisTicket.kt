package com.example.smartmedicapp.dataLayer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "servis_ticket_table")
data class ServisTicket(

    @PrimaryKey(autoGenerate = true)
    var ticketId: Long = 0L,

    @ColumnInfo(name = "user_id")
    val user_id : String?,

    @ColumnInfo(name = "device_brand")
    val device_brand : String?,

    @ColumnInfo(name = "device_model")
    val device_model : String?,

    @ColumnInfo(name = "device_type")
    val device_type : String?,

    @ColumnInfo(name = "problem")
    val problem : String?,

    @ColumnInfo(name = "ticket_note")
    val ticket_note : String?,

    @ColumnInfo(name = "ticket_state")
    val ticket_state : Int = 0,

    @ColumnInfo(name = "time_stamp")
    val time_stamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "ticket_finished")
    val ticket_finished : Boolean?,

    @ColumnInfo(name = "ticket_price")
    val ticket_price : Int = 0,

)