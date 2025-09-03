package com.chslcompany.estudos.status

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.estudos.R
import com.chslcompany.estudos.adapters.InvestmentsAdapter
import com.google.android.material.tabs.TabLayout


class StatusFragment : Fragment(), OrderingFragment.OnOrderingSelectedListener {

    private lateinit var tabLayout: TabLayout
    private lateinit var investAdapter: InvestmentsAdapter
    private lateinit var tvOrdering: AppCompatTextView
    private lateinit var button: Button
    private var currentType = TYPE_TODOS
    private var orderingSelected = ""

    // 1. Defina a interface para comunicação
    interface OnFragmentInteractionListener {
        fun onNavigateToFragmentB()
    }

    private var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Certifica de que a Activity implementa a interface
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context deve implementar OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)
        setupListeners()
    }

    private fun setupViews(view: View) {
        tabLayout = view.findViewById<TabLayout>(R.id.tabInvestments)

        tvOrdering = view.findViewById<AppCompatTextView>(R.id.tvOrdering)

        button = view.findViewById<Button>(R.id.button)

        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        investAdapter = InvestmentsAdapter(getStatusList())
        val myRecyclerView: RecyclerView = view.findViewById(R.id.rvInvestments)
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        myRecyclerView.adapter = investAdapter
    }

    private fun setupListeners() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentType = when (tab.position) {
                    1 -> TYPE_ENTRADA
                    2 -> TYPE_SAIDA
                    else -> TYPE_TODOS
                }
                showUpdateScreen()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        tvOrdering.setOnClickListener {
//            val fragment = OrderingFragment()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container_fragment, fragment)
//                .addToBackStack(null)
//                .commit()
            listener?.onNavigateToFragmentB()
        }

    }

    fun getStatusList(): List<InvestmentsStatus> = listOf(
        InvestmentsStatus(1, "Banco do Brasil", "Portabilidade nº 3454646464", "ENTRADA"),
        InvestmentsStatus(1030, "Santander", "Portabilidade nº 34535345643095345", "SAIDA"),
        InvestmentsStatus(13400, "Inter", "Portabilidade nº 34546464d345353564", "ENTRADA"),
        InvestmentsStatus(
            3433,
            "Santander",
            "Portabilidade nº 34535334535353535345643095345",
            "ENTRADA"
        ),
        InvestmentsStatus(2400, "Nubank", "Portabilidade nº 00043535343", "SAIDA"),
        InvestmentsStatus(2, "XP", "Portabilidade nº 4325533533345", "SAIDA"),
    )

    override fun onOrderingSelected(orderingSelected: String) {
        this.orderingSelected = orderingSelected
        tvOrdering.text = this.orderingSelected
        showUpdateScreen()
    }

    private fun getFilteredAndSortedList(): List<InvestmentsStatus> {
        val filtered = when (currentType) {
            TYPE_ENTRADA -> getStatusList().filter { it.type == TYPE_ENTRADA }
            TYPE_SAIDA -> getStatusList().filter { it.type == TYPE_SAIDA }
            else -> getStatusList()
        }

        return when (orderingSelected) {
            ORDER_RECENT -> filtered.sortedByDescending { it.id }
            ORDER_OLD -> filtered.sortedBy { it.id }
            else -> filtered
        }
    }


    private fun showUpdateScreen() {
        investAdapter.updateList(getFilteredAndSortedList())
        button.visibility =
            if (currentType == TYPE_ENTRADA) View.VISIBLE else View.GONE
    }


    companion object {
        private const val TYPE_ENTRADA = "ENTRADA"
        private const val TYPE_SAIDA = "SAIDA"
        private const val TYPE_TODOS = "TODOS"
        private const val ORDER_RECENT = "Mais recentes"
        private const val ORDER_OLD = "Mais antigas"
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


}