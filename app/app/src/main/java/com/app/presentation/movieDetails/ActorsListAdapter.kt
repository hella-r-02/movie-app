package com.app.presentation.movieDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.R
import com.app.domain.model.Actor
import com.bumptech.glide.Glide

class ActorsListAdapter : ListAdapter<Actor, ActorsListAdapter.DataViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_actor, parent, false)
        return DataViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val avatar: ImageView = itemView.findViewById(R.id.iv_cast)
        private val name: TextView = itemView.findViewById(R.id.tv_cast_name)

        fun onBind(actor: Actor) {
            Glide.with(context)
                .asBitmap()
                .load(actor.imageUrl)
                .into(avatar)
            name.text = actor.name
        }

        private val RecyclerView.ViewHolder.context
            get() = this.itemView.context
    }

    class DiffCallback : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
            return oldItem == newItem
        }
    }
}

