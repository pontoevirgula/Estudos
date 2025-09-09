package com.chslcompany.estudos.ativos.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.estudos.EDIT_BUNDLE_KEY
import com.chslcompany.estudos.R
import com.chslcompany.estudos.adapters.SeeMoreAdapter
import com.chslcompany.estudos.adapters.SeeMoreAdapter.RendaRow
import com.chslcompany.estudos.ativos.util.BaseFragment
import com.chslcompany.estudos.ativos.util.InvestmentType
import com.chslcompany.estudos.ativos.viewmodel.ActiveViewModel
import com.chslcompany.estudos.model.ActiveProduct
import kotlinx.coroutines.launch

class SeeMoreFragment : BaseFragment() {

    private lateinit var activesIncluded: TextView
    private val viewModel: ActiveViewModel by viewModels { viewModelFactory }
    private var list: List<ActiveProduct> = emptyList()
    private lateinit var adapter: SeeMoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_see_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activesIncluded = view.findViewById<TextView>(R.id.tvCountValue)
        showScreen()
    }

    private fun showScreen() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                list = viewModel.getAllInvestmentsSaved() ?: emptyList()
                activesIncluded.text = viewModel.getInvestmentCount().toString()
                val rvActives = view?.findViewById<RecyclerView>(R.id.rvActives)
                rvActives?.layoutManager = LinearLayoutManager(requireContext())
                adapter = SeeMoreAdapter()
                rvActives?.adapter = adapter
                adapter.submitInvestments(list)


                adapter.onEditCallback = { row ->
                    Bundle().run {
                        putBoolean(EDIT_BUNDLE_KEY, true)
                        when (row) {
                            is RendaRow -> {
                                viewModel.saveRendaFixaSelected(
                                    row.data,
                                    InvestmentType.FIXED_INCOME
                                )
                                val fragment = RendaFixaFragment()
                                fragment.arguments = this
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack(null)
                                    .commit()
                            }

                            is SeeMoreAdapter.FundsRow -> {
                                viewModel.saveFundsSelected(row.data, InvestmentType.FUNDS)
                                val fragment = FundoFragment()
                                fragment.arguments = this
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, fragment)
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }
                    }
                }
            }
        }

    }

}