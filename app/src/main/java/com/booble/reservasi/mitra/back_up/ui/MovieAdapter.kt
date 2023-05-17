package com.booble.reservasi.mitra.back_up.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.booble.reservasi.mitra.R
import com.booble.reservasi.mitra.databinding.RowItemMovieBinding
import com.booble.reservasi.mitra.utils.UtilConstants.BASE_IMAGE_URL

/**
 * Created by rivaldy on 09/07/21
 * Find me on my Github -> https://github.com/im-o
 **/

class MovieAdapter(
    private val listener: (com.booble.reservasi.mitra.data.model.offline.MovieLocaleData) -> Unit
) : ListAdapter<com.booble.reservasi.mitra.data.model.offline.MovieLocaleData, MovieAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: RowItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: com.booble.reservasi.mitra.data.model.offline.MovieLocaleData) {
            Glide.with(binding.root.context)
                .load(BASE_IMAGE_URL + item.posterPath)
                .placeholder(R.color.colorDividerHigh)
                .into(binding.posterIV)
            binding.movieNameTV.text = item.title
            binding.movieDescTV.text = item.description
            binding.root.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<com.booble.reservasi.mitra.data.model.offline.MovieLocaleData>() {
            override fun areItemsTheSame(oldItem: com.booble.reservasi.mitra.data.model.offline.MovieLocaleData, newItem: com.booble.reservasi.mitra.data.model.offline.MovieLocaleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: com.booble.reservasi.mitra.data.model.offline.MovieLocaleData, newItem: com.booble.reservasi.mitra.data.model.offline.MovieLocaleData): Boolean {
                return oldItem == newItem
            }
        }
    }
}