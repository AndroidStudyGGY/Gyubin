package com.gyub.implementationtestexample.xml

import androidx.recyclerview.widget.RecyclerView
import com.gyub.implementationtestexample.databinding.AdapterIssueBinding

/**
 *
 *
 * @author   Gyub
 * @created  2024/04/16
 */
class IssueViewHolder(private val binding: AdapterIssueBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Issue) {
        binding.run {
            textNum.text = "#${item.num}"
            textTitle.text = item.title
        }
    }
}