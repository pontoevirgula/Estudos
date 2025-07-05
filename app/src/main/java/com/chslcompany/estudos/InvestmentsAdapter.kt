package com.chslcompany.estudos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView


class InvestmentsAdapter(private var list: List<InvestmentsStatus>) :
    RecyclerView.Adapter<InvestmentsAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<InvestmentsStatus>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: InvestmentsStatus) {
            val bank : AppCompatTextView = itemView.findViewById(R.id.test23)
            val portability : AppCompatTextView = itemView.findViewById(R.id.portabilityNumber)
            val idNumber : AppCompatTextView = itemView.findViewById(R.id.idNumber)
            bank.text = item.bankName
            portability.text = item.portabilityNumber
            idNumber.text = item.id.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_investment_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}

