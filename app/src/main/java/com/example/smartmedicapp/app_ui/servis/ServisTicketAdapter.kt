package com.example.smartmedicapp.app_ui.servis

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmedicapp.R
import com.example.smartmedicapp.dataLayer.ServisTicket
import com.example.smartmedicapp.databinding.ListItemServisTicketBinding
/**
 * a Simple adapter for List of service tickets
 *
 */

class ServisTicketAdapter: RecyclerView.Adapter<ServisTicketAdapter.ViewHolder>() {

    // create dataset
    var data =  listOf<ServisTicket>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(
        parent: ViewGroup , viewType: Int): ViewHolder {

        val layoutInflater =
            LayoutInflater.from(parent.context)

        val binding =
            ListItemServisTicketBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }


    override fun getItemCount() = data.size

    /**
     * Viewholder for single items in list useful for binding properties
     *
     */
    class ViewHolder constructor(val binding: ListItemServisTicketBinding) : RecyclerView.ViewHolder(binding.root){

        val ticketName: TextView = binding.ticketName
        val ticketProblem: TextView = binding.ticketProblem
        val ticketState: TextView = binding.ticketState
        val ticketStateImage: ImageView = binding.imageTicketState
        val ticketDeviceImage: ImageView = binding.imageDevice

        fun bind(item: ServisTicket) {
            val res = itemView.context.resources
            ticketName.text = item.device_brand.plus( " ") .plus( item.device_model)
            ticketProblem.text = item.problem
            ticketState.text = when (item.ticket_state) {
                0 -> "Odoslané" // odoslane
                1 -> "Potvrdené, čaká na prebratie"// potvrdene caka na prebratie
                2 -> "Zariadenie sa servisuje"// Zariadenie sa servisuje
                3 -> "Zariadenie opravené"  // opravene caka na odovzdanie
                4 -> "Servis ukončený" // ukoncene

                else ->  res.getString(R.string.ticket_state_unkown)
            }
            // set image based on ticket state
            ticketStateImage.setImageResource(
                when (item.ticket_state) {
                    0 -> R.mipmap.local_shipping_svg  // odoslane
                    1 -> R.mipmap.timer_glass_orange_svg // potvrdene caka na prebratie
                    2 -> R.drawable.timer_red_svg // Zariadenie sa servisuje
                    3 -> R.mipmap.done_orange_svg  // opravene caka na odovzdanie
                    else -> R.mipmap.done_all_green_svg // ukoncene
                }
            )
            // set ticket image based on device type
            ticketDeviceImage.setImageResource(
                when (item.device_type) {
                    1 -> R.mipmap.phone_image
                    2 -> R.mipmap.pc_image
                    3 -> R.mipmap.console_image
                    else -> R.mipmap.phone_image
                }
            )

    }
    }
}
