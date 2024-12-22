package com.example.aktivist

import android.app.usage.UsageEvents
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultsAdapter(
    private val events: List<UsageEvents.Event>) :
    RecyclerView.Adapter<SearchResultsAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val eventName: TextView = itemView.findViewById(R.id.tvEventName)
        //val eventDate: TextView = itemView.findViewById(R.id.tvEventDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        //holder.eventName.text = event.name
        //holder.eventDate.text = event.date
    }

    override fun getItemCount() = events.size
}
