package com.example.smartmedicapp.dataLayer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ServisTicketDatabaseDao {

    @Insert
    suspend fun insertServisTicket(servisTicket: ServisTicket)

    @Update
    suspend fun update(servisTicket: ServisTicket)

    @Query("SELECT * from servis_ticket_table WHERE ticketId = :key")
    suspend fun get(key: Long): ServisTicket?

    @Query("DELETE FROM servis_ticket_table")
    suspend fun clearServisTickets()

    @Query("DELETE FROM device_details_temp")
    suspend fun clearTemDevDetails()

    @Query("SELECT * FROM servis_ticket_table ORDER BY ticketId DESC LIMIT 1")
    suspend fun getLastTicket(): ServisTicket?

    @Query("SELECT * FROM servis_ticket_table WHERE user_id = :key ORDER BY ticketId ASC")
    suspend fun getAllTickets(key: String): List<ServisTicket>

    @Query("SELECT * FROM servis_ticket_table WHERE user_id = :key AND device_type =:type ORDER BY ticketId ASC")
    suspend fun getAllTicketsByType(key: String, type: Int): List<ServisTicket>

    @Query("SELECT * FROM servis_ticket_table WHERE user_id = :key AND ticket_state = 4 ORDER BY ticketId ASC")
    suspend fun getAllDoneTickets(key: String): List<ServisTicket>


    @Query("SELECT * from device_details_temp WHERE detailsId = :key")
    suspend fun getTempDeviceDetails(key: Long): DeviceDetailsTemp?

    @Query("SELECT * FROM device_details_temp ORDER BY detailsId DESC LIMIT 1")
    suspend fun getLastDevDetails(): DeviceDetailsTemp?

    @Insert
    suspend fun insertDeviceDetails(devDetails: DeviceDetailsTemp)





}