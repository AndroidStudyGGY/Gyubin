package com.gyub.implementationtestexample.xml

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.gyub.implementationtestexample.databinding.AdapterBannerBinding

/**
 *
 *
 * @author   Gyub
 * @created  2024/04/16
 */
class BannerViewHolder(private val binding: AdapterBannerBinding) : ViewHolder(binding.root) {

    fun bind(item: Banner) {
        Glide.with(binding.imgBanner.context)
            .load(item.url)
            .into(binding.imgBanner)
    }
}