package com.waleed.asteroidradarapp.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waleed.asteroidradarapp.Asteroid
import com.waleed.asteroidradarapp.databinding.AsteroidListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragmentAdapter(
    private val onClickListener: AdapterClickListener
) :
    ListAdapter<Asteroid, MainFragmentAdapter.AsteroidViewHolder>(DiffCallback) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AsteroidListItemBinding.inflate(layoutInflater, parent, false)
        return AsteroidViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(asteroid)
        }
        holder.bind(asteroid)


    }


    fun setAsteroidsList(list: List<Asteroid>?) {
        adapterScope.launch {
            withContext(Dispatchers.Main) {
                submitList(list)
            }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class AsteroidViewHolder(private var binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }

    }

    class AdapterClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }

}