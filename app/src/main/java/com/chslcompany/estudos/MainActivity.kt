package com.chslcompany.estudos

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity(), OrderingFragment.OnOrderingSelectedListener {
    private lateinit var tabLayout: TabLayout
    private lateinit var investAdapter: InvestmentsAdapter
    private lateinit var tvOrdering: AppCompatTextView
    private lateinit var button: Button
    private var currentType = TYPE_TODOS
    private var orderingSelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        tabLayout = findViewById<TabLayout>(R.id.tabInvestments)

        tvOrdering = findViewById<AppCompatTextView>(R.id.tvOrdering)

        button = findViewById<Button>(R.id.button)

       setupRecyclerView()
    }

    private fun setupRecyclerView() {
        investAdapter = InvestmentsAdapter(getStatusList())
        val myRecyclerView: RecyclerView = findViewById(R.id.rvInvestments)
        myRecyclerView.layoutManager = LinearLayoutManager(this)
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
            val fragment = OrderingFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }

    }


    fun getStatusList(): List<InvestmentsStatus> = listOf(
        InvestmentsStatus(1,"Banco do Brasil", "Portabilidade nº 3454646464", "ENTRADA"),
        InvestmentsStatus(1030,"Santander", "Portabilidade nº 34535345643095345","SAIDA"),
        InvestmentsStatus(13400,"Inter", "Portabilidade nº 34546464d345353564", "ENTRADA"),
        InvestmentsStatus(3433,"Santander", "Portabilidade nº 34535334535353535345643095345","ENTRADA"),
        InvestmentsStatus(2400,"Nubank", "Portabilidade nº 00043535343","SAIDA"),
        InvestmentsStatus(2,"XP", "Portabilidade nº 4325533533345","SAIDA"),
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



}