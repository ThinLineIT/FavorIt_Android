package com.thinlineit.favorit_android.android.ui.present.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.thinlineit.favorit_android.android.data.entity.Present
import com.thinlineit.favorit_android.android.databinding.ItemPresentListBinding
import com.thinlineit.favorit_android.android.di.GlideApp
import com.thinlineit.favorit_android.android.util.BindingRecyclerViewAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresentAdapter(
    private val onPresentClicked: (Present) -> Unit
) : RecyclerView.Adapter<PresentAdapter.PresentViewHolder>(),
    BindingRecyclerViewAdapter<List<Present>> {

    private var presentList: List<Present> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresentViewHolder {
        return PresentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PresentViewHolder, position: Int) {
        holder.bind(position, presentList[position], onPresentClicked)
    }

    override fun getItemCount(): Int = presentList.size

    class PresentViewHolder(
        private val dataBinding: ItemPresentListBinding
    ) : RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(position: Int, present: Present, onPresentClicked: (Present) -> Unit) {
            CoroutineScope(Dispatchers.Main).launch {
                GlideApp.with(dataBinding.photo.context)
                    .load(present.photo)
                    .centerCrop()
                    .into(dataBinding.photo)
            }
            dataBinding.supporterName.text = present.supporterNickName
            dataBinding.root.setOnClickListener {
                onPresentClicked(present)
            }
            dataBinding.root.rotation = when (position % 3) {
                0 -> 5f
                1 -> -5f
                else -> 5f
            }
        }

        companion object {
            fun from(parent: ViewGroup): PresentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val dataBinding = ItemPresentListBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                ).apply {
                    lifecycleOwner = parent.findViewTreeLifecycleOwner()
                }
                return PresentViewHolder(dataBinding)
            }
        }
    }

    override fun setData(data: List<Present>) {
        presentList = data
        notifyItemRangeChanged(0, 9)
    }

    companion object {
        const val MAX_PRESENT_COUNT_EACH_PAGE = 9
    }
}
