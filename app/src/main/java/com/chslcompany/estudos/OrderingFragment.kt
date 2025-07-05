package com.chslcompany.estudos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView


class OrderingFragment : Fragment() {

    interface OnOrderingSelectedListener {
        fun onOrderingSelected(orderingSelected: String)
    }

    private var listener: OnOrderingSelectedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? OnOrderingSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ordering, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<AppCompatTextView>(R.id.tvOption1).setOnClickListener {
            listener?.onOrderingSelected("Mais recentes")
            parentFragmentManager.popBackStack()
        }

        view.findViewById<AppCompatTextView>(R.id.tvOption2).setOnClickListener {
            listener?.onOrderingSelected("Mais antigas")
            parentFragmentManager.popBackStack()
        }
    }

}