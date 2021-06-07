package com.example.smartmedicapp.dataLayer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ServisTicket::class , DeviceDetailsTemp::class], version =3, exportSchema = false)
abstract class ServisDatabase : RoomDatabase() {

    abstract val servisDatabaseDao: ServisTicketDatabaseDao

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
                        "servis_ticket_database"
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