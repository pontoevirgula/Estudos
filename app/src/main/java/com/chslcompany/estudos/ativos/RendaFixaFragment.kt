package com.chslcompany.estudos.ativos

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.chslcompany.estudos.ADDED_MODE_KEY
import com.chslcompany.estudos.EDITED_MODE_KEY
import com.chslcompany.estudos.MyPreferences
import com.chslcompany.estudos.R
import com.chslcompany.estudos.RESULT_SUCCESS

class RendaFixaFragment : Fragment() {

    private lateinit var myPreferences: MyPreferences
    private lateinit var useCase: ActiveUseCase
    private lateinit var viewModel: ActiveViewModel
    private lateinit var edtRendaFixa: EditText
    private lateinit var edtQtd: EditText
    private lateinit var btnIncluir: Button
    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_renda_fixa, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val applicationContext: Context = requireContext().applicationContext
        myPreferences = MyPreferences(applicationContext)
        useCase = ActiveUseCase(myPreferences)
        viewModel = ActiveViewModel(useCase)

        edtRendaFixa = view.findViewById<EditText>(R.id.edtRendaFixa)
        edtQtd = view.findViewById<EditText>(R.id.edtQtd)
        btnIncluir = view.findViewById<Button>(R.id.btnIncluir)

        setupTextWatcher()
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
                viewModel.saveRendaFixa(false, rendaFixaActive)
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



