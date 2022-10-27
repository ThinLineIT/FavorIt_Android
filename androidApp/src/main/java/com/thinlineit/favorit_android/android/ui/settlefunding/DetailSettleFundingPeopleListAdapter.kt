package com.thinlineit.favorit_android.android.ui.settlefunding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thinlineit.favorit_android.android.R
import com.thinlineit.favorit_android.android.data.entity.Present

class DetailSettleFundingPeopleListAdapter(private val presentList: ArrayList<Present>) :
    RecyclerView.Adapter<DetailSettleFundingPeopleListAdapter.PeopleInfoHolder>() {

    inner class PeopleInfoHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindInfo(presentData: Present) {
            when (layoutPosition % 3) {
                0 -> view.rotation = 0f
                1 -> {
                    view.rotation = -10f
                    view.y = view.y + 60
                }
                else -> {
                    view.rotation = 10f
                    view.y = view.y - 40
                }
            }
            view.findViewById<TextView>(R.id.peopleNameTextView).text =
                presentData.supporterNickName
            //view.findViewById<ImageView>(R.id.peopleImageView).setImageURI(presentData.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleInfoHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_funding_settle_people, parent, false)

        return PeopleInfoHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleInfoHolder, position: Int) {
        holder.apply {
            bindInfo(presentList[position])
        }
    }

    override fun getItemCount(): Int = presentList.size

}