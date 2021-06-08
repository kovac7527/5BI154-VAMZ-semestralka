package com.example.smartmedicapp.dataLayer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Entity to hold temporary data about device details on the service ticket
 */

@Entity(tableName = "device_details_temp")
data class DeviceDetailsTemp(


    @ColumnInfo(name = "device_brand")
    val device_brand : String?,

    @ColumnInfo(name = "device_model")
    val device_model : String?,

    @ColumnInfo(name = "device_type")
    val device_type : Int = 1,

    @ColumnInfo(name = "problem")
    val problem : String?,

    @ColumnInfo(name = "ticket_note")
    val ticket_note : String?,

    @PrimaryKey(autoGenerate = true)
    var detailsId: Long = 0L

)