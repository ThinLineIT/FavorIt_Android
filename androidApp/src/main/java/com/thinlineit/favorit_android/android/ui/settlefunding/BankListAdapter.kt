package com.thinlineit.favorit_android.android.ui.settlefunding

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thinlineit.favorit_android.android.data.entity.Bank
import com.thinlineit.favorit_android.android.databinding.ListItemBankBinding
import com.thinlineit.favorit_android.android.util.BindingRecyclerViewAdapter

class BankListAdapter(private val clickListener: (Bank) -> Unit) :
    RecyclerView.Adapter<BankListAdapter.BankListViewHolder>(),
    BindingRecyclerViewAdapter<List<Bank>> {
    private var bankList = emptyList<Bank>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankListViewHolder =
        BankListViewHolder.from(parent)

    override fun onBindViewHolder(holder: BankListViewHolder, position: Int) {
        val bank = bankList[position]
        holder.bind(bank, clickListener)
    }

    override fun getItemCount(): Int = bankList.size

    class BankListViewHolder(private val dataBinding: ListItemBankBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(
            bank: Bank,
            clickListener: (Bank) -> Unit
        ) {
            dataBinding.apply {
                this.bank = bank
                root.setOnClickListener {
                    clickListener(bank)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): BankListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val dataBinding = ListItemBankBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return BankListViewHolder(dataBinding)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setData(data: List<Bank>) {
        bankList = data
        notifyDataSetChanged()
    }

}