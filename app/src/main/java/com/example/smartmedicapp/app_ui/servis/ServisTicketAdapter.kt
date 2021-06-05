package com.example.smartmedicapp.app_ui.servis

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmedicapp.R
import com.example.smartmedicapp.dataLayer.ServisTicket
import com.example.smartmedicapp.databinding.ListItemServisTicketBinding


class ServisTicketAdapter: RecyclerView.Adapter<ServisTicketAdapter.ViewHolder>() {


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


    class ViewHolder constructor(val binding: ListItemServisTicketBinding) : RecyclerView.ViewHolder(binding.root){

        val ticketName: TextView = binding.ticketName
        val ticketProblem: TextView = binding.ticketProblem
        val ticketState: TextView = binding.ticketState
        val ticketStateImage: ImageView = binding.imageTicketState

        fun bind(item: ServisTicket) {
            val res = itemView.context.resources
            ticketName.text = item.device_brand.plus( " ") .plus( item.device_model)
            ticketProblem.text = item.problem
            ticketState.text = when (item.ticket_state) {
                0 -> res.getString(R.string.ticket_state_sent)
                1 -> res.getString(R.string.ticket_state_in_progress)
                2 -> res.getString(R.string.ticket_state_servis_done)
                3 -> res.getString(R.string.ticket_state_finished)

                else ->  res.getString(R.string.ticket_state_unkown)
            }
            ticketStateImage.setImageResource(
                when (item.ticket_state) {
                    0 -> R.mipmap.local_shipping_svg
                    1 -> R.mipmap.timer_glass_orange_svg
                    2 -> R.mipmap.done_orange_svg
                    2 -> R.mipmap.done_all_green_svg
                    else -> R.drawable.timer_red_svg
                }
            )

    }
    }
}
