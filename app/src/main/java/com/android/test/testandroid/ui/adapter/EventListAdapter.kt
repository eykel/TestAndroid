package com.android.test.testandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.test.testandroid.R
import com.android.test.testandroid.databinding.EventItemBinding
import com.android.test.testandroid.domain.model.Event
import com.android.test.testandroid.ui.adapter.EventListAdapter.EventListViewHolder
import com.android.test.testandroid.ui.loadImageUrl

typealias onEventClick = ((item: Event) -> Unit)?
class EventListAdapter(
    private val onEventClick: onEventClick
) : ListAdapter<Event, EventListViewHolder>(eventDiffUtil) {


    inner class EventListViewHolder(private val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.eventImage.loadImageUrl(event.image)
            binding.eventName.text = event.title
            binding.eventDate.text = binding.root.context.getString(R.string.tv_event_date, event.date)
            binding.eventPrice.text = binding.root.context.getString(R.string.tv_event_price, event.price)
            binding.eventItem.setOnClickListener {
                onEventClick?.invoke(event)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder =
        EventListViewHolder(
            EventItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

internal val eventDiffUtil = object : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean  =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
        oldItem.id == newItem.id &&
                oldItem.description == newItem.description &&
                oldItem.title == newItem.description &&
                oldItem.date == newItem.date &&
                oldItem.price == newItem.price &&
                oldItem.latitude == newItem.latitude &&
                oldItem.longitude == newItem.longitude &&
                oldItem.people == newItem.people &&
                oldItem.image == newItem.image
}