package com.example.smartmedicapp.dataLayer

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ServisTicketDatabaseDao {

    @Insert
    suspend fun insert(servisTicket: ServisTicket)

    @Update
    suspend fun update(servisTicket: ServisTicket)

    @Query("SELECT * from servis_ticket_table WHERE ticketId = :key")
    suspend fun get(key: Long): ServisTicket?

    @Query("DELETE FROM servis_ticket_table")
    suspend fun clear()

    @Query("SELECT * FROM servis_ticket_table ORDER BY ticketId ASC")
    suspend fun getLastTicket(): ServisTicket?

    @Query("SELECT * FROM servis_ticket_table WHERE user_id = :key ORDER BY ticketId ASC")
    fun getAllTickets(key: String): LiveData<List<ServisTicket>>
}