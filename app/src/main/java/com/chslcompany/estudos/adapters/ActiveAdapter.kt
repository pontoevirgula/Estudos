package com.chslcompany.estudos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.estudos.R

class ActiveAdapter(
    private var items : List<Actives>,
) : RecyclerView.Adapter<ActiveAdapter.ViewHolder>() {

    var onItemClick : ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvActive = itemView.findViewById<TextView>(R.id.tvActive)

        fun bind(item : Actives){
            tvActive.text = item.descricao
            itemView.setOnClickListener {
                onItemClick?.invoke(item.descricao)
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_active, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

data class Actives (
  val descricao : String
)
