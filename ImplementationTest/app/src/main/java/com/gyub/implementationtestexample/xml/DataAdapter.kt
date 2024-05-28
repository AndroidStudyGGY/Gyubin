package com.gyub.implementationtestexample.xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.gyub.implementationtestexample.R
import com.gyub.implementationtestexample.databinding.AdapterBannerBinding
import com.gyub.implementationtestexample.databinding.AdapterIssueBinding

/**
 *
 *
 * @author   Gyub
 * @created  2024/04/16
 */
class DataAdapter : ListAdapter<Data, ViewHolder>(DataDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Data.IssueData -> R.layout.adapter_issue
            is Data.BannerData -> R.layout.adapter_banner
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.adapter_issue -> {
                IssueViewHolder(
                    AdapterIssueBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            R.layout.adapter_banner -> {
                BannerViewHolder(
                    AdapterBannerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> throw IllegalAccessException()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val item = currentList[position]) {
            is Data.IssueData -> {
                (holder as IssueViewHolder).bind(item.data)
            }

            is Data.BannerData -> {
                (holder as BannerViewHolder).bind(item.data)
            }
        }
    }

    class DataDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return when (oldItem) {
                is Data.IssueData -> newItem is Data.IssueData && oldItem.data.id == newItem.data.id
                is Data.BannerData -> newItem is Data.BannerData && oldItem.data == newItem.data
            }
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}
