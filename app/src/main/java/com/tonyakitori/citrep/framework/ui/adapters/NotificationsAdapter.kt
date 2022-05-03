package com.tonyakitori.citrep.framework.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tonyakitori.citrep.databinding.ItemNotificationSourceBinding
import com.tonyakitori.citrep.domain.entities.NotificationEntity
import com.tonyakitori.citrep.framework.utils.getFormattedDate

class DiffNotifications() : DiffUtil.ItemCallback<NotificationEntity>() {
    override fun areItemsTheSame(
        oldItem: NotificationEntity,
        newItem: NotificationEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: NotificationEntity,
        newItem: NotificationEntity
    ): Boolean {
        return oldItem == newItem
    }

}

class NotificationsAdapter(private val callback: () -> Unit) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>() {

    private val notifications: AsyncListDiffer<NotificationEntity> =
        AsyncListDiffer(this, DiffNotifications())

    fun addNotifications(list: List<NotificationEntity>) {
        notifications.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val binding = ItemNotificationSourceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NotificationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val item = notifications.currentList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return notifications.currentList.size
    }

    inner class NotificationsViewHolder(private val binding: ItemNotificationSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NotificationEntity) = with(binding) {

            title.text = item.title
            message.text = item.message
            date.text = item.date.getFormattedDate()

            mainContainer.setOnClickListener {
                callback()
            }

        }

    }

}