package com.example.paringjsonfile.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paringjsonfile.FavoriteActivity
import com.example.paringjsonfile.Image
import com.example.paringjsonfile.MainActivity
import com.example.paringjsonfile.R
import com.example.paringjsonfile.databinding.ItemRowBinding


class RVAdapter (val activity: MainActivity, val imges: ArrayList<Image>): RecyclerView.Adapter<RVAdapter.ItemeHplder>(){
    class ItemeHplder (val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemeHplder {
        return ItemeHplder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ItemeHplder, position: Int) {
        val img = imges[position]

        holder.binding.apply {
            tvImageText.text = img.author
            Glide.with(activity).load(img.url).into(ivThumbnail)
            llItemRow.setOnClickListener { activity.openImg(img.url) }

            ibFavImage.setOnClickListener {
                activity.addToFavorite(img.author,img.url)
            }

            }
        }


    override fun getItemCount() = imges.size
}