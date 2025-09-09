package com.chslcompany.estudos.ativos.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.chslcompany.estudos.ADDED_MODE_KEY
import com.chslcompany.estudos.EDITED_MODE_KEY
import com.chslcompany.estudos.EDIT_BUNDLE_KEY
import com.chslcompany.estudos.R
import com.chslcompany.estudos.RESULT_SUCCESS
import com.chslcompany.estudos.ativos.util.BaseFragment
import com.chslcompany.estudos.ativos.viewmodel.ActiveViewModel
import com.chslcompany.estudos.model.RendaFixaActive

class RendaFixaFragment : BaseFragment() {

    private lateinit var edtRendaFixa: EditText
    private lateinit var edtQtd: EditText
    private lateinit var btnIncluir: Button
    private var isEditMode = false
    private val viewModel: ActiveViewModel by viewModels { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_renda_fixa, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtRendaFixa = view.findViewById<EditText>(R.id.edtRendaFixa)
        edtQtd = view.findViewById<EditText>(R.id.edtQtd)
        btnIncluir = view.findViewById<Button>(R.id.btnIncluir)
        setupTextWatcher()

        arguments?.let {
            isEditMode = it.getBoolean(EDIT_BUNDLE_KEY)
        }

        if (isEditMode) {
            val rendaFixaActive = viewModel.getRendaFixaSelected()
            edtRendaFixa.setText(rendaFixaActive?.name)
            edtQtd.setText(rendaFixaActive?.qtd.toString())
        }
    }

    private fun setupTextWatcher() {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                updateButtonState()
            }
        }
        edtRendaFixa.addTextChangedListener(watcher)
        edtQtd.addTextChangedListener(watcher)
    }

    private fun updateButtonState() {
        if (!edtRendaFixa.text.isNullOrBlank() && !edtQtd.text.isNullOrBlank()) {
            btnIncluir.isEnabled = true
            btnIncluir.setOnClickListener {
                notifyWhenAdded()
                val rendaFixaActive = RendaFixaActive(
                    name = edtRendaFixa.text.toString(),
                    qtd = edtQtd.text.toString().toInt()
                )
                viewModel.saveRendaFixa(isEditMode, rendaFixaActive)
                val fragment = SendInvestmentFragment()
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        } else {
            btnIncluir.isEnabled = false
        }
    }

    private fun notifyWhenAdded(){
        when{
            isEditMode -> {
                setFragmentResult(
                    EDITED_MODE_KEY,
                    bundleOf(RESULT_SUCCESS to true)
                )
            }
            else -> {
                setFragmentResult(
                    ADDED_MODE_KEY,
                    bundleOf(RESULT_SUCCESS to true)
                )
            }
        }
    }

}



