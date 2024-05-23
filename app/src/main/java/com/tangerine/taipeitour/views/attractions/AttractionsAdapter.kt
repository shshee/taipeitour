/**
 * Copyright (C) 2020 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tangerine.taipeitour.views.attractions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tangerine.taipeitour.R
import com.tangerine.taipeitour.databinding.ItemAttractionBinding
import com.tangerine.taipeitour.models.Attraction
import com.tangerine.taipeitour.utils.fromHtml
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class AttractionsAdapter : RecyclerView.Adapter<AttractionsAdapter.ViewHolder>() {

    internal var collection: List<Attraction> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (Int) -> Unit = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAttractionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(position)
    }

    override fun getItemCount() = collection.size

    inner class ViewHolder(private val binding: ItemAttractionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(index: Int) {
            val item = collection[index]

            binding.textViewTitle.text = item.name.fromHtml()
            binding.textViewDescription.text = item.introduction.fromHtml()

            Glide.with(binding.root.context)
                .load(item.images.firstOrNull()?.src ?: R.mipmap.ic_launcher)
                .into(binding.imgViewAttraction)

            itemView.setOnClickListener {
                clickListener.invoke(index)
            }
        }
    }
}
