package com.chslcompany.estudos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.estudos.R
import com.chslcompany.estudos.adapters.SeeMoreAdapter.Row.Companion.TYPE_FUNDS
import com.chslcompany.estudos.adapters.SeeMoreAdapter.Row.Companion.TYPE_RENDA
import com.chslcompany.estudos.adapters.SeeMoreAdapter.SeeMoreViewHolder
import com.chslcompany.estudos.model.ActiveProduct
import com.chslcompany.estudos.model.FundoActive
import com.chslcompany.estudos.model.RendaFixaActive

class SeeMoreAdapter
    : ListAdapter<SeeMoreAdapter.Row, SeeMoreViewHolder>(Diff) {

    var onEditCallback: ((Row) -> Unit)? = null
    var onDeleteCallback: ((Row) -> Unit)? = null

    sealed interface Row {
        val id: String
        val viewType: Int

        companion object {
            const val TYPE_RENDA = 0
            const val TYPE_FUNDS = 1
        }
    }

    data class RendaRow(val data: RendaFixaActive) : Row {
        override val id: String = data.id
        override val viewType: Int = TYPE_RENDA
    }

    data class FundsRow(val data: FundoActive) : Row {
        override val id: String = data.id
        override val viewType: Int = TYPE_FUNDS
    }

    object Diff : DiffUtil.ItemCallback<Row>() {
        override fun areItemsTheSame(oldItem: Row, newItem: Row): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Row, newItem: Row): Boolean {
            return oldItem == newItem
        }
    }


    fun submitInvestments(source: List<ActiveProduct>) {
        val rows = buildList {
            source.forEach { inv ->
                inv.rendaFixaList.forEach { add(RendaRow(it)) }
                inv.fundoList.forEach { add(FundsRow(it)) }
            }
        }
        submitList(rows)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeMoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_see_more_adapter, parent, false)
        return SeeMoreViewHolder(view, onEditCallback, onDeleteCallback)
    }

    override fun onBindViewHolder(
        holder: SeeMoreViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class SeeMoreViewHolder(
        itemView: View,
        private val onEdit: ((Row) -> Unit)?,
        private val onDelete: ((Row) -> Unit)?
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(row: Row) {
            when (row) {
                is RendaRow -> {
                    itemView.findViewById<TextView>(R.id.tvName).text = "Nome da Renda Fixa"
                    itemView.findViewById<TextView>(R.id.tvNameValue).text = row.data.name
                    itemView.findViewById<TextView>(R.id.tvSubtitle).text = "Quantidade"
                    itemView.findViewById<TextView>(R.id.tvValue).text = row.data.qtd.toString()

                    itemView.findViewById<Button>(R.id.btnEdit).setOnClickListener {
                        onEdit?.invoke(RendaRow(row.data))
                    }

                    itemView.findViewById<ImageView>(R.id.ivDelete).setOnClickListener {
                        onDelete?.invoke(RendaRow(row.data))
                    }
                }

                is FundsRow -> {
                    itemView.findViewById<TextView>(R.id.tvName).text = "Nome do Fundo"
                    itemView.findViewById<TextView>(R.id.tvNameValue).text = row.data.name
                    itemView.findViewById<TextView>(R.id.tvSubtitle).text = "Código"
                    itemView.findViewById<TextView>(R.id.tvValue).text = row.data.code

                    itemView.findViewById<Button>(R.id.btnEdit).setOnClickListener {
                        onEdit?.invoke(FundsRow(row.data))
                    }

                    itemView.findViewById<ImageView>(R.id.ivDelete).setOnClickListener {
                        onDelete?.invoke(FundsRow(row.data))
                    }
                }
            }

        }
    }
}