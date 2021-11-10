package com.example.paringjsonfile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paringjsonfile.FavoriteActivity
import com.example.paringjsonfile.MainActivity
import com.example.paringjsonfile.database.EntityImage
import com.example.paringjsonfile.databinding.FavRowBinding

class FavImagesAdapter(
    private val activity: FavoriteActivity,
    private val imges: List<EntityImage>
) : RecyclerView.Adapter<FavImagesAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: FavRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavImagesAdapter.ItemViewHolder {
        return ItemViewHolder(
            FavRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavImagesAdapter.ItemViewHolder, position: Int) {
        val img = imges[position]

        holder.binding.apply {

            Glide.with(activity).load(img.url).into(imageView)
            ibDeleteNote.setOnClickListener {
                activity.checkDeleteDialog(img.id)

            }
        }

    }

    override fun getItemCount() = imges.size
}