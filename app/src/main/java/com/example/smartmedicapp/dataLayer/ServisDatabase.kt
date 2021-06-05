package com.example.smartmedicapp.dataLayer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ServisTicket::class], version = 1, exportSchema = false)
abstract class ServisDatabase : RoomDatabase() {

    abstract val servisDatabase: ServisTicketDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: ServisDatabase? = null

        fun getInstance(context: Context): ServisDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ServisDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}