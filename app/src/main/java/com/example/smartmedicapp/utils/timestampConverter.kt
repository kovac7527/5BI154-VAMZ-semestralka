package com.example.smartmedicapp.utils

import java.sql.Date
import java.sql.Timestamp

class timestampConverter {

    private fun getDateTime(timeInMilisec: Long): Date? {
        val stamp = Timestamp(timeInMilisec)
        return Date(stamp.getTime())
    }
}