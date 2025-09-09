package com.chslcompany.estudos.ativos.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.estudos.ADDED_MODE_KEY
import com.chslcompany.estudos.R
import com.chslcompany.estudos.RESULT_SUCCESS
import com.chslcompany.estudos.adapters.ActiveAdapter
import com.chslcompany.estudos.adapters.Actives
import com.chslcompany.estudos.ativos.util.BaseFragment
import com.chslcompany.estudos.ativos.viewmodel.ActiveViewModel
import kotlinx.coroutines.launch

class SendInvestmentFragment : BaseFragment() {

    private lateinit var myAdapter: ActiveAdapter
    private lateinit var tvCountValue: TextView
    private lateinit var btnContinue: Button
    private lateinit var btnSeeMore: Button
    private var activeSize: Int = 0
    private val viewModel: ActiveViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_send_investment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnContinue = view.findViewById<Button>(R.id.btnContinue)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)
        val rvActives = view.findViewById<RecyclerView>(R.id.rvActives)
        tvCountValue = view.findViewById<TextView>(R.id.tvCountValue)
        btnSeeMore = view.findViewById<Button>(R.id.btnSeeMore)

        setupListeners()
        setupAdapter(rvActives)

        btnSeeMore.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    activeSize = viewModel.getInvestmentCount() ?: 0
                    if (activeSize > 0) {
                        val fragment = SeeMoreFragment()
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }

        btnContinue.setOnClickListener {

        }

        btnCancel.setOnClickListener {
            tvCountValue.text = 0.toString()
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.setEmptyList()
                }
            }

            parentFragmentManager.popBackStack(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )

        }


    }

    private fun setupListeners() {
        parentFragmentManager.setFragmentResultListener(
            ADDED_MODE_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            if (bundle.getBoolean(RESULT_SUCCESS)) {
                lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        activeSize = viewModel.getInvestmentCount() ?: 0
                        tvCountValue.text = activeSize.toString()
                        if (activeSize >= 1) {
                            btnContinue.isEnabled = true
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter(
        rvActives: RecyclerView
    ) {
        val items = listOf(
            Actives("Renda Fixa"),
            Actives("Fundos")
        )
        myAdapter = ActiveAdapter(items).also {
            it.onItemClick = { item ->
                if (item == "Renda Fixa") {
                    val fragment = RendaFixaFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                } else {
                    val fragment = FundoFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

        }
        rvActives.adapter = myAdapter
        rvActives.layoutManager = LinearLayoutManager(requireContext())
    }


}